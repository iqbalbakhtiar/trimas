package com.siriuserp.tools.service;

import java.util.Base64;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.security.util.Sha512DigestUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dao.LocaleDao;
import com.siriuserp.sdk.dao.PartyDao;
import com.siriuserp.sdk.dao.RoleDao;
import com.siriuserp.sdk.dao.UserDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.dm.DisplayConfiguration;
import com.siriuserp.sdk.dm.FlagLevel;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.Profile;
import com.siriuserp.sdk.dm.User;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.DateHelper;
import com.siriuserp.sdk.utility.FormHelper;
import com.siriuserp.sdk.utility.QueryFactory;
import com.siriuserp.sdk.utility.SiriusValidator;
import com.siriuserp.sdk.utility.UserHelper;
import com.siriuserp.tools.form.ToolForm;

import javolution.util.FastMap;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class UserService implements UserDetailsService
{
	@Autowired
	private UserDao userDao;

	@Autowired
	private PartyDao partyDao;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private LocaleDao localeDao;

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private ProfileService profileService;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("users", FilterAndPaging.filter(userDao, QueryFactory.create(filterCriteria, queryclass)));
		map.put("filterCriteria", filterCriteria);

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preadd()
	{
		ToolForm form = new ToolForm();
		form.setFlagLevel(FlagLevel.USERLEVEL);

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("roles", roleDao.loadAllByMandatory(false));
		map.put("persons", partyDao.loadAllNoUser());
		map.put("user_form", form);

		return map;
	}

	@AuditTrails(className = User.class, actionType = AuditTrailsActionType.CREATE)
	public void add(User user) throws ServiceException
	{
		Profile profile = new Profile();
		profile.setCreatedBy(genericDao.load(Party.class, UserHelper.activePerson().getId()));
		profile.setCreatedDate(DateHelper.now());
		profile.setLocale(localeDao.loadDefault());
		profile.setRowperpage(20);

		DisplayConfiguration displayConfiguration = genericDao.load(DisplayConfiguration.class, Long.valueOf(1));
		if (displayConfiguration != null)
		{
			profile.setLocale(displayConfiguration.getLocale());
			profile.setRowperpage(displayConfiguration.getRow());
		}

		user.setProfile(profile);
		user.setPassword(Sha512DigestUtils.shaHex(user.getUsername()));

		if (SiriusValidator.validateParam(user.getForm().getCode()))
			user.setPassword(Sha512DigestUtils.shaHex(Base64.getDecoder().decode(user.getForm().getCode())));

		genericDao.add(user);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public User load(Long id)
	{
		return genericDao.load(User.class, id);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(Long id) throws Exception
	{
		ToolForm form = FormHelper.bind(ToolForm.class, load(id));

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("user_form", form);
		map.put("user_edit", form.getUser());
		map.put("roles", roleDao.loadAllByMandatory(false));

		return map;
	}

	@AuditTrails(className = User.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(User user) throws ServiceException
	{
		String oldpassword = user.getForm().getLegend();
		String newpassword = user.getForm().getReason();

		if (SiriusValidator.validateParam(oldpassword))
		{
			if (!Sha512DigestUtils.shaHex(Base64.getDecoder().decode(oldpassword)).equals(user.getPassword()))
				throw new ServiceException("Change Password fail, User typed old password not equal with current password.");

			user.setPassword(Sha512DigestUtils.shaHex(Base64.getDecoder().decode(newpassword)));
		}

		if (SiriusValidator.validateParam(user.getForm().getCode()))
		{
			if (!user.getForm().getCode().equals(user.getPassword()))
				throw new ServiceException("Change Password fail, User typed old password not equal with current password.");

			user.setPassword(Sha512DigestUtils.shaHex(Base64.getDecoder().decode(newpassword)));
		}

		genericDao.clear();
		genericDao.update(user);

		profileService.reloadUser();
	}

	@AuditTrails(className = User.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(User user) throws ServiceException
	{
		userDao.delete(user);
	}

	@AuditTrails(className = User.class, actionType = AuditTrailsActionType.UPDATE)
	public void reset(User user) throws ServiceException
	{
		if (user != null)
		{
			user.setPassword(Sha512DigestUtils.shaHex(user.getUsername()));
			genericDao.clear();
			genericDao.update(user);
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException
	{
		User user = userDao.loadByName(username);

		if (user != null)
		{
			Hibernate.initialize(user.getRole());
			Hibernate.initialize(user.getProfile());
			Hibernate.initialize(user.getPerson());

			if (user.getProfile() != null)
				Hibernate.initialize(user.getProfile().getOrganization());
		}

		return user;
	}
}

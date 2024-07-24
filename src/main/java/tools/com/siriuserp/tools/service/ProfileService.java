/**
 * Dec 22, 2008 4:01:40 PM
 * com.siriuserp.tools.service
 * ProfileService.java
 */
package com.siriuserp.tools.service;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.dashboard.service.QuickSearchService;
import com.siriuserp.sdk.dao.AccessibleModuleDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dm.AccessibleModule;
import com.siriuserp.sdk.dm.Locale;
import com.siriuserp.sdk.dm.ModuleDetail;
import com.siriuserp.sdk.dm.ModuleDetailType;
import com.siriuserp.sdk.dm.Profile;
import com.siriuserp.sdk.dm.UrlCache;
import com.siriuserp.sdk.dm.User;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.utility.SessionHelper;
import com.siriuserp.sdk.utility.UserHelper;

import javolution.util.FastList;
import javolution.util.FastMap;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class ProfileService
{
	@Autowired
	private GenericDao genericDao;

	@Autowired
	private AccessibleModuleDao accessibleModuleDao;

	@Autowired
	private UserService userService;

	@Autowired
	private QuickSearchService quickSearchService;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit() throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("locales", genericDao.loadAll(Locale.class));

		User user = userService.load(UserHelper.activeUser().getId());
		if (user != null)
		{
			Profile profile = user.getProfile();
			if (profile != null)
				map.put("profile", genericDao.load(Profile.class, profile.getId()));
			else
			{
				profile = new Profile();

				user.setProfile(profile);
				userService.edit(user);

				map.put("profile", profile);
			}
		}

		return map;
	}

	public void edit(Profile profile, HttpSession session) throws ServiceException
	{
		genericDao.update(profile);

		session.setAttribute(SessionHelper.PROFILE_KEY, profile);
		SessionHelper.refreshLocale(session, profile);

		User user = genericDao.load(User.class, profile.getUser().getId());
		intializeUser(user, profile.getLocale().getCode());
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public void intializeUser(User user, String locale) throws ServiceException
	{
		user.getUrls().clear();
		user.getMenus().clear();

		if (user.getUrls().isEmpty())
		{
			for (AccessibleModule accessibleModule : accessibleModuleDao.loadAll(user.getRole().getId()))
			{
				Hibernate.initialize(accessibleModule.getRole());
				Hibernate.initialize(accessibleModule.getAccessType());

				String name = accessibleModule.getModule().getMenus().get(locale);

				UrlCache cache = new UrlCache();
				cache.setAccessType(accessibleModule.getAccessType());
				cache.setCode(accessibleModule.getModule().getCode());
				cache.setName(name);
				cache.setDetailType(ModuleDetailType.READ);
				cache.setRole(accessibleModule.getRole().getName());

				user.getUrls().put(accessibleModule.getModule().getDefaultUri(), cache);

				List<String> accesses = new FastList<String>();
				if (accessibleModule.getAccessType().getRank() > 1)
					accesses.addAll((List<String>) Arrays.asList(accessibleModule.getAccessType().getName().split(",")));
				else
					accesses.addAll(EnumSet.allOf(ModuleDetailType.class).stream().map(type -> type.toString()).collect(Collectors.toList()));

				for (ModuleDetail detail : accessibleModule.getModule().getDetails())
					if (accesses.contains(detail.getDetailType().toString()))
					{
						UrlCache url = new UrlCache();
						url.setAccessType(accessibleModule.getAccessType());
						url.setCode(accessibleModule.getModule().getCode());
						url.setName(name);
						url.setRole(accessibleModule.getRole().getName());
						url.setDetailType(detail.getDetailType());

						user.getUrls().put(detail.getUri(), url);
					}

				if (accessibleModule.isEnabled())
					user.getMenus().put(cache.getCode().trim(), cache.getName() + " > " + accessibleModule.getModule().getDefaultUri());
			}

			reloadUser();
		}
	}

	public void reloadUser() throws ServiceException
	{
		User user = UserHelper.activeUser();
		user.getAccess().clear();

		user.getAccess().put("orgs", accessibleModuleDao.getAccessibleOrgs(user.getRole().getId()));
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Profile loadProfile() throws ServiceException
	{
		if (UserHelper.activeUser() != null)
		{
			User user = userService.load(UserHelper.activeUser().getId());
			if (user != null && user.getProfile() != null)
				return user.getProfile();
		}

		return null;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Set<Map<String, Object>> loadDashBoard(User user) throws ServiceException
	{
		return user.getUrls().values().stream().filter(cache -> cache.getName().toLowerCase().contains("dashboard")).map(cache -> quickSearchService.dashboard(cache)).collect(Collectors.toSet());
	}
}

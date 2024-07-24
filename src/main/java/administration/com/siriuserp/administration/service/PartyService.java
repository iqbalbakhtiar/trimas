/**
 * Nov 3, 2008 9:48:11 AM
 * com.siriuserp.administration.service
 * PartyService.java
 */
package com.siriuserp.administration.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.base.Service;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dao.PartyDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.dm.UrlCache;
import com.siriuserp.sdk.dm.User;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.PathHelper;
import com.siriuserp.sdk.utility.QueryFactory;
import com.siriuserp.sdk.utility.UserHelper;

import javolution.util.FastMap;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class PartyService extends Service
{
	@Autowired
	private PartyDao partyDao;

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private CodeSequenceDao codeSequenceDao;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("partys", FilterAndPaging.filter(partyDao, QueryFactory.create(filterCriteria, queryclass)));
		map.put("filterCriteria", filterCriteria);

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preadd()
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("party_add", new Party());

		return map;
	}

	@AuditTrails(className = Party.class, actionType = AuditTrailsActionType.CREATE)
	public void add(Party party, MultipartFile file) throws Exception
	{
		party.setCode(GeneratorHelper.instance().generate(TableType.PARTY, codeSequenceDao));

		if (!file.isEmpty())
		{
			String[] _name = file.getOriginalFilename().split("[.]");
			file.transferTo(new File(PathHelper.SERVLET_PATH + "//static//party//" + party.getCode() + "." + _name[_name.length - 1]));

			party.setPicture("party/" + party.getCode() + "." + _name[_name.length - 1]);
		}
		partyDao.add(party);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(String id) throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		User user = UserHelper.activeUser();
		UrlCache partyAccess = user.getUrls().get("/page/partyrelationshipview.htm");

		Party party = load(Long.valueOf(id));
		map.put("relAccess", partyAccess != null ? partyAccess.getAccessType() : null);
		map.put("party_edit", party);

		return map;
	}

	@AuditTrails(className = Party.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(Party party, MultipartFile file) throws Exception
	{
		if (file != null && !file.isEmpty())
		{
			String[] _name = file.getOriginalFilename().split("[.]");
			File current = new File(PathHelper.SERVLET_PATH + "//static//party//" + party.getCode() + "." + _name[_name.length - 1]);

			if (current.exists())
				file.transferTo(current);

			party.setPicture("party/" + party.getCode() + "." + _name[_name.length - 1]);
		}

		partyDao.update(party);
	}

	@AuditTrails(className = Party.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(Party party) throws ServiceException
	{
		genericDao.delete(party);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Party load(Long id)
	{
		return genericDao.load(Party.class, id);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public boolean exist(String firstName, String middleName, String lastName)
	{
		Party out = partyDao.load(firstName, middleName, lastName);
		if (out != null)
			return true;

		return false;
	}
}

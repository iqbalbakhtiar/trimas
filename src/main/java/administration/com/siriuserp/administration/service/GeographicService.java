/**
 * Mar 14, 2008 4:46:40 PM
 * com.siriuserp.administration.service
 * CountryService.java
 */
package com.siriuserp.administration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.administration.dm.Geographic;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.base.Service;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dao.GeographicDao;
import com.siriuserp.sdk.dao.GeographicTypeDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.DateHelper;
import com.siriuserp.sdk.utility.QueryFactory;

import javolution.util.FastMap;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class GeographicService extends Service
{
	@Autowired
	private GeographicDao geographicDao;

	@Autowired
	private GeographicTypeDao geographicTypeDao;

	@Autowired
	private GenericDao genericDao;

	@Transactional(readOnly = true)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("geographics", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));
		map.put("filterCriteria", filterCriteria);

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> viewJson(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("geographics", genericDao.filter(QueryFactory.create(filterCriteria, queryclass)));

		return map;
	}

	@Transactional(readOnly = true)
	public FastMap<String, Object> preadd()
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("geographic_add", new Geographic());
		map.put("types", geographicTypeDao.loadAll(true));

		return map;
	}

	@AuditTrails(className = Geographic.class, actionType = AuditTrailsActionType.CREATE)
	public void add(Geographic geographic) throws ServiceException
	{
		geographic.setCreatedBy(getPerson());
		geographic.setCreatedDate(DateHelper.now());

		geographicDao.add(geographic);
	}

	@Transactional(readOnly = true)
	public FastMap<String, Object> preedit(String id)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("geographic_edit", load(id));
		map.put("types", geographicTypeDao.loadAll(true));

		return map;
	}

	@AuditTrails(className = Geographic.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(Geographic geographic) throws ServiceException
	{
		geographic.setUpdatedBy(getPerson());
		geographic.setUpdatedDate(DateHelper.now());

		geographicDao.update(geographic);
	}

	@AuditTrails(className = Geographic.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(Geographic geographic) throws ServiceException
	{
		geographicDao.delete(geographic);
	}

	@Transactional(readOnly = true)
	public Geographic load(String id)
	{
		return genericDao.load(Geographic.class, Long.valueOf(id));
	}
}

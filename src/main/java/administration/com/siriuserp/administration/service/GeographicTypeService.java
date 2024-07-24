/**
 * Feb 2, 2009 11:09:59 AM
 * com.siriuserp.administration.service
 * GeographicTypeService.java
 */
package com.siriuserp.administration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.administration.dm.GeographicType;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.exceptions.ServiceException;

import javolution.util.FastMap;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class GeographicTypeService
{
	@Autowired
	private GenericDao genericDao;

	@Transactional(readOnly = true)
	public FastMap<String, Object> view()
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("types", genericDao.loadAll(GeographicType.class));

		return map;
	}

	@Transactional(readOnly = true)
	public FastMap<String, Object> preadd()
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("geographicType_add", new GeographicType());

		return map;
	}

	@Transactional(readOnly = true)
	public FastMap<String, Object> preedit(String id)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("geographicType_edit", load(id));

		return map;
	}

	@Transactional(readOnly = true)
	public GeographicType load(String id)
	{
		return genericDao.load(GeographicType.class, Long.valueOf(id));
	}

	@AuditTrails(className = GeographicType.class, actionType = AuditTrailsActionType.CREATE)
	public void add(GeographicType geographicType) throws ServiceException
	{
		genericDao.add(geographicType);
	}

	@AuditTrails(className = GeographicType.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(GeographicType geographicType) throws ServiceException
	{
		genericDao.update(geographicType);
	}

	@AuditTrails(className = GeographicType.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(GeographicType geographicType) throws ServiceException
	{
		genericDao.delete(geographicType);
	}
}

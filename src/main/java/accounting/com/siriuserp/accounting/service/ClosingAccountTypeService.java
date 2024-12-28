/**
 * Nov 27, 2008 11:19:02 AM
 * com.siriuserp.accounting.service
 * ClosingAccountService.java
 */
package com.siriuserp.accounting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.accounting.dao.ClosingAccountDao;
import com.siriuserp.accounting.dm.ClosingAccountType;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;

import javolution.util.FastMap;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class ClosingAccountTypeService
{
	@Autowired
	private GenericDao genericDao;

	@Autowired
	private ClosingAccountDao closingAccountDao;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria criteria)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("closingAccountTypes", genericDao.loadAll(ClosingAccountType.class));

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preadd()
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("closingAccountType_add", new ClosingAccountType());

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(String id)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("closingAccountType_edit", load(id));

		return map;
	}

	@AuditTrails(className = ClosingAccountType.class, actionType = AuditTrailsActionType.CREATE)
	public void add(ClosingAccountType closingAccountType) throws ServiceException
	{
		genericDao.add(closingAccountType);
	}

	@AuditTrails(className = ClosingAccountType.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(ClosingAccountType closingAccountType) throws ServiceException
	{
		genericDao.update(closingAccountType);
	}

	@AuditTrails(className = ClosingAccountType.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(ClosingAccountType closingAccountType) throws ServiceException
	{
		closingAccountDao.deleteByType(closingAccountType.getId());
		genericDao.delete(closingAccountType);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public ClosingAccountType load(String id)
	{
		return genericDao.load(ClosingAccountType.class, Long.valueOf(id));
	}
}

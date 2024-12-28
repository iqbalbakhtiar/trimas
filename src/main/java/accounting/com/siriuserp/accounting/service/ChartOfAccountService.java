package com.siriuserp.accounting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.accounting.dm.ChartOfAccount;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.utility.QueryFactory;

import javolution.util.FastMap;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class ChartOfAccountService
{
	@Autowired
	private GenericDao genericDao;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view()
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("chartOfAccounts", genericDao.loadAll(ChartOfAccount.class));

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("charts", genericDao.filter(QueryFactory.create(filterCriteria, queryclass)));

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preadd()
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("chartOfAccount", new ChartOfAccount());

		return map;
	}

	@AuditTrails(className = ChartOfAccount.class, actionType = AuditTrailsActionType.CREATE)
	public void add(ChartOfAccount chartOfAccount) throws ServiceException
	{
		genericDao.add(chartOfAccount);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(String id)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("chartOfAccount", load(id));

		return map;
	}

	@AuditTrails(className = ChartOfAccount.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(ChartOfAccount chartOfAccount) throws ServiceException
	{
		genericDao.update(chartOfAccount);
	}

	@AuditTrails(className = ChartOfAccount.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(ChartOfAccount chartOfAccount) throws ServiceException
	{
		genericDao.delete(chartOfAccount);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public ChartOfAccount load(String id)
	{
		return genericDao.load(ChartOfAccount.class, Long.valueOf(id));
	}
}

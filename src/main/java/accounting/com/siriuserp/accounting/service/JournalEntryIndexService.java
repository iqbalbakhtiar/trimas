/**
 * Nov 12, 2008 10:21:56 AM
 * com.siriuserp.accounting.service
 * JournalEntryIndexService.java
 */
package com.siriuserp.accounting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.accounting.dm.IndexType;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.QueryFactory;

import javolution.util.FastMap;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class JournalEntryIndexService
{
	@Autowired
	private GenericDao genericDao;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view()
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("indexs", genericDao.loadAll(IndexType.class));

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> voucher(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("filterCriteria", filterCriteria);
		map.put("vouchers", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preadd()
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("journalEntryIndex_add", new IndexType());

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(String id)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("journalEntryIndex_edit", load(id));

		return map;
	}

	@AuditTrails(className = IndexType.class, actionType = AuditTrailsActionType.CREATE)
	public void add(IndexType journalEntryIndex) throws ServiceException
	{
		genericDao.add(journalEntryIndex);
	}

	@AuditTrails(className = IndexType.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(IndexType journalEntryIndex) throws ServiceException
	{
		genericDao.update(journalEntryIndex);
	}

	@AuditTrails(className = IndexType.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(IndexType journalEntryIndex) throws ServiceException
	{
		genericDao.delete(journalEntryIndex);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public IndexType load(String id)
	{
		return genericDao.load(IndexType.class, Long.valueOf(id));
	}
}

/**
 * 
 */
package com.siriuserp.inventory.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.inventory.criteria.OnHandQuantityFilterCriteria;
import com.siriuserp.inventory.dao.InventoryItemDao;
import com.siriuserp.inventory.dm.InventoryItem;
import com.siriuserp.inventory.dm.Product;
import com.siriuserp.inventory.query.OnHandQuantityDetailViewQuery;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.QueryFactory;

import javolution.util.FastMap;

/**
 * @author ferdinand
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class OnHandQuantityService
{
	@Autowired
	private GenericDao genericDao;

	@Autowired
	private InventoryItemDao inventoryItemDao;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws Exception
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("filterCriteria", filterCriteria);
		map.put("onhands", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> preedit(OnHandQuantityFilterCriteria criteria) throws ServiceException
	{
		OnHandQuantityDetailViewQuery query = new OnHandQuantityDetailViewQuery();
		query.setFilterCriteria(criteria);

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("details", genericDao.filter(query));
		map.put("product", genericDao.load(Product.class, criteria.getProduct()));

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> viewOnHand(OnHandQuantityFilterCriteria criteria) throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("onHand", inventoryItemDao.getOnHand(criteria.getProduct(), criteria.getContainer()));

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public InventoryItem load(Long id)
	{
		return genericDao.load(InventoryItem.class, id);
	}
}

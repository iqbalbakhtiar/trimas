/**
 * Apr 27, 2009 10:37:21 AM
 * com.siriuserp.administration.service
 * GridService.java
 */
package com.siriuserp.administration.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.Grid;
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
public class GridService
{
	@Autowired
	private GenericDao genericDao;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("filterCriteria", filterCriteria);
		map.put("grids", genericDao.filter(QueryFactory.create(filterCriteria, queryclass)));

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> preadd(String id)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();

		Grid grid = new Grid();
		grid.setFacility(genericDao.load(Facility.class, Long.valueOf(id)));

		map.put("grid", grid);

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> preedit(String id)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("grid", load(id));

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Grid load(String id)
	{
		return genericDao.load(Grid.class, Long.valueOf(id));
	}

	@AuditTrails(className = Grid.class, actionType = AuditTrailsActionType.CREATE)
	public void add(Grid grid) throws ServiceException
	{
		genericDao.add(grid);
	}

	@AuditTrails(className = Grid.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(Grid grid) throws ServiceException
	{
		genericDao.update(grid);
	}

	@AuditTrails(className = Grid.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(Grid grid) throws ServiceException
	{
		genericDao.delete(grid);
	}
}

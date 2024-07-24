/**
 * Sep 20, 2006 2:46:44 PM
 * net.konsep.sirius.administration.service
 * ControllerService.java
 */
package com.siriuserp.administration.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.dao.ContainerDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dao.GridDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.ContainerType;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.Grid;
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
public class ContainerService
{
	@Autowired
	private GridDao gridDao;

	@Autowired
	private ContainerDao containerDao;

	@Autowired
	private GenericDao genericDao;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("filterCriteria", filterCriteria);
		map.put("containers", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> preadd(Long id)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();

		Container container = new Container();
		container.setGrid(genericDao.load(Grid.class, Long.valueOf(id)));

		map.put("container", container);
		map.put("types", genericDao.loadAll(ContainerType.class));

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> preaddFromFacility(Long id)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();

		Container container = new Container();
		container.setFacilityId(Long.valueOf(id));

		map.put("container", container);
		map.put("types", genericDao.loadAll(ContainerType.class));

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> preedit(Long id)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("container", load(id));

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Container load(Long id)
	{
		return genericDao.load(Container.class, id);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Container loadDefault(Long facility)
	{
		return containerDao.loadDefaultContainer(facility);
	}

	@AuditTrails(className = Container.class, actionType = AuditTrailsActionType.CREATE)
	public void add(Container container) throws ServiceException
	{
		if (container.getGrid() == null)
		{
			Facility facility = genericDao.load(Facility.class, container.getFacilityId());
			Grid grid = gridDao.loadByFacility(facility.getId());
			if (grid == null)
			{
				Grid newGrid = new Grid();
				newGrid.setCode("GRD" + facility.getCode());
				newGrid.setName(facility.getName() + " (Grid)");
				newGrid.setFacility(facility);
				container.setGrid(newGrid);
				newGrid.getContainers().add(container);

				gridDao.add(newGrid);
			} else
			{
				container.setGrid(grid);
			}
		}

		containerDao.add(container);
	}

	@AuditTrails(className = Container.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(Container container) throws ServiceException
	{
		containerDao.update(container);
	}

	@AuditTrails(className = Container.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(Container container) throws ServiceException
	{
		containerDao.delete(container);
	}
}

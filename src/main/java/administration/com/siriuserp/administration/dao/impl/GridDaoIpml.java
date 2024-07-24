/**
 * Apr 20, 2006
 * GriddaoIpml.java
 */
package com.siriuserp.administration.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.siriuserp.sdk.dao.GridDao;
import com.siriuserp.sdk.db.DaoHelper;
import com.siriuserp.sdk.dm.Grid;
import com.siriuserp.tools.adapter.RoleDetailUIAdapter;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@SuppressWarnings("unchecked")
@Component("gridDao")
public class GridDaoIpml extends DaoHelper<Grid> implements GridDao
{
	@Override
	public Grid loadByFacility(Long facilityId)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("FROM Grid g ");
		builder.append("WHERE g.facility.id =:facilityId");

		Query query = getSession().createQuery(builder.toString());
		query.setMaxResults(1);
		query.setParameter("facilityId", facilityId);

		return (Grid) query.uniqueResult();
	}

	@Override
	public List<RoleDetailUIAdapter> loadIsNotMapped(List<Long> grids)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT new com.siriuserp.tools.adapter.RoleDetailUIAdapter(grid.id, grid.name, grid.name) ");
		builder.append("FROM GridRole grid WHERE grid.organization IS NOT NULL ");
		builder.append(!grids.isEmpty() ? " AND grid.id NOT IN(:grids) " : " ");
		builder.append("ORDER BY grid.name ASC");

		Query query = getSession().createQuery(builder.toString());
		query.setReadOnly(true);
		query.setCacheable(true);

		if (!grids.isEmpty())
			query.setParameterList("grids", grids);

		return query.list();
	}

	@Override
	public List<RoleDetailUIAdapter> loadIsMapped(Long role)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT new com.siriuserp.tools.adapter.RoleDetailUIAdapter(access.enabled, grid.id, grid.name, grid.name, access.id) ");
		builder.append("FROM AccessibleGridRole access JOIN access.gridRole grid WHERE access.role.id =:role ");
		builder.append("ORDER BY grid.name ASC");

		Query query = getSession().createQuery(builder.toString());
		query.setReadOnly(true);
		query.setCacheable(true);
		query.setParameter("role", role);

		return query.list();
	}
}

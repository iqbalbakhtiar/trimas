/**
 * Apr 24, 2009 4:14:09 PM
 * com.siriuserp.administration.dao.impl
 * FailityDaoImpl.java
 */
package com.siriuserp.administration.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.siriuserp.sdk.dao.FacilityDao;
import com.siriuserp.sdk.db.DaoHelper;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.tools.adapter.RoleDetailUIAdapter;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@SuppressWarnings("unchecked")
@Component
public class FacilityDaoImpl extends DaoHelper<Facility> implements FacilityDao
{
	@Override
	public List<RoleDetailUIAdapter> loadIsNotMapped(List<Long> facilities)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT new com.siriuserp.tools.adapter.RoleDetailUIAdapter(facility.id, facility.code, facility.name) ");
		builder.append("FROM Facility facility WHERE facility.facilityType IS NOT NULL ");
		builder.append(!facilities.isEmpty() ? " AND facility.id NOT IN(:facilities) " : " ");
		builder.append("ORDER BY facility.name ASC");

		Query query = getSession().createQuery(builder.toString());
		query.setReadOnly(true);
		query.setCacheable(true);

		if (!facilities.isEmpty())
			query.setParameterList("facilities", facilities);

		return query.list();
	}

	@Override
	public List<RoleDetailUIAdapter> loadIsMapped(Long role)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT new com.siriuserp.tools.adapter.RoleDetailUIAdapter(access.enabled, facility.id, facility.code, facility.name, access.id) ");
		builder.append("FROM AccessibleFacility access JOIN access.facility facility WHERE access.role.id =:role ");
		builder.append("ORDER BY facility.code ASC");

		Query query = getSession().createQuery(builder.toString());
		query.setReadOnly(true);
		query.setCacheable(true);
		query.setParameter("role", role);

		return query.list();
	}
}

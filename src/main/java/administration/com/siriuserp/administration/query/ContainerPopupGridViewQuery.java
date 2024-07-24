/**
 * May 4, 2009 5:22:38 PM
 * com.siriuserp.popup.query
 * ContainerPopupGridViewQuery.java
 */
package com.siriuserp.administration.query;

import org.hibernate.Query;

import com.siriuserp.administration.criteria.ContainerFilterCriteria;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastList;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public class ContainerPopupGridViewQuery extends AbstractGridViewQuery
{
	public Query getQuery(StringBuilder builder, ContainerFilterCriteria filter)
	{
		if (SiriusValidator.validateParam(filter.getOrganization()))
			builder.append("AND container.grid.facility.id IN(SELECT role.facility.id FROM FacilityRole role WHERE role.party.id =:org) ");

		if (SiriusValidator.validateParam(filter.getFacility()))
			builder.append("AND container.grid.facility.id =:facility ");

		if (SiriusValidator.validateParam(filter.getGrid()))
			builder.append("AND container.grid.id =:grid ");

		builder.append("ORDER BY container.grid.facility.id, container.grid.id, container.id ASC");

		Query query = getSession().createQuery(builder.toString());

		if (SiriusValidator.validateParam(filter.getOrganization()))
			query.setParameter("org", filter.getOrganization());

		if (SiriusValidator.validateParam(filter.getFacility()))
			query.setParameter("facility", filter.getFacility());

		if (SiriusValidator.validateParam(filter.getGrid()))
			query.setParameter("grid", filter.getGrid());

		return query;
	}

	@Override
	public Long count()
	{
		ContainerFilterCriteria filter = (ContainerFilterCriteria) getFilterCriteria();

		StringBuilder builder = new StringBuilder();
		builder.append("SELECT COUNT(container) FROM Container container WHERE container.id IS NOT NULL ");

		Query query = getQuery(builder, filter);
		Object object = query.uniqueResult();
		if (object != null)
			return (Long) object;

		return Long.valueOf(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object execute()
	{
		FastList<Container> list = new FastList<Container>();

		ContainerFilterCriteria filter = (ContainerFilterCriteria) getFilterCriteria();

		StringBuilder builder = new StringBuilder();
		builder.append("FROM Container container WHERE container.id IS NOT NULL ");

		Query query = getQuery(builder, filter);
		query.setCacheable(true);
		query.setReadOnly(true);
		query.setFirstResult(filter.start());
		query.setMaxResults(filter.getMax());

		list.addAll(query.list());

		return list;
	}
}

/**
 * Feb 18, 2009 2:12:18 PM
 * com.siriuserp.popup.query
 * FixedAssetGroupPopupGridViewQuery.java
 */
package com.siriuserp.accounting.query;

import org.hibernate.Query;

import com.siriuserp.accounting.criteria.FixedAssetGroupFilterCriteria;
import com.siriuserp.accounting.dm.FixedAssetGroup;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastList;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@SuppressWarnings("unchecked")
public class FixedAssetGroupPopupGridViewQuery extends AbstractGridViewQuery
{
	@Override
	public Long count()
	{
		FixedAssetGroupFilterCriteria filter = (FixedAssetGroupFilterCriteria) getFilterCriteria();

		if (!getAccessibleOrganizations().isEmpty())
		{
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT COUNT(*) FROM FixedAssetGroup group WHERE group.organization.id in(:accessible)");

			if (!filter.getOrganizations().isEmpty())
				builder.append(" AND group.organization.id in (:orgs)");

			if (SiriusValidator.validateParam(filter.getName()))
				builder.append(" AND group.name like '%" + filter.getName() + "%'");

			Query query = getSession().createQuery(builder.toString());
			query.setParameterList("accessible", getAccessibleOrganizations());

			if (!filter.getOrganizations().isEmpty())
				query.setParameterList("orgs", filter.getOrganizations());

			Object object = query.uniqueResult();
			if (object != null)
				return (Long) object;
		}

		return Long.valueOf(0);
	}

	@Override
	public Object execute()
	{
		FastList<FixedAssetGroup> groups = new FastList<FixedAssetGroup>();

		FixedAssetGroupFilterCriteria filter = (FixedAssetGroupFilterCriteria) getFilterCriteria();

		if (!getAccessibleOrganizations().isEmpty())
		{
			StringBuilder builder = new StringBuilder();
			builder.append("FROM FixedAssetGroup group WHERE group.organization.id in(:accessible)");

			if (!filter.getOrganizations().isEmpty())
				builder.append(" AND group.organization.id in (:orgs)");

			if (SiriusValidator.validateParam(filter.getName()))
				builder.append(" AND group.name like '%" + filter.getName() + "%'");

			builder.append(" ORDER BY group.name ASC");

			Query query = getSession().createQuery(builder.toString());
			query.setParameterList("accessible", getAccessibleOrganizations());
			query.setFirstResult(filter.start());
			query.setMaxResults(filter.getMax());

			if (!filter.getOrganizations().isEmpty())
				query.setParameterList("orgs", filter.getOrganizations());

			groups.addAll(query.list());
		}

		return groups;
	}
}

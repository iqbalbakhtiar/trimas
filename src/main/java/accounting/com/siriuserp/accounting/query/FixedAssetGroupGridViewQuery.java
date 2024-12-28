package com.siriuserp.accounting.query;

import org.hibernate.Query;

import com.siriuserp.accounting.criteria.FixedAssetGroupFilterCriteria;
import com.siriuserp.accounting.dm.FixedAssetGroup;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastList;

@SuppressWarnings("unchecked")
public class FixedAssetGroupGridViewQuery extends AbstractGridViewQuery
{
	@Override
	public Long count()
	{
		FixedAssetGroupFilterCriteria filter = (FixedAssetGroupFilterCriteria) getFilterCriteria();

		if (!getAccessibleOrganizations().isEmpty())
		{
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT COUNT(grp) FROM FixedAssetGroup grp WHERE grp.organization.id in(:accessible)");

			if (!filter.getOrganizations().isEmpty())
				builder.append(" AND grp.organization.id in (:orgs)");

			if (SiriusValidator.validateParam(filter.getCode()))
				builder.append(" AND grp.code like '%" + filter.getCode() + "%'");

			if (SiriusValidator.validateParam(filter.getName()))
				builder.append(" AND grp.name like '%" + filter.getName() + "%'");

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
			builder.append("SELECT grp FROM FixedAssetGroup grp WHERE grp.organization.id in(:accessible)");

			if (!filter.getOrganizations().isEmpty())
				builder.append(" AND grp.organization.id in (:orgs)");

			if (SiriusValidator.validateParam(filter.getCode()))
				builder.append(" AND grp.code like '%" + filter.getCode() + "%'");

			if (SiriusValidator.validateParam(filter.getName()))
				builder.append(" AND grp.name like '%" + filter.getName() + "%'");

			builder.append(" ORDER BY grp.name ASC");

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

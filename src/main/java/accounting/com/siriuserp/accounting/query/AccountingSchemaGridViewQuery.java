/**
 * Feb 2, 2009 1:39:12 PM
 * com.siriuserp.accounting.query
 * AccountingSchemaGridViewQuery.java
 */
package com.siriuserp.accounting.query;

import org.hibernate.Query;

import com.siriuserp.accounting.criteria.AccountingSchemaFilterCriteria;
import com.siriuserp.accounting.dm.AccountingSchema;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastList;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@SuppressWarnings("unchecked")
public class AccountingSchemaGridViewQuery extends AbstractGridViewQuery
{
	@Override
	public Long count()
	{
		if (!getAccessibleOrganizations().isEmpty())
		{
			AccountingSchemaFilterCriteria filter = (AccountingSchemaFilterCriteria) filterCriteria;

			StringBuilder builder = new StringBuilder();
			builder.append("SELECT COUNT(schema.id) FROM AccountingSchema schema WHERE schema.organization.id in(:orgs)");

			if (!filter.getOrganizations().isEmpty())
				builder.append("AND schema.organization.id in(:filteredorgs) ");

			if (SiriusValidator.validateParam(filter.getCode()))
				builder.append("AND schema.code like :code ");

			if (SiriusValidator.validateParam(filter.getName()))
				builder.append("AND schema.name like :name ");

			Query query = getSession().createQuery(builder.toString());
			query.setCacheable(true);
			query.setReadOnly(true);
			query.setParameterList("orgs", getAccessibleOrganizations());

			if (!filter.getOrganizations().isEmpty())
				query.setParameterList("filteredorgs", filter.getOrganizations());

			if (SiriusValidator.validateParam(filter.getCode()))
				query.setParameter("code", "%" + filter.getCode() + "%");

			if (SiriusValidator.validateParam(filter.getName()))
				query.setParameter("name", "%" + filter.getName() + "%");

			Object object = query.uniqueResult();
			if (object != null)
				return (Long) object;
		}

		return Long.valueOf(0);
	}

	@Override
	public Object execute()
	{
		FastList<AccountingSchema> list = new FastList<AccountingSchema>();
		if (!getAccessibleOrganizations().isEmpty())
		{
			AccountingSchemaFilterCriteria filter = (AccountingSchemaFilterCriteria) filterCriteria;

			StringBuilder builder = new StringBuilder();
			builder.append("FROM AccountingSchema schema WHERE schema.organization.id in(:orgs)");

			if (!filter.getOrganizations().isEmpty())
				builder.append("AND schema.organization.id in(:filteredorgs) ");

			if (SiriusValidator.validateParam(filter.getCode()))
				builder.append("AND schema.code like :code ");

			if (SiriusValidator.validateParam(filter.getName()))
				builder.append("AND schema.name like :name ");

			Query query = getSession().createQuery(builder.toString());
			query.setCacheable(true);
			query.setReadOnly(true);
			query.setParameterList("orgs", getAccessibleOrganizations());
			query.setFirstResult(filter.start());
			query.setMaxResults(filter.getMax());

			if (!filter.getOrganizations().isEmpty())
				query.setParameterList("filteredorgs", filter.getOrganizations());

			if (SiriusValidator.validateParam(filter.getCode()))
				query.setParameter("code", "%" + filter.getCode() + "%");

			if (SiriusValidator.validateParam(filter.getName()))
				query.setParameter("name", "%" + filter.getName() + "%");

			list.addAll(query.list());
		}

		return list;
	}
}

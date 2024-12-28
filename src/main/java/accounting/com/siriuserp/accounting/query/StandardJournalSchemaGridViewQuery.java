/**
 * Feb 2, 2009 3:59:27 PM
 * com.siriuserp.accounting.query
 * JournalSchemaGridViewQuery.java
 */
package com.siriuserp.accounting.query;

import org.hibernate.Query;

import com.siriuserp.accounting.criteria.StandardJournalSchemaFilterCriteria;
import com.siriuserp.accounting.dm.StandardJournalSchema;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastList;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@SuppressWarnings("unchecked")
public class StandardJournalSchemaGridViewQuery extends AbstractGridViewQuery
{
	@Override
	public Long count()
	{
		if (!getAccessibleOrganizations().isEmpty())
		{
			StandardJournalSchemaFilterCriteria criteria = (StandardJournalSchemaFilterCriteria) filterCriteria;

			StringBuilder builder = new StringBuilder();
			builder.append("SELECT COUNT(journal) FROM StandardJournalSchema journal ");
			builder.append("WHERE journal.organization.id in(:orgs) ");

			if (SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()))
				builder.append("AND journal.organization.id =:org ");

			if (SiriusValidator.validateParam(criteria.getCode()))
				builder.append("AND journal.code like :code ");

			if (SiriusValidator.validateParam(criteria.getName()))
				builder.append("AND journal.name like :name ");

			Query query = getSession().createQuery(builder.toString());
			query.setCacheable(true);
			query.setReadOnly(true);
			query.setParameterList("orgs", getAccessibleOrganizations());

			if (SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()))
				query.setParameter("org", criteria.getOrganization());

			if (SiriusValidator.validateParam(criteria.getCode()))
				query.setParameter("code", "%" + criteria.getCode() + "%");

			if (SiriusValidator.validateParam(criteria.getName()))
				query.setParameter("name", "%" + criteria.getName() + "%");

			Object object = query.uniqueResult();
			if (object != null)
				return (Long) object;
		}

		return Long.valueOf(0);
	}

	@Override
	public Object execute()
	{
		FastList<StandardJournalSchema> list = new FastList<StandardJournalSchema>();

		if (!getAccessibleOrganizations().isEmpty())
		{
			StandardJournalSchemaFilterCriteria criteria = (StandardJournalSchemaFilterCriteria) filterCriteria;

			StringBuilder builder = new StringBuilder();
			builder.append("FROM StandardJournalSchema journal ");
			builder.append("WHERE journal.organization.id in(:orgs) ");

			if (SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()))
				builder.append("AND journal.organization.id =:org ");

			if (SiriusValidator.validateParam(criteria.getCode()))
				builder.append("AND journal.code like :code ");

			if (SiriusValidator.validateParam(criteria.getName()))
				builder.append("AND journal.name like :name ");

			Query query = getSession().createQuery(builder.toString());
			query.setCacheable(true);
			query.setReadOnly(true);
			query.setParameterList("orgs", getAccessibleOrganizations());
			query.setFirstResult(criteria.start());
			query.setMaxResults(criteria.getMax());

			if (SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()))
				query.setParameter("org", criteria.getOrganization());

			if (SiriusValidator.validateParam(criteria.getCode()))
				query.setParameter("code", "%" + criteria.getCode() + "%");

			if (SiriusValidator.validateParam(criteria.getName()))
				query.setParameter("name", "%" + criteria.getName() + "%");

			list.addAll(query.list());
		}

		return list;
	}
}

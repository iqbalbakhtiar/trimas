/**
 * Mar 23, 2009 2:34:33 PM
 * com.siriuserp.popup.controller.query
 * AccountingPeriodPopupGridViewQuery.java
 */
package com.siriuserp.accounting.query;

import org.hibernate.Query;

import com.siriuserp.accounting.criteria.AccountingPeriodFilterCriteria;
import com.siriuserp.accounting.dm.AccountingPeriod;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.dm.Level;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastList;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public class AccountingPeriodPopupGridViewQuery extends AbstractGridViewQuery
{
	public Long count()
	{
		if (!getAccessibleOrganizations().isEmpty())
		{
			AccountingPeriodFilterCriteria filter = (AccountingPeriodFilterCriteria) getFilterCriteria();

			StringBuilder builder = new StringBuilder();
			builder.append("SELECT COUNT(DISTINCT period) FROM AccountingPeriod period,PartyRelationship relation");
			builder.append(" WHERE period.organization.id in(:orgs)");
			builder.append(" AND relation.relationshipType.id = 5");
			builder.append(" AND ");
			builder.append("(relation.fromRole.party.id = period.organization.id OR relation.toRole.party.id = period.organization.id)");
			builder.append(" AND ");
			builder.append("(relation.fromRole.party.id =:org OR relation.toRole.party.id =:org)");

			if (SiriusValidator.validateParam(filter.getLevel()))
				builder.append(" AND period.level =:level");

			if (filter.isOpenonly())
			{
				builder.append(" AND ");
				builder.append("period.status='OPEN'");
			}

			if (filter.getDate() != null)
			{
				builder.append(" AND ");
				builder.append(":date BETWEEN period.startDate AND period.endDate");
			}

			if (SiriusValidator.validateParam(filter.getName()))
			{
				builder.append(" AND ");
				builder.append("(period.code like '%" + filter.getName() + "%'");
				builder.append(" OR ");
				builder.append("period.name like '%" + filter.getName() + "%')");
			}

			Query criteria = getSession().createQuery(builder.toString());
			criteria.setParameter("org", filter.getOrganization());
			criteria.setParameterList("orgs", getAccessibleOrganizations());

			if (SiriusValidator.validateParam(filter.getLevel()))
				criteria.setParameter("level", Level.valueOf(filter.getLevel()));

			if (filter.getDate() != null)
				criteria.setParameter("date", filter.getDate());

			Object object = criteria.uniqueResult();
			if (object != null)
				return (Long) object;
		}

		return Long.valueOf(0);
	}

	@SuppressWarnings("unchecked")
	public Object execute()
	{
		FastList<AccountingPeriod> list = new FastList<AccountingPeriod>();

		if (!getAccessibleOrganizations().isEmpty())
		{
			AccountingPeriodFilterCriteria filter = (AccountingPeriodFilterCriteria) getFilterCriteria();

			StringBuilder builder = new StringBuilder();
			builder.append("SELECT DISTINCT(period) FROM AccountingPeriod period,PartyRelationship relation");
			builder.append(" WHERE period.organization.id in(:orgs)");
			builder.append(" AND relation.relationshipType.id = 5");
			builder.append(" AND ");
			builder.append("(relation.fromRole.party.id = period.organization.id OR relation.toRole.party.id = period.organization.id)");
			builder.append(" AND ");
			builder.append("(relation.fromRole.party.id =:org OR relation.toRole.party.id =:org)");

			if (SiriusValidator.validateParam(filter.getLevel()))
				builder.append(" AND period.level =:level");

			if (filter.isOpenonly())
			{
				builder.append(" AND ");
				builder.append("period.status='OPEN'");
			}

			if (filter.getDate() != null)
			{
				builder.append(" AND ");
				builder.append(":date BETWEEN period.startDate AND period.endDate");
			}

			if (SiriusValidator.validateParam(filter.getName()))
			{
				builder.append(" AND ");
				builder.append("(period.code like '%" + filter.getName() + "%'");
				builder.append(" OR ");
				builder.append("period.name like '%" + filter.getName() + "%')");
			}

			Query criteria = getSession().createQuery(builder.toString());
			criteria.setParameter("org", filter.getOrganization());
			criteria.setParameterList("orgs", getAccessibleOrganizations());

			if (SiriusValidator.validateParam(filter.getLevel()))
				criteria.setParameter("level", Level.valueOf(filter.getLevel()));

			if (filter.getDate() != null)
				criteria.setParameter("date", filter.getDate());

			criteria.setFirstResult(filter.start());
			criteria.setMaxResults(filter.getMax());

			list.addAll(criteria.list());
		}

		return list;
	}
}

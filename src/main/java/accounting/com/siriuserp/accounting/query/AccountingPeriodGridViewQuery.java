/**
 * Aug 24, 2009 10:32:41 AM
 * com.siriuserp.accounting.query
 * AccountingPeriodGridViewQuery.java
 */
package com.siriuserp.accounting.query;

import org.hibernate.Query;

import com.siriuserp.accounting.criteria.AccountingPeriodFilterCriteria;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public class AccountingPeriodGridViewQuery extends AbstractGridViewQuery
{
	@Override
	public Long count()
	{
		AccountingPeriodFilterCriteria criteria = (AccountingPeriodFilterCriteria) filterCriteria;

		Query query = getQuery(criteria, ExecutorType.COUNT);
		Object object = query.uniqueResult();
		if (object != null)
			return (Long) object;

		return Long.valueOf(0);
	}

	@Override
	public Object execute()
	{
		AccountingPeriodFilterCriteria criteria = (AccountingPeriodFilterCriteria) filterCriteria;

		Query query = getQuery(criteria, ExecutorType.HQL);
		query.setFirstResult(criteria.start());
		query.setMaxResults(criteria.getMax());

		return query.list();
	}

	private Query getQuery(AccountingPeriodFilterCriteria criteria, ExecutorType type)
	{
		StringBuilder builder = new StringBuilder();

		if (type.compareTo(ExecutorType.COUNT) == 0)
			builder.append("SELECT COUNT(DISTINCT period) ");
		else
			builder.append("SELECT DISTINCT period ");

		builder.append("FROM AccountingPeriod period,PartyRelationship relation ");
		builder.append("WHERE relation.relationshipType.id = 5 ");
		builder.append("AND (relation.fromRole.party.id = period.organization.id OR relation.toRole.party.id = period.organization.id) ");
		builder.append("AND (relation.fromRole.party.id =:org OR relation.toRole.party.id =:org) ");

		if (criteria.isOpenonly())
			builder.append("AND period.status = 'OPEN' ");

		if (SiriusValidator.validateDate(criteria.getDate()))
			builder.append("AND :date BETWEEN period.startDate AND period.endDate ");

		if (SiriusValidator.validateParam(criteria.getName()))
			builder.append("AND (period.code LIKE '%" + criteria.getName() + "%' OR period.name LIKE '%" + criteria.getName() + "%') ");

		Query query = getSession().createQuery(builder.toString());
		query.setParameter("org", criteria.getOrganization());

		if (SiriusValidator.validateDate(criteria.getDate()))
			query.setParameter("date", criteria.getDate());

		return query;
	}
}

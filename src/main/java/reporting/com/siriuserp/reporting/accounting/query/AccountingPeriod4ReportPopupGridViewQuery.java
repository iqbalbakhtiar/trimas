/**
 * Oct 29, 2009 4:37:22 PM
 * com.siriuserp.accounting.query
 * AccountingPeriod4ReportPopupGridViewQuery.java
 */
package com.siriuserp.reporting.accounting.query;

import org.hibernate.Query;

import com.siriuserp.accounting.criteria.AccountingPeriodFilterCriteria;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.dm.Level;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Muhammad Rizal
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public class AccountingPeriod4ReportPopupGridViewQuery extends AbstractGridViewQuery
{
	@Override
	public Long count()
	{
		AccountingPeriodFilterCriteria criteria = (AccountingPeriodFilterCriteria) getFilterCriteria();

		Query query = getQuery(criteria, ExecutorType.COUNT);
		Object object = query.uniqueResult();
		if (object != null)
			return (Long) object;

		return Long.valueOf(0);
	}

	@Override
	public Object execute()
	{
		AccountingPeriodFilterCriteria criteria = (AccountingPeriodFilterCriteria) getFilterCriteria();

		Query query = getQuery(criteria, ExecutorType.HQL);
		query.setFirstResult(criteria.start());
		query.setMaxResults(criteria.getMax());

		return query.list();
	}

	public Query getQuery(AccountingPeriodFilterCriteria criteria, ExecutorType type)
	{
		StringBuilder builder = new StringBuilder();

		if (type.compareTo(ExecutorType.COUNT) == 0)
			builder.append("SELECT COUNT(period) ");

		builder.append("FROM AccountingPeriod period WHERE period.organization.id =:org ");

		if (criteria.isOpenonly())
			builder.append("AND period.status = 'OPEN' ");

		if (SiriusValidator.validateParam(criteria.getLevel()))
			builder.append("AND period.level =:level ");

		if (SiriusValidator.validateDate(criteria.getDate()))
			builder.append("AND (:date BETWEEN period.startDate AND period.endDate) ");

		if (SiriusValidator.validateParam(criteria.getName()))
		{
			builder.append("AND (period.code LIKE '%" + criteria.getName() + "%' ");
			builder.append("OR period.name LIKE '%" + criteria.getName() + "%') ");
		}

		if (type.compareTo(ExecutorType.HQL) == 0)
			builder.append("ORDER BY period.startDate DESC ");

		Query query = getSession().createQuery(builder.toString());
		query.setParameter("org", criteria.getOrganization());

		if (SiriusValidator.validateDate(criteria.getDate()))
			query.setParameter("date", criteria.getDate());

		if (SiriusValidator.validateParam(criteria.getLevel()))
			query.setParameter("level", Level.valueOf(criteria.getLevel()));

		return query;
	}
}

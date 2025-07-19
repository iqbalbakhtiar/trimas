/**
 * File Name  : FundApplicationViewQuery.java
 * Created On : Jul 12, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.accountpayable.query;

import org.hibernate.Query;

import com.siriuserp.accountpayable.criteria.FundApplicationFilterCriteria;
import com.siriuserp.accountpayable.dm.FundApplicationStatus;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

public class FundApplicationViewQuery extends AbstractGridViewQuery
{
	@Override
	public Query getQuery(ExecutorType type)
	{
		FundApplicationFilterCriteria criteria = (FundApplicationFilterCriteria) getFilterCriteria();

		StringBuilder builder = new StringBuilder();

		if (type.compareTo(ExecutorType.COUNT) == 0)
			builder.append("SELECT COUNT(DISTINCT fund) ");

		builder.append("FROM FundApplication fund ");
		builder.append("WHERE fund.id IS NOT NULL ");

		if (SiriusValidator.validateParam(criteria.getCode()))
			builder.append("AND fund.code LIKE :code ");

		if (SiriusValidator.validateParam(criteria.getStatus()))
			builder.append("AND fund.status =:status ");

		if (SiriusValidator.validateDate(criteria.getDateFrom()))
		{
			if (SiriusValidator.validateDate(criteria.getDateTo()))
				builder.append("AND fund.date BETWEEN :startDate AND :endDate ");
			else
				builder.append("AND fund.date >= :startDate ");
		}

		if (SiriusValidator.validateDate(criteria.getDateTo()))
			builder.append("AND fund.date <= :endDate ");

		if (type.compareTo(ExecutorType.HQL) == 0)
			builder.append("ORDER BY fund.id DESC");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);

		if (SiriusValidator.validateParam(criteria.getCode()))
			query.setParameter("code", "%" + criteria.getCode() + "%");

		if (SiriusValidator.validateParam(criteria.getStatus()))
			query.setParameter("status", FundApplicationStatus.valueOf(criteria.getStatus()));

		if (SiriusValidator.validateDate(criteria.getDateFrom()))
			query.setParameter("startDate", criteria.getDateFrom());

		if (SiriusValidator.validateDate(criteria.getDateTo()))
			query.setParameter("endDate", criteria.getDateTo());

		return query;
	}
}

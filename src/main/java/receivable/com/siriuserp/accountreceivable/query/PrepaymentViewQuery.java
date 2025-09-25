package com.siriuserp.accountreceivable.query;

import org.hibernate.Query;

import com.siriuserp.accountpayable.dm.PaymentMethodType;
import com.siriuserp.accountreceivable.criteria.PrepaymentFilterCriteria;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author ferdinand
 */

public class PrepaymentViewQuery extends AbstractGridViewQuery 
{
	@Override
	public Query getQuery(ExecutorType executorType)
	{
		PrepaymentFilterCriteria criteria = (PrepaymentFilterCriteria) getFilterCriteria();
		StringBuilder builder = new StringBuilder();
		
		if (executorType.equals(ExecutorType.COUNT))
			builder.append("SELECT COUNT(DISTINCT prepayment) ");
		else
			builder.append("SELECT DISTINCT(prepayment) ");
		
		builder.append("FROM Prepayment prepayment WHERE prepayment.id IS NOT NULL ");
		
		if (SiriusValidator.validateParam(criteria.getCode()))
			builder.append("AND prepayment.code LIKE :code ");
		
		if (SiriusValidator.validateParam(criteria.getCustomerName()))
			builder.append("AND prepayment.customer.fullName LIKE :customer ");

		if (SiriusValidator.validateParam(criteria.getType()))
			builder.append("AND prepayment.prepaymentMethodType = :type ");

		builder.append("ORDER BY prepayment.id DESC");

		if (SiriusValidator.validateDate(criteria.getDateFrom()))
		{
			if (SiriusValidator.validateDate(criteria.getDateTo()))
				builder.append("AND prepayment.date BETWEEN :startDate AND :endDate ");
			else
				builder.append("AND prepayment.date >= :startDate ");
		} 
		else if (SiriusValidator.validateDate(criteria.getDateTo()))
			builder.append("AND prepayment.date <= :endDate ");
		
		Query query = getSession().createQuery(builder.toString());
		query.setReadOnly(true);

		if (SiriusValidator.validateParam(criteria.getCode()))
			query.setParameter("code", "%" + criteria.getCode() + "%");

		if (SiriusValidator.validateDate(criteria.getDateFrom()))
			query.setParameter("startDate", criteria.getDateFrom());

		if (SiriusValidator.validateDate(criteria.getDateTo()))
			query.setParameter("endDate", criteria.getDateTo());

		if (SiriusValidator.validateParam(criteria.getCustomerName()))
			query.setParameter("customer", "%" + criteria.getCustomerName() + "%");

		if (SiriusValidator.validateParam(criteria.getType()))
			query.setParameter("type", PaymentMethodType.valueOf(criteria.getType()));

		return query;
	}
}

/**
 * File Name  : DeliveryPlanningViewQuery.java
 * Created On : Mar 6, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.sales.query;

import org.hibernate.Query;

import com.siriuserp.sales.criteria.DeliveryPlanningFilterCriteria;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

public class DeliveryPlanningViewQuery extends AbstractGridViewQuery
{
	@Override
	public Query getQuery(ExecutorType type)
	{
		DeliveryPlanningFilterCriteria criteria = (DeliveryPlanningFilterCriteria) getFilterCriteria();
		StringBuilder builder = new StringBuilder();

		if (type.equals(ExecutorType.COUNT))
			builder.append("SELECT COUNT(planning) ");
		
		if (type.equals(ExecutorType.HQL))
			builder.append("SELECT planning ");

		builder.append("FROM DeliveryPlanning planning ");
		
		if (!criteria.getInProgress().equals("ALL"))
			builder.append("JOIN planning.salesOrder.items item ");
			
		builder.append("WHERE planning.salesOrder IS NOT NULL ");

		if (SiriusValidator.validateParam(criteria.getCode()))
			builder.append("AND planning.code LIKE :code ");

		if (SiriusValidator.validateParam(criteria.getCustomerName()))
			builder.append("AND so.customer.fullName LIKE :customerName ");

		if (SiriusValidator.validateParam(criteria.getSalesOrderCode()))
			builder.append("AND planning.planable.code LIKE :salesCode ");

		if (SiriusValidator.validateDate(criteria.getDateFrom()))
		{
			if (SiriusValidator.validateDate(criteria.getDateTo()))
				builder.append("AND planning.date BETWEEN :dateFrom AND :dateTo");
			else
				builder.append("AND planning.date >= :dateFrom ");
		} else if (SiriusValidator.validateDate(criteria.getDateTo()))
			builder.append("AND planning.date <= :dateTo ");

		if (type.equals(ExecutorType.HQL))
		{
			builder.append("GROUP BY planning.salesOrder.id ");
			
			if (criteria.getInProgress().equals("IN_PROGRESS"))
				builder.append("HAVING SUM(item.quantity - item.delivered) > 0 "); 
			
			if (criteria.getInProgress().equals("DONE"))
				builder.append("HAVING SUM(item.quantity - item.delivered) <= 0 ");
			
			builder.append("ORDER BY planning.id DESC");
		}

		Query query = getSession().createQuery(builder.toString());
		query.setReadOnly(true);

		if (SiriusValidator.validateParam(criteria.getCode()))
			query.setParameter("code", "%" + criteria.getCode() + "%");

		if (SiriusValidator.validateParam(criteria.getCustomerName()))
			query.setParameter("customerName", "%" + criteria.getCustomerName() + "%");

		if (SiriusValidator.validateParam(criteria.getSalesOrderCode()))
			query.setParameter("salesCode", "%" + criteria.getSalesOrderCode() + "%");

		if (SiriusValidator.validateDate(criteria.getDateFrom()))
			query.setParameter("dateFrom", criteria.getDateFrom());

		if (SiriusValidator.validateDate(criteria.getDateTo()))
			query.setParameter("dateTo", criteria.getDateTo());

		return query;
	}
}

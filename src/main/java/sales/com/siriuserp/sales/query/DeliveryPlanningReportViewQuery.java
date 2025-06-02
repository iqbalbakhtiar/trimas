package com.siriuserp.sales.query;

import org.hibernate.Query;

import com.siriuserp.sales.criteria.SalesReportFilterCriteria;
import com.siriuserp.sales.dm.SalesInternalType;
import com.siriuserp.sdk.db.AbstractStandardReportQuery;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author ferdinand
 */

public class DeliveryPlanningReportViewQuery extends AbstractStandardReportQuery
{
	@Override
	public Object execute()
	{
		SalesReportFilterCriteria criteria = (SalesReportFilterCriteria) getFilterCriteria();

		StringBuilder builder = new StringBuilder();
		builder.append("SELECT sequenceItem FROM DeliveryPlanningSequenceItem sequenceItem JOIN sequenceItem.deliveryOrderReferenceItem referenceItem ");
		//Exclude Sequence Items with DeliveryOrder
		builder.append("WHERE sequenceItem.id NOT IN (SELECT DISTINCT deliveryOrderItem.deliveryReferenceItem.id FROM DeliveryOrderItem deliveryOrderItem WHERE deliveryOrderItem.deliveryReferenceItem.id IS NOT NULL) ");

		if (SiriusValidator.validateLongParam(criteria.getOrganization()))
			builder.append("AND sequenceItem.salesOrderItem.salesOrder.organization.id =:organizationId ");

		if (SiriusValidator.validateLongParam(criteria.getCustomer()))
			builder.append("AND sequenceItem.salesOrderItem.salesOrder.customer.id =:customerId ");

		if (SiriusValidator.validateParam(criteria.getSalesOrderCode()))
			builder.append("AND sequenceItem.salesOrderItem.salesOrder.code LIKE :salesOrderCode ");

		if (SiriusValidator.validateParam(criteria.getSalesInternalType()))
			builder.append("AND sequenceItem.salesOrderItem.salesOrder.salesInternalType =:salesInternalType ");

		if (SiriusValidator.validateDate(criteria.getDateFrom()))
		{
			if (SiriusValidator.validateDate(criteria.getDateTo()))
				builder.append("AND sequenceItem.deliveryPlanningSequence.date BETWEEN :dateFrom AND :dateTo ");
			else
				builder.append("AND sequenceItem.deliveryPlanningSequence.date >= :dateFrom ");
		} else if (SiriusValidator.validateDate(criteria.getDateTo()))
			builder.append("AND sequenceItem.deliveryPlanningSequence.date <= :dateTo ");

		builder.append("ORDER BY sequenceItem.deliveryPlanningSequence.date, sequenceItem.product.code ASC");

		Query query = getSession().createQuery(builder.toString());
		query.setReadOnly(true);

		if (SiriusValidator.validateLongParam(criteria.getOrganization()))
			query.setParameter("organizationId", criteria.getOrganization());

		if (SiriusValidator.validateLongParam(criteria.getCustomer()))
			query.setParameter("customerId", criteria.getCustomer());

		if (SiriusValidator.validateParam(criteria.getSalesOrderCode()))
			query.setParameter("salesOrderCode", "%" + criteria.getSalesOrderCode() + "%");

		if (SiriusValidator.validateParam(criteria.getSalesInternalType()))
			query.setParameter("salesInternalType", SalesInternalType.valueOf(criteria.getSalesInternalType()));

		if (SiriusValidator.validateDate(criteria.getDateFrom()))
			query.setParameter("dateFrom", criteria.getDateFrom());

		if (SiriusValidator.validateDate(criteria.getDateTo()))
			query.setParameter("dateTo", criteria.getDateTo());

		return query.list();
	}
}

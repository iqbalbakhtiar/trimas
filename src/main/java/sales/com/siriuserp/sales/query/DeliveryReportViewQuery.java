/**
 * File Name  : DeliveryReportViewQuery.java
 * Created On : Apr 30, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.sales.query;

import org.hibernate.Query;

import com.siriuserp.sales.criteria.SalesReportFilterCriteria;
import com.siriuserp.sdk.db.AbstractStandardReportQuery;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

public class DeliveryReportViewQuery extends AbstractStandardReportQuery
{
	@Override
	public Object execute()
	{
		SalesReportFilterCriteria criteria = (SalesReportFilterCriteria) getFilterCriteria();

		StringBuilder builder = new StringBuilder();
		builder.append("SELECT NEW com.siriuserp.sales.adapter.DeliveryReportAdapter(deliveryItem, salesItem, ");
		builder.append(
				"(SELECT DISTINCT(billItem.billing) FROM BillingItem billItem WHERE billItem.billingReferenceItem.referenceId = realizationItem.deliveryOrderRealization.id AND billItem.billingReferenceItem.referenceName = 'DELIVERY_ORDER_REALIZATION'))");
		builder.append("FROM DeliveryOrderRealizationItem realizationItem JOIN realizationItem.deliveryOrderItem deliveryItem JOIN deliveryItem.deliveryReferenceItem.salesOrderItem salesItem ");
		builder.append("WHERE deliveryItem.deliveryItemType = 'BASE' ");

		if (SiriusValidator.validateLongParam(criteria.getOrganization()))
			builder.append("AND deliveryItem.deliveryOrder.organization.id =:organizationId ");

		if (SiriusValidator.validateLongParam(criteria.getCustomer()))
			builder.append("AND deliveryItem.deliveryOrder.customer.id =:customerId ");

		if (SiriusValidator.validateParam(criteria.getSalesOrderCode()))
			builder.append("AND salesItem.salesOrder.code LIKE :salesOrderCode ");

		if (SiriusValidator.validateLongParam(criteria.getProduct()))
			builder.append("AND salesItem.product.id =:productId ");

		if (criteria.getTaxReport() != null)
		{
			if (criteria.getTaxReport())
				builder.append("AND salesItem.salesOrder.tax.taxId != 'Ex' ");
		}

		if (SiriusValidator.validateDate(criteria.getDateFrom()))
		{
			if (SiriusValidator.validateDate(criteria.getDateTo()))
				builder.append("AND deliveryItem.deliveryOrder.date BETWEEN :dateFrom AND :dateTo ");
			else
				builder.append("AND deliveryItemdeliveryOrder..date >= :dateFrom ");
		} else if (SiriusValidator.validateDate(criteria.getDateTo()))
			builder.append("AND deliveryItem.deliveryOrder.date <= :dateTo ");

		builder.append("ORDER BY deliveryItem.deliveryOrder.date ASC, deliveryItem.deliveryOrder.id ASC");

		Query query = getSession().createQuery(builder.toString());
		query.setReadOnly(true);

		if (SiriusValidator.validateLongParam(criteria.getOrganization()))
			query.setParameter("organizationId", criteria.getOrganization());

		if (SiriusValidator.validateLongParam(criteria.getCustomer()))
			query.setParameter("customerId", criteria.getCustomer());

		if (SiriusValidator.validateParam(criteria.getSalesOrderCode()))
			query.setParameter("salesOrderCode", "%" + criteria.getSalesOrderCode() + "%");

		if (SiriusValidator.validateLongParam(criteria.getProduct()))
			query.setParameter("productId", criteria.getProduct());

		if (SiriusValidator.validateDate(criteria.getDateFrom()))
			query.setParameter("dateFrom", criteria.getDateFrom());

		if (SiriusValidator.validateDate(criteria.getDateTo()))
			query.setParameter("dateTo", criteria.getDateTo());

		return query.list();
	}
}

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
		builder.append("SELECT NEW com.siriuserp.sales.adapter.DeliveryReportAdapter(deliveryItem, deliveryItem.deliveryReferenceItem.salesOrderItem) ");
		builder.append("FROM DeliveryOrderItem deliveryItem ");
		builder.append("WHERE deliveryItem.deliveryItemType = 'BASE' ");

		if (SiriusValidator.validateLongParam(criteria.getOrganization()))
			builder.append("AND deliveryItem.deliveryOrder.organization.id =:organizationId ");

		if (SiriusValidator.validateLongParam(criteria.getCustomer()))
			builder.append("AND deliveryItem.deliveryOrder.customer.id =:customerId ");

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

		if (SiriusValidator.validateDate(criteria.getDateFrom()))
			query.setParameter("dateFrom", criteria.getDateFrom());

		if (SiriusValidator.validateDate(criteria.getDateTo()))
			query.setParameter("dateTo", criteria.getDateTo());

		return query.list();
	}
}

/**
 * File Name  : SalesOnProgressReportViewQuery.java
 * Created On : May 10, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.sales.query;

import org.hibernate.Query;

import com.siriuserp.sales.criteria.SalesReportFilterCriteria;
import com.siriuserp.sales.dm.SalesInternalType;
import com.siriuserp.sdk.db.AbstractStandardReportQuery;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

public class SalesOnProgressReportViewQuery extends AbstractStandardReportQuery
{
	@Override
	public Object execute()
	{
		SalesReportFilterCriteria criteria = (SalesReportFilterCriteria) getFilterCriteria();

		StringBuilder builder = new StringBuilder();
		builder.append("SELECT NEW com.siriuserp.sales.adapter.SalesOnProgressReportAdapter(salesItem) ");
		builder.append("FROM SalesOrderItem salesItem ");
		builder.append("WHERE salesItem.salesOrder.soStatus != 'CLOSE' AND salesItem.salesOrder.soStatus != 'CANCELED' ");

		if (SiriusValidator.validateLongParam(criteria.getOrganization()))
			builder.append("AND salesItem.salesOrder.organization.id =:organizationId ");

		if (SiriusValidator.validateLongParam(criteria.getCustomer()))
			builder.append("AND salesItem.salesOrder.customer.id =:customerId ");

		if (SiriusValidator.validateParam(criteria.getSalesInternalType()))
			builder.append("AND salesItem.salesOrder.salesInternalType =:salesInternalType ");

		if (SiriusValidator.validateParam(criteria.getStatus()))
		{
			if (criteria.getStatus().equals("IN_PROGRESS"))
				builder.append("AND (salesItem.quantity - salesItem.delivered) > 0");
			
			if (criteria.getStatus().equals("DONE"))
				builder.append("AND (salesItem.quantity - salesItem.delivered) <= 0");
		}
		
		if (SiriusValidator.validateDate(criteria.getDateFrom()))
		{
			if (SiriusValidator.validateDate(criteria.getDateTo()))
				builder.append("AND salesItem.salesOrder.date BETWEEN :dateFrom AND :dateTo ");
			else
				builder.append("AND salesItem.salesOrder.date >= :dateFrom ");
		} else if (SiriusValidator.validateDate(criteria.getDateTo()))
			builder.append("AND salesItem.salesOrder.date <= :dateTo ");

		builder.append("ORDER BY salesItem.salesOrder.date ASC, salesItem.salesOrder.id ASC");

		Query query = getSession().createQuery(builder.toString());
		query.setReadOnly(true);

		if (SiriusValidator.validateLongParam(criteria.getOrganization()))
			query.setParameter("organizationId", criteria.getOrganization());

		if (SiriusValidator.validateLongParam(criteria.getCustomer()))
			query.setParameter("customerId", criteria.getCustomer());

		if (SiriusValidator.validateParam(criteria.getSalesInternalType()))
			query.setParameter("salesInternalType", SalesInternalType.valueOf(criteria.getSalesInternalType()));

		if (SiriusValidator.validateDate(criteria.getDateFrom()))
			query.setParameter("dateFrom", criteria.getDateFrom());

		if (SiriusValidator.validateDate(criteria.getDateTo()))
			query.setParameter("dateTo", criteria.getDateTo());

		return query.list();
	}
}

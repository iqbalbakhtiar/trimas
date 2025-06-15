/**
 * File Name  : SalesSummaryReportQuery.java
 * Created On : Jul 11, 2019
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.sales.query;

import org.hibernate.Query;

import com.siriuserp.sales.adapter.SalesSummaryReportAdapter;
import com.siriuserp.sales.criteria.SalesReportFilterCriteria;
import com.siriuserp.sdk.db.AbstractStandardReportQuery;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastList;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@SuppressWarnings("unchecked")
public class SalesSummaryReportQuery extends AbstractStandardReportQuery
{
	@Override
	public Object execute()
	{
		SalesReportFilterCriteria criteria = (SalesReportFilterCriteria) getFilterCriteria();
		FastList<SalesSummaryReportAdapter> details = new FastList<SalesSummaryReportAdapter>();

		StringBuilder builder = new StringBuilder();
		builder.append("SELECT NEW com.siriuserp.sales.adapter.SalesSummaryReportAdapter(salesOrder.customer, ");
		builder.append("SUM(item.quantity), SUM((item.money.amount-item.discount)*item.quantity), salesOrder.tax.taxRate) ");
		builder.append("FROM SalesOrder salesOrder JOIN salesOrder.items item ");
		builder.append("WHERE salesOrder.id IS NOT NULL ");

		if (SiriusValidator.validateLongParam(criteria.getOrganization()))
			builder.append("AND salesOrder.organization.id =:organizationId ");

		if (SiriusValidator.validateLongParam(criteria.getCustomer()))
			builder.append("AND salesOrder.customer.id =:customerId ");

		if (SiriusValidator.validateDate(criteria.getDateFrom()))
		{
			if (SiriusValidator.validateDate(criteria.getDateTo()))
				builder.append("AND salesOrder.date BETWEEN :dateFrom AND :dateTo ");
			else
				builder.append("AND salesOrder.date >= :dateFrom ");
		} else if (SiriusValidator.validateDate(criteria.getDateTo()))
			builder.append("AND salesOrder.date <= :dateTo ");

		builder.append("GROUP BY salesOrder.customer.id ");
		builder.append("ORDER BY salesOrder.customer.fullName ASC");

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

		details.addAll(query.list());

		return details;
	}
}

/**
 * File Name  : SalesDetailReportViewQuery.java
 * Created On : Jul 11, 2019
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

public class SalesDetailReportViewQuery extends AbstractStandardReportQuery
{
	@Override
	public Object execute()
	{
		SalesReportFilterCriteria criteria = (SalesReportFilterCriteria) getFilterCriteria();

		StringBuilder builder = new StringBuilder();
		builder.append("SELECT DISTINCT NEW com.siriuserp.sales.adapter.SalesDetailReportAdapter(sales) ");
		builder.append("FROM SalesOrder sales ");
		builder.append("WHERE sales.id IS NOT NULL ");

		if (SiriusValidator.validateLongParam(criteria.getOrganization()))
			builder.append("AND sales.organization.id =:organizationId ");

		if (SiriusValidator.validateLongParam(criteria.getCustomer()))
			builder.append("AND sales.customer.id =:customerId ");

		if (SiriusValidator.validateDate(criteria.getDateFrom()))
		{
			if (SiriusValidator.validateDate(criteria.getDateTo()))
				builder.append("AND sales.date BETWEEN :dateFrom AND :dateTo ");
			else
				builder.append("AND sales.date >= :dateFrom ");
		} else if (SiriusValidator.validateDate(criteria.getDateTo()))
			builder.append("AND sales.date <= :dateTo ");

		builder.append("ORDER BY sales.date ASC, sales.customer.fullName ASC");

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

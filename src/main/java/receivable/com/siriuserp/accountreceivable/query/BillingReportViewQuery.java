/**
 * File Name  : BillingReportViewQuery.java
 * Created On : Jul 19, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.accountreceivable.query;

import org.hibernate.Query;

import com.siriuserp.accountreceivable.criteria.BillingReportFilterCriteria;
import com.siriuserp.sdk.db.AbstractStandardReportQuery;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

public class BillingReportViewQuery extends AbstractStandardReportQuery
{
	@Override
	public Object execute()
	{
		BillingReportFilterCriteria criteria = (BillingReportFilterCriteria) getFilterCriteria();

		StringBuilder builder = new StringBuilder();
		builder.append("FROM Billing bill ");
		builder.append("WHERE bill.id IS NOT NULL ");

		if (SiriusValidator.validateLongParam(criteria.getOrganization()))
			builder.append("AND bill.organization.id =:organizationId ");

		if (SiriusValidator.validateLongParam(criteria.getCustomer()))
			builder.append("AND bill.customer.id =:customerId ");

		if (SiriusValidator.validateDate(criteria.getDateFrom()))
		{
			if (SiriusValidator.validateDate(criteria.getDateTo()))
				builder.append("AND bill.date BETWEEN :dateFrom AND :dateTo ");
			else
				builder.append("AND bill.date >= :dateFrom ");
		} else if (SiriusValidator.validateDate(criteria.getDateTo()))
			builder.append("AND bill.date <= :dateTo ");

		builder.append("ORDER BY bill.date ASC, bill.id ASC");

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

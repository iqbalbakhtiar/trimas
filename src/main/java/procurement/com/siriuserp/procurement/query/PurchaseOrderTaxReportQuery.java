/**
 * Jun 30, 2011
 * PurchaseReportQuery.java
 */
package com.siriuserp.procurement.query;

import org.hibernate.Query;

import com.siriuserp.procurement.criteria.PurchaseReportFilterCriteria;
import com.siriuserp.procurement.dm.PurchaseDocumentType;
import com.siriuserp.sdk.db.AbstractStandardReportQuery;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Iqbal Bakhtiar
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public class PurchaseOrderTaxReportQuery extends AbstractStandardReportQuery
{
	@Override
	public Object execute()
	{
		PurchaseReportFilterCriteria criteria = (PurchaseReportFilterCriteria) getFilterCriteria();

		StringBuilder builder = new StringBuilder();
		builder.append("SELECT new com.siriuserp.procurement.adapter.PurchaseReportAdapter(purchaseItem) ");
		builder.append("FROM PurchaseOrderItem purchaseItem JOIN purchaseItem.invoiceReferences invo ");
		builder.append("WHERE purchaseItem.purchaseOrder.organization.id =:org ");
		builder.append("AND purchaseItem.purchaseItemType = 'BASE' ");
		builder.append("AND purchaseItem.tax IS NOT NULL ");

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getSupplier()))
			builder.append("AND purchaseItem.purchaseOrder.supplier.id =:supplier ");

		if (SiriusValidator.validateParam(criteria.getDocumentType()))
			builder.append("AND purchaseItem.purchaseOrder.purchaseDocumentType =:documentType ");

		if (SiriusValidator.validateDate(criteria.getDateFrom()))
		{
			if (SiriusValidator.validateDate(criteria.getDateTo()))
				builder.append("AND purchaseItem.purchaseOrder.date BETWEEN :from AND :to ");
			else
				builder.append("AND purchaseItem.purchaseOrder.date >= :from ");
		} else if (SiriusValidator.validateDate(criteria.getDateTo()))
			builder.append("AND purchaseItem.purchaseOrder.date <= :to ");

		builder.append("ORDER BY invo.invoiceVerificationItem.invoiceVerification.date ASC, invo.invoiceVerificationItem.invoiceVerification.taxNo ASC ");

		Query query = getSession().createQuery(builder.toString());
		query.setReadOnly(true);
		query.setParameter("org", criteria.getOrganization());

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getSupplier()))
			query.setParameter("supplier", criteria.getSupplier());

		if (SiriusValidator.validateParam(criteria.getDocumentType()))
			query.setParameter("documentType", PurchaseDocumentType.valueOf(criteria.getDocumentType()));

		if (SiriusValidator.validateDate(criteria.getDateFrom()))
			query.setParameter("from", criteria.getDateFrom());

		if (SiriusValidator.validateDate(criteria.getDateTo()))
			query.setParameter("to", criteria.getDateTo());
		
		return query.list();
	}
}

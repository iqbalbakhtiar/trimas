/**
 * File Name  : StandardPurchaseOrderViewQuery.java
 * Created On : Feb 23, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.procurement.query;

import org.hibernate.Query;

import com.siriuserp.procurement.criteria.PurchaseOrderFilterCriteria;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.dm.ApprovalDecisionStatus;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

public class StandardPurchaseOrderViewQuery extends AbstractGridViewQuery
{
	@Override
	public Query getQuery(ExecutorType executorType)
	{
		PurchaseOrderFilterCriteria criteria = (PurchaseOrderFilterCriteria) getFilterCriteria();
		StringBuilder builder = new StringBuilder();

		if (executorType.equals(ExecutorType.COUNT))
			builder.append("SELECT COUNT(DISTINCT purchase) ");
		else
			builder.append("SELECT DISTINCT(purchase) ");

		builder.append("FROM PurchaseOrder purchase ");
		builder.append("WHERE purchase.purchaseType = 'STANDARD' ");

		if (SiriusValidator.validateParam(criteria.getCode()))
			builder.append("AND purchase.code LIKE :code");

		if (SiriusValidator.validateParam(criteria.getSupplierName()))
			builder.append("AND purchase.supplier.fullName LIKE :supplierName ");

		if (SiriusValidator.validateParam(criteria.getTax()))
			builder.append("AND purchase.tax.taxName LIKE :tax ");

		if (SiriusValidator.validateParam(criteria.getApprovalDecisionStatus()))
			builder.append("AND purchase.approvable.approvalDecision.approvalDecisionStatus =:approvalDecisionStatus ");

		if (SiriusValidator.validateParam(criteria.getApprover()))
			builder.append("AND purchase.approver.fullName LIKE :approver ");

		if (SiriusValidator.validateParam(criteria.getBillToAddress()))
			builder.append("AND purchase.billTo.addressName LIKE :billToAddress ");

		if (SiriusValidator.validateParam(criteria.getShipToFacility()))
			builder.append("AND purchase.shipTo.name LIKE :shipToFacility ");

		if (SiriusValidator.validateDate(criteria.getDateFrom()))
		{
			if (SiriusValidator.validateDate(criteria.getDateTo()))
				builder.append("AND purchase.date BETWEEN :startDate AND :endDate ");
			else
				builder.append("AND purchase.date >= :startDate ");
		} else if (SiriusValidator.validateDate(criteria.getDateTo()))
			builder.append("AND purchase.date <= :endDate ");

		builder.append(" ORDER BY purchase.id DESC ");

		Query query = getSession().createQuery(builder.toString());
		query.setReadOnly(true);

		if (SiriusValidator.validateParam(criteria.getCode()))
			query.setParameter("code", "%" + criteria.getCode() + "%");

		if (SiriusValidator.validateDate(criteria.getDateFrom()))
			query.setParameter("startDate", criteria.getDateFrom());

		if (SiriusValidator.validateDate(criteria.getDateTo()))
			query.setParameter("endDate", criteria.getDateTo());

		if (SiriusValidator.validateParam(criteria.getSupplierName()))
			query.setParameter("supplierName", "%" + criteria.getSupplierName() + "%");

		if (SiriusValidator.validateParam(criteria.getTax()))
			query.setParameter("tax", "%" + criteria.getTax() + "%");

		if (SiriusValidator.validateParam(criteria.getApprovalDecisionStatus()))
			query.setParameter("approvalDecisionStatus", ApprovalDecisionStatus.valueOf(criteria.getApprovalDecisionStatus()));

		if (SiriusValidator.validateParam(criteria.getApprover()))
			query.setParameter("approver", "%" + criteria.getApprover() + "%");

		if (SiriusValidator.validateParam(criteria.getBillToAddress()))
			query.setParameter("billToAddress", "%" + criteria.getBillToAddress() + "%");

		if (SiriusValidator.validateParam(criteria.getShipToFacility()))
			query.setParameter("shipToFacility", "%" + criteria.getShipToFacility() + "%");

		return query;
	}
}

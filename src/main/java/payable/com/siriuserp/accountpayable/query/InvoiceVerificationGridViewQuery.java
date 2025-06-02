package com.siriuserp.accountpayable.query;

import com.siriuserp.accountpayable.criteria.InvoiceVerificationFilterCriteria;
import com.siriuserp.accountpayable.dm.InvoiceVerificationType;
import com.siriuserp.accountreceivable.dm.FinancialStatus;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.utility.SiriusValidator;
import org.hibernate.Query;

public class InvoiceVerificationGridViewQuery extends AbstractGridViewQuery
{
	@Override
	public Query getQuery(ExecutorType type)
	{
		InvoiceVerificationFilterCriteria criteria = (InvoiceVerificationFilterCriteria) getFilterCriteria();

		StringBuilder builder = new StringBuilder();

		if (type.compareTo(ExecutorType.COUNT) == 0)
			builder.append("SELECT COUNT(DISTINCT ver)");
		else
			builder.append("SELECT DISTINCT item.invoiceVerification");

		builder.append(" FROM InvoiceVerificationItem item JOIN item.invoiceVerification ver");
		builder.append(" WHERE ver.id IS NOT NULL ");

		if (SiriusValidator.validateParam(criteria.getInvoiceType()))
			builder.append(" AND ver.invoiceType =:invoiceType");
		
		if (SiriusValidator.validateParam(criteria.getCode()))
			builder.append(" AND ver.code like '%" + criteria.getCode() + "%'");

		if (SiriusValidator.validateParam(criteria.getReference()))
			builder.append(" AND ver.goodsReceipt.code like '%" + criteria.getReference() + "%'");

		if (criteria.getVerification() != null)
			builder.append(" AND ver.verificated = :verificated");

		if (SiriusValidator.validateParam(criteria.getSupplierName()))
			builder.append(" AND ver.supplier.fullName like '%" + criteria.getSupplierName() + "%' ");

		if (SiriusValidator.validateParam(criteria.getTaxName()))
			builder.append(" AND ver.tax.taxName like '%" + criteria.getTaxName() + "%'");

		if (SiriusValidator.validateParam(criteria.getDocumentNo()))
			builder.append(" AND ver.supplierDocumentNo like '%" + criteria.getDocumentNo() + "%'");

		if (SiriusValidator.validateParam(criteria.getCurrencyName()))
			builder.append(" AND ver.currency.symbol like '%" + criteria.getCurrencyName() + "%'");

		if (SiriusValidator.validateDate(criteria.getDateFrom()))
		{
			if (SiriusValidator.validateDate(criteria.getDateTo()))
				builder.append(" AND ver.date BETWEEN :startDate AND :endDate");
			else
				builder.append(" AND ver.date >= :startDate");
		}

		if (SiriusValidator.validateDate(criteria.getDateTo()))
			builder.append(" AND ver.date <= :endDate");

		if (SiriusValidator.validateParam(criteria.getReferenceCode()))
			builder.append(" AND item.goodsReceiptItem.warehouseTransactionItem.referenceItem.referenceCode like '%" + criteria.getReferenceCode() + "%'");

		// Filter From Popup
		if (SiriusValidator.validateParam(criteria.getSupplier()))
			builder.append(" AND ver.supplier.id = :supplier");

		if (SiriusValidator.validateParam(criteria.getFinancialStatus()))
			builder.append(" AND ver.status = :financialStatus");

		if (type.compareTo(ExecutorType.HQL) == 0)
			builder.append(" ORDER BY ver.id DESC");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);

		if (SiriusValidator.validateDate(criteria.getDateFrom()))
			query.setParameter("startDate", criteria.getDateFrom());

		if (SiriusValidator.validateDate(criteria.getDateTo()))
			query.setParameter("endDate", criteria.getDateTo());

		if (criteria.getVerification() != null)
			query.setParameter("verificated", criteria.getVerification());

//		Unused
//		if (criteria.getGoodsType() != null)
//			query.setParameter("goodsType", criteria.getGoodsType());

		if (SiriusValidator.validateLongParam(criteria.getSupplier()))
			query.setParameter("supplier", criteria.getSupplier());

		if (SiriusValidator.validateParam(criteria.getFinancialStatus()))
			query.setParameter("financialStatus", FinancialStatus.valueOf(criteria.getFinancialStatus()));
		
		if (SiriusValidator.validateParam(criteria.getInvoiceType()))
			query.setParameter("invoiceType", InvoiceVerificationType.valueOf(criteria.getInvoiceType()));

		return query;
	}
}

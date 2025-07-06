/**
 * File Name  : ReceiptManualViewQuery.java
 * Created On : Dec 5, 2023
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.accountreceivable.query;

import org.hibernate.Query;

import com.siriuserp.accountpayable.dm.PaymentMethodType;
import com.siriuserp.accountreceivable.criteria.ReceiptFilterCriteria;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

public class ReceiptManualViewQuery extends AbstractGridViewQuery
{
	@Override
	public Query getQuery(ExecutorType type)
	{
		ReceiptFilterCriteria criteria = (ReceiptFilterCriteria) getFilterCriteria();
		StringBuilder builder = new StringBuilder();

		if (type.equals(ExecutorType.COUNT))
			builder.append("SELECT COUNT(receipt.id) ");

		builder.append("FROM ReceiptManual receipt WHERE receipt.id IS NOT NULL ");
		builder.append("AND receipt.id IS NOT NULL ");

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()))
			builder.append("AND receipt.organization.id = :org ");

		if (SiriusValidator.validateParam(criteria.getCode()))
			builder.append("AND receipt.code LIKE :code ");

		if (SiriusValidator.validateParam(criteria.getCustomerName()))
			builder.append("AND receipt.customer.fullName LIKE :customer ");

		if (SiriusValidator.validateParam(criteria.getType()))
			builder.append("AND receipt.receiptManualType.name LIKE :receiptType ");

		if (SiriusValidator.validateParam(criteria.getPaymentMethodType()))
			builder.append("AND receipt.receiptInformation.paymentMethodType =:methodType ");

		if (SiriusValidator.validateParam(criteria.getReference()))
			builder.append("AND receipt.purchaseMemoable.code LIKE :reference ");

		if (SiriusValidator.validateDate(criteria.getDateFrom()))
		{
			if (SiriusValidator.validateDate(criteria.getDateTo()))
				builder.append("AND receipt.date BETWEEN :dateFrom AND :dateTo ");
			else
				builder.append("AND receipt.date >= :dateFrom ");
		} else if (SiriusValidator.validateDate(criteria.getDateTo()))
			builder.append("AND receipt.date <= :dateTo ");

		if (type.equals(ExecutorType.HQL))
			builder.append("ORDER BY receipt.id DESC");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setReadOnly(true);

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()))
			query.setParameter("org", criteria.getOrganization());

		if (SiriusValidator.validateParam(criteria.getCode()))
			query.setParameter("code", "%" + criteria.getCode() + "%");

		if (SiriusValidator.validateParam(criteria.getCustomerName()))
			query.setParameter("customer", "%" + criteria.getCustomerName() + "%");

		if (SiriusValidator.validateParam(criteria.getType()))
			query.setParameter("receiptType", "%" + criteria.getType() + "%");

		if (SiriusValidator.validateParam(criteria.getPaymentMethodType()))
			query.setParameter("methodType", PaymentMethodType.valueOf(criteria.getPaymentMethodType()));

		if (SiriusValidator.validateParam(criteria.getReference()))
			query.setParameter("reference", "%" + criteria.getReference() + "%");

		if (SiriusValidator.validateDate(criteria.getDateFrom()))
			query.setParameter("dateFrom", criteria.getDateFrom());

		if (SiriusValidator.validateDate(criteria.getDateTo()))
			query.setParameter("dateTo", criteria.getDateTo());

		return query;
	}
}

/**
 * File Name  : PaymentManualViewQuery.java
 * Created On : Oct 17, 2023
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.accountpayable.query;

import org.hibernate.Query;

import com.siriuserp.accountpayable.criteria.PaymentFilterCriteria;
import com.siriuserp.accountpayable.dm.PaymentMethodType;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

public class PaymentManualViewQuery extends AbstractGridViewQuery
{
	@Override
	public Query getQuery(ExecutorType type)
	{
		PaymentFilterCriteria criteria = (PaymentFilterCriteria) getFilterCriteria();
		StringBuilder builder = new StringBuilder();

		if (type.equals(ExecutorType.COUNT))
			builder.append("SELECT COUNT(payment.id) ");

		builder.append("FROM PaymentManual payment WHERE payment.id IS NOT NULL ");
		builder.append("AND payment.id IS NOT NULL ");

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()))
			builder.append("AND payment.organization.id = :org ");

		if (SiriusValidator.validateParam(criteria.getCode()))
			builder.append("AND payment.code LIKE :code ");

		if (SiriusValidator.validateParam(criteria.getSupplierName()))
			builder.append("AND payment.supplier.fullName LIKE :supplier ");

		if (SiriusValidator.validateParam(criteria.getPaymentType()))
			builder.append("AND payment.paymentManualType.name LIKE :paymentType ");

		if (SiriusValidator.validateParam(criteria.getPaymentMethodType()))
			builder.append("AND payment.paymentInformation.paymentMethodType =:methodType ");

		if (SiriusValidator.validateParam(criteria.getReference()))
			builder.append("AND payment.salesMemoable.code LIKE :reference ");

		if (SiriusValidator.validateDate(criteria.getDateFrom()))
		{
			if (SiriusValidator.validateDate(criteria.getDateTo()))
				builder.append("AND payment.date BETWEEN :dateFrom AND :dateTo ");
			else
				builder.append("AND payment.date >= :dateFrom ");
		} else if (SiriusValidator.validateDate(criteria.getDateTo()))
			builder.append("AND payment.date <= :dateTo ");

		if (type.equals(ExecutorType.HQL))
			builder.append("ORDER BY payment.id DESC");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setReadOnly(true);

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()))
			query.setParameter("org", criteria.getOrganization());

		if (SiriusValidator.validateParam(criteria.getCode()))
			query.setParameter("code", "%" + criteria.getCode() + "%");

		if (SiriusValidator.validateParam(criteria.getSupplierName()))
			query.setParameter("supplier", "%" + criteria.getSupplierName() + "%");

		if (SiriusValidator.validateParam(criteria.getPaymentType()))
			query.setParameter("paymentType", "%" + criteria.getPaymentType() + "%");

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

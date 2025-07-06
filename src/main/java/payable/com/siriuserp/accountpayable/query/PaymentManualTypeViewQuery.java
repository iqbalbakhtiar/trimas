/**
 * File Name  : PaymentManualTypeViewQuery.java
 * Created On : Oct 17, 2023
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.accountpayable.query;

import org.hibernate.Query;

import com.siriuserp.accountpayable.criteria.PaymentFilterCriteria;
import com.siriuserp.accountpayable.dm.PaymentManualReferenceType;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

public class PaymentManualTypeViewQuery extends AbstractGridViewQuery
{
	@Override
	public Query getQuery(ExecutorType type)
	{
		PaymentFilterCriteria criteria = (PaymentFilterCriteria) getFilterCriteria();
		StringBuilder builder = new StringBuilder();

		if (type.equals(ExecutorType.COUNT))
			builder.append("SELECT COUNT(payment.id) ");

		builder.append("FROM PaymentManualType payment ");
		builder.append("WHERE payment.id IS NOT NULL ");

		if (SiriusValidator.validateParam(criteria.getCode()))
			builder.append("AND payment.code LIKE :code ");

		if (SiriusValidator.validateParam(criteria.getName()))
			builder.append("AND payment.name LIKE :name ");

		if (SiriusValidator.validateParam(criteria.getAccount()))
			builder.append("AND payment.account.name LIKE :account ");

		if (SiriusValidator.validateParam(criteria.getReferenceType()))
			builder.append("AND payment.referenceType =:referenceType ");

		if (SiriusValidator.validateParam(criteria.getStatus()))
			builder.append("AND payment.enabled =:status ");

		if (type.equals(ExecutorType.HQL))
			builder.append("ORDER BY payment.id DESC ");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setReadOnly(true);

		if (SiriusValidator.validateParam(criteria.getCode()))
			query.setParameter("code", "%" + criteria.getCode() + "%");

		if (SiriusValidator.validateParam(criteria.getName()))
			query.setParameter("name", "%" + criteria.getName() + "%");

		if (SiriusValidator.validateParam(criteria.getAccount()))
			query.setParameter("account", "%" + criteria.getAccount() + "%");

		if (SiriusValidator.validateParam(criteria.getReferenceType()))
			query.setParameter("referenceType", PaymentManualReferenceType.valueOf(criteria.getReferenceType()));

		if (SiriusValidator.validateParam(criteria.getStatus()))
			query.setParameter("status", Boolean.valueOf(criteria.getStatus()));

		return query;
	}
}

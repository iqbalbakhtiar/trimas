/**
 * File Name  : ReceiptManualTypeViewQuery.java
 * Created On : Dec 5, 2023
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.accountreceivable.query;

import org.hibernate.Query;

import com.siriuserp.accountreceivable.criteria.ReceiptFilterCriteria;
import com.siriuserp.accountreceivable.form.ReceiptManualReferenceType;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

public class ReceiptManualTypeViewQuery extends AbstractGridViewQuery
{
	@Override
	public Query getQuery(ExecutorType type)
	{
		ReceiptFilterCriteria criteria = (ReceiptFilterCriteria) getFilterCriteria();
		StringBuilder builder = new StringBuilder();

		if (type.equals(ExecutorType.COUNT))
			builder.append("SELECT COUNT(receipt.id) ");

		builder.append("FROM ReceiptManualType receipt ");
		builder.append("WHERE receipt.id IS NOT NULL ");

		if (SiriusValidator.validateParam(criteria.getCode()))
			builder.append("AND receipt.code LIKE :code ");

		if (SiriusValidator.validateParam(criteria.getName()))
			builder.append("AND receipt.name LIKE :name ");

		if (SiriusValidator.validateParam(criteria.getAccount()))
			builder.append("AND receipt.account.name LIKE :account ");

		if (SiriusValidator.validateParam(criteria.getReferenceType()))
			builder.append("AND receipt.referenceType =:referenceType ");

		if (SiriusValidator.validateParam(criteria.getStatus()))
			builder.append("AND receipt.enabled =:status ");

		if (type.equals(ExecutorType.HQL))
			builder.append("ORDER BY receipt.id DESC ");

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
			query.setParameter("referenceType", ReceiptManualReferenceType.valueOf(criteria.getReferenceType()));

		if (SiriusValidator.validateParam(criteria.getStatus()))
			query.setParameter("status", Boolean.valueOf(criteria.getStatus()));

		return query;
	}
}

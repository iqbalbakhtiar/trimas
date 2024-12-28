package com.siriuserp.accountreceivable.query;

import org.hibernate.Query;

import com.siriuserp.accountreceivable.criteria.BankAccountFilterCriteria;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.dm.AccountType;
import com.siriuserp.sdk.utility.SiriusValidator;

public class BankAccountViewQuery extends AbstractGridViewQuery {
	@Override
	public Query getQuery(ExecutorType executorType)
	{
		BankAccountFilterCriteria criteria = (BankAccountFilterCriteria) getFilterCriteria();

		StringBuilder builder = new StringBuilder();

		if (executorType.equals(ExecutorType.COUNT))
			builder.append("SELECT COUNT(DISTINCT acc) ");
		else
			builder.append("SELECT DISTINCT(acc) ");

		builder.append("FROM BankAccount acc ");
		builder.append("WHERE acc.code IS NOT NULL ");

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()))
			builder.append("AND acc.holder.id =:org ");

		if (SiriusValidator.validateParam(criteria.getCode()))
			builder.append("AND acc.code LIKE :code ");

		if (SiriusValidator.validateParam(criteria.getAccountName()))
			builder.append("AND acc.accountName LIKE :accountName ");

		if (SiriusValidator.validateParam(criteria.getBankName()))
			builder.append("AND acc.bankName LIKE :bankName ");

		if (SiriusValidator.validateParam(criteria.getHolderName()))
		{
			builder.append("AND (acc.holder.fulltName LIKE :holderName ");
		}

		if (SiriusValidator.validateParam(criteria.getType()))
			builder.append("AND acc.accountType =:accountType ");

		if (executorType.equals(ExecutorType.HQL))
			builder.append("ORDER BY acc.id DESC");

		Query query = getSession().createQuery(builder.toString());
		query.setReadOnly(true);

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()))
			query.setParameter("org", criteria.getOrganization());

		if (SiriusValidator.validateParam(criteria.getCode()))
			query.setParameter("code", "%" + criteria.getCode() + "%");

		if (SiriusValidator.validateParam(criteria.getAccountName()))
			query.setParameter("accountName", "%" + criteria.getAccountName() + "%");

		if (SiriusValidator.validateParam(criteria.getBankName()))
			query.setParameter("bankName", "%" + criteria.getBankName() + "%");

		if (SiriusValidator.validateParam(criteria.getHolderName()))
			query.setParameter("holderName", "%" + criteria.getHolderName() + "%");

		if (SiriusValidator.validateParam(criteria.getType()))
			query.setParameter("accountType", AccountType.valueOf(criteria.getType()));

		return query;
	}
}

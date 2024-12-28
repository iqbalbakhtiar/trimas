/**
 * Dec 21, 2009 3:02:14 PM
 * com.siriuserp.accounting.dao.impl
 * TaxPostingAccountDaoImpl.java
 */
package com.siriuserp.accounting.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.siriuserp.accounting.dao.TaxPostingAccountDao;
import com.siriuserp.accounting.dm.TaxPostingAccount;
import com.siriuserp.sdk.db.DaoHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@SuppressWarnings("unchecked")
@Component
public class TaxPostingAccountDaoImpl extends DaoHelper<TaxPostingAccount> implements TaxPostingAccountDao
{
	@Override
	public TaxPostingAccount load(Long accountingSchema, Long tax, Long closingAccountType)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("FROM TaxPostingAccount account ");
		builder.append("WHERE account.taxSchema.accountingSchema.id =:schema ");
		builder.append("AND account.accountType.id =:type ");
		builder.append("AND account.taxSchema.tax.id =:tax ");
		builder.append("AND account.account is not null ");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setReadOnly(true);
		query.setParameter("schema", accountingSchema);
		query.setParameter("type", closingAccountType);
		query.setParameter("tax", tax);

		List<TaxPostingAccount> list = query.list();
		if (!list.isEmpty())
			return list.get(0);

		return null;
	}

	@Override
	public TaxPostingAccount loadByOrganization(Long org, Long tax, Long closingAccountType)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("FROM TaxPostingAccount account ");
		builder.append("WHERE account.taxSchema.accountingSchema.organization.id =:org ");
		builder.append("AND account.accountType.id =:type ");
		builder.append("AND account.taxSchema.tax.id =:tax ");
		builder.append("AND account.account is not null ");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setReadOnly(true);
		query.setParameter("org", org);
		query.setParameter("type", closingAccountType);
		query.setParameter("tax", tax);

		List<TaxPostingAccount> list = query.list();
		if (!list.isEmpty())
			return list.get(0);

		return null;
	}
}

/**
 * Nov 20, 2008 1:58:40 PM
 * com.siriuserp.sdk.dao.impl
 * GLAccountBalanceDaoImpl.java
 */
package com.siriuserp.accounting.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.siriuserp.accounting.adapter.GLAccountBalanceAdapter;
import com.siriuserp.accounting.dao.GLAccountBalanceDao;
import com.siriuserp.accounting.dm.AccountingPeriod;
import com.siriuserp.accounting.dm.GLAccount;
import com.siriuserp.accounting.dm.GLAccountBalance;
import com.siriuserp.accounting.dm.GLCashType;
import com.siriuserp.accounting.dm.GLClosingType;
import com.siriuserp.sdk.db.DaoHelper;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.ReportType;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
@SuppressWarnings("unchecked")
public class GLAccountBalanceDaoImpl extends DaoHelper<GLAccountBalance> implements GLAccountBalanceDao
{
	@Override
	public GLAccountBalance load(GLAccount account, Currency currency, Party organization, AccountingPeriod accountingPeriod)
	{
		Query query = getSession().createQuery("FROM GLAccountBalance balance WHERE balance.organization.id =:org AND balance.accountingPeriod.id =:period AND balance.currency.id =:currency AND balance.account.id =:account ");
		query.setMaxResults(1);
		query.setParameter("org", organization.getId());
		query.setParameter("period", accountingPeriod.getId());
		query.setParameter("currency", currency.getId());
		query.setParameter("account", account.getId());

		Object object = query.uniqueResult();
		if (object != null)
			return (GLAccountBalance) object;

		return null;
	}

	public List<GLAccountBalanceAdapter> loadAll(AccountingPeriod accountingPeriod, Party organization, GLClosingType closingType)
	{
		Query query = getSession().createQuery(
				"SELECT new com.siriuserp.accounting.adapter.GLAccountBalanceAdapter(balance.account,SUM(balance.userTransaction.defaultdebet),SUM(balance.userTransaction.defaultcredit)) FROM GLAccountBalance balance WHERE balance.organization.id =:org AND balance.accountingPeriod.id =:period AND balance.account.closingType =:closingType GROUP BY balance.account.id");
		query.setParameter("org", organization.getId());
		query.setParameter("period", accountingPeriod.getId());
		query.setParameter("closingType", closingType);

		return query.list();
	}

	public List<GLAccountBalanceAdapter> loadAll(AccountingPeriod accountingPeriod, Party organization, ReportType reportType)
	{
		Query query = getSession().createQuery(
				"SELECT new com.siriuserp.accounting.adapter.GLAccountBalanceAdapter(balance.account,SUM(balance.userTransaction.defaultdebet),SUM(balance.userTransaction.defaultcredit)) FROM GLAccountBalance balance WHERE balance.organization.id =:org AND balance.accountingPeriod.id =:period AND balance.account.accountType.reportType =:reportType GROUP BY balance.account.id");
		query.setParameter("org", organization.getId());
		query.setParameter("period", accountingPeriod.getId());
		query.setParameter("reportType", reportType);

		return query.list();
	}

	public List<GLAccountBalanceAdapter> loadAll(AccountingPeriod accountingPeriod, Party organization)
	{
		return loadAll(accountingPeriod.getId(), organization.getId());
	}

	public List<GLAccountBalance> loadCurrNonDefault(AccountingPeriod accountingPeriod, Party organization)
	{
		Criteria query = getSession().createCriteria(GLAccountBalance.class);
		query.createCriteria("currency").add(Restrictions.eq("base", Boolean.FALSE));
		query.createCriteria("organization").add(Restrictions.eq("id", organization.getId()));
		query.createCriteria("accountingPeriod").add(Restrictions.eq("id", accountingPeriod.getId()));
		query.createCriteria("account").add(Restrictions.eq("cashType", GLCashType.CASH));

		return query.list();
	}

	public List<GLAccountBalance> loadCurrDefault(AccountingPeriod accountingPeriod, Party organization)
	{
		Criteria query = getSession().createCriteria(GLAccountBalance.class);
		query.createCriteria("currency").add(Restrictions.eq("base", true));
		query.createCriteria("organization").add(Restrictions.eq("id", organization.getId()));
		query.createCriteria("accountingPeriod").add(Restrictions.eq("id", accountingPeriod.getId()));

		return query.list();
	}

	@Override
	public List<GLAccountBalanceAdapter> loadAll(Long accountingPeriod, Long organization)
	{
		Query query = getSession().createQuery(
				"SELECT new com.siriuserp.accounting.adapter.GLAccountBalanceAdapter(balance,SUM(balance.userTransaction.defaultdebet),SUM(balance.userTransaction.defaultcredit)) FROM GLAccountBalance balance WHERE balance.organization.id =:org AND balance.accountingPeriod.id =:period GROUP BY balance.account.id");
		query.setParameter("org", organization);
		query.setParameter("period", accountingPeriod);

		return query.list();
	}

	@Override
	public List<Map<String, Object>> loadBalance(Long accountingPeriod, Long organization)
	{
		Query query = getSession().createQuery(getQBalance());
		query.setParameter("org", organization);
		query.setParameter("period", accountingPeriod);

		return null;
	}

	public String getQBalance()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT new map(");
		builder.append("bal.account.id as id");
		builder.append(",bal.account.code as code");
		builder.append(",bal.account.name as name");
		builder.append(",bal.userTransaction.debet as debet");
		builder.append(",bal.userTransaction.credit as credit");
		builder.append(",bal.userTransaction.defaultdebet as defaultdebet");
		builder.append(",bal.userTransaction.defaultcredit as defaultcredit) ");
		builder.append("FROM GLAccountBalance bal ");
		builder.append("WHERE bal.organization.id =:org ");
		builder.append("AND bal.accountingPeriod.id =:period ");
		builder.append("GROUP BY bal.account ");
		builder.append("ORDER BY bal.account.code ASC");

		return builder.toString();
	}

	public String getQJournal()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("FROM JournalEntryDetail det ");

		return builder.toString();
	}
}

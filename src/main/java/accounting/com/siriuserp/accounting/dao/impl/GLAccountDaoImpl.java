package com.siriuserp.accounting.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.siriuserp.accounting.criteria.GLAccountingFilterCriteria;
import com.siriuserp.accounting.criteria.GLRegisterFilterCriteria;
import com.siriuserp.accounting.dao.GLAccountDao;
import com.siriuserp.accounting.dm.AccountingPeriod;
import com.siriuserp.accounting.dm.GLAccount;
import com.siriuserp.accounting.dm.GLLevel;
import com.siriuserp.sdk.db.DaoHelper;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastList;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
@SuppressWarnings("unchecked")
public class GLAccountDaoImpl extends DaoHelper<GLAccount> implements GLAccountDao
{
	public GLAccount load(Long id)
	{
		return (GLAccount) getSession().load(GLAccount.class, id);
	}

	public List<GLAccount> loadAll()
	{
		Query query = getSession().createQuery("FROM GLAccount gl ORDER BY gl.code ASC");
		query.setCacheable(true);

		return query.list();
	}

	public List<GLAccount> loadAll(List<Long> ids)
	{
		if (ids.isEmpty())
			return loadAll();

		Query query = getSession().createQuery("FROM GLAccount gl WHERE gl.id in(:ids) ORDER BY gl.code ASC");
		query.setParameterList("ids", ids);
		query.setCacheable(true);

		return query.list();
	}

	public List<GLAccount> filter(GridViewFilterCriteria filterCriteria)
	{
		GLAccountingFilterCriteria filter = (GLAccountingFilterCriteria) filterCriteria;

		Criteria criteria = getSession().createCriteria(GLAccount.class);
		criteria.addOrder(Order.asc("code"));

		if (SiriusValidator.validateParamWithZeroPosibility(filter.getCoa()))
			criteria.createCriteria("coa").add(Restrictions.eq("id", filter.getCoa()));

		if (SiriusValidator.validateParam(filter.getCode()))
			criteria.add(Restrictions.like("code", filter.getCode(), MatchMode.ANYWHERE));

		if (SiriusValidator.validateParam(filter.getName()))
			criteria.add(Restrictions.or(Restrictions.like("name", filter.getName(), MatchMode.ANYWHERE), Restrictions.like("code", filter.getName(), MatchMode.ANYWHERE)));

		if (SiriusValidator.validateParam(filter.getLevel()))
			criteria.add(Restrictions.eq("level", GLLevel.valueOf(filter.getLevel())));

		criteria.setFirstResult(filter.start());
		criteria.setMaxResults(filter.getMax());

		return criteria.list();
	}

	public List<GLAccount> loadAllAccount()
	{
		Criteria criteria = getSession().createCriteria(GLAccount.class);
		criteria.add(Restrictions.eq("level", GLLevel.ACCOUNT));
		criteria.addOrder(Order.asc("code"));

		return criteria.list();
	}

	public List<Long> loadAllIDAccount()
	{
		Query query = getSession().createQuery("SELECT account.id FROM GLAccount account WHERE account.level = 'ACCOUNT' ORDER BY account.code ASC");
		query.setCacheable(true);
		return query.list();
	}

	public List<Long> loadReversedAccount(AccountingPeriod accountingPeriod, Party organization)
	{
		Query query = getSession().createQuery(
				"SELECT DISTINCT(detail.account.id) FROM JournalEntryDetail detail WHERE detail.journalEntry.id in(SELECT entry.id FROM JournalEntry entry WHERE entry.organization.id =:org AND entry.accountingPeriod.id =:period AND entry.entrySourceType in('AUTOAJUSTMENT','OPENING','CLOSING') )");
		query.setParameter("org", organization.getId());
		query.setParameter("period", accountingPeriod.getId());

		return query.list();
	}

	@Override
	public List<GLAccount> filter(GridViewQuery query)
	{
		query.setSession(getSession());
		return (List<GLAccount>) query.execute();
	}

	@Override
	public Long getMax(GridViewQuery query)
	{
		query.setSession(getSession());
		return query.count();
	}

	@Override
	public GLAccount load(String code)
	{
		if (SiriusValidator.validateParam(code))
		{
			Criteria criteria = getSession().createCriteria(GLAccount.class);
			criteria.setCacheable(true);
			criteria.add(Restrictions.eq("code", code));

			Object object = criteria.uniqueResult();
			if (object != null)
				return (GLAccount) object;
		}

		return null;
	}

	@Override
	public List<Long> loadAllIDAccount(GLRegisterFilterCriteria filterCriteria)
	{
		Query query = getSession().createQuery("SELECT account.id FROM GLAccount account WHERE account.level = 'ACCOUNT' ORDER BY account.code ASC");
		query.setFirstResult(filterCriteria.start());
		query.setMaxResults(filterCriteria.getMax());
		query.setCacheable(true);
		return query.list();
	}

	@Override
	public List<GLAccount> loadAllActiveAccount()
	{

		List<GLAccount> list = new FastList<GLAccount>();

		Criteria criteria = getSession().createCriteria(GLAccount.class);
		criteria.add(Restrictions.eq("level", GLLevel.ACCOUNT));
		criteria.addOrder(Order.asc("code"));

		for (GLAccount acc : (List<GLAccount>) criteria.list())
		{
			if (acc.getBalances().size() > 0)
			{
				list.add(acc);
			}
		}

		return list;
	}

	@Override
	public List<GLAccount> loadByCOAandType(Long coa, Long type)
	{
		Query query = getSession().createQuery("FROM GLAccount account WHERE account.level = 'ACCOUNT' AND account.coa.id =:coa AND account.accountType.id =:type ORDER BY account.code ASC");
		query.setParameter("coa", coa);
		query.setParameter("type", type);
		query.setCacheable(true);

		return query.list();
	}

	@Override
	public Long getCountJournalEntryDetail(Long glAccount)
	{
		StringBuilder builder = new StringBuilder();

		builder.append("SELECT COALESCE(COUNT(detail.id),0) ");
		builder.append("FROM JournalEntryDetail detail WHERE detail.account.id IS NOT NULL ");
		builder.append("AND detail.account.id =:glAccount ");

		Query query = getSession().createQuery(builder.toString());
		query.setParameter("glAccount", glAccount);

		return (Long) query.uniqueResult();
	}
}

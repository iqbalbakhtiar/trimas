package com.siriuserp.accounting.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Component;

import com.siriuserp.accounting.dao.JournalEntryDao;
import com.siriuserp.accounting.dm.AccountingPeriod;
import com.siriuserp.accounting.dm.JournalEntry;
import com.siriuserp.sdk.db.DaoHelper;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.exceptions.DataDeletionException;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@SuppressWarnings("unchecked")
@Component
public class JournalEntryDaoImpl extends DaoHelper<JournalEntry> implements JournalEntryDao
{
	public List<JournalEntry> loadAll(Integer start, Integer max)
	{
		Criteria criteria = getSession().createCriteria(JournalEntry.class);
		criteria.setCacheable(true);
		criteria.addOrder(Order.desc("id"));
		criteria.setFirstResult(start);
		criteria.setMaxResults(max);
		return criteria.list();
	}

	public void deleteAll(AccountingPeriod accountingPeriod, Party organization, AccountingPeriod next) throws DataDeletionException
	{
		/**
		 * delete all journal entry with source AUTOADJUSTMENT,CLOSING 
		 * (Note:this proccess sould olso delete opening balance entry created by previous AP closing process)
		 */
		StringBuilder builder = new StringBuilder();
		builder.append("FROM JournalEntry entry ");
		builder.append("WHERE entry.organization.id =:org ");
		builder.append("AND entry.accountingPeriod.id =:period ");
		builder.append("AND entry.entrySourceType IN('AUTOAJUSTMENT','CLOSING') ");

		Query query = getSession().createQuery(builder.toString());
		query.setParameter("org", organization.getId());
		query.setParameter("period", accountingPeriod.getId());

		List<JournalEntry> entrys = query.list();
		if (entrys != null && !entrys.isEmpty())
		{
			for (JournalEntry journalEntry : entrys)
				getSession().delete(journalEntry);
		}

		/**
		 * Delete Opening balance for next AP
		 * (Note:If The current targeted AP is not the last AP,this process just to make sure that the target Journal Entry has been removed
		 * else this is the real process for deleting next AP opening balance.)
		 */
		if (next != null)
		{
			Query nextPeriod = getSession().createQuery("FROM JournalEntry entry WHERE entry.accountingPeriod.id =:next AND entry.organization.id =:org AND entry.entrySourceType = 'OPENING'");
			nextPeriod.setCacheable(true);
			nextPeriod.setParameter("org", organization.getId());
			nextPeriod.setParameter("next", next.getId());

			List<JournalEntry> nextentrys = nextPeriod.list();
			if (nextentrys != null && !nextentrys.isEmpty())
			{
				for (JournalEntry journalEntry : nextentrys)
					getSession().delete(journalEntry);
			}
		}
	}

	@Override
	public Long getBatchedCount(Long period)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT COUNT(entry.id) FROM JournalEntry entry ");
		builder.append("WHERE entry.accountingPeriod.id =:period ");
		builder.append("AND entry.entryStatus = 'BATCHED '");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setReadOnly(true);
		query.setParameter("period", period);

		Object object = query.uniqueResult();
		if (object != null)
			return (Long) object;

		return Long.valueOf(0);
	}

	@Override
	public List<JournalEntry> load(Long organization, Long period)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("FROM JournalEntry journal ");
		builder.append("WHERE journal.organization.id =:org ");
		builder.append("AND journal.accountingPeriod.id =:period ");
		builder.append("AND journal.entryStatus = 'POSTED' ");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setParameter("org", organization);
		query.setParameter("period", period);

		return query.list();
	}
}

package com.siriuserp.accounting.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.siriuserp.accounting.adapter.SimpleAccountingReportAdapter;
import com.siriuserp.accounting.dao.JournalEntryDetailDao;
import com.siriuserp.accounting.dm.AccountingPeriod;
import com.siriuserp.accounting.dm.JournalEntryDetail;
import com.siriuserp.sdk.db.DaoHelper;
import com.siriuserp.sdk.dm.Party;

@SuppressWarnings("unchecked")
@Component
public class JournalEntryDetailDaoImpl extends DaoHelper<JournalEntryDetail> implements JournalEntryDetailDao
{
	public List<JournalEntryDetail> loadForOpen(AccountingPeriod accountingPeriod, Party organization)
	{
		Query entry = getSession()
				.createQuery("SELECT DISTINCT(detail) FROM JournalEntryDetail detail join detail.journalEntry entry WHERE entry.organization.id=:org AND entry.accountingPeriod.id =:period AND entry.entrySourceType in('AUTOAJUSTMENT','CLOSING')");
		entry.setParameter("org", organization.getId());
		entry.setParameter("period", accountingPeriod.getId());

		return entry.list();
	}

	public List<JournalEntryDetail> loadOpening(AccountingPeriod accountingPeriod, Party organization)
	{
		Query entry = getSession().createQuery(
				"SELECT DISTINCT(detail) FROM JournalEntryDetail detail join detail.journalEntry entry WHERE entry.organization.id=:org AND entry.accountingPeriod.id =:period AND entry.entrySourceType = 'OPENING' AND entry.entryStatus = 'POSTED'");
		entry.setParameter("org", organization.getId());
		entry.setParameter("period", accountingPeriod.getId());

		return entry.list();
	}

	@Override
	public List<JournalEntryDetail> loadRegister(SimpleAccountingReportAdapter adapter, Long account)
	{
		StringBuilder register = new StringBuilder();
		register.append("SELECT detail FROM JournalEntryDetail detail ");
		register.append("WHERE detail.account.id =:account ");
		register.append("AND detail.journalEntry.entrySourceType != 'OPENING' ");
		register.append("AND detail.journalEntry.entrySourceType != 'CLOSING' ");
		register.append("AND detail.journalEntry.accountingPeriod.id in (:periods) ");
		register.append("AND detail.journalEntry.organization.id in(:orgs) ");
		register.append("ORDER BY detail.account.id, detail.journalEntry.entryDate ASC, detail.journalEntry.code ASC ");

		Query entrys = getSession().createQuery(register.toString());
		entrys.setCacheable(true);
		entrys.setParameter("account", account);
		entrys.setParameterList("periods", adapter.getPeriods());
		entrys.setParameterList("orgs", adapter.getOrganizations());

		return entrys.list();
	}

	@Override
	public List<Long> loadIDAccount(SimpleAccountingReportAdapter adapter)
	{
		StringBuilder register = new StringBuilder();
		register.append("SELECT DISTINCT detail.account.id FROM JournalEntryDetail detail ");
		register.append("WHERE detail.journalEntry.entrySourceType != 'OPENING' ");
		register.append("AND detail.journalEntry.entrySourceType != 'CLOSING' ");
		register.append("AND detail.journalEntry.accountingPeriod.id in (:periods) ");
		register.append("AND detail.journalEntry.organization.id in(:orgs) ");
		register.append("ORDER BY detail.account.code ASC");

		Query entrys = getSession().createQuery(register.toString());
		entrys.setCacheable(true);
		entrys.setParameterList("periods", adapter.getPeriods());
		entrys.setParameterList("orgs", adapter.getOrganizations());

		return entrys.list();
	}
}

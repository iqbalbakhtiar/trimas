package com.siriuserp.accounting.dao;

import java.util.List;

import com.siriuserp.accounting.adapter.SimpleAccountingReportAdapter;
import com.siriuserp.accounting.dm.AccountingPeriod;
import com.siriuserp.accounting.dm.JournalEntryDetail;
import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.dm.Party;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public interface JournalEntryDetailDao extends Dao<JournalEntryDetail>
{
	public List<JournalEntryDetail> loadForOpen(AccountingPeriod accountingPeriod, Party organization);
	public List<JournalEntryDetail> loadOpening(AccountingPeriod accountingPeriod, Party organization);
	public List<JournalEntryDetail> loadRegister(SimpleAccountingReportAdapter adapter, Long account);
	public List<Long> loadIDAccount(SimpleAccountingReportAdapter adapter);
}

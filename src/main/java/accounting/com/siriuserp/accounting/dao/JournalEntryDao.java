package com.siriuserp.accounting.dao;

import java.util.List;

import com.siriuserp.accounting.dm.AccountingPeriod;
import com.siriuserp.accounting.dm.JournalEntry;
import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.base.Filterable;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.exceptions.DataDeletionException;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public interface JournalEntryDao extends Dao<JournalEntry>, Filterable
{
	public void deleteAll(AccountingPeriod accountingPeriod, Party organization, AccountingPeriod next) throws DataDeletionException;
	public Long getBatchedCount(Long period);
	public List<JournalEntry> load(Long organization, Long period);
}

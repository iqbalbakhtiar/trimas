package com.siriuserp.accounting.dao;

import java.util.Date;
import java.util.List;

import com.siriuserp.accounting.dm.AccountingPeriod;
import com.siriuserp.accounting.dm.JournalEntry;
import com.siriuserp.accounting.dm.PeriodStatus;
import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.base.Filterable;
import com.siriuserp.sdk.dm.Month;
import com.siriuserp.sdk.exceptions.DataEditException;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public interface AccountingPeriodDao extends Dao<AccountingPeriod>, Filterable
{
	public AccountingPeriod loadToday(Long organization);
	public void openStatusNextPeriod(AccountingPeriod accountingPeriod) throws DataEditException;
	public void changeStatus(AccountingPeriod accountingPeriod, PeriodStatus periodStatus) throws DataEditException;
	public List<AccountingPeriod> loadAllNext(AccountingPeriod accountingPeriod);
	public List<Long> loadAllIdByOrgs(List<Long> ids);
	public List<AccountingPeriod> loadAll(List<Long> organizations);
	public AccountingPeriod loadNext(AccountingPeriod from);
	public AccountingPeriod load(Long organization, Date date, PeriodStatus status);
	public AccountingPeriod loadPrev(AccountingPeriod from);
	public List<AccountingPeriod> loadAllPrevInYear(AccountingPeriod from);
	public List<Long> loadAllIDPrevInYear(AccountingPeriod from);
	public Date loadMin(List<Long> periods);
	public List<Integer> loadAllAsYear();
	public List<Long> getAllIdByYearAndMonth(Integer year, Month month, Long org);
	public List<JournalEntry> loadJournals(Long periodId);
}

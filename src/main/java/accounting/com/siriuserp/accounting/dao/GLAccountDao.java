package com.siriuserp.accounting.dao;

import java.util.List;

import com.siriuserp.accounting.criteria.GLRegisterFilterCriteria;
import com.siriuserp.accounting.dm.AccountingPeriod;
import com.siriuserp.accounting.dm.GLAccount;
import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.base.Filterable;
import com.siriuserp.sdk.dm.Party;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public interface GLAccountDao extends Dao<GLAccount>, Filterable
{
	public List<GLAccount> loadAllAccount();
	public List<GLAccount> loadAllActiveAccount();
	public List<Long> loadAllIDAccount();
	public List<Long> loadAllIDAccount(GLRegisterFilterCriteria filterCriteria);
	public List<Long> loadReversedAccount(AccountingPeriod accountingPeriod, Party organization);
	public List<GLAccount> loadAll(List<Long> ids);
	public List<GLAccount> loadByCOAandType(Long coa, Long type);
	public GLAccount load(String code);
	public Long getCountJournalEntryDetail(Long glAccount);
}

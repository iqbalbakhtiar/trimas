/**
 * Nov 20, 2008 1:57:04 PM
 * com.siriuserp.sdk.dao
 * GLAccountBalanceDao.java
 */
package com.siriuserp.accounting.dao;

import java.util.List;
import java.util.Map;

import com.siriuserp.accounting.adapter.GLAccountBalanceAdapter;
import com.siriuserp.accounting.dm.AccountingPeriod;
import com.siriuserp.accounting.dm.GLAccount;
import com.siriuserp.accounting.dm.GLAccountBalance;
import com.siriuserp.accounting.dm.GLClosingType;
import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.base.Filterable;
import com.siriuserp.sdk.base.ReportDao;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.ReportType;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public interface GLAccountBalanceDao extends Dao<GLAccountBalance>, ReportDao, Filterable
{
	public GLAccountBalance load(GLAccount account, Currency currency, Party organization, AccountingPeriod accountingPeriod);
	public List<GLAccountBalanceAdapter> loadAll(AccountingPeriod accountingPeriod, Party organization, GLClosingType closingType);
	public List<GLAccountBalance> loadCurrNonDefault(AccountingPeriod accountingPeriod, Party organization);
	public List<GLAccountBalance> loadCurrDefault(AccountingPeriod accountingPeriod, Party organization);
	public List<GLAccountBalanceAdapter> loadAll(AccountingPeriod accountingPeriod, Party organization);
	public List<GLAccountBalanceAdapter> loadAll(Long accountingPeriod, Long organization);
	public List<GLAccountBalanceAdapter> loadAll(AccountingPeriod accountingPeriod, Party organization, ReportType reportType);
	public List<Map<String, Object>> loadBalance(Long accountingPeriod, Long organization);
}

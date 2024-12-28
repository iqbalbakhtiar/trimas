/**
 * Dec 1, 2008 11:53:24 AM
 * com.siriuserp.sdk.dao
 * IncomeSummaryBalanceDao.java
 */
package com.siriuserp.accounting.dao;

import com.siriuserp.accounting.dm.AccountingPeriod;
import com.siriuserp.accounting.dm.IncomeSummaryBalance;
import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.exceptions.DataDeletionException;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public interface IncomeSummaryBalanceDao extends Dao<IncomeSummaryBalance>
{
	public IncomeSummaryBalance load(Long accountingPeriod, Long organization);
	public void delete(AccountingPeriod accountingPeriod, Party organization) throws DataDeletionException;
}

/**
 * Jan 30, 2009 11:08:50 AM
 * com.siriuserp.sdk.filter
 * AccountingReportFilterCriteria.java
 */
package com.siriuserp.accounting.criteria;

import com.siriuserp.accounting.dm.AccountingPeriod;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.filter.FilterCriteria;

import javolution.util.FastList;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public interface AccountingReportFilterCriteria extends FilterCriteria
{
	public void setOrganizations(FastList<Party> organizations);
	public FastList<Party> getOrganizations();

	public void setAccountingPeriods(FastList<AccountingPeriod> accountingPeriods);
	public FastList<AccountingPeriod> getAccountingPeriods();

	public void setOrganizationIds(FastList<Long> organizations);
	public FastList<Long> getOrganizationIds();

	public void setAccountingPeriodIds(FastList<Long> accountingPeriods);
	public FastList<Long> getAccountingPeriodIds();
}

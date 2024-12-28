/**
 * Jan 30, 2009 11:10:46 AM
 * com.siriuserp.sdk.filter
 * DefaultAccountingReportFilterCriteria.java
 */
package com.siriuserp.accounting.criteria;

import com.siriuserp.accounting.dm.AccountingPeriod;
import com.siriuserp.sdk.dm.Party;

import javolution.util.FastList;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Getter
@Setter
public class DefaultAccountingReportFilterCriteria implements AccountingReportFilterCriteria
{
	private static final long serialVersionUID = 1781234320793591180L;

	private FastList<Long> organizationIds = new FastList<Long>();
	private FastList<Long> accountingPeriodIds = new FastList<Long>();
	private FastList<Long> prevAccountingPeriodIds = new FastList<Long>();
	private FastList<Party> organizations = new FastList<Party>();
	private FastList<AccountingPeriod> accountingPeriods = new FastList<AccountingPeriod>();
	private FastList<AccountingPeriod> prevAccountingPeriods = new FastList<AccountingPeriod>();

	public FastList<Long> getPrevAccountingPeriodIds()
	{
		return prevAccountingPeriodIds;
	}

	public void setPrevAccountingPeriodIds(FastList<Long> prevAccountingPeriodIds)
	{
		this.prevAccountingPeriodIds = prevAccountingPeriodIds;
	}

	public FastList<AccountingPeriod> getPrevAccountingPeriods()
	{
		return prevAccountingPeriods;
	}

	public void setPrevAccountingPeriods(FastList<AccountingPeriod> prevAccountingPeriods)
	{
		this.prevAccountingPeriods = prevAccountingPeriods;
	}

	@Override
	public FastList<Long> getOrganizationIds()
	{
		return organizationIds;
	}

	@Override
	public void setOrganizationIds(FastList<Long> organizations)
	{
		this.organizationIds = organizations;
	}

	@Override
	public FastList<Long> getAccountingPeriodIds()
	{
		return accountingPeriodIds;
	}

	@Override
	public void setAccountingPeriodIds(FastList<Long> accountingPeriods)
	{
		this.accountingPeriodIds = accountingPeriods;
	}

	@Override
	public FastList<AccountingPeriod> getAccountingPeriods()
	{
		return this.accountingPeriods;
	}

	@Override
	public FastList<Party> getOrganizations()
	{
		return this.organizations;
	}

	@Override
	public void setAccountingPeriods(FastList<AccountingPeriod> accountingPeriods)
	{
		this.accountingPeriods = accountingPeriods;
	}

	@Override
	public void setOrganizations(FastList<Party> organizations)
	{
		this.organizations = organizations;
	}
}

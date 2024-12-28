/**
 * Dec 2, 2008 10:36:21 AM
 * com.siriuserp.reporting.accounting.dto
 * SimpleAccountingReportAdapter.java
 */
package com.siriuserp.accounting.adapter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.siriuserp.accounting.dm.AccountingPeriod;
import com.siriuserp.accounting.dm.GLAccount;
import com.siriuserp.accounting.dm.GLPostingType;
import com.siriuserp.accounting.dm.JournalEntryType;
import com.siriuserp.sdk.dm.AccessType;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.utility.DecimalHelper;

import javolution.util.FastList;
import javolution.util.FastMap;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Getter
@Setter
@SuppressWarnings("unchecked")
public class SimpleAccountingReportAdapter extends AbstractAccountingReportAdapter
{
	private static final long serialVersionUID = -2168992964604410839L;

	private Long currency;
	private Long accountId;

	private BigDecimal netIncome = BigDecimal.ZERO;
	private BigDecimal opening = BigDecimal.ZERO;

	private Date dateFrom;
	private Date dateTo;

	private AccountingPeriod accountingPeriod;
	private Currency defaultCurrency;

	private FastList<Long> periods = new FastList<Long>();
	private FastList<Long> organizations = new FastList<Long>();
	private FastList<Long> openingPeriods = new FastList<Long>();
	private FastList<SimpleAccountingReportAdapter> reports = new FastList<SimpleAccountingReportAdapter>();
	private FastList<WorksheetReportAdapter> worksheets = new FastList<WorksheetReportAdapter>();
	private FastMap<String, Object> reportMaps = new FastMap<String, Object>();
	private FastList<Long> accounts = new FastList<Long>();
	private FastList<AccountingPeriod> openingPeriodObj = new FastList<AccountingPeriod>();

	public SimpleAccountingReportAdapter()
	{
	}

	public SimpleAccountingReportAdapter(GLAccount account, BigDecimal amount, GLPostingType postingType, JournalEntryType entryType)
	{
		setAccount(account);
		setJournalEntryType(entryType);

		switch (postingType)
		{
		case DEBET:
			setDebet(amount);
			break;

		default:
			setCredit(amount);
			break;
		}
	}

	public SimpleAccountingReportAdapter(GLAccount account, BigDecimal debet, BigDecimal credit)
	{
		setAccount(account);
		setDebet(debet);
		setCredit(credit);
	}

	public SimpleAccountingReportAdapter(GLAccount account, BigDecimal debet, BigDecimal credit, BigDecimal adjustmentdebet, BigDecimal adjustmentcredit)
	{
		setAccount(account);

		BigDecimal amount = debet.subtract(credit);
		BigDecimal adjutment = adjustmentdebet.subtract(adjustmentcredit);

		if (amount.compareTo(BigDecimal.ZERO) > 0)
			setDebet(amount);
		else
			setCredit(amount.multiply(BigDecimal.valueOf(-1)));

		if (adjutment.compareTo(BigDecimal.ZERO) > 0)
			setAdjustmentdebet(adjutment);
		else
			setAdjustmentcredit(adjutment.multiply(BigDecimal.valueOf(-1)));
	}

	@Override
	public FastList<Long> getOrganizations()
	{
		return organizations;
	}

	@Override
	public void setOrganizations(FastList<Long> organizations)
	{
		this.organizations = organizations;
	}

	public BigDecimal getTotalDebet()
	{
		BigDecimal amount = BigDecimal.ZERO;

		for (SimpleAccountingReportAdapter adapter : getReports())
			amount = amount.add(DecimalHelper.safe(adapter.getDebet()));

		return amount;
	}

	public BigDecimal getTotalCredit()
	{
		BigDecimal amount = BigDecimal.ZERO;

		for (SimpleAccountingReportAdapter adapter : getReports())
			amount = amount.add(DecimalHelper.safe(adapter.getCredit()));

		return amount;
	}

	public BigDecimal getTotalAdjDebet()
	{
		BigDecimal amount = BigDecimal.ZERO;

		for (SimpleAccountingReportAdapter adapter : getReports())
			amount = amount.add(DecimalHelper.safe(adapter.getAdjustmentdebet()));

		return amount;
	}

	public BigDecimal getTotalAdjCredit()
	{
		BigDecimal amount = BigDecimal.ZERO;

		for (SimpleAccountingReportAdapter adapter : getReports())
			amount = amount.add(DecimalHelper.safe(adapter.getAdjustmentcredit()));

		return amount;
	}

	public BigDecimal getTotalAsset()
	{
		BigDecimal decima = BigDecimal.ZERO;

		List<BalancesheetAdapter> asset = (List<BalancesheetAdapter>) getReportMaps().get("assets");
		if (asset != null && !asset.isEmpty())
		{
			for (BalancesheetAdapter adapter : asset)
				decima = decima.add(adapter.getActual());
		}

		return decima;
	}

	public BigDecimal getTotalEquity()
	{
		BigDecimal decima = BigDecimal.ZERO;

		List<BalancesheetAdapter> asset = (List<BalancesheetAdapter>) getReportMaps().get("equitys");
		if (asset != null && !asset.isEmpty())
		{
			for (BalancesheetAdapter adapter : asset)
				decima = decima.add(adapter.getActual());
		}

		return decima;
	}

	public BigDecimal getTotalLiability()
	{
		BigDecimal decima = BigDecimal.ZERO;

		List<BalancesheetAdapter> asset = (List<BalancesheetAdapter>) getReportMaps().get("liabilities");
		if (asset != null && !asset.isEmpty())
		{
			for (BalancesheetAdapter adapter : asset)
				decima = decima.add(adapter.getActual());
		}

		return decima;
	}

	public BigDecimal getTotalDWSTrialBalance()
	{
		BigDecimal decima = BigDecimal.ZERO;

		for (WorksheetReportAdapter adapter : getWorksheets())
			decima = decima.add(adapter.getDebet());

		return decima;
	}

	public BigDecimal getTotalCWSTrialBalance()
	{
		BigDecimal decima = BigDecimal.ZERO;

		for (WorksheetReportAdapter adapter : getWorksheets())
			decima = decima.add(adapter.getCredit());

		return decima;
	}

	public BigDecimal getTotalDWSAdjutment()
	{
		BigDecimal decima = BigDecimal.ZERO;

		for (WorksheetReportAdapter adapter : getWorksheets())
			decima = decima.add(adapter.getAdjustmentcredit());

		return decima;
	}

	public BigDecimal getTotalCWSAdjutment()
	{
		BigDecimal decima = BigDecimal.ZERO;

		for (WorksheetReportAdapter adapter : getWorksheets())
			decima = decima.add(adapter.getAdjustmentcredit());

		return decima;
	}

	public BigDecimal getTotalDWSIS()
	{
		BigDecimal decima = BigDecimal.ZERO;

		for (WorksheetReportAdapter adapter : getWorksheets())
			decima = decima.add(adapter.getStatementdebet());

		return decima;
	}

	public BigDecimal getTotalCWSIS()
	{
		BigDecimal decima = BigDecimal.ZERO;

		for (WorksheetReportAdapter adapter : getWorksheets())
			decima = decima.add(adapter.getStatementcredit());

		return decima;
	}

	public BigDecimal getTotalDWSBS()
	{
		BigDecimal decima = BigDecimal.ZERO;

		for (WorksheetReportAdapter adapter : getWorksheets())
			decima = decima.add(adapter.getBalancesheetdebet());

		return decima;
	}

	public BigDecimal getTotalCWSBS()
	{
		BigDecimal decima = BigDecimal.ZERO;

		for (WorksheetReportAdapter adapter : getWorksheets())
			decima = decima.add(adapter.getBalancesheetcredit());

		return decima;
	}

	@Override
	public AccessType getAccessType()
	{
		return null;
	}

	@Override
	public void setAccessType(AccessType accessType)
	{
	}

	public int getSize()
	{
		return getAccounts().size();
	}
}

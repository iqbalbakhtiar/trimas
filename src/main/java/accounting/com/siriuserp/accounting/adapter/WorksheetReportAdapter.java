/**
 * Dec 10, 2008 10:10:40 AM
 * com.siriuserp.reporting.accounting.adapter
 * WorksheetReportAdapter.java
 */
package com.siriuserp.accounting.adapter;

import java.math.BigDecimal;

import com.siriuserp.accounting.dm.GLAccount;
import com.siriuserp.sdk.dm.AccessType;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Getter
@Setter
public class WorksheetReportAdapter extends AbstractAccountingReportAdapter
{
	private static final long serialVersionUID = 5374244969180362821L;

	private BigDecimal statementdebet = BigDecimal.valueOf(0);
	private BigDecimal statementcredit = BigDecimal.valueOf(0);
	private BigDecimal balancesheetdebet = BigDecimal.valueOf(0);
	private BigDecimal balancesheetcredit = BigDecimal.valueOf(0);
	private BigDecimal adjusteddebet = BigDecimal.valueOf(0);
	private BigDecimal adjustedcredit = BigDecimal.valueOf(0);

	public WorksheetReportAdapter()
	{
	}

	public WorksheetReportAdapter(GLAccount account, BigDecimal debet, BigDecimal credit, BigDecimal adjdebet, BigDecimal adjcredit)
	{
		setAccount(account);

		if (debet.subtract(credit).compareTo(BigDecimal.valueOf(0)) > 0)
			setDebet(debet.subtract(credit));
		else
			setCredit(debet.subtract(credit).multiply(BigDecimal.valueOf(-1)));

		if (adjdebet.subtract(adjcredit).compareTo(BigDecimal.valueOf(0)) > 0)
			setAdjustmentdebet(adjdebet.subtract(adjcredit));
		else
			setAdjustmentcredit(adjdebet.subtract(adjcredit).multiply(BigDecimal.valueOf(-1)));

		if (getDebet().add(getAdjustmentdebet()).subtract(getCredit().add(getAdjustmentcredit())).compareTo(BigDecimal.valueOf(0)) > 0)
			setAdjusteddebet(getDebet().add(getAdjustmentdebet()).subtract(getCredit().add(getAdjustmentcredit())));
		else
			setAdjustedcredit(getDebet().add(getAdjustmentdebet()).subtract(getCredit().add(getAdjustmentcredit())).multiply(BigDecimal.valueOf(-1)));

		switch (account.getAccountType().getReportType())
		{
		case INCOMESTATEMENT:
			if (debet.add(adjustmentdebet).subtract(credit.add(adjustmentcredit)).compareTo(BigDecimal.valueOf(0)) > 0)
				setStatementdebet(debet.add(adjustmentdebet).subtract(credit.add(adjustmentcredit)));
			else
				setStatementcredit(debet.add(adjustmentdebet).subtract(credit.add(adjustmentcredit)).multiply(BigDecimal.valueOf(-1)));
			break;

		case BALANCESHEET:
			if (debet.add(adjustmentdebet).subtract(credit.add(adjustmentcredit)).compareTo(BigDecimal.valueOf(0)) > 0)
				setBalancesheetdebet(debet.add(adjustmentdebet).subtract(credit.add(adjustmentcredit)));
			else
				setBalancesheetcredit(debet.add(adjustmentdebet).subtract(credit.add(adjustmentcredit)).multiply(BigDecimal.valueOf(-1)));
			break;
		}
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
}

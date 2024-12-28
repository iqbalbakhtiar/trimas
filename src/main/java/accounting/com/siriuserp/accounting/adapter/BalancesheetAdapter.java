/**
 * Dec 9, 2008 4:39:29 PM
 * com.siriuserp.reporting.accounting.adapter
 * BalanceSheetReportAdapter.java
 */
package com.siriuserp.accounting.adapter;

import java.math.BigDecimal;

import com.siriuserp.accounting.dm.GLAccount;
import com.siriuserp.accounting.dm.GLPostingType;
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
public class BalancesheetAdapter extends AbstractAccountingReportAdapter
{
	private static final long serialVersionUID = -5596454710204034464L;

	private boolean negative;
	private GLAccount account;
	private BigDecimal amount = BigDecimal.ZERO;
	private BigDecimal actual = BigDecimal.ZERO;
	private GLPostingType postingType;

	public BalancesheetAdapter()
	{
	}

	public BalancesheetAdapter(GLAccount account, BigDecimal amount)
	{
		setAccount(account);
		setActual(amount);

		if (amount.compareTo(BigDecimal.ZERO) < 0)
		{
			setAmount(amount.multiply(BigDecimal.valueOf(-1)));
			setNegative(true);
		} else
			setAmount(amount);
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

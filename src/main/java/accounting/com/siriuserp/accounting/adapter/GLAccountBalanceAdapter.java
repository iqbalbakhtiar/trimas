/**
 * Nov 28, 2008 10:28:39 AM
 * com.siriuserp.sdk.adapter
 * GLAccountBalanceAdapter.java
 */
package com.siriuserp.accounting.adapter;

import java.math.BigDecimal;

import com.siriuserp.accounting.dm.GLAccount;
import com.siriuserp.accounting.dm.GLAccountBalance;
import com.siriuserp.sdk.adapter.AbstractServiceAdapter;
import com.siriuserp.sdk.dm.Currency;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Getter
@Setter
public class GLAccountBalanceAdapter extends AbstractServiceAdapter
{
	private static final long serialVersionUID = 4868924905116746952L;

	private BigDecimal debet = BigDecimal.ZERO;
	private BigDecimal credit = BigDecimal.ZERO;
	private BigDecimal factor = BigDecimal.ONE;

	private Currency currency;
	private GLAccount account;
	private GLAccountBalance accountBalance;

	public GLAccountBalanceAdapter(BigDecimal debet, BigDecimal credit)
	{
		if (debet != null)
			this.debet = debet;

		if (credit != null)
			this.credit = credit;
	}

	public GLAccountBalanceAdapter(GLAccount account, BigDecimal debet, BigDecimal credit)
	{
		if (debet != null)
			this.debet = debet;

		if (credit != null)
			this.credit = credit;

		this.account = account;
	}

	public GLAccountBalanceAdapter(GLAccountBalance accountBalance, BigDecimal debet, BigDecimal credit)
	{
		if (debet != null)
			this.debet = debet;

		if (credit != null)
			this.credit = credit;

		this.accountBalance = accountBalance;
	}

	public GLAccountBalanceAdapter(Currency currency, BigDecimal debet, BigDecimal credit, BigDecimal factor)
	{
		if (currency != null)
			this.currency = currency;

		if (debet != null)
			this.debet = debet;

		if (credit != null)
			this.credit = credit;

		if (factor != null)
			this.factor = factor;
	}
}

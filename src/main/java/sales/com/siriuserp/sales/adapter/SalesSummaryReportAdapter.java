/**
 * File Name  : SalesSummaryReportAdapter.java
 * Created On : Jul 11, 2019
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.sales.adapter;

import java.math.BigDecimal;

import com.siriuserp.sdk.adapter.AbstractUIAdapter;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.utility.DecimalHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Getter
@Setter
@NoArgsConstructor
public class SalesSummaryReportAdapter extends AbstractUIAdapter
{
	private static final long serialVersionUID = -7631012998001732310L;

	private Party customer;
	private BigDecimal quantity = BigDecimal.ZERO;
	private BigDecimal amount = BigDecimal.ZERO;
	private BigDecimal taxAmount = BigDecimal.ZERO;

	public SalesSummaryReportAdapter(Party customer, BigDecimal quantity, BigDecimal amount)
	{
		this.customer = customer;
		this.quantity = DecimalHelper.safe(quantity);
		this.amount = DecimalHelper.safe(amount);
	}

	public SalesSummaryReportAdapter(Party customer, BigDecimal quantity, BigDecimal amount, BigDecimal taxRate)
	{
		this.customer = customer;
		this.quantity = DecimalHelper.safe(quantity);
		this.amount = DecimalHelper.safe(amount);
		this.taxAmount = amount.multiply(DecimalHelper.safe(taxRate).divide(BigDecimal.valueOf(100)));
	}

	public BigDecimal getTotal()
	{
		return getAmount().add(getTaxAmount());
	}
}

/**
 * File Name  : InvoiceVerificationAdapter.java
 * Created On : Feb 26, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.accountpayable.adapter;

import java.math.BigDecimal;

import com.siriuserp.accountpayable.dm.DebitMemoManual;
import com.siriuserp.accountpayable.dm.InvoiceVerification;
import com.siriuserp.accountpayable.dm.InvoiceVerificationItem;
import com.siriuserp.sdk.adapter.AbstractUIAdapter;
import com.siriuserp.sdk.utility.DecimalHelper;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Getter
@Setter
public class InvoiceVerificationAdapter extends AbstractUIAdapter
{
	private static final long serialVersionUID = 1687613468170404435L;

	private InvoiceVerification invoiceVerification;

	public InvoiceVerificationAdapter(InvoiceVerification invoiceVerification)
	{
		this.invoiceVerification = invoiceVerification;
	}

	public BigDecimal getPurchase()
	{
		BigDecimal amount = BigDecimal.ZERO;

		for (InvoiceVerificationItem item : getInvoiceVerification().getItems())
			amount = amount.add(item.getQuantity().multiply(item.getMoney().getAmount()));

		return amount;
	}

	public BigDecimal getDefPurchase()
	{
		BigDecimal amount = BigDecimal.ZERO;

		for (InvoiceVerificationItem item : getInvoiceVerification().getItems())
			amount = amount.add(item.getQuantity().multiply(item.getMoney().getAmount().multiply(item.getMoney().getRate())));

		return amount;
	}

	public BigDecimal getDiscount()
	{
		BigDecimal amount = BigDecimal.ZERO;

		for (InvoiceVerificationItem item : getInvoiceVerification().getItems())
			amount = amount.add(item.getDiscount());

		return amount;
	}

	public BigDecimal getDefDiscount()
	{
		BigDecimal amount = BigDecimal.ZERO;

		for (InvoiceVerificationItem item : getInvoiceVerification().getItems())
			amount = amount.add(item.getDiscount().multiply(item.getMoney().getRate()));

		return amount;
	}

	public BigDecimal getSubTotal()
	{
		return getPurchase().subtract(getDiscount());
	}

	public BigDecimal getDefSubTotal()
	{
		return getDefPurchase().subtract(getDefDiscount());
	}

	public BigDecimal getTaxAmount()
	{
		return getInvoiceVerification().getTax() != null ? DecimalHelper.percent(getInvoiceVerification().getTax().getTaxRate()).multiply(getSubTotal()) : BigDecimal.ZERO;
	}

	public BigDecimal getDefTaxAmount()
	{
		return getTaxAmount().multiply(getInvoiceVerification().getMoney().getRate());
	}

	public BigDecimal getTotalAfterTax()
	{
		return getSubTotal().add(getTaxAmount());
	}

	public BigDecimal getDefTotalAfterTax()
	{
		return getDefSubTotal().add(getDefTaxAmount());
	}

	public BigDecimal getMemoAmount()
	{
		BigDecimal amount = BigDecimal.ZERO;

		/*for (DebitMemo memo : getInvoiceVerification().getDebitMemos())
			amount = amount.add(memo.getAmountAfterTax());*/

		return amount;
	}

	public BigDecimal getDefMemoAmount()
	{
		return getMemoAmount().multiply(getInvoiceVerification().getMoney().getRate());
	}

	public BigDecimal getMemoManualAmount()
	{
		BigDecimal amount = BigDecimal.ZERO;

		for (DebitMemoManual memo : getInvoiceVerification().getDebitMemoManuals())
			amount = amount.add(memo.getAmount());

		return amount;
	}

	public BigDecimal getDefMemoManualAmount()
	{
		return getMemoManualAmount().multiply(getInvoiceVerification().getMoney().getRate());
	}

	public BigDecimal getTotal()
	{
		return getTotalAfterTax().subtract(getMemoAmount()).subtract(getMemoManualAmount());
	}

	public BigDecimal getDefTotal()
	{
		return getTotal().multiply(getInvoiceVerification().getMoney().getRate());
	}
}

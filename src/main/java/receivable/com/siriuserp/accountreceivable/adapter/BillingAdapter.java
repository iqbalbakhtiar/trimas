package com.siriuserp.accountreceivable.adapter;

import com.siriuserp.accountreceivable.dm.Billing;
import com.siriuserp.accountreceivable.dm.BillingItem;
import com.siriuserp.accountreceivable.dm.BillingReferenceItem;
import com.siriuserp.sdk.adapter.AbstractUIAdapter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BillingAdapter extends AbstractUIAdapter
{
	private static final long serialVersionUID = 3889107143175166569L;

	private Billing billing;

	public BigDecimal getTotalLineAmount()
	{
		BigDecimal totalLineAmount = BigDecimal.ZERO;

		if (billing != null && billing.getItems() != null)
		{
			for (BillingItem item : billing.getItems())
			{
				BillingReferenceItem refItem = item.getBillingReferenceItem();
				if (refItem != null)
				{
					BigDecimal itemTotal = refItem.getTotalAmount();
					totalLineAmount = totalLineAmount.add(itemTotal);
				}
			}
		}

		return totalLineAmount;
	}

	/* Method Calculation For Billing Pre-edit */
	public BigDecimal getTaxAmount()
	{
		BigDecimal totalLineAmount = getTotalLineAmount();

		if (billing == null || billing.getTax() == null || billing.getTax().getTaxRate() == null)
			return BigDecimal.ZERO;

		BigDecimal taxRate = billing.getTax().getTaxRate();

		if (totalLineAmount == null || taxRate == null)
			return BigDecimal.ZERO;

		// Calculate tax amount: (TotalLineAmount * TaxRate) / 100
		return totalLineAmount.multiply(taxRate).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
	}

	public BigDecimal getTotalAfterTax()
	{
		return this.getTotalLineAmount().add(getTaxAmount());
	}

	// TODO: Mendapatkan Total Credit Memo dari Billing terkait apabila CreditMemo sudah dimplementasikan
	public BigDecimal getTotalCreditMemo()
	{
		return BigDecimal.ZERO;
	}

	public BigDecimal getTotalBillingAmount()
	{
		return this.getTotalAfterTax().subtract(this.getTotalCreditMemo());
	}

	// Used In Billing Print Out
	public BigDecimal getTotalLineItemAmountForPrint()
	{
		BigDecimal total = BigDecimal.ZERO;

		for (BillingItem item : billing.getItems())
			total = total.add(item.getBillingReferenceItem().getTotalAmountPerItemDiscounted());

		return total;
	}

	public BigDecimal getTotalDiscountAmount()
	{
		BigDecimal totalDiscount = BigDecimal.ZERO;

		if (billing != null && billing.getItems() != null)
		{
			for (BillingItem item : billing.getItems())
			{
				BillingReferenceItem refItem = item.getBillingReferenceItem();
				if (refItem != null)
				{
					BigDecimal discountAmount = refItem.getDiscountAmount();
					if (discountAmount != null)
						totalDiscount = totalDiscount.add(discountAmount);
				}
			}
		}

		return totalDiscount;
	}

	/**
	 * Hitung total amount seluruh item setelah diskon (sebelum pajak).
	 */
	public BigDecimal getTotalAfterDiscount()
	{
		BigDecimal totalAfterDiscount = BigDecimal.ZERO;

		if (billing != null && billing.getItems() != null)
		{
			for (BillingItem item : billing.getItems())
			{
				BillingReferenceItem refItem = item.getBillingReferenceItem();
				if (refItem != null)
					totalAfterDiscount = totalAfterDiscount.add(refItem.getTotalAmount());
			}
		}

		return totalAfterDiscount;
	}
}

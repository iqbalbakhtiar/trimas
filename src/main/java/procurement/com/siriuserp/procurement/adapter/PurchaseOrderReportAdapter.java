package com.siriuserp.procurement.adapter;

import java.math.BigDecimal;

import com.siriuserp.procurement.dm.PurchaseOrder;
import com.siriuserp.procurement.dm.PurchaseOrderItem;
import com.siriuserp.sdk.adapter.AbstractUIAdapter;
import com.siriuserp.sdk.utility.DecimalHelper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PurchaseOrderReportAdapter extends AbstractUIAdapter
{
	private static final long serialVersionUID = 6487977149021666750L;
	
	private PurchaseOrder purchaseOrder;
	
	public BigDecimal getPurchase()
	{
		BigDecimal amount = BigDecimal.ZERO;

		for (PurchaseOrderItem item : getPurchaseOrder().getItems())
			amount = amount.add(item.getTotalAmount());

		return amount;
	}
	
	public BigDecimal getTax()
	{
		return getTotalAfterDiscount().multiply(DecimalHelper.percent(getPurchaseOrder().getTax().getTaxRate()));
	}
	
	public BigDecimal getTotalAfterDiscount()
	{
		return getPurchase().subtract(getDiscount());
	}
	
	public BigDecimal getDiscount()
	{
		return getDiscountOne();
	}
	
	public BigDecimal getDiscountOne()
	{
		BigDecimal amount = BigDecimal.ZERO;

		for (PurchaseOrderItem item : getPurchaseOrder().getItems())
			amount = amount.add(item.getDiscount().multiply(item.getQuantity()));

		return amount;
	}
	
	public BigDecimal getTotal()
	{
		return getTotalAfterDiscount().add(getTax());
	}
}

package com.siriuserp.procurement.adapter;

import java.math.BigDecimal;

import com.siriuserp.administration.util.TaxHelper;
import com.siriuserp.procurement.dm.PurchaseOrderItem;
import com.siriuserp.sdk.adapter.AbstractUIAdapter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PurchaseReportAdapter extends AbstractUIAdapter
{
	private static final long serialVersionUID = 6487977149021666750L;

	private PurchaseOrderItem purchaseOrderItem;

	public BigDecimal getPrice()
	{
		return getPurchaseOrderItem().getMoney().getAmount();
	}

	public BigDecimal getDpp()
	{
		return getPrice().multiply(getPurchaseOrderItem().getQuantity());
	}

	public BigDecimal getNettPrice()
	{
		return getDpp().subtract(getPurchaseOrderItem().getDiscount());
	}

	public BigDecimal getTaxAmount()
	{
		return TaxHelper.get(purchaseOrderItem.getTax(), getNettPrice());
	}

	public BigDecimal getTotal()
	{
		return getNettPrice().add(getTaxAmount());
	}
}

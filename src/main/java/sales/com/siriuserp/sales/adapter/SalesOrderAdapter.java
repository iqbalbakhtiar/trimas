package com.siriuserp.sales.adapter;

import com.siriuserp.sales.dm.SalesOrder;
import com.siriuserp.sales.dm.SalesOrderItem;
import com.siriuserp.sdk.adapter.AbstractUIAdapter;
import javolution.util.FastList;
import javolution.util.FastMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalesOrderAdapter extends AbstractUIAdapter
{
	private static final long serialVersionUID = -3297349667614236225L;

	private SalesOrder salesOrder;

	public FastList<FastMap<String, Object>> getItems()
	{
		FastList<FastMap<String, Object>> items = new FastList<FastMap<String, Object>>();

		for (SalesOrderItem salesOrderItem : salesOrder.getItems())
		{
			FastMap<String, Object> item = new FastMap<String, Object>();
			item.put("id", salesOrderItem.getId());
			item.put("product", salesOrderItem.getProduct());
			item.put("quantity", salesOrderItem.getQuantity());
			item.put("amount", salesOrderItem.getMoney().getAmount());
			item.put("discount", salesOrderItem.getDiscount());
			item.put("priceNett", getPriceNett(salesOrderItem));
			item.put("subTotal", getSubTotal(salesOrderItem));

			items.add(item);
		}

		return items;
	}

	// PriceNett = Price - Discount
	private BigDecimal getPriceNett(SalesOrderItem item)
	{
		BigDecimal discountAmount = item.getMoney().getAmount().multiply(item.getDiscount()).divide(BigDecimal.valueOf(100));
		return item.getMoney().getAmount().subtract(discountAmount);
	}

	// SubTotal = PriceNett * Quantity
	private BigDecimal getSubTotal(SalesOrderItem item)
	{
		return getPriceNett(item).multiply(item.getQuantity());
	}

	// TotalAmount = Sum of all SubTotals
	public BigDecimal getTotalAmount()
	{
		return salesOrder.getItems().stream().map(this::getSubTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}

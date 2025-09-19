package com.siriuserp.sales.adapter;

import java.math.BigDecimal;

import com.siriuserp.sales.dm.SalesOrder;
import com.siriuserp.sales.dm.SalesOrderItem;
import com.siriuserp.sdk.adapter.AbstractUIAdapter;

import javolution.util.FastList;
import javolution.util.FastMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
			item.put("discountPercent", salesOrderItem.getDiscountPercent());
			item.put("priceNett", getPriceNett(salesOrderItem));
			item.put("subTotal", getSubTotal(salesOrderItem));
			item.put("note", salesOrderItem.getNote());

			items.add(item);
		}

		return items;
	}

	// PriceNett = Price - Discount
	private BigDecimal getPriceNett(SalesOrderItem item)
	{
		return item.getMoney().getAmount().subtract(item.getDiscount());
	}

	// SubTotal = PriceNett * Quantity
	private BigDecimal getSubTotal(SalesOrderItem item)
	{
		return item.getMoney().getAmount().multiply(item.getQuantity());
	}

	public BigDecimal getTotalAmount()
	{
		BigDecimal amount = BigDecimal.ZERO;

		for (SalesOrderItem item : getSalesOrder().getItems())
			amount = amount.add(getSubTotal(item));

		return amount;
	}

	public BigDecimal getTotalDiscount()
	{
		BigDecimal amount = BigDecimal.ZERO;

		for (SalesOrderItem item : getSalesOrder().getItems())
			amount = amount.add(item.getDiscount().multiply(item.getQuantity()));

		return amount;
	}

	public BigDecimal getTotalAfterDiscount()
	{
		return getTotalAmount().subtract(getTotalDiscount());
	}

	public BigDecimal getTaxAmount()
	{
		return getTotalAfterDiscount().multiply(getSalesOrder().getTax().getTaxRate().divide(BigDecimal.valueOf(100)));
	}

	public BigDecimal getTotalTransaction()
	{
		return getTotalAfterDiscount().add(getTaxAmount());
	}
}

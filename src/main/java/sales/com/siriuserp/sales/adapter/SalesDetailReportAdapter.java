/**
 * File Name  : SalesDetailReportAdapter.java
 * Created On : Jul 11, 2019
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.sales.adapter;

import java.math.BigDecimal;

import com.siriuserp.sales.dm.SalesOrder;
import com.siriuserp.sales.dm.SalesOrderItem;
import com.siriuserp.sdk.adapter.AbstractUIAdapter;

import javolution.util.FastList;
import javolution.util.FastMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Getter
@Setter
@AllArgsConstructor
public class SalesDetailReportAdapter extends AbstractUIAdapter
{
	private static final long serialVersionUID = -8098420191623434890L;

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
			item.put("discountAmount", salesOrderItem.getMoney().getAmount().multiply(salesOrderItem.getDiscount()).divide(BigDecimal.valueOf(100)));
			item.put("priceNett", getPriceNett(salesOrderItem));
			item.put("subTotal", getSubTotal(salesOrderItem));
			item.put("taxAmount", getTaxAmount(salesOrderItem));
			item.put("total", getTotal(salesOrderItem));
			item.put("note", salesOrderItem.getNote());

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

	public BigDecimal getTotalItemAmount()
	{
		BigDecimal total = BigDecimal.ZERO;

		for (SalesOrderItem item : getSalesOrder().getItems())
			total = total.add(getSubTotal(item));

		return total;
	}

	public BigDecimal getTaxAmount(SalesOrderItem item)
	{
		if (item != null)
			return getSubTotal(item).multiply(getSalesOrder().getTax().getTaxRate().divide(BigDecimal.valueOf(100)));

		return getTotalItemAmount().multiply(getSalesOrder().getTax().getTaxRate().divide(BigDecimal.valueOf(100)));
	}

	public BigDecimal getTotal(SalesOrderItem item)
	{
		if (item != null)
			return getSubTotal(item).add(getTaxAmount(item));

		return getTotalItemAmount().add(getTaxAmount(null));
	}
}

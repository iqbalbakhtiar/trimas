/**
 * File Name  : SalesReportAdapter.java
 * Created On : Apr 30, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.sales.adapter;

import com.siriuserp.accountreceivable.dm.Billing;
import com.siriuserp.sales.dm.DeliveryOrderItem;
import com.siriuserp.sales.dm.SalesOrderItem;
import com.siriuserp.sdk.adapter.AbstractUIAdapter;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Getter
@Setter
public class DeliveryReportAdapter extends AbstractUIAdapter
{
	private static final long serialVersionUID = 1812855810287092657L;

	private DeliveryOrderItem deliveryOrderItem;
	private SalesOrderItem salesOrderItem;
	private Billing billing;

	public DeliveryReportAdapter(DeliveryOrderItem deliveryOrderItem, SalesOrderItem salesOrderItem)
	{
		this.deliveryOrderItem = deliveryOrderItem;
		this.salesOrderItem = salesOrderItem;
	}

	public DeliveryReportAdapter(DeliveryOrderItem deliveryOrderItem, SalesOrderItem salesOrderItem, Billing billing)
	{
		this.deliveryOrderItem = deliveryOrderItem;
		this.salesOrderItem = salesOrderItem;
		this.billing = billing;
	}
}

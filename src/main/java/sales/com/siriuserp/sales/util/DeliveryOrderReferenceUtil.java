/**
 * File Name  : DeliveryOrderReferenceUtil.java
 * Created On : Mar 18, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.sales.util;

import com.siriuserp.sales.dm.DeliveryPlanning;
import com.siriuserp.sales.dm.DeliveryPlanningSequenceItem;
import com.siriuserp.sales.dm.SalesOrder;
import com.siriuserp.sales.dm.SalesOrderItem;
import com.siriuserp.sales.dm.DeliveryOrderReferenceItem;
import com.siriuserp.sales.dm.DeliveryOrderReferenceType;
import com.siriuserp.sales.dm.DeliveryOrderReferenceable;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

public class DeliveryOrderReferenceUtil
{
	public static DeliveryOrderReferenceItem initItem(DeliveryOrderReferenceable itemReference) throws Exception
	{
		SalesOrder salesOrder = null;
		DeliveryOrderReferenceItem item = new DeliveryOrderReferenceItem();

		if (itemReference.getReferenceType().equals(DeliveryOrderReferenceType.DELIVERY_PLANNING))
		{
			DeliveryPlanningSequenceItem seqItem = (DeliveryPlanningSequenceItem) itemReference;
			DeliveryPlanning planning = seqItem.getDeliveryPlanningSequence().getDeliveryPlanning();
			salesOrder = planning.getSalesOrder();

			item.setReferenceId(seqItem.getDeliveryPlanningSequence().getId());
			item.setCode(planning.getCode() + "." + seqItem.getDeliveryPlanningSequence().getNo());
			item.setDate(seqItem.getDeliveryPlanningSequence().getDate());
			item.setReferenceType(DeliveryOrderReferenceType.DELIVERY_PLANNING);
			item.setFacility(seqItem.getDeliveryPlanningSequence().getFacility());
			item.setSalesOrderItem(seqItem.getSalesOrderItem());
			item.setSequenceItem(seqItem);
			item.setUri("deliveryplanning");
		} else
		{
			SalesOrderItem salesOrderItem = (SalesOrderItem) itemReference;
			salesOrder = salesOrderItem.getSalesOrder();

			item.setReferenceId(salesOrder.getId());
			item.setCode(salesOrder.getCode());
			item.setDate(salesOrder.getDate());
			item.setReferenceType(DeliveryOrderReferenceType.SALES_ORDER);
			item.setFacility(salesOrder.getFacility());
			item.setSalesOrderItem(salesOrderItem);
			item.setUri("salesorder");
		}

		if (salesOrder != null)
		{
			item.setTerm(salesOrder.getTerm());
			item.setOrganization(salesOrder.getOrganization());
			item.setCustomer(salesOrder.getCustomer());
			item.setApprover(salesOrder.getApprover());
			item.setShippingAddress(salesOrder.getShippingAddress());
			item.setTax(salesOrder.getTax());
		}

		return item;
	}
}

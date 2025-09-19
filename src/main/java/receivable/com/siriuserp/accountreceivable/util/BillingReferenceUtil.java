/**
 * File Name  : BillingReferenceUtil.java
 * Created On : Aug 25, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.accountreceivable.util;

import com.siriuserp.accountreceivable.dm.BillingReferenceItem;
import com.siriuserp.accountreceivable.dm.BillingReferenceType;
import com.siriuserp.accountreceivable.dm.BillingReferenceable;
import com.siriuserp.sales.dm.DeliveryOrderRealization;
import com.siriuserp.sales.dm.DeliveryOrderRealizationItem;
import com.siriuserp.sales.dm.SalesOrder;
import com.siriuserp.sales.dm.SalesOrderItem;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

public class BillingReferenceUtil
{
	public static BillingReferenceItem initItem(BillingReferenceable itemReference) throws Exception
	{
		BillingReferenceItem referenceItem = new BillingReferenceItem();

		if (itemReference.getBillingReferenceType().equals(BillingReferenceType.DELIVERY_ORDER_REALIZATION))
		{
			DeliveryOrderRealizationItem realizationItem = (DeliveryOrderRealizationItem) itemReference;
			DeliveryOrderRealization realization = realizationItem.getDeliveryOrderRealization();

			referenceItem.setReferenceId(realization.getId());
			referenceItem.setReferenceCode(realization.getCode());
			referenceItem.setReferenceDate(realization.getDate());
			referenceItem.setReferenceName(SiriusValidator.getEnumName(realization.getClass()));
			referenceItem.setQuantity(realizationItem.getAccepted());
			referenceItem.getMoney().setAmount(realizationItem.getMoney().getAmount());
			referenceItem.getMoney().setRate(realizationItem.getMoney().getRate());
			referenceItem.getMoney().setCurrency(realizationItem.getMoney().getCurrency());
			referenceItem.setDiscount(realizationItem.getDeliveryOrderItem().getDiscount());
			referenceItem.setDiscountPercent(realizationItem.getDeliveryOrderItem().getDiscountPercent());
			referenceItem.setOrganization(realization.getOrganization());
			referenceItem.setFacility(realization.getFacility());
			referenceItem.setCustomer(realization.getCustomer());
			referenceItem.setReferenceType(BillingReferenceType.DELIVERY_ORDER_REALIZATION);
			referenceItem.setCreatedBy(realization.getCreatedBy());
			referenceItem.setCreatedDate(realization.getCreatedDate());
			referenceItem.setTax(realizationItem.getTax());
			referenceItem.setProduct(realizationItem.getProduct());
			referenceItem.setTerm(realizationItem.getDeliveryOrderItem().getDeliveryReferenceItem().getTerm());
		} else
		{
			SalesOrderItem salesOrderItem = (SalesOrderItem) itemReference;
			SalesOrder salesOrder = salesOrderItem.getSalesOrder();

			referenceItem.setReferenceId(salesOrder.getId());
			referenceItem.setReferenceCode(salesOrder.getCode());
			referenceItem.setReferenceDate(salesOrder.getDate());
			referenceItem.setReferenceName(SiriusValidator.getEnumName(salesOrder.getClass()));
			referenceItem.setQuantity(salesOrderItem.getQuantity());
			referenceItem.getMoney().setAmount(salesOrderItem.getMoney().getAmount());
			referenceItem.getMoney().setRate(salesOrderItem.getMoney().getRate());
			referenceItem.getMoney().setCurrency(salesOrderItem.getMoney().getCurrency());
			referenceItem.setDiscount(salesOrderItem.getDiscount());
			referenceItem.setDiscountPercent(salesOrderItem.getDiscountPercent());
			referenceItem.setOrganization(salesOrder.getOrganization());
			referenceItem.setFacility(salesOrder.getFacility());
			referenceItem.setCustomer(salesOrder.getCustomer());
			referenceItem.setReferenceType(BillingReferenceType.SALES_ORDER);
			referenceItem.setCreatedBy(salesOrder.getCreatedBy());
			referenceItem.setCreatedDate(salesOrder.getCreatedDate());
			referenceItem.setTax(salesOrderItem.getSalesOrder().getTax());
			referenceItem.setProduct(salesOrderItem.getProduct());
			referenceItem.setTerm(salesOrder.getTerm());
		}

		return referenceItem;
	}
}

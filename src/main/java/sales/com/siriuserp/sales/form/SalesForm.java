package com.siriuserp.sales.form;

import java.math.BigDecimal;
import java.util.Date;

import com.siriuserp.inventory.dm.Product;
import com.siriuserp.sales.dm.DeliveryPlanning;
import com.siriuserp.sales.dm.DeliveryPlanningSequence;
import com.siriuserp.sales.dm.SOStatus;
import com.siriuserp.sales.dm.SalesOrder;
import com.siriuserp.sdk.dm.Form;
import com.siriuserp.sdk.dm.PostalAddress;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesForm extends Form
{
	private static final long serialVersionUID = -1116229667455960263L;

	private BigDecimal amount = BigDecimal.ZERO;

	private Date date;
	private Date expDate;
	private Date shippingDate;

	private SalesOrder salesOrder;
	private DeliveryPlanning deliveryPlanning;
	private DeliveryPlanningSequence deliveryPlanningSequence;
	private Product product;
	private PostalAddress shippingAddress;

	private SOStatus soStatus;
}

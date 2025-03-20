package com.siriuserp.sales.form;

import java.math.BigDecimal;
import java.util.Date;

import com.siriuserp.inventory.dm.Product;
import com.siriuserp.sales.dm.DeliveryOrder;
import com.siriuserp.sales.dm.DeliveryOrderRealization;
import com.siriuserp.sales.dm.DeliveryPlanning;
import com.siriuserp.sales.dm.DeliveryPlanningSequence;
import com.siriuserp.sales.dm.SOStatus;
import com.siriuserp.sales.dm.SalesOrder;
import com.siriuserp.sdk.dm.Form;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.PostalAddress;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesForm extends Form
{
	private static final long serialVersionUID = -1116229667455960263L;

	private String vehicle;
	private String driver;
	private String noteExt;

	private BigDecimal amount = BigDecimal.ZERO;
	private BigDecimal rit;

	private Date date;
	private Date expDate;
	private Date shippingDate;
	private Date acceptanceDate;
	private Date returnDate;

	private Party expedition;

	private Product product;
	private SalesOrder salesOrder;
	private DeliveryPlanning deliveryPlanning;
	private DeliveryPlanningSequence deliveryPlanningSequence;
	private DeliveryOrder deliveryOrder;
	private DeliveryOrderRealization deliveryOrderRealization;
	private PostalAddress shippingAddress;

	private SOStatus soStatus;
}

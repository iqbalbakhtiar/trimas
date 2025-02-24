package com.siriuserp.sales.form;

import java.math.BigDecimal;
import java.util.Date;

import com.siriuserp.inventory.dm.Product;
import com.siriuserp.sales.dm.SalesOrder;
import com.siriuserp.sdk.dm.Form;
import com.siriuserp.sdk.dm.PostalAddress;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesForm extends Form {

	private static final long serialVersionUID = -1116229667455960263L;

	@Deprecated
	private String poCode;

	private BigDecimal amount = BigDecimal.ZERO;
	
	private Date date;
	private Date expDate;
	private Date shippingDate;
	
	private PostalAddress shippingAddress;
	private SalesOrder salesOrder;
	private Product product;
}

package com.siriuserp.accountpayable.form;

import java.util.Date;

import com.siriuserp.accountpayable.dm.InvoiceVerification;
import com.siriuserp.inventory.dm.GoodsReceipt;
import com.siriuserp.sdk.dm.Form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentForm extends Form
{
	private static final long serialVersionUID = -4982771481190917771L;

	private GoodsReceipt goodsReceipt;
	private InvoiceVerification invoiceVerification;

	private Date dueDate;
	private Date paidDate;

	private Long id;

	private String taxNo;
}

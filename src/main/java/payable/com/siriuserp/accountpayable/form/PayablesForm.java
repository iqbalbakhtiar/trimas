package com.siriuserp.accountpayable.form;

import java.util.Date;

import com.siriuserp.accounting.dm.GLAccount;
import com.siriuserp.accountpayable.dm.InvoiceVerification;
import com.siriuserp.accountpayable.dm.Payment;
import com.siriuserp.accountpayable.dm.PaymentInformation;
import com.siriuserp.accountpayable.dm.PaymentManual;
import com.siriuserp.accountpayable.dm.PaymentManualReferenceType;
import com.siriuserp.accountpayable.dm.PaymentManualType;
import com.siriuserp.accountpayable.dm.PaymentMethodType;
import com.siriuserp.inventory.dm.GoodsReceipt;
import com.siriuserp.sdk.dm.Form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayablesForm extends Form
{
	private static final long serialVersionUID = -8755566007524678306L;

	private GoodsReceipt goodsReceipt;
	private Payment payment;
	private PaymentInformation paymentInformation;
	private PaymentManualType paymentManualType;
	private PaymentManual paymentManual;
	private GLAccount account;
	private InvoiceVerification invoiceVerification;

	private PaymentMethodType paymentMethodType;
	private PaymentManualReferenceType referenceType;

	private Date dueDate;
	private Date paidDate;

	private Long id;

	private String taxNo;
}

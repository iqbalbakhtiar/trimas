package com.siriuserp.accounting.form;

import com.siriuserp.accounting.dm.BankAccount;
import com.siriuserp.accounting.dm.Billing;
import com.siriuserp.accounting.dm.Receipt;
import com.siriuserp.accounting.dm.ReceiptInformation;
import com.siriuserp.sdk.dm.*;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class AccountingForm extends Form {

	private static final long serialVersionUID = 3699191307919352580L;
	
	private String code;
	private String bankName;
	private String accountName;
	private String accountNo;
	private String note;
	private String invoiceTaxHeader;
	private String invoiceTaxNo;

	private BigDecimal unpaid;
	private BigDecimal clearing;
	private BigDecimal rounding;

	private int year;
	private int term;

	private Date dueDate;
	private Date paidDate;

	private Party holder;
	private BankAccount bankAccount;
	private Billing billing;
	private PostalAddress shippingAddress;
	private PostalAddress billingAddress;
	private PostalAddress taxAddress;
	private Money money;
	private ReceiptInformation receiptInformation;
	private Receipt receipt;

	private PaymentMethodType paymentMethodType;
	private AccountType accountType;
	private Month month;
}

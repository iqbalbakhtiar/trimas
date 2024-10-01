package com.siriuserp.accounting.form;

import com.siriuserp.accounting.dm.BankAccount;
import com.siriuserp.sdk.dm.AccountType;
import com.siriuserp.sdk.dm.Form;
import com.siriuserp.sdk.dm.Month;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.PaymentMethodType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountingForm extends Form {

	private static final long serialVersionUID = 3699191307919352580L;
	
	private String code;
	private String bankName;
	private String accountName;
	private String accountNo;
	private String note;

	private int year;

	private Party holder;

	private BankAccount bankAccount;

	private PaymentMethodType paymentMethodType;
	private AccountType accountType;

	private Month month;
}

package com.siriuserp.accountreceivable.form;

import com.siriuserp.accounting.dm.GLAccount;
import com.siriuserp.accountpayable.dm.PaymentMethodType;
import com.siriuserp.accountreceivable.dm.Billing;
import com.siriuserp.accountreceivable.dm.CreditMemo;
import com.siriuserp.accountreceivable.dm.CreditMemoReferenceType;
import com.siriuserp.accountreceivable.dm.Receipt;
import com.siriuserp.accountreceivable.dm.ReceiptInformation;
import com.siriuserp.accountreceivable.dm.ReceiptManual;
import com.siriuserp.accountreceivable.dm.ReceiptManualReferenceType;
import com.siriuserp.accountreceivable.dm.ReceiptManualType;
import com.siriuserp.sdk.dm.Form;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReceivablesForm extends Form
{
	private static final long serialVersionUID = -7116283893872931004L;

	private Billing billing;
	private Receipt receipt;
	private ReceiptInformation receiptInformation;
	private GLAccount account;
	private CreditMemo creditMemo;
	private ReceiptManualType receiptManualType;
	private ReceiptManual receiptManual;

	private PaymentMethodType paymentMethodType;
	private ReceiptManualReferenceType referenceType;
	private CreditMemoReferenceType creditMemoReferenceType;
}

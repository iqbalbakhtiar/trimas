/**
 * File Name  : AccountingForm.java
 * Created On : Jul 23, 2024
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.accounting.form;

import java.math.BigDecimal;
import java.util.Date;

import com.siriuserp.accounting.dm.BankAccount;
import com.siriuserp.accounting.dm.ChartOfAccount;
import com.siriuserp.accounting.dm.GLAccount;
import com.siriuserp.accountpayable.dm.Payment;
import com.siriuserp.accountpayable.dm.PaymentInformation;
import com.siriuserp.accountpayable.dm.PaymentMethodType;
import com.siriuserp.accountreceivable.dm.Billing;
import com.siriuserp.accountreceivable.dm.BillingBatch;
import com.siriuserp.accountreceivable.dm.CreditMemoReferenceType;
import com.siriuserp.accountreceivable.dm.Prepayment;
import com.siriuserp.accountreceivable.dm.Receipt;
import com.siriuserp.accountreceivable.dm.ReceiptInformation;
import com.siriuserp.sdk.dm.AccountType;
import com.siriuserp.sdk.dm.Form;
import com.siriuserp.sdk.dm.Money;
import com.siriuserp.sdk.dm.Month;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.PostalAddress;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Getter
@Setter
public class AccountingForm extends Form
{
	private static final long serialVersionUID = 6197597147074723126L;

	private String code;
	private String bankName;
	private String accountName;
	private String bankBranch;
	private String accountNo;
	private String note;
	private String invoiceTaxHeader;
	private String invoiceTaxNo;

	private BigDecimal unpaid;
	private BigDecimal clearing;
	private BigDecimal rounding;

	private int year;

	private Date dueDate;
	private Date paidDate;
	private Date realDate;

	private Party holder;
	private Money money;
	private PostalAddress shippingAddress;
	private PostalAddress billingAddress;
	private PostalAddress taxAddress;

	//Accounting
	private ChartOfAccount chartOfAccount;
	private GLAccount account;
	private GLAccount chargeAccount;
	private BankAccount bankAccount;

	private Month month;
	private PaymentMethodType paymentMethodType;
	private AccountType accountType;

	//Receivable
	private Billing billing;
	private BillingBatch billingBatch;
	private Receipt receipt;
	private ReceiptInformation receiptInformation;
	private Prepayment prepayment;

	private CreditMemoReferenceType memoReferenceType;

	//Payable
	private Payment payment;
	private PaymentInformation paymentInformation;
}

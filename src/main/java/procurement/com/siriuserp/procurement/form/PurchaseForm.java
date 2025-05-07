package com.siriuserp.procurement.form;

import java.math.BigDecimal;
import java.util.Date;

import com.siriuserp.procurement.dm.POStatus;
import com.siriuserp.procurement.dm.PurchaseDocumentType;
import com.siriuserp.procurement.dm.PurchaseOrder;
import com.siriuserp.procurement.dm.PurchaseRequisition;
import com.siriuserp.procurement.dm.PurchaseType;
import com.siriuserp.sdk.dm.Approvable;
import com.siriuserp.sdk.dm.ContactMechanism;
import com.siriuserp.sdk.dm.Department;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.Form;
import com.siriuserp.sdk.dm.PostalAddress;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseForm extends Form
{
	private static final long serialVersionUID = -3975750510372614700L;

	private String accountNo;
	private String accountName;
	private String deliveryTerm;
	private String paymentTerm;

	private PostalAddress supplierAddress;
	private PostalAddress billTo;
	private Facility shipTo;
	private Approvable approvable;
	private PurchaseRequisition purchaseRequisition;
	private PurchaseOrder purchaseOrder;
	private ContactMechanism supplierPhone;
	private Department department;

	private PurchaseType purchaseType;
	private PurchaseDocumentType purchaseDocumentType;
	private POStatus status;

	private BigDecimal totalTransaction;
	private BigDecimal downPayment;
	private BigDecimal paymentAmount;

	private Date deliveryDate;

	private boolean invoiceBeforeReceipt = true;
}

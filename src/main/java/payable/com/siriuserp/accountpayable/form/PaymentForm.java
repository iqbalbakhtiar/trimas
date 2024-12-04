package com.siriuserp.accountpayable.form;

import com.siriuserp.inventory.dm.GoodsReceipt;
import com.siriuserp.sdk.dm.Form;
import com.siriuserp.sdk.dm.InvoiceVerification;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PaymentForm extends Form {
    private static final long serialVersionUID = -4982771481190917771L;

    private GoodsReceipt goodsReceipt;
    private InvoiceVerification invoiceVerification;

    private Date dueDate;
    private Date paidDate;

    private Long id;
}

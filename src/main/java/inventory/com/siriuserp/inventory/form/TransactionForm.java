package com.siriuserp.inventory.form;

import com.siriuserp.inventory.dm.GoodsIssue;
import com.siriuserp.inventory.dm.GoodsReceipt;
import com.siriuserp.procurement.dm.PurchaseOrder;
import com.siriuserp.sdk.dm.Form;
import com.siriuserp.sdk.dm.StatusType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionForm extends Form {
    private static final long serialVersionUID = 7445610996434139452L;

    private String invoiceNo;

    private GoodsIssue goodsIssue;
    private GoodsReceipt goodsReceipt;
    private PurchaseOrder purchaseOrder;

    private StatusType statusType;
}

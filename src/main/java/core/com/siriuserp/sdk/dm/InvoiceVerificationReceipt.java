package com.siriuserp.sdk.dm;

import com.siriuserp.inventory.dm.GoodsReceiptItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "invoice_verification_receipt")
public class InvoiceVerificationReceipt extends Model {
    private static final long serialVersionUID = 7637895966849656217L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_goods_receipt_item")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private GoodsReceiptItem goodsReceiptItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_invoice_verification")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private InvoiceVerification invoiceVerification;

    @Override
    public String getAuditCode() {
        return "";
    }
}

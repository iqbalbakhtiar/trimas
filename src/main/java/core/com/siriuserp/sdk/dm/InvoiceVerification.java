package com.siriuserp.sdk.dm;

import com.siriuserp.inventory.dm.GoodsReceipt;
import javolution.util.FastSet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "invoice_verification")
public class InvoiceVerification extends Payable {
    private static final long serialVersionUID = -6623484035906573364L;

    @Column(name = "verificated")
    @Type(type = "yes_no")
    private boolean verificated;

    @OneToMany(mappedBy = "invoiceVerification", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.EXTRA)
    @Fetch(FetchMode.SELECT)
    @Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
    private Set<InvoiceVerificationReceipt> receipts = new FastSet<InvoiceVerificationReceipt>();

    @Override
    public String getAuditCode() {
        return getId() + ',' + getCode();
    }

    public GoodsReceipt getGoodsReceipt() {
        for (InvoiceVerificationReceipt receipt : receipts) {
            return receipt.getGoodsReceiptItem().getGoodsReceipt();
        }
        return null;
    }
}

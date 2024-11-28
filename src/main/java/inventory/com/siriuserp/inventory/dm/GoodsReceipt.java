package com.siriuserp.inventory.dm;


import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.JSONSupport;
import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.Party;
import javolution.util.FastSet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Merepresentasikan penerimaan barang dalam sistem inventory.
 * Berisi detail tentang item yang diterima, tanggal penerimaan,
 * dan informasi organisasi terkait.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "goods_receipt")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GoodsReceipt extends Model implements Transaction, JSONSupport {
    private static final long serialVersionUID = -85665829865456908L;

    @Column(name = "code")
    private String code;

    @Column(name = "invoice_no")
    private String invoiceNo;

    @Column(name = "date")
    private Date date;

    @Column(name = "verificated")
    @Type(type = "yes_no")
    private boolean verificated = false;

    @Column(name = "note")
    private String note;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="fk_party_organization")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Party organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_facility")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Facility facility;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "fk_journal_entry")
//    @LazyToOne(LazyToOneOption.PROXY)
//    @Fetch(FetchMode.SELECT)
//    private JournalEntry journalEntry;

    @OneToMany(mappedBy = "goodsReceipt", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.EXTRA)
    @Fetch(FetchMode.SELECT)
    @Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
    @OrderBy("warehouseTransactionItem ASC")
    private Set<GoodsReceiptItem> items = new FastSet<GoodsReceiptItem>();

    @Override
    public String getAuditCode() {
        return id + ',' + code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public String getReceiveFrom()
    {
        HashSet<String> references = new HashSet<String>();
        references.addAll(getItems().stream().map(item -> item.getWarehouseTransactionItem().getReferenceItem().getReferenceFrom()).collect(Collectors.toSet()));

        return String.join(", ", references);
    }

    public Set<GoodsReceiptItem> getAllItems()
    {
        FastSet<GoodsReceiptItem> items = new FastSet<GoodsReceiptItem>();
//        items.addAll(getExtras());

        if(items.isEmpty())
            items.addAll(getItems());

        return items;
    }

    public String getReference()
    {
        HashSet<String> references = new HashSet<String>();
        references.addAll(getItems().stream().map(item -> item.getWarehouseTransactionItem().getFullUri()).collect(Collectors.toSet()));

        return String.join("<br/>", references);
    }
}

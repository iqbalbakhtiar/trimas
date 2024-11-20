package com.siriuserp.inventory.dm;

import com.siriuserp.sdk.dm.Facility;
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

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "goods_issue")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GoodsIssue extends Model implements Transaction {
    private static final long serialVersionUID = -423501571990197786L;

    @Column(name = "code")
    private String code;

    @Column(name = "date")
    private Date date;

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

    @OneToMany(mappedBy = "goodsIssue", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.EXTRA)
    @Fetch(FetchMode.SELECT)
    @Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
    @OrderBy("id")
    private Set<GoodsIssueItem> items = new FastSet<>();

    @Override
    public String getAuditCode() {
        return id + ',' + code;
    }

    public String getIssuedTo()
    {
        HashSet<String> references = new HashSet<String>();
        references.addAll(getItems().stream().map(item -> item.getWarehouseTransactionItem().getReferenceItem().getReferenceTo()).collect(Collectors.toSet()));

        return String.join(", ", references);
    }

    public Set<String> getReferenceTypes()
    {
        HashSet<String> references = new HashSet<String>();
        references.addAll(getItems().stream().map(item -> item.getWarehouseTransactionItem().getTransactionSource().getMessage()).collect(Collectors.toSet()));

        return references;
    }

    public String getReference()
    {
        HashSet<String> references = new HashSet<String>();
        references.addAll(getItems().stream().map(item -> item.getWarehouseTransactionItem().getFullUri()).collect(Collectors.toSet()));

        return String.join("<br/>", references);
    }

    public Set<StockControl> getStockControlls()
    {
        FastSet<StockControl> controlls = new FastSet<StockControl>();

        for (GoodsIssueItem item : getItems())
            controlls.addAll(item.getStockControls());

        return controlls;
    }

}

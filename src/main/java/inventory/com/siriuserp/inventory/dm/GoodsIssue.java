package com.siriuserp.inventory.dm;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;

import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.Party;

import javolution.util.FastList;
import javolution.util.FastSet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public List<StockControl> getStockControlls()
	{
		FastList<StockControl> controlls = new FastList<StockControl>();

		for (GoodsIssueItem item : getItems())
			controlls.addAll(item.getStockControls());

		return controlls;
	}

}

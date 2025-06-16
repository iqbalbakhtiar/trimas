package com.siriuserp.inventory.dm;

import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.Tax;
import javolution.util.FastSet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ferdinand
 */

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "transfer_order")
public class TransferOrder extends Model implements Warehouseable
{
    private static final long serialVersionUID = 8774235759907063762L;

    @Column(name = "code")
    private String code;

    @Column(name = "date")
    private Date date = new Date();

    @Column(name = "note")
    private String note;

    @Column(name = "transfer_type")
    @Enumerated(EnumType.STRING)
    private TransferType transferType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_party_organization")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Party organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_facility_source")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Facility source;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_facility_destination")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Facility destination;

    @OneToMany(mappedBy = "transferOrder", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.EXTRA)
    @Fetch(FetchMode.SELECT)
    @Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
    @OrderBy("product.id ASC")
    private Set<TransferOrderItem> items = new HashSet<TransferOrderItem>();

    @Override
    public Tax getTax() {
        return Tax.newInstance("1", "Exempt");
    }

    @Override
    public Currency getCurrency() {
        return Currency.newInstance("1");
    }

    @Override
    public Party getParty() {
        return getOrganization();
    }

    @Override
    public String getRef() {
        return "";
    }

    @Override
    public Set<TransferOrderItem> getIssueables() {
        return getItems();
    }

    @Override
    public Set<GoodsIssue> getIssueds() {
        FastSet<GoodsIssue> issueds = new FastSet<GoodsIssue>();

        for (TransferOrderItem detail : getItems())
            issueds.addAll(detail.getIssueds());

        return issueds;
    }

    @Override
    public Set<TransferOrderItem> getReceiptables() {
        return getItems();
    }

    @Override
    public Set<GoodsReceipt> getReceipts() {
        FastSet<GoodsReceipt> receipts = new FastSet<GoodsReceipt>();

        for (TransferOrderItem detail : getItems())
            receipts.addAll(detail.getReceipts());

        return receipts;
    }

    @Override
    public String getSelf() {
        return "Transfer Order";
    }

    @Override
    public String getAuditCode() {
        return this.id + "," + this.code;
    }
}

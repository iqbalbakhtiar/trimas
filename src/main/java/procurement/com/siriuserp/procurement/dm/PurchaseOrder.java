package com.siriuserp.procurement.dm;

import com.siriuserp.inventory.dm.GoodsReceipt;
import com.siriuserp.inventory.dm.Receiptable;
import com.siriuserp.sales.dm.ApprovableType;
import com.siriuserp.sdk.dm.ApprovableBridge;
import com.siriuserp.sdk.dm.CreditTerm;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.JSONSupport;
import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.Money;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.PostalAddress;
import com.siriuserp.sdk.dm.Tax;
import javolution.util.FastSet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "purchase_order")
@Inheritance(strategy = InheritanceType.JOINED)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PurchaseOrder extends Model implements JSONSupport, ApprovableBridge, Receiptable {
    private static final long serialVersionUID = -7099548148106157972L;

    @Column(name = "code")
    private String code;

    @Column(name = "date")
    private Date date;

    @Column(name = "shipping_date")
    private Date shippingDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    protected POStatus status = POStatus.OPEN;

    @Column(name = "purchase_type")
    @Enumerated(EnumType.STRING)
    protected PurchaseType purchaseType = PurchaseType.DIRECT;

    @Embedded
    private Money money;

    @Column(name = "note")
    private String note;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_party_organization")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Party organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_facility")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Facility facility;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_party_supplier")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Party supplier;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_party_approver")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Party approver;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_tax")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Tax tax;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_credit_term")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private CreditTerm creditTerm;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_address_bill_to")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private PostalAddress billTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_facility_ship_to")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Facility shipTo;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_purchase_order_approvable_bridge")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private PurchaseOrderApprovableBridge approvable;

    @OneToMany(mappedBy = "purchaseOrder", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.EXTRA)
    @Fetch(FetchMode.SELECT)
    @Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
    @OrderBy("id")
    private Set<PurchaseOrderItem> items = new FastSet<PurchaseOrderItem>();

    @Override
    public String getAuditCode() {
        return id + "," + code;
    }

    @Override
    public ApprovableType getApprovableType() {
        return ApprovableType.PURCHASE_ORDER;
    }

    @Override
    public String getUri() {
        return "";
    }

    @Override
    public Set<String> getInterceptorNames() {
        Set<String> interceptors = new FastSet<String>();
        interceptors.add("purchaseOrderApprovableInterceptor");

        return interceptors;
    }

    @Override
    public Set<PurchaseOrderItem> getReceiptables() {
        Set<PurchaseOrderItem> items = new FastSet<PurchaseOrderItem>();
        items.addAll(getItems().stream().filter(item -> !item.isLocked()).collect(Collectors.toSet()));

        return items;
    }

    @Override
    public Set<GoodsReceipt> getReceipts() {
        Set<GoodsReceipt> receipts = new HashSet<GoodsReceipt>();
        receipts.addAll(getReceiptables().stream().flatMap(item -> item.getReceipts().stream()).collect(Collectors.toSet()));

        return receipts;
    }

    @Override
    public Currency getCurrency() {
        return this.getMoney().getCurrency();
    }

    @Override
    public Party getParty() {
        return getSupplier();
    }

    @Override
    public String getRef() {
        return "";
    }
}

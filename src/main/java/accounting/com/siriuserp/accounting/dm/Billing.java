package com.siriuserp.accounting.dm;

import com.siriuserp.sdk.dm.*;
import com.siriuserp.sdk.utility.DateHelper;
import javolution.util.FastMap;
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
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "billing")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Billing extends Model implements JSONSupport {

    private static final long serialVersionUID = -520730360408808920L;

    @Column(name = "code")
    private String code;

    @Column(name = "date")
    private Date date;

    @Column(name = "due_date")
    private Date dueDate;

    @Column(name = "paid_date")
    private Date paidDate;

    @Embedded
    private Money money; // Exchange Type not used

    @Column(name = "unpaid")
    private BigDecimal unpaid = BigDecimal.ZERO;

    @Column(name = "clearing")
    private BigDecimal clearing = BigDecimal.ZERO;

    @Column(name = "rounding")
    private BigDecimal rounding = BigDecimal.ZERO;

    @Column(name = "term")
    private Integer term = 1;

    @Column(name = "legend")
    private String legend;

    @Column(name = "invoice_tax_No")
    private String invoiceTaxNo;

    @Column(name = "invoice_tax_header")
    private String invoiceTaxHeader;

    @Column(name = "note")
    private String note;

    @Column(name = "financial_status")
    @Enumerated(EnumType.STRING)
    private FinancialStatus financialStatus = FinancialStatus.UNPAID;

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

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_party_customer")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Party customer;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_tax")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Tax tax;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "fk_billing_type")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private BillingType billingType;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_billing_address")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private PostalAddress billingAddress;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_tax_address")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private PostalAddress taxAddress;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_shipping_address")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private PostalAddress shippingAddress;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_billing_collecting_status")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private BillingCollectingStatus collectingStatus;

    @OneToMany(mappedBy = "billing", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.EXTRA)
    @Fetch(FetchMode.SELECT)
    @Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
    @OrderBy("id")
    private Set<BillingItem> items = new FastSet<BillingItem>();

    @OneToMany(mappedBy = "billing", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.EXTRA)
    @Fetch(FetchMode.SELECT)
    @Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
    @OrderBy("id")
    private Set<ReceiptApplication> receipts = new FastSet<>();

    @Override
    public String getAuditCode() {
        return id + "," + code;
    }

    @Override
    public Map<String, Object> val()
    {
        FastMap<String, Object> map = new FastMap<String, Object>();
        map.put("billId", getId());
        map.put("billCode", getCode());

        map.put("amount", money.getAmount());
        map.put("unpaid", getUnpaid());
        map.put("paid", money.getAmount().subtract(getUnpaid()));

        map.put("customer", getCustomer());
        map.put("date", DateHelper.format(getDate()));

        return map;
    }
}

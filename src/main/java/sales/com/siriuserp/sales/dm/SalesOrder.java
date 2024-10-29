package com.siriuserp.sales.dm;

import com.siriuserp.sdk.dm.*;
import javolution.util.FastSet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "sales_order")
@Inheritance(strategy = InheritanceType.JOINED)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SalesOrder extends Model implements JSONSupport, ApprovableBridge {
	
	private static final long serialVersionUID = -5053662637061845245L;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "date")
	private Date date;
	
	@Column(name = "po_code")
	private String poCode;
	
	@Column(name = "shipping_date")
	private Date shippingDate;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	protected SOStatus soStatus = SOStatus.OPEN;
	
	@Column(name = "sales_type")
	@Enumerated(EnumType.STRING)
	protected SalesType salesType = SalesType.STANDARD;
	
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
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_party_customer")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Party customer;
	
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
	
	// Sales Order Approvable Bride
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_sales_order_approvable_bridge")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private SalesOrderApprovableBridge approvable;
	
	// Journal Entry
//	@OneToOne(fetch=FetchType.LAZY)
//    @JoinColumn(name="fk_journal_entry")
//    @LazyToOne(LazyToOneOption.PROXY)
//    @Fetch(FetchMode.SELECT)
//    private JournalEntry journalEntry;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_shipping_address")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private PostalAddress shippingAddress;

	@OneToMany(mappedBy = "salesOrder", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	@OrderBy("id")
	private Set<SalesOrderItem> items = new FastSet<SalesOrderItem>();
	
	@Override
	public String getAuditCode() {
		return id + "," + code;
	}

	@Override
	public ApprovableType getApprovableType() {
		return ApprovableType.SALES_ORDER;
	}

	@Override
	public String getUri() {
		return "";
	}

	@Override
	public Set<String> getInterceptorNames() {
		Set<String> interceptors = new FastSet<String>();
		interceptors.add("salesOrderApprovableInterceptor");

		return interceptors;
	}
}

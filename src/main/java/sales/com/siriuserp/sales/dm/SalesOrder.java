/**
 * File Name  : SalesOrder.java
 * Created On : Mar 6, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.sales.dm;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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

import com.siriuserp.sdk.dm.ApprovableBridge;
import com.siriuserp.sdk.dm.CreditTerm;
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

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "sales_order")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SalesOrder extends Model implements JSONSupport, ApprovableBridge
{
	private static final long serialVersionUID = -5053662637061845245L;

	@Column(name = "code")
	private String code;

	@Column(name = "date")
	private Date date;

	@Column(name = "exp_date")
	private Date expDate;

	@Column(name = "shipping_date")
	private Date shippingDate;

	@Column(name = "po_code")
	private String poCode;

	@Column(name = "term")
	private Integer term = 1;

	@Column(name = "auto_dps")
	@Type(type = "yes_no")
	private boolean autoDPS = Boolean.FALSE;

	@Column(name = "deliverable")
	@Type(type = "yes_no")
	private boolean deliverable = Boolean.FALSE;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private SOStatus soStatus = SOStatus.OPEN;

	@Column(name = "sales_type")
	@Enumerated(EnumType.STRING)
	private SalesType salesType = SalesType.STANDARD;

	@Column(name = "direct_invoice")
	@Type(type = "yes_no")
	private boolean directInvoice = Boolean.FALSE;

	@Embedded
	private Money money;

	@Column(name = "note")
	private String note;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_party_organization")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Party organization;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_facility")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Facility facility;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_party_customer")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Party customer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_party_sales_person")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Party salesPerson;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_party_approver")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Party approver;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_tax")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Tax tax;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_credit_term")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private CreditTerm creditTerm;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_sales_order_approvable_bridge")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private SalesOrderApprovableBridge approvable;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_shipping_address")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private PostalAddress shippingAddress;

	@OneToOne(mappedBy = "salesOrder", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private DeliveryPlanning deliveryPlanning;

	@OneToMany(mappedBy = "salesOrder", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	@OrderBy("id")
	private Set<SalesOrderItem> items = new FastSet<SalesOrderItem>();

	public boolean isLocked()
	{
		Optional<SalesOrderItem> products = getItems().stream().filter(item -> !item.getLockStatus().equals(SOStatus.LOCK)).findFirst();
		return !products.isPresent();
	}

	@Override
	public ApprovableType getApprovableType()
	{
		return ApprovableType.SALES_ORDER;
	}

	@Override
	public Set<String> getInterceptorNames()
	{
		Set<String> interceptors = new FastSet<String>();
		interceptors.add("salesOrderApprovableInterceptor");

		return interceptors;
	}

	@Override
	public String getUri()
	{
		return "salesorderpreedit.htm";
	}

	@Override
	public String getAuditCode()
	{
		return id + "," + code;
	}
}

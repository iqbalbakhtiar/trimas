/**
 * File Name  : DeliveryOrder.java
 * Created On : Mar 18, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.sales.dm;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
import com.siriuserp.sdk.dm.JSONSupport;
import com.siriuserp.sdk.dm.Model;
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
@Table(name = "delivery_order")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DeliveryOrder extends Model implements JSONSupport
{
	private static final long serialVersionUID = -8504495874811110216L;

	@Column(name = "code")
	private String code;

	@Column(name = "date")
	private Date date;

	@Column(name = "rit")
	private BigDecimal rit = BigDecimal.ONE;

	@Column(name = "realization")
	@Type(type = "yes_no")
	private boolean realization = Boolean.FALSE;

	@Column(name = "vehicle")
	private String vehicle;

	@Column(name = "driver_name")
	private String driverName;

	@Column(name = "plate_number")
	private String plateNumber;

	@Column(name = "note")
	private String note;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private SOStatus status = SOStatus.OPEN;

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
	@JoinColumn(name = "fk_shipping_address")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private PostalAddress shippingAddress;

	@OneToMany(mappedBy = "deliveryOrder", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	@OrderBy("id")
	private Set<DeliveryOrderItem> items = new FastSet<DeliveryOrderItem>();

	public String getReferenceCode()
	{
		HashSet<String> references = new HashSet<String>();
		references.addAll(getItems().stream().map(item -> item.getSaleOrderCode()).collect(Collectors.toSet()));

		return String.join(" ", references);
	}

	public String getReferenceLot()
	{
		HashSet<String> references = new HashSet<String>();
		references.addAll(getItems().stream().map(item -> item.getLot() != null ? "LOT " + item.getLot().getCode() : "").collect(Collectors.toSet()));

		return String.join(" ", references);
	}

	public String getReferencePOCustomer()
	{
		HashSet<String> references = new HashSet<String>();
		references.addAll(getItems().stream().map(item -> item.getSaleOrderPOCustomer()).collect(Collectors.toSet()));

		return String.join(" ", references);
	}

	public Tax getTax()
	{
		for (DeliveryOrderItem item : getItems())
			return item.getTax();

		return null;
	}

	@Override
	public String getAuditCode()
	{
		return id + "," + code;
	}
}

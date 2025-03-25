/**
 * File Name  : DeliveryOrderReferenceItem.java
 * Created On : Feb 5, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.sales.dm;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;

import com.siriuserp.inventory.dm.Product;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.JSONSupport;
import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.PostalAddress;
import com.siriuserp.sdk.dm.Tax;

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
@Table(name = "delivery_order_reference_item")
@Inheritance(strategy = InheritanceType.JOINED)
public class DeliveryOrderReferenceItem extends Model implements JSONSupport
{
	private static final long serialVersionUID = 4479431094475781734L;

	@Column(name = "reference_id")
	private Long referenceId;

	@Column(name = "reference_code")
	private String code;

	@Column(name = "reference_date")
	private Date date;

	@Column(name = "note")
	private String note;

	@Column(name = "uri")
	private String uri;

	@Column(name = "term")
	private Integer term = 1;

	@Column(name = "deliverable")
	@Type(type = "yes_no")
	private boolean deliverable = Boolean.TRUE;

	@Column(name = "approved")
	@Type(type = "yes_no")
	private boolean approved = Boolean.TRUE;

	@Enumerated(EnumType.STRING)
	@Column(name = "reference_type")
	private DeliveryOrderReferenceType referenceType;

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
	@JoinColumn(name = "fk_party_approver")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Party approver;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_shipping_address")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private PostalAddress shippingAddress;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_tax")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Tax tax;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_sales_order_item")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private SalesOrderItem salesOrderItem;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_delivery_planning_sequence_item")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private DeliveryPlanningSequenceItem sequenceItem;

	@OneToOne(mappedBy = "deliveryReferenceItem", fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private DeliveryOrderItem deliveryOrderItem;

	public Product getProduct()
	{
		if (getSequenceItem() != null)
			return getSequenceItem().getProduct();

		return getSalesOrderItem().getProduct();
	}

	public BigDecimal getQuantity()
	{
		if (getSequenceItem() != null)
			return getSequenceItem().getQuantity();

		return getSalesOrderItem().getQuantity();
	}

	@Override
	public String getAuditCode()
	{
		return this.getCode().toString();
	}
}

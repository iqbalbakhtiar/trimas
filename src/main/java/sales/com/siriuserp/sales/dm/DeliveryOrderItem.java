/**
 * File Name  : DeliveryOrderItem.java
 * Created On : Mar 18, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.sales.dm;

import java.math.BigDecimal;
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

import com.siriuserp.inventory.dm.Product;
import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.Lot;
import com.siriuserp.sdk.dm.Model;

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
@Table(name = "delivery_order_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DeliveryOrderItem extends Model
{
	private static final long serialVersionUID = -4841672668391969021L;

	@Column(name = "quantity")
	protected BigDecimal quantity = BigDecimal.ZERO;

	@Column(name = "note")
	private String note;

	@Embedded
	private Lot lot = new Lot();

	@Enumerated(EnumType.STRING)
	@Column(name = "delivery_item_type")
	private DeliveryOrderItemType deliveryItemType = DeliveryOrderItemType.BASE;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_delivery_order")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private DeliveryOrder deliveryOrder;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_delivery_order_item")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private DeliveryOrderItem itemParent;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_container")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Container container;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_delivery_reference")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private DeliveryOrderReferenceItem deliveryReferenceItem;

	@OneToMany(mappedBy = "itemParent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	@OrderBy("id")
	private Set<DeliveryOrderItem> serials = new FastSet<DeliveryOrderItem>();

	@OneToMany(mappedBy = "deliveryOrderItem", fetch = FetchType.LAZY)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	private Set<DeliveryOrderRealizationItem> realizationItems = new FastSet<DeliveryOrderRealizationItem>();

	public Product getProduct()
	{
		if (getItemParent() != null)
			return getItemParent().getDeliveryReferenceItem().getProduct();

		return getDeliveryReferenceItem().getProduct();
	}

	@Override
	public String getAuditCode()
	{
		return getId().toString();
	}
}

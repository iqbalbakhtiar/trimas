/**
 * File Name  : DeliveryPlanningSequenceItem.java
 * Created On : Mar 6, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.sales.dm;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.siriuserp.inventory.dm.Product;
import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.Money;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Getter
@Setter
@Entity
@Table(name = "delivery_planning_sequence_item")
public class DeliveryPlanningSequenceItem extends Model implements DeliveryOrderReferenceable
{
	private static final long serialVersionUID = 2275882991642002784L;

	@Column(name = "quantity")
	private BigDecimal quantity = BigDecimal.ZERO;

	@Column(name = "discount")
	private BigDecimal discount = BigDecimal.ZERO;

	@Column(name = "note")
	private String note;

	@Embedded
	private Money money = new Money();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_container")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Container container;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_product")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_sales_order_item")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private SalesOrderItem salesOrderItem;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_delivery_planning_sequence")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private DeliveryPlanningSequence deliveryPlanningSequence;

	@OneToOne(mappedBy = "sequenceItem", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private DeliveryOrderReferenceItem deliveryOrderReferenceItem;

	public BigDecimal getBale()
	{
		return getQuantity().divide(BigDecimal.valueOf(181.44), 2, RoundingMode.HALF_UP);
	}
	
	@Override
	public DeliveryOrderReferenceType getReferenceType()
	{
		return DeliveryOrderReferenceType.DELIVERY_PLANNING;
	}

	@Override
	public String getAuditCode()
	{
		return this.getId().toString();
	}
}

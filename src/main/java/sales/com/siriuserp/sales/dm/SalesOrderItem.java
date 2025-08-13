/**
 * File Name  : SalesOrderItem.java
 * Created On : Mar 6, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.sales.dm;

import java.math.BigDecimal;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.siriuserp.inventory.dm.Product;
import com.siriuserp.sdk.dm.Lot;
import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.Money;

import javolution.util.FastMap;
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
@Table(name = "sales_order_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SalesOrderItem extends Model implements DeliveryOrderReferenceable
{
	private static final long serialVersionUID = -2172781865197634218L;

	@Column(name = "quantity")
	private BigDecimal quantity = BigDecimal.ZERO;

	@Column(name = "assigned")
	private BigDecimal assigned = BigDecimal.ZERO;

	@Column(name = "resend")
	private BigDecimal resend = BigDecimal.ZERO;

	@Column(name = "returned")
	private BigDecimal returned = BigDecimal.ZERO;

	@Column(name = "delivered")
	private BigDecimal delivered = BigDecimal.ZERO;

	@Column(name = "accepted")
	private BigDecimal accepted = BigDecimal.ZERO;

	@Column(name = "discount")
	private BigDecimal discount = BigDecimal.ZERO;

	@Column(name = "note")
	private String note;

	@Embedded
	private Money money = new Money();

	@Embedded
	private Lot lot = new Lot();

	@Column(name = "item_status")
	@Enumerated(EnumType.STRING)
	private SOStatus lockStatus = SOStatus.OPEN;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_product")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_sales_order")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private SalesOrder salesOrder;

	public Long getReferenceId()
	{
		return getSalesOrder().getId();
	}

	@Override
	public DeliveryOrderReferenceType getReferenceType()
	{
		return DeliveryOrderReferenceType.SALES_ORDER;
	}

	// (Qty * Amount)
	public BigDecimal getTotalAmount()
	{
		return getQuantity().multiply(getMoney().getAmount());
	}

	@Override
	public Map<String, Object> val()
	{
		Map<String, Object> map = new FastMap<String, Object>();
		map.put("id", getId());
		map.put("product", getProduct());
		map.put("price", getMoney().getAmount());

		return map;
	}

	@Override
	public String getAuditCode()
	{
		return getId().toString();
	}
}

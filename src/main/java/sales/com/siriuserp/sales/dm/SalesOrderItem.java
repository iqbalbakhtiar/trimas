package com.siriuserp.sales.dm;

import java.math.BigDecimal;

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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "sales_order_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SalesOrderItem extends Model
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
	protected BigDecimal delivered = BigDecimal.ZERO;

	@Column(name = "accepted")
	protected BigDecimal accepted = BigDecimal.ZERO;

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
	protected SOStatus lockStatus = SOStatus.OPEN;

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
	public String getAuditCode()
	{
		return getId().toString();
	}
}

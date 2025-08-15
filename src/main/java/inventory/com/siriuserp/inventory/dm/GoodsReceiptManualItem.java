package com.siriuserp.inventory.dm;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.Grid;
import com.siriuserp.sdk.dm.JSONSupport;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Andres Nodas
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "goods_receipt_manual_item")
public class GoodsReceiptManualItem extends WarehouseReferenceItem implements JSONSupport
{
	private static final long serialVersionUID = -790816080477891556L;

	@Column(name = "amount")
	private BigDecimal amount = BigDecimal.ZERO;

	@Column(name = "quantity")
	private BigDecimal quantity = BigDecimal.ZERO;

	@Column(name = "unreceipted")
	private BigDecimal unreceipted = BigDecimal.ZERO;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_product")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_container")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Container container;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_grid")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Grid grid;

	@Enumerated(EnumType.STRING)
	@Column(name = "warehouse_transaction_source")
	private WarehouseTransactionSource transactionSource = WarehouseTransactionSource.GOODS_RECEIPT_MANUAL;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_goods_receipt_manual")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private GoodsReceiptManual goodsReceiptManual;

	@Override
	public WarehouseTransaction getWarehouseTransaction()
	{
		return getGoodsReceiptManual();
	}

	@Override
	public WarehouseTransactionSource getTransactionSource()
	{
		return this.transactionSource;
	}

	@Override
	public Container getContainer()
	{
		return this.container;
	}

	@Override
	public Container getDestinationContainer()
	{
		return this.container;
	}

	@Override
	public Container getSourceContainer()
	{
		return this.container;
	}

	@Override
	public String getAuditCode()
	{
		return "";
	}
}

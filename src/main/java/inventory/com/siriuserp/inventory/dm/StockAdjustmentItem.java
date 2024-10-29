/**
 * 
 */
package com.siriuserp.inventory.dm;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.Grid;
import com.siriuserp.sdk.dm.Lot;
import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.Money;
import com.siriuserp.sdk.dm.Tag;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author ferdinand
 */

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "stock_adjustment_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StockAdjustmentItem extends Model implements TransactionItem
{
	private static final long serialVersionUID = -3827094728235055310L;

	@Column(name = "quantity")
	private BigDecimal quantity = BigDecimal.ZERO;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_grid")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Grid grid;

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
	@JoinColumn(name = "fk_stock_adjustment")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private StockAdjustment stockAdjustment;
	
	@OneToOne(mappedBy = "stockAdjustmentItem", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private StockAdjustmentItemStockableBridge stockableBridge;
	
	@OneToOne(mappedBy = "stockAdjustmentItem", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private StockAdjustmentItemControllableBridge controllableBridge;
	
	@Embedded
	private Money money = new Money();
	
	@Embedded
	private Lot lot = new Lot();
	
	@Transient
	private Tag tag = new Tag();

	@Override
	public String getAuditCode() {
		return this.id + "";
	}

	@Override
	public Long getTransactionId() {
		return getStockAdjustment().getId();
	}

	@Override
	public String getTransactionCode() {
		return getStockAdjustment().getCode();
	}

	@Override
	public WarehouseTransactionSource getTransactionSource() {
		return WarehouseTransactionSource.STOCK_ADJUSTMENT;
	}
}

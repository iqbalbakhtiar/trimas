/**
 * 
 */
package com.siriuserp.inventory.dm;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
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

import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.Grid;
import com.siriuserp.sdk.dm.Lot;
import com.siriuserp.sdk.dm.Party;
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
@Table(name = "stock_adjustment_item_controllable_bridge")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StockAdjustmentItemControllableBridge extends Controllable
{
	private static final long serialVersionUID = -4080723051752661867L;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_stock_adjustment_item")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private StockAdjustmentItem stockAdjustmentItem;

	@Override
	public Date getDate() {
		return getStockAdjustmentItem().getStockAdjustment().getDate();
	}

	@Override
	public Product getProduct() {
		return getProduct();
	}

	@Override
	public BigDecimal getQuantity() {
		return getStockAdjustmentItem().getQuantity();
	}

	@Override
	public Party getOrganization() {
		return getStockAdjustmentItem().getStockAdjustment().getOrganization();
	}

	@Override
	public Container getContainer() {
		return getStockAdjustmentItem().getContainer();
	}

	@Override
	public Grid getGrid() {
		return getStockAdjustmentItem().getGrid();
	}

	@Override
	public Container getSourceContainer() {
		return getStockAdjustmentItem().getContainer();
	}

	@Override
	public Grid getSourceGrid() {
		return getStockAdjustmentItem().getGrid();
	}

	@Override
	public Container getDestinationContainer() {
		return getStockAdjustmentItem().getContainer();
	}

	@Override
	public Grid getDestinationGrid() {
		return getStockAdjustmentItem().getGrid();
	}

	@Override
	public Lot getLot() {
		return getStockAdjustmentItem().getLot();
	}

	@Override
	public Tag getTag() {
		return getStockAdjustmentItem().getTag();
	}
	
	@Override
	public BigDecimal getUnitPrice() {
		return getInOuts().iterator().next().getPrice();
	}
	
	@Override
	public Lot getOriginLot() {
		return null;
	}
	
	@Override
	public String getAuditCode() {
		return this.id + "";
	}
}

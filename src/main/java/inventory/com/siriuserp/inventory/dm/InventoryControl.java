/**
 * 
 */
package com.siriuserp.inventory.dm;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.Grid;
import com.siriuserp.sdk.dm.Lot;
import com.siriuserp.sdk.dm.Model;
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
@Table(name = "inventory_control")
@Inheritance(strategy = InheritanceType.JOINED)
public class InventoryControl extends Model implements Inventoriable
{
	private static final long serialVersionUID = 5797539093136167226L;

	@Column(name = "serial_no", length = 100, unique = true)
	private String serial;
	
	@Column(name = "code")
	private String code;

	@Column(name = "info")
	private String info;
	
	@Embedded
	private Tag tag = new Tag();
	
	@Column(name = "quantity")
    private BigDecimal quantity = BigDecimal.ZERO;
	
	@Column(name = "buffer")
    private BigDecimal buffer = BigDecimal.ZERO;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_stockable")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Stockable stockable;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_transaction_item")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private WarehouseTransactionItem warehouseTransactionItem;
	
	@Override
	public Date getDate() {
		return getWarehouseTransactionItem().getReferenceItem().getDate();
	}
	
	@Override
	public Product getProduct() {
		return getWarehouseTransactionItem().getReferenceItem().getProduct();
	}

	@Override
	public Party getOrganization() {
		return getWarehouseTransactionItem().getOrganization();
	}

	@Override
	public Container getContainer() {
		return getSourceContainer();
	}
	
	@Override
	public Grid getGrid() {
		return getWarehouseTransactionItem().getSourceGrid();
	}

	@Override
	public Container getSourceContainer() {
		return getWarehouseTransactionItem().getSourceContainer();
	}

	@Override
	public Grid getSourceGrid() {
		return getWarehouseTransactionItem().getSourceGrid();
	}

	@Override
	public Container getDestinationContainer() {
		return getWarehouseTransactionItem().getDestinationContainer();
	}

	@Override
	public Grid getDestinationGrid() {
		return getWarehouseTransactionItem().getDestinationGrid();
	}

	@Override
	public Lot getLot() 
	{
		Lot lot = new Lot();
		lot.setCode(getCode());
		lot.setSerial(getSerial());
		lot.setInfo(getInfo());
		
		return lot;
	}

	@Override
	public String getAuditCode() {
		return getId() + "";
	}
}

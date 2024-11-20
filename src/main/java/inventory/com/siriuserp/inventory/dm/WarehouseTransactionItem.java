/**
 * 
 */
package com.siriuserp.inventory.dm;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;

import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.Grid;
import com.siriuserp.sdk.dm.Lot;
import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.Money;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.Tag;

import javolution.util.FastSet;
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
@Table(name = "warehouse_transaction_item")
@Inheritance(strategy = InheritanceType.JOINED)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WarehouseTransactionItem extends Model implements TransactionItem
{
	private static final long serialVersionUID = -5470757129182546234L;

	@Column(name = "quantity")
	protected BigDecimal quantity;

	@Column(name = "receipted")
	protected BigDecimal receipted = BigDecimal.ZERO;

	@Column(name = "unreceipted")
	protected BigDecimal unreceipted = BigDecimal.ZERO;

	@Column(name = "issued")
	protected BigDecimal issued = BigDecimal.ZERO;

	@Column(name = "unissued")
	protected BigDecimal unissued = BigDecimal.ZERO;
	
	@Column(name = "returned")
	protected BigDecimal returned = BigDecimal.ZERO;

	@Column(name = "locked")
	@Type(type = "yes_no")
	protected boolean locked = false;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "warehouse_transaction_type")
	protected WarehouseTransactionType type;

	@Enumerated(EnumType.STRING)
	@Column(name = "goods_type")
	protected GoodsType goodsType = GoodsType.STOCK;

	@Enumerated(EnumType.STRING)
	@Column(name = "warehouse_transaction_source")
	protected WarehouseTransactionSource transactionSource;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_reference_item")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	protected WarehouseReferenceItem referenceItem;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_transaction_item")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	protected WarehouseTransactionItem transactionItem;
	
	@OneToMany(mappedBy = "transactionItem", fetch = FetchType.LAZY)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	protected Set<GoodsIssueItem> issuedItems = new FastSet<GoodsIssueItem>();
//
//	@OneToMany(mappedBy = "warehouseTransactionItem", fetch = FetchType.LAZY)
//	@LazyCollection(LazyCollectionOption.EXTRA)
//	@Fetch(FetchMode.SELECT)
//	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
//	protected Set<GoodsReceiptItem> receiptedItems = new FastSet<GoodsReceiptItem>();

	@OneToMany(mappedBy = "originItem", fetch = FetchType.LAZY)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	protected Set<ProductInOutTransaction> inOuts = new FastSet<ProductInOutTransaction>();
	
	@OneToMany(mappedBy = "sourceItem", fetch = FetchType.LAZY)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	@OrderBy("id ASC")
	protected Set<StockControl> sources = new FastSet<StockControl>();

	@OneToMany(mappedBy = "destinationItem", fetch = FetchType.LAZY)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	@OrderBy("id ASC")
	protected Set<StockControl> destinations = new FastSet<StockControl>();

	@OneToMany(mappedBy = "warehouseTransactionItem", fetch = FetchType.LAZY)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	@OrderBy("id ASC")
	protected Set<InventoryControl> inventoryControls = new FastSet<InventoryControl>();
	
	public Party getOrganization()
	{
		return getReferenceItem().getOrganization();
	}
	
	public Container getSourceContainer()
	{
		return getReferenceItem().getSourceContainer();
	}

	public Container getDestinationContainer()
	{
		return getReferenceItem().getDestinationContainer();
	}

	public Grid getSourceGrid()
	{
		return getReferenceItem().getSourceGrid();
	}

	public Grid getDestinationGrid()
	{
		return getReferenceItem().getDestinationGrid();
	}

	public Grid getTargetGrid()
	{
		return null;
	}

	public Product getProduct()
	{
		return getReferenceItem().getProduct();
	}

	public Money getMoney()
	{
		return getReferenceItem().getMoney();
	}

	public String getReference()
	{
		if (getType().equals(WarehouseTransactionType.IN))
			return getReferenceItem().getReferenceFrom();

		if (getType().equals(WarehouseTransactionType.OUT))
			return getReferenceItem().getReferenceTo();

		return "";
	}

	@Override
	public WarehouseTransactionSource getTransactionSource()
	{
		return transactionSource;
	}

	public void setTransactionSource(WarehouseTransactionSource transactionSource)
	{
		this.transactionSource = transactionSource;
	}

	public Lot getLot()
	{
		return getReferenceItem().getLot();
	}
	
	public Lot getExtLot()
	{
		return getReferenceItem().getExtLot();
	}
	
	public Tag getTag() 
	{
		return getReferenceItem().getTag();
	}

	public String getNote()
	{
		return getReferenceItem().getNote();
	}

	public Long getReferenceId()
	{
		return getReferenceItem().getReferenceId();
	}

	@Override
	public Long getTransactionId() {
		return getReferenceItem().getWarehouseTransaction().getId();
	}

	@Override
	public String getTransactionCode() {
		return getReferenceItem().getReferenceCode();
	}
	
	@Override
	public String getAuditCode() {
		return getId() + "";
	}
	
	public Set<InventoryControl> getInternalInventories()
	{
		Set<InventoryControl> controls = new FastSet<InventoryControl>();
		
		if(getTransactionItem() != null)
			controls.addAll(getTransactionItem().getInternalInventories());
		
		controls.addAll(getInventoryControls());
		
		return controls;
	}
	
	public Set<StockControl> getInternalStocks()
	{
		Set<StockControl> controls = new FastSet<StockControl>();
		
		if(getTransactionItem() != null)
			controls.addAll(getTransactionItem().getInternalStocks());
		
		controls.addAll(getDestinations());
		
		return controls;
	}
	
	public String getFullUri() 
	{
		return "<a href=\"page/" + getTransactionSource().getUri() + "preedit.htm?id=" + getReferenceItem().getWarehouseTransaction().getId() + "\">" + getReferenceItem().getReferenceCode() +"</a>";
	}
}

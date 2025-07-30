/**
 * 
 */
package com.siriuserp.inventory.dm;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
import org.springframework.security.userdetails.ldap.Person;

import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.CreditTerm;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.Grid;
import com.siriuserp.sdk.dm.Lot;
import com.siriuserp.sdk.dm.MemoReferenceItem;
import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.Money;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.ReserveBridge;
import com.siriuserp.sdk.dm.Tag;
import com.siriuserp.sdk.dm.Tax;

import lombok.Getter;
import lombok.Setter;

/**
 * @author
 * Betsu Brahmana Restu
 * PT. Sirius Indonesia
 * betsu@siriuserp.com
 */

@Getter
@Setter
@Entity
@Table(name = "warehouse_reference_item")
@Inheritance(strategy = InheritanceType.JOINED)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public abstract class WarehouseReferenceItem extends Model implements LotComparable, MemoReferenceItem
{
	private static final long serialVersionUID = 4151290313001919093L;

	@Column(name = "reference_code")
	protected String referenceCode;

	@Column(name = "date")
	protected Date date = new Date();

	@Column(name = "note")
	protected String note;

	@Column(name = "reference_to")
	protected String referenceTo;

	@Column(name = "reference_from")
	protected String referenceFrom;

	@Embedded
	protected Tag tag = new Tag();

	@Embedded
	protected Lot lot = new Lot();

	@Embedded
	private Money money = new Money();

	@Column(name = "verificated")
	@Type(type = "yes_no")
	protected boolean verificated = false;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_party_organization")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	protected Party organization;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_facility")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	protected Facility facility;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_party")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	protected Party party;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_facility_destination")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	protected Facility facilityDestination;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_facility_source")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	protected Facility facilitySource;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_grid_source")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	protected Grid sourceGrid;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_grid_destination")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	protected Grid destinationGrid;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_product")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	protected Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_tax")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	protected Tax tax;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_tax_ext")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	protected Tax extTax1;

	@OneToOne(mappedBy = "referenceItem", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	protected WarehouseTransactionItem transactionItem;
	
	@Override
	public Money getMoney()
	{
		return this.money;
	}

	public abstract WarehouseTransaction getWarehouseTransaction();

	public abstract WarehouseTransactionSource getTransactionSource();

	public Person getApprover()
	{
		return null;
	}

	public Person getSecondaryApprover()
	{
		return null;
	}

	public String getRefTo()
	{
		return getFacilityDestination().getName();
	}

	public String getRefFrom()
	{
		return getReferenceFrom();
	}

	public void setSourceContainer(Container container)
	{
	}

	public void setDestinationContainer(Container container)
	{
	}

	public Container getPosition()
	{
		return null;
	}

	public Container getContainer()
	{
		return null;
	}

	public Container getSourceContainer()
	{
		return null;
	}

	public Container getDestinationContainer()
	{
		return null;
	}

	public CreditTerm getCreditTerm()
	{
		return null;
	}

	@Override
	public Long getMemoableId()
	{
		return null;
	}

	public Long getReferenceId()
	{
		return null;
	}

	public Long getConversionId()
	{
		return null;
	}

	public Long getTransferId()
	{
		return null;
	}

	public Lot getExtLot()
	{
		return null;
	}

	public String getMeasureName()
	{
		return getProduct().getUnitOfMeasure().getMeasureId();
	}

	public GoodsType getGoodsType()
	{
		return GoodsType.STOCK;
	}

	public Set<GoodsReceipt> getReceipts()
	{
		Set<GoodsReceipt> receipts = new HashSet<GoodsReceipt>();
		if (getTransactionItem() != null)
			receipts.addAll(getTransactionItem().getReceiptedItems().stream().map(item -> item.getGoodsReceipt()).collect(Collectors.toSet()));

		return receipts;
	}

	public Set<GoodsIssue> getIssueds()
	{
		Set<GoodsIssue> issueds = new HashSet<GoodsIssue>();
		if (getTransactionItem() != null)
			issueds.addAll(getTransactionItem().getIssuedItems().stream().map(item -> item.getGoodsIssue()).collect(Collectors.toSet()));

		return issueds;
	}

	public ReserveBridge getReserveBridge()
	{
		return null;
	}

	public BigDecimal getQuantity()
	{
		return BigDecimal.ZERO;
	}

	@Override
	public String getAuditCode()
	{
		return this.id + " ";
	}

	// Needed for PO Item
	public Date getDeliveryDate()
	{
		return null;
	}
}

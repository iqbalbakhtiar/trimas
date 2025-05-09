package com.siriuserp.procurement.dm;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;

import com.siriuserp.accountpayable.dm.InvoiceVerificationReferenceItem;
import com.siriuserp.inventory.dm.WarehouseReferenceItem;
import com.siriuserp.inventory.dm.WarehouseTransaction;
import com.siriuserp.inventory.dm.WarehouseTransactionSource;
import com.siriuserp.sdk.dm.JSONSupport;

import javolution.util.FastSet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "purchase_order_item")
public class PurchaseOrderItem extends WarehouseReferenceItem implements JSONSupport
{
	private static final long serialVersionUID = 6323790446769057283L;

	@Column(name = "delivery_date")
	private Date deliveryDate;

	@Column(name = "quantity")
	private BigDecimal quantity = BigDecimal.ZERO;

	@Column(name = "barcode_quantity")
	private BigDecimal barcodeQuantity = BigDecimal.ZERO;

	@Column(name = "discount")
	private BigDecimal discount = BigDecimal.ZERO;

	@Column(name = "discount_percent")
	private BigDecimal discountPercent = BigDecimal.ZERO;

	@Column(name = "locked")
	@Type(type = "yes_no")
	private boolean locked = Boolean.FALSE;

	@Enumerated(EnumType.STRING)
	@Column(name = "purchase_item_type")
	private PurchaseOrderItemType purchaseItemType = PurchaseOrderItemType.BASE;

	@Enumerated(EnumType.STRING)
	@Column(name = "warehouse_transaction_source")
	private WarehouseTransactionSource transactionSource = WarehouseTransactionSource.DIRECT_PURCHASE_ORDER;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_purchase_order")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private PurchaseOrder purchaseOrder;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_purchase_order_item")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private PurchaseOrderItem itemParent;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_purchase_requisition_item")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private PurchaseRequisitionItem requisitionItem;

	@OneToMany(mappedBy = "purchaseOrderItem", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	@OrderBy("id")
	private Set<InvoiceVerificationReferenceItem> invoiceReferences = new FastSet<InvoiceVerificationReferenceItem>();

	@OneToMany(mappedBy = "itemParent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	@OrderBy("id")
	private Set<PurchaseOrderItem> serials = new FastSet<PurchaseOrderItem>();

	@Override
	public Date getDeliveryDate()
	{
		return this.deliveryDate;
	}

	@Override
	public Long getReferenceId()
	{
		return getPurchaseOrder().getId();
	}

	@Override
	public WarehouseTransaction getWarehouseTransaction()
	{
		return getPurchaseOrder();
	}

	// (Qty * Amount)
	public BigDecimal getTotalAmount()
	{
		return this.quantity.multiply(this.getMoney().getAmount()).subtract(getDiscount());
	}

	@Override
	public String getRefFrom()
	{
		return getPurchaseOrder().getSupplier().getFullName();
	}

	@Override
	public WarehouseTransactionSource getTransactionSource()
	{
		return this.transactionSource;
	}
}

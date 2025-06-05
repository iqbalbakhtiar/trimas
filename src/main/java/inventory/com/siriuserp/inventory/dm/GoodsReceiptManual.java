package com.siriuserp.inventory.dm;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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

import com.siriuserp.sdk.dm.BarcodeGroup;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.JSONSupport;
import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.Tax;

import javolution.util.FastSet;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Andres Nodas
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Entity
@Getter
@Setter
@Table(name = "goods_receipt_manual")
public class GoodsReceiptManual extends Model implements Transaction, JSONSupport, Receiptable {

	private static final long serialVersionUID = 5895311821742826408L;
	
	@Column(name = "code")
	private String code;

	@Column(name = "invoice_no")
    private String invoiceNo;
	
	@Column(name = "date")
	private Date date;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_facility")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Facility facility;
	
	@ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="fk_party_organization")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Party organization;

	@ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="fk_party_supplier")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Party supplier;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_barcode_group")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private BarcodeGroup barcodeGroup;
	
	@OneToMany(mappedBy = "goodsReceiptManual", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	@OrderBy("id")
	private Set<GoodsReceiptManualItem> items = new FastSet<GoodsReceiptManualItem>();

	
	public BigDecimal getTotalQuantity() {
		
		BigDecimal amount = BigDecimal.ZERO;
		
		for(GoodsReceiptManualItem item : getItems())
			amount = amount.add(item.getQuantity());
			
		return amount;
	}
	
	public BigDecimal getTotalQuantityPcs() {
		
		BigDecimal amount = BigDecimal.ZERO;
		
		for(GoodsReceiptManualItem item : getItems())
			amount = amount.add(item.getQuantity().multiply(item.getProduct().getQtyToBase()));
			
		return amount;
	}

	@Override
    public String getAuditCode() {
        return id + ',' + code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

	@Override
	public Date getDate() {
		return date;
	}

	@Override
	public Tax getTax() {
		return null;
	}

	@Override
	public Currency getCurrency()
	{
		return null;
	}

	@Override
	public Party getParty()
	{
		return getOrganization();
	}
	
	@Override
	public String getNote() {
		return "";
	}

	@Override
	public String getRef()
	{
		return "";
	}

	@Override
	public Set<? extends WarehouseReferenceItem> getReceiptables() {
		return this.items;
	}

	public Set<GoodsReceipt> getReceipts() {
	    return getItems() == null ? Collections.emptySet() :
	        getItems().stream()
	            .map(GoodsReceiptManualItem::getTransactionItem)
	            .filter(Objects::nonNull)
	            .map(WarehouseTransactionItem::getReceiptedItems)
	            .filter(Objects::nonNull)
	            .flatMap(list -> list.stream())
	            .map(GoodsReceiptItem::getGoodsReceipt)
	            .filter(Objects::nonNull)
	            .collect(Collectors.toSet());
	}
	
}

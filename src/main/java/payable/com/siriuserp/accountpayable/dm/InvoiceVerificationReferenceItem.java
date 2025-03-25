/**
 * File Name  : InvoiceVerificationReferenceItem.java
 * Created On : Feb 26, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.accountpayable.dm;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;

import com.siriuserp.inventory.dm.GoodsReceiptItem;
import com.siriuserp.procurement.dm.PurchaseOrderItem;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.Party;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Getter
@Setter
@Entity
@Table(name = "invoice_verification_item_reference")
public class InvoiceVerificationReferenceItem extends Model
{
	private static final long serialVersionUID = -8150505060906124238L;

	@Column(name = "code")
	private String code;

	@Column(name = "date")
	private Date date;

	@Column(name = "note")
	private String note;

	@Column(name = "verificated")
	@Type(type = "yes_no")
	private boolean verificated = false;

	@Enumerated(EnumType.STRING)
	@Column(name = "reference_type")
	private InvoiceVerificationReferenceType referenceType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_party_organization")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Party organization;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_facility")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Facility facility;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_party_supplier")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Party supplier;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_purchase_order_item")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private PurchaseOrderItem purchaseOrderItem;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_goods_receipt_item")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private GoodsReceiptItem goodsReceiptItem;

	@OneToOne(mappedBy = "invoiceReference", fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private InvoiceVerificationItem invoiceVerificationItem;

	@Override
	public String getAuditCode()
	{
		return this.id.toString();
	}
}

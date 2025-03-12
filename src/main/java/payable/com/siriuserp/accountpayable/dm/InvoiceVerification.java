/**
 * File Name  : InvoiceVerification.java
 * Created On : Feb 26, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.accountpayable.dm;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import com.siriuserp.inventory.dm.GoodsReceipt;
import com.siriuserp.procurement.dm.PurchaseOrder;
import com.siriuserp.sdk.dm.datawarehouse.APLedgerView;

import javolution.util.FastSet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "invoice_verification")
public class InvoiceVerification extends Payable
{
	private static final long serialVersionUID = -6623484035906573364L;

	@OneToMany(mappedBy = "invoiceVerification", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	private Set<InvoiceVerificationItem> items = new FastSet<InvoiceVerificationItem>();

	public Set<GoodsReceipt> getGoodsReceipts()
	{
		Set<GoodsReceipt> receipts = new HashSet<GoodsReceipt>();

		for (InvoiceVerificationItem item : getItems())
		{
			if (item.getInvoiceReference().getGoodsReceiptItem() != null)
				receipts.add(item.getInvoiceReference().getGoodsReceiptItem().getGoodsReceipt());
		}

		return receipts;
	}

	public Set<PurchaseOrder> getPurchaseOrders()
	{
		Set<PurchaseOrder> purchaseOrders = new HashSet<PurchaseOrder>();

		for (InvoiceVerificationItem item : getItems())
		{
			if (item.getInvoiceReference().getPurchaseOrderItem() != null)
				purchaseOrders.add(item.getInvoiceReference().getPurchaseOrderItem().getPurchaseOrder());
		}

		return purchaseOrders;
	}

	@Override
	public String getUri()
	{
		return "invoiceverificationpreedit.htm";
	}

	@Override
	public String getAuditCode()
	{
		return getId() + ',' + getCode();
	}

	@Override
	public BigDecimal getCredit()
	{
		return getMoney().getAmount();
	}

	@Override
	public BigDecimal getDebet()
	{
		return BigDecimal.ZERO;
	}

	@Override
	public String getLedgerType()
	{
		return "INV";
	}

	@Override
	public int compareTo(APLedgerView o)
	{
		return getDate().compareTo(o.getDate());
	}
}

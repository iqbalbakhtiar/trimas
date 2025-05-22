/**
 * File Name  : InvoiceVerification.java
 * Created On : Feb 26, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.accountpayable.dm;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import com.siriuserp.sdk.utility.DateHelper;

import javolution.util.FastMap;
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

	@Enumerated(EnumType.STRING)
	@Column(name = "invoice_type")
	protected InvoiceVerificationType invoiceType = InvoiceVerificationType.STANDARD;

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
	public Long getReferenceId()
	{
		return getId();
	}

	@Override
	public String getUri()
	{
		if (this.getInvoiceType() == InvoiceVerificationType.STANDARD)
			return "invoiceverificationpreedit.htm";
		else if (this.getInvoiceType() == InvoiceVerificationType.MANUAL) {
			return "manualinvoiceverificationpreedit.htm";
		}
		return "";
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
	public int compareTo(APLedgerView o)
	{
		return getDate().compareTo(o.getDate());
	}

	@Override
	public Map<String, Object> val()
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("verId", getId());
		map.put("verCode", getCode());

		map.put("amount", money.getAmount());
		map.put("unpaid", getUnpaid());
		map.put("paid", money.getAmount().subtract(getUnpaid()));

		map.put("date", DateHelper.format(getDate()));

		return map;
	}

	@Override
	public String getAuditCode()
	{
		return getId() + ',' + getCode();
	}
}

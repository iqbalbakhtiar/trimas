package com.siriuserp.inventory.adapter;

import com.siriuserp.inventory.dm.GoodsReceipt;
import com.siriuserp.inventory.dm.Product;
import com.siriuserp.inventory.dm.WarehouseReferenceItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author Rama Almer Felix
 * Sirius Indonesia, PT
 */

@Setter
@Getter
@NoArgsConstructor
public class GoodsReceiptReportAdapter {
	private Product product;
	private WarehouseReferenceItem purchaseOrderItem;
	private BigDecimal quantity;
	private BigDecimal unitPrice;

	public GoodsReceiptReportAdapter(Product product, WarehouseReferenceItem refItem, BigDecimal quantity, BigDecimal unitPrice) {
		this.product = product;
		this.purchaseOrderItem = refItem;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
	}

	public BigDecimal getTotal() {
		return quantity != null && unitPrice != null ? quantity.multiply(unitPrice) : BigDecimal.ZERO;
	}

	public String getInvoiceNo() {
		return purchaseOrderItem.getReceipts()
				.stream()
				.findFirst()
				.map(GoodsReceipt::getInvoiceNo)
				.orElse("");
	}
}

package com.siriuserp.procurement.adapter;

import com.siriuserp.inventory.dm.Product;
import com.siriuserp.procurement.dm.PurchaseDocumentType;
import com.siriuserp.sdk.adapter.AbstractUIAdapter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseMonthlyReportAdapter extends AbstractUIAdapter
{
	private static final long serialVersionUID = 6487977149021666750L;

	// Purchase Order Item
	private Product product;
	private BigDecimal quantity;
	private BigDecimal amount;

	// Purchase Order
	PurchaseDocumentType purchaseDocumentType;
}

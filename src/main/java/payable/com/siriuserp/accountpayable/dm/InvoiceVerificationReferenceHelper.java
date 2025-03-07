/**
 * File Name  : InvoiceVerificationReferenceHelper.java
 * Created On : Feb 26, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.accountpayable.dm;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

public class InvoiceVerificationReferenceHelper
{
	public static InvoiceVerificationItem initItem(InvoiceVerificationReferenceItem itemReference) throws Exception
	{
		InvoiceVerificationItem item = new InvoiceVerificationItem();
		item.setInvoiceReference(itemReference);

		if (itemReference.getReferenceType().equals(InvoiceVerificationReferenceType.PURCHASE_ORDER))
		{
			item.setQuantity(itemReference.getPurchaseOrderItem().getQuantity());
			item.setDiscount(itemReference.getPurchaseOrderItem().getDiscount());
			item.setDiscountPercent(itemReference.getPurchaseOrderItem().getDiscountPercent());
			item.setMoney(itemReference.getPurchaseOrderItem().getMoney());
			item.setProduct(itemReference.getPurchaseOrderItem().getProduct());
		} else
		{
			item.setQuantity(itemReference.getGoodsReceiptItem().getQuantity());
			item.setMoney(itemReference.getGoodsReceiptItem().getMoney());
			item.setProduct(itemReference.getGoodsReceiptItem().getProduct());
		}

		return item;
	}
}

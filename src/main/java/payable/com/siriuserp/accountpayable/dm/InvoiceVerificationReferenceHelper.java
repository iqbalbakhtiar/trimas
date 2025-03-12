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
	public static InvoiceVerificationItem initItem(InvoiceVerificationItemReference itemReference) throws Exception
	{
		InvoiceVerificationItem item = new InvoiceVerificationItem();
		item.setInvoiceReference(itemReference);
		item.setDiscount(itemReference.getPurchaseOrderItem().getDiscount());
		item.setDiscountPercent(itemReference.getPurchaseOrderItem().getDiscountPercent());
		item.setMoney(itemReference.getPurchaseOrderItem().getMoney());
		item.setProduct(itemReference.getPurchaseOrderItem().getProduct());

		if (itemReference.getReferenceType().equals(InvoiceVerificationReferenceType.PURCHASE_ORDER))
			item.setQuantity(itemReference.getPurchaseOrderItem().getQuantity());
		else
			item.setQuantity(itemReference.getPurchaseOrderItem().getBarcodeQuantity());

		return item;
	}
}

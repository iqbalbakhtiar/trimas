/**
 * File Name  : InvoiceVerificationReferenceType.java
 * Created On : Feb 26, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.accountpayable.dm;

import com.siriuserp.sdk.dm.SiriusEnum;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

public enum InvoiceVerificationReferenceType implements SiriusEnum
{
	PURCHASE_ORDER, GOODS_RECEIPT;

	public String getMessageName()
	{
		return this.toString().replace("_", "").toLowerCase();
	}
}

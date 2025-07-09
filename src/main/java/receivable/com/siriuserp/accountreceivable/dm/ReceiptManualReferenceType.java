/**
 * File Name  : ReceiptManualReferenceType.java
 * Created On : Dec 5, 2023
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.accountreceivable.dm;

import com.siriuserp.sdk.dm.SiriusEnum;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

public enum ReceiptManualReferenceType implements SiriusEnum
{
	GENERAL, DEBIT_MEMO_RETURN;

	public String getMessageName()
	{
		return this.toString().replace("_", "").toLowerCase();
	}
}

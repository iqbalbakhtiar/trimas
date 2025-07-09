/**
 * File Name  : PaymentManualReferenceType.java
 * Created On : Oct 18, 2023
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.accountpayable.dm;

import com.siriuserp.sdk.dm.SiriusEnum;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

public enum PaymentManualReferenceType implements SiriusEnum
{
	GENERAL, CREDIT_MEMO_RETURN;

	public String getMessageName()
	{
		return this.toString().replace("_", "").toLowerCase();
	}
}

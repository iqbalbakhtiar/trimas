/**
 * File Name  : DeliveryOrderReferenceType.java
 * Created On : Mar 6, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.sales.dm;

import com.siriuserp.sdk.dm.SiriusEnum;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

public enum DeliveryOrderReferenceType implements SiriusEnum
{
	SALES_ORDER, DELIVERY_PLANNING;

	public String getMessageName()
	{
		return this.toString().replace("_", "").toLowerCase();
	}
}

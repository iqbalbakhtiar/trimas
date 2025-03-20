/**
 * File Name  : DeliveryOrderItemType.java
 * Created On : Mar 20, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.sales.dm;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

public enum DeliveryOrderItemType
{
	BASE, SERIAL;

	public String getNormalizedName()
	{
		return this.toString().replace("_", " ");
	}
}

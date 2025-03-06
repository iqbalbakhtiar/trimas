/**
 * File Name  : PurchaseOrderItemType.java
 * Created On : Mar 1, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.procurement.dm;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

public enum PurchaseOrderItemType
{
	BASE, SERIAL;

	public String getNormalizedName()
	{
		return this.toString().replace("_", " ");
	}
}

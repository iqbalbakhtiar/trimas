/**
 * File Name  : SOStatus.java
 * Created On : Mar 6, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.sales.dm;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

public enum SOStatus
{
	OPEN, CLOSE, LOCK, UNLOCK, PLANNING, SENT, DELIVERED, CANCELED;

	public String getNormalizedName()
	{
		return this.toString().replace("_", " ");
	}

	public String getMessageName()
	{
		return this.toString().replace("_", "").toLowerCase();
	}
}

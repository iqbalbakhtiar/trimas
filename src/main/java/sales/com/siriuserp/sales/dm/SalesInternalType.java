/**
 * File Name  : SalesInternalType.java
 * Created On : May 5, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.sales.dm;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

public enum SalesInternalType
{
	YARN, WASTE;

	public String getNormalizedName()
	{
		return this.toString().replace("_", " ");
	}

	public String getMessageName()
	{
		return this.toString().replace("_", "").toLowerCase();
	}

	public String getCode()
	{
		switch (this)
		{
		case YARN:
			return "BNG";
		case WASTE:
			return "WST";
		default:
			return null;
		}
	}
}

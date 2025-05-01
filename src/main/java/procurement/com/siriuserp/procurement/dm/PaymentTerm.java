/**
 * File Name  : PaymentTerm.java
 * Created On : May 1, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.procurement.dm;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

public enum PaymentTerm
{
	COD,CBD,TT,TENOR;

	public String getNormalizedName()
	{
		return this.toString().replace("_", " ");
	}
}

/**
 * Dec 19, 2007 12:01:08 PM
 * net.konsep.sirius.model
 * DepricationMethod.java
 */
package com.siriuserp.accounting.dm;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public enum DepreciationMethod
{
	STRAIGHT_LINE, NO_DEPRECIATION, DECLINING_BALANCE;

	public String getNormalizedName()
	{
		return this.toString().replace("_", " ");
	}
}

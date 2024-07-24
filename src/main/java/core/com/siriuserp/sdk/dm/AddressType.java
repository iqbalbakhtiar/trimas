/**
 * Oct 29, 2008 6:10:15 PM
 * com.siriuserp.sdk.dm
 * AddressType.java
 */
package com.siriuserp.sdk.dm;

import org.apache.commons.lang.WordUtils;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public enum AddressType
{
    HOME,OFFICE,TAX,SHIPPING,FACTORY,WAREHOUSE;
	
	public String getNormalizedName()
	{
		return this.toString().replace("_", " ");
	}

	public String getCapitalizedName()
	{
		return WordUtils.capitalize(getNormalizedName().toLowerCase());
	}
}

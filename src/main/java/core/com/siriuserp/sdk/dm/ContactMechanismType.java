/**
 * Oct 27, 2008 4:50:01 PM
 * com.siriuserp.sdk.dm
 * ContactMechanismType.java
 */
package com.siriuserp.sdk.dm;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public enum ContactMechanismType
{
    PHONE,MOBILE,FAX,EMAIL,WEBSITE;
	
	public String getMessage()
	{
		return this.toString().toLowerCase();
	}
}

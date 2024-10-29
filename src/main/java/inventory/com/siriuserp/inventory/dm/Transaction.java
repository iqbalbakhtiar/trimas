/**
 * 
 */
package com.siriuserp.inventory.dm;

import com.siriuserp.sdk.dm.Siblingable;

/**
 * @author Betsu Brahmana Restu
 * PT Sirius Indonesia
 * betsu@siriuserp.com
 */

public interface Transaction extends Siblingable
{
	public String getCode();
	public String getSelf();
	
	public Long getId();
}

/**
 * 
 */
package com.siriuserp.inventory.dm;

import java.util.Date;

import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.Siblingable;
import com.siriuserp.sdk.dm.Tax;

/**
 * @author
 * Betsu Brahmana Restu
 * PT. Sirius Indonesia
 * betsu@siriuserp.com
 */
public interface WarehouseTransaction extends Siblingable
{	
	public Party getOrganization();
	public Date getDate();
	public Tax getTax();
	public Currency getCurrency();
	public Party getParty();

	public String getCode();
	public String getNote();
	public String getSelf();
	public String getRef();
}

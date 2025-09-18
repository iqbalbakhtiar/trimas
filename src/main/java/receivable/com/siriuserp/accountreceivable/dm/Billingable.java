/**
 * File Name  : Billingable.java
 * Created On : Aug 25, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.accountreceivable.dm;

import java.util.Date;
import java.util.Set;

import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.PostalAddress;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

public interface Billingable
{
	public Long getReferenceId();
	public Party getOrganization();
	public Party getCustomer();
	public Facility getFacility();
	public String getCode();
	public Date getDate();
	public Currency getCurrency();
	public PostalAddress getShippingAddress();
	public Long getBillingType();
	public Set<BillingReferenceable> getBillingReferenceables();
	public boolean isBillingable();
	public boolean isAutoReceipt();
}

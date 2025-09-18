/**
 * File Name  : BillingReferenceable.java
 * Created On : Aug 25, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.accountreceivable.dm;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

public interface BillingReferenceable
{
	public BillingReferenceType getBillingReferenceType();
	public BillingableItemType getBillingableItemType();
}

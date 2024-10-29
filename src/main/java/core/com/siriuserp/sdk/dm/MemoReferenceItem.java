/**
 * 
 */
package com.siriuserp.sdk.dm;

import com.siriuserp.inventory.dm.Product;

/**
 * @author
 * Betsu Brahmana Restu
 * PT. Sirius Indonesia
 * betsu@siriuserp.com
 */
public interface MemoReferenceItem 
{
	public Long getMemoableId();
	public Product getProduct();
	public Lot getLot();
	public Money getMoney();
}

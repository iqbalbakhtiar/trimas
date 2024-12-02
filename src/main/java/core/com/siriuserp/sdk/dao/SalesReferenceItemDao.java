/**
 * Oct 29, 2008 3:11:34 PM
 * com.siriuserp.sdk.dao
 * PartyDao.java
 */
package com.siriuserp.sdk.dao;

import com.siriuserp.sales.dm.SalesReferenceItem;
import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.base.Filterable;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public interface SalesReferenceItemDao extends Dao<SalesReferenceItem>, Filterable
{
	public SalesReferenceItem loadByProduct(Long productId);

}

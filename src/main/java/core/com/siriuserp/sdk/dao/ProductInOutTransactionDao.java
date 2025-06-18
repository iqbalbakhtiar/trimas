/**
 * Oct 29, 2008 3:11:34 PM
 * com.siriuserp.sdk.dao
 * PartyDao.java
 */
package com.siriuserp.sdk.dao;

import com.siriuserp.inventory.criteria.OnHandQuantityFilterCriteria;
import com.siriuserp.inventory.dm.ProductInOutTransaction;
import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.base.Filterable;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public interface ProductInOutTransactionDao extends Dao<ProductInOutTransaction>, Filterable
{
	public ProductInOutTransaction loadByProduct(OnHandQuantityFilterCriteria criteria);

}

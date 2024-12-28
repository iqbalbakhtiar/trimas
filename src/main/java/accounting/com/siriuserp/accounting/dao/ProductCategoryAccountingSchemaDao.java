/**
 * Mar 31, 2009 2:53:59 PM
 * com.siriuserp.sdk.dao
 * ProductCategoryAccountingShemaDao.java
 */
package com.siriuserp.accounting.dao;

import com.siriuserp.accounting.dm.ProductCategoryAccountingSchema;
import com.siriuserp.sdk.base.Dao;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public interface ProductCategoryAccountingSchemaDao extends Dao<ProductCategoryAccountingSchema>
{
	public ProductCategoryAccountingSchema load(Long schema, Long category);
}

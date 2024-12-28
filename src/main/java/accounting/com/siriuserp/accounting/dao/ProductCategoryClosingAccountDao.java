/**
 * Mar 31, 2009 4:28:25 PM
 * com.siriuserp.sdk.dao
 * ProductCategoryClosingAccountDao.java
 */
package com.siriuserp.accounting.dao;

import com.siriuserp.accounting.dm.ProductCategoryClosingAccount;
import com.siriuserp.sdk.base.Dao;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public interface ProductCategoryClosingAccountDao extends Dao<ProductCategoryClosingAccount>
{
	public ProductCategoryClosingAccount loadBySchemaAndAccountType(Long categorySchema, Long type);
	public ProductCategoryClosingAccount loadByOrganizationAndType(Long org, Long type, Long prodCategory);
}

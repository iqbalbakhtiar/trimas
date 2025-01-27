/**
 * Mar 16, 2009 11:09:54 AM
 * com.siriuserp.sdk.dao
 * ProductDao.java
 */
package com.siriuserp.inventory.dao;

import com.siriuserp.inventory.dm.Product;
import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.base.Filterable;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public interface ProductDao extends Dao<Product>, Filterable
{
	public Product loadByCode(String code);
	public Product loadByName(String name);
	public Product loadByBarcodeId(String barcodeId);
}

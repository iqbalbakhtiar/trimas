/**
 * Mar 16, 2009 11:09:54 AM
 * com.siriuserp.sdk.dao
 * ProductDao.java
 */
package com.siriuserp.inventory.dao;

import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.base.Filterable;
import com.siriuserp.sdk.dm.Barcode;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public interface BarcodeDao extends Dao<Barcode>, Filterable
{
	public Barcode loadByCode(String barcode);
}

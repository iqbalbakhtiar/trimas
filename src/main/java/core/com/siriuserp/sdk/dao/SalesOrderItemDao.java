/**
 * File Name  : SalesOrderItemDao.java
 * Created On : Feb 5, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.sdk.dao;

import com.siriuserp.sales.dm.SalesOrderItem;
import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.base.Filterable;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

public interface SalesOrderItemDao extends Dao<SalesOrderItem>, Filterable
{
	public SalesOrderItem loadByProduct(Long productId);
}

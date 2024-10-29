/**
 * May 13, 2009 11:08:49 AM
 * com.siriuserp.sdk.dao
 * ProductInOutAveragePriceDao.java
 */
package com.siriuserp.inventory.dao;

import com.siriuserp.inventory.dm.ProductInOutAveragePrice;
import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.base.Filterable;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public interface ProductInOutAveragePriceDao extends Dao<ProductInOutAveragePrice>, Filterable
{
    public ProductInOutAveragePrice load(Long product, Long organization);
}

/**
 * 
 */
package com.siriuserp.inventory.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.siriuserp.inventory.dao.ProductInOutAveragePriceDao;
import com.siriuserp.inventory.dm.ProductInOutAveragePrice;
import com.siriuserp.sdk.db.DaoHelper;

/**
 * @author ferdinand
 */
public class ProductInOutAveragePriceDaoImpl extends DaoHelper<ProductInOutAveragePrice> implements ProductInOutAveragePriceDao 
{
	@Override
	public ProductInOutAveragePrice load(Long product, Long organization) 
	{
		Criteria criteria = getSession().createCriteria(ProductInOutAveragePrice.class);
        criteria.createCriteria("product").add(Restrictions.eq("id",product));
        criteria.createCriteria("organization").add(Restrictions.eq("id",organization));
        
        return (ProductInOutAveragePrice)criteria.uniqueResult();
	}
}

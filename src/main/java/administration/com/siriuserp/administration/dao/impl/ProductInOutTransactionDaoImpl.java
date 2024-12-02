/**
 * Apr 20, 2006
 * GriddaoIpml.java
 */
package com.siriuserp.administration.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.siriuserp.inventory.dm.ProductInOutTransaction;
import com.siriuserp.sdk.dao.ProductInOutTransactionDao;
import com.siriuserp.sdk.db.DaoHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component("productInOutTransactionDao")
public class ProductInOutTransactionDaoImpl extends DaoHelper<ProductInOutTransaction> implements ProductInOutTransactionDao
{
	@Override
	public ProductInOutTransaction loadByProduct(Long productId)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("FROM ProductInOutTransaction piot  ");
		builder.append("WHERE piot.product.id =:productId"
				+ " GROUP BY piot.product.id ORDER BY piot.date DESC ");

		Query query = getSession().createQuery(builder.toString());
		query.setMaxResults(1);
		query.setParameter("productId", productId);

		return (ProductInOutTransaction) query.uniqueResult();
	}
	
}

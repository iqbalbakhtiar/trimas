/**
 * File Name  : SalesOrderItemDaoImpl.java
 * Created On : Feb 5, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.sales.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.siriuserp.sales.dao.SalesOrderItemDao;
import com.siriuserp.sales.dm.SalesOrderItem;
import com.siriuserp.sdk.db.DaoHelper;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Component
public class SalesOrderItemDaoImpl extends DaoHelper<SalesOrderItem> implements SalesOrderItemDao
{
	@Override
	public SalesOrderItem loadByProduct(Long productId)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("FROM SalesOrderItem item  ");
		builder.append("WHERE item.product.id =:productId ");
		builder.append("ORDER BY item.id DESC ");

		Query query = getSession().createQuery(builder.toString());
		query.setMaxResults(1);
		query.setParameter("productId", productId);

		return (SalesOrderItem) query.uniqueResult();
	}
}

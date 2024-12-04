/**
 * Apr 20, 2006
 * GriddaoIpml.java
 */
package com.siriuserp.administration.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.siriuserp.sales.dm.SalesReferenceItem;
import com.siriuserp.sdk.dao.SalesReferenceItemDao;
import com.siriuserp.sdk.db.DaoHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component("salesReferenceItemDao")
public class SalesReferenceItemDaoIpml extends DaoHelper<SalesReferenceItem> implements SalesReferenceItemDao
{
	@Override
	public SalesReferenceItem loadByProduct(Long productId)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("FROM SalesReferenceItem sri  ");
		builder.append("WHERE sri.product.id =:productId GROUP BY sri.product.id ORDER BY sri.date DESC ");

		Query query = getSession().createQuery(builder.toString());
		query.setMaxResults(1);
		query.setParameter("productId", productId);

		return (SalesReferenceItem) query.uniqueResult();
	}
	
}

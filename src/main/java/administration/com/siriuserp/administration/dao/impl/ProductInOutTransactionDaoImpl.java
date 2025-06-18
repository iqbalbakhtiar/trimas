/**
 * Apr 20, 2006
 * GriddaoIpml.java
 */
package com.siriuserp.administration.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.siriuserp.inventory.criteria.OnHandQuantityFilterCriteria;
import com.siriuserp.inventory.dm.ProductInOutTransaction;
import com.siriuserp.sdk.dao.ProductInOutTransactionDao;
import com.siriuserp.sdk.db.DaoHelper;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component("productInOutTransactionDao")
public class ProductInOutTransactionDaoImpl extends DaoHelper<ProductInOutTransaction> implements ProductInOutTransactionDao
{
	@Override
	public ProductInOutTransaction loadByProduct(OnHandQuantityFilterCriteria criteria) {
		
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT trans FROM ProductInOutTransaction trans ");
		builder.append("WHERE trans.product.id =:productId AND trans.container.id =:containerId ");
		
		if(SiriusValidator.validateParam(criteria.getSerial()))
			builder.append("AND trans.serial =:serial ");
		
		builder.append("ORDER BY trans.id DESC");

		Query query = getSession().createQuery(builder.toString());
		
		if(SiriusValidator.validateParam(criteria.getSerial()))
			query.setParameter("serial", criteria.getSerial());
		
		query.setParameter("productId", criteria.getProductId());
		query.setParameter("containerId", criteria.getContainerId());
		
		query.setMaxResults(1);

		 Object obj = query.uniqueResult();
		    return obj != null ? (ProductInOutTransaction) obj : null;
	}
}

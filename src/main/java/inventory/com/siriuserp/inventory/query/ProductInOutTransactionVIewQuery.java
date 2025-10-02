/**
 * File Name  : ProductInOutTransactionVIewQuery.java
 * Created On : Oct 2, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.inventory.query;

import org.hibernate.Query;

import com.siriuserp.inventory.criteria.OnHandQuantityFilterCriteria;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

public class ProductInOutTransactionVIewQuery extends AbstractGridViewQuery
{
	@Override
	public Query getQuery(ExecutorType type)
	{
		OnHandQuantityFilterCriteria criteria = (OnHandQuantityFilterCriteria) getFilterCriteria();
		StringBuilder builder = new StringBuilder();

		if (type.equals(ExecutorType.COUNT))
			builder.append("SELECT COUNT(trans.id) ");

		builder.append("FROM ProductInOutTransaction trans ");
		builder.append("WHERE trans.quantity > 0 AND trans.product.id =:product AND trans.container.id =:container ");

		if (SiriusValidator.validateParam(criteria.getSerial()))
			builder.append("AND trans.serial =:serial ");

		if (SiriusValidator.validateParam(criteria.getLotCode()))
			builder.append("AND trans.code =:lotCode ");

		if (type.equals(ExecutorType.HQL))
			builder.append("ORDER BY trans.date ASC ");

		Query query = getSession().createQuery(builder.toString());
		query.setParameter("product", criteria.getProduct());
		query.setParameter("container", criteria.getContainer());

		if (SiriusValidator.validateParam(criteria.getSerial()))
			query.setParameter("serial", criteria.getSerial());

		if (SiriusValidator.validateParam(criteria.getLotCode()))
			query.setParameter("lotCode", criteria.getLotCode());

		return query;
	}
}

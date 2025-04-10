/**
 * File Name  : DeliveryOrderRealizationAddViewQuery.java
 * Created On : Mar 18, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.sales.query;

import org.hibernate.Query;

import com.siriuserp.sales.dm.SOStatus;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

public class DeliveryOrderRealizationAddViewQuery extends AbstractGridViewQuery
{
	@Override
	public Query getQuery(ExecutorType executorType)
	{
		StringBuilder builder = new StringBuilder();

		if (executorType.equals(ExecutorType.COUNT))
			builder.append("SELECT COUNT(DISTINCT delivery) ");

		builder.append("FROM DeliveryOrder delivery ");
		builder.append("WHERE delivery.status =:status ");
		builder.append("ORDER BY delivery.id DESC ");

		Query query = getSession().createQuery(builder.toString());
		query.setReadOnly(true);
		query.setParameter("status", SOStatus.OPEN);

		return query;
	}
}

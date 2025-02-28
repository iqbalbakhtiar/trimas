/**
 * File Name  : PurchaseRequisitionItemViewQuery.java
 * Created On : Feb 23, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.procurement.query;

import org.hibernate.Query;

import com.siriuserp.procurement.criteria.PurchaseRequisitionFilterCriteria;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

public class PurchaseRequisitionItemViewQuery extends AbstractGridViewQuery
{
	@Override
	public Query getQuery(ExecutorType executorType)
	{
		PurchaseRequisitionFilterCriteria criteria = (PurchaseRequisitionFilterCriteria) getFilterCriteria();
		StringBuilder builder = new StringBuilder();

		if (executorType.equals(ExecutorType.COUNT))
			builder.append("SELECT COUNT(DISTINCT item) ");
		else
			builder.append("SELECT DISTINCT(item) ");

		builder.append("FROM PurchaseRequisitionItem item ");
		builder.append("WHERE item.available = 'Y' ");

		if (SiriusValidator.validateParam(criteria.getCode()))
			builder.append("AND item.purchaseRequisition.code LIKE :code");

		if (SiriusValidator.validateParam(criteria.getRequisitionerName()))
			builder.append("AND item.purchaseRequisition.requisitioner.fullName LIKE :requisitioner ");

		if (SiriusValidator.validateDate(criteria.getDateFrom()))
		{
			if (SiriusValidator.validateDate(criteria.getDateTo()))
				builder.append("AND item.purchaseRequisition.date BETWEEN :startDate AND :endDate ");
			else
				builder.append("AND item.purchaseRequisition.date >= :startDate ");
		} else if (SiriusValidator.validateDate(criteria.getDateTo()))
			builder.append("AND item.purchaseRequisition.date <= :endDate ");

		builder.append("ORDER BY item.product.name ASC");

		Query query = getSession().createQuery(builder.toString());
		query.setReadOnly(true);

		if (SiriusValidator.validateParam(criteria.getCode()))
			query.setParameter("code", "%" + criteria.getCode() + "%");

		if (SiriusValidator.validateParam(criteria.getRequisitionerName()))
			query.setParameter("requisitioner", "%" + criteria.getRequisitionerName() + "%");

		if (SiriusValidator.validateDate(criteria.getDateFrom()))
			query.setParameter("startDate", criteria.getDateFrom());

		if (SiriusValidator.validateDate(criteria.getDateTo()))
			query.setParameter("endDate", criteria.getDateTo());

		return query;
	}
}

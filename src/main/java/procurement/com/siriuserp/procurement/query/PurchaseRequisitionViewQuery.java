/**
 * File Name  : PurchaseRequisitionViewQuery.java
 * Created On : Feb 21, 2025
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

public class PurchaseRequisitionViewQuery extends AbstractGridViewQuery
{
	@Override
	public Query getQuery(ExecutorType executorType)
	{
		PurchaseRequisitionFilterCriteria criteria = (PurchaseRequisitionFilterCriteria) getFilterCriteria();
		StringBuilder builder = new StringBuilder();

		if (executorType.equals(ExecutorType.COUNT))
			builder.append("SELECT COUNT(DISTINCT req) ");
		else
			builder.append("SELECT DISTINCT(req) ");

		builder.append("FROM PurchaseRequisition req WHERE req.id IS NOT NULL ");

		if (SiriusValidator.validateParam(criteria.getCode()))
			builder.append("AND req.code LIKE :code");

		if (SiriusValidator.validateParam(criteria.getRequisitionerName()))
			builder.append("AND req.requisitioner.fullName LIKE :requisitioner ");

		if (SiriusValidator.validateDate(criteria.getDateFrom()))
		{
			if (SiriusValidator.validateDate(criteria.getDateTo()))
				builder.append("AND req.date BETWEEN :startDate AND :endDate ");
			else
				builder.append("AND req.date >= :startDate ");
		} else if (SiriusValidator.validateDate(criteria.getDateTo()))
			builder.append("AND req.date <= :endDate ");

		builder.append("ORDER BY req.id DESC");

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

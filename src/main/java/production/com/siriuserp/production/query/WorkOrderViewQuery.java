/**
 * File Name  : WorkOrderViewQuery.java
 * Created On : Jul 30, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.production.query;

import org.hibernate.Query;

import com.siriuserp.production.criteria.WorkOrderFilterCriteria;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

public class WorkOrderViewQuery extends AbstractGridViewQuery
{
	@Override
	public Query getQuery(ExecutorType executorType)
	{
		WorkOrderFilterCriteria criteria = (WorkOrderFilterCriteria) getFilterCriteria();
		StringBuilder builder = new StringBuilder();

		if (executorType.equals(ExecutorType.COUNT))
			builder.append("SELECT COUNT(DISTINCT workOrder) ");
		else
			builder.append("SELECT DISTINCT(workOrder) ");

		builder.append("FROM WorkOrder workOrder ");
		builder.append("WHERE workOrder.code IS NOT NULL ");

		if (SiriusValidator.validateParam(criteria.getCode()))
			builder.append("AND workOrder.code LIKE :code ");

		if (SiriusValidator.validateParam(criteria.getOperatorName()))
			builder.append("AND workOrder.operator.fullName LIKE :operatorName ");

		if (SiriusValidator.validateParam(criteria.getApproverName()))
			builder.append("AND workOrder.approver.fullName LIKE :approverName ");

		if (SiriusValidator.validateDate(criteria.getDateFrom()))
		{
			if (SiriusValidator.validateDate(criteria.getDateTo()))
				builder.append("AND workOrder.date BETWEEN :dateFrom AND :dateTo ");
			else
				builder.append("AND workOrder.date >= :dateFrom ");
		} else if (SiriusValidator.validateDate(criteria.getDateTo()))
			builder.append("AND workOrder.date <= :dateTo ");

		builder.append("ORDER BY workOrder.date DESC, workOrder.id DESC");

		Query query = getSession().createQuery(builder.toString());
		query.setReadOnly(true);

		if (SiriusValidator.validateParam(criteria.getCode()))
			query.setParameter("code", "%" + criteria.getCode() + "%");

		if (SiriusValidator.validateParam(criteria.getOperatorName()))
			query.setParameter("operatorName", "%" + criteria.getOperatorName() + "%");

		if (SiriusValidator.validateParam(criteria.getApproverName()))
			query.setParameter("approverName", "%" + criteria.getApproverName() + "%");

		if (SiriusValidator.validateDate(criteria.getDateFrom()))
			query.setParameter("dateFrom", criteria.getDateFrom());

		if (SiriusValidator.validateDate(criteria.getDateTo()))
			query.setParameter("dateTo", criteria.getDateTo());

		return query;
	}
}

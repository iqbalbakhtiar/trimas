package com.siriuserp.sales.query;

import com.siriuserp.sdk.dm.ApprovalDecisionStatus;
import org.hibernate.Query;

import com.siriuserp.sales.criteria.SalesOrderFilterCriteria;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.utility.SiriusValidator;

public class SalesOrderGridViewQuery extends AbstractGridViewQuery {
	@Override
	public Query getQuery(ExecutorType executorType) {
		SalesOrderFilterCriteria criteria = (SalesOrderFilterCriteria) getFilterCriteria();

		StringBuilder builder = new StringBuilder();

		if (executorType.equals(ExecutorType.COUNT))
			builder.append("SELECT COUNT(DISTINCT so) ");
		else
			builder.append("SELECT DISTINCT(so) ");

		builder.append("FROM SalesOrder so WHERE 1=1 ");

		if (SiriusValidator.validateParam(criteria.getCode()))
			builder.append("AND so.code LIKE :code ");

		if (SiriusValidator.validateParam(criteria.getApprover()))
			builder.append("AND so.approver.fullName LIKE :approver ");

		if (SiriusValidator.validateParam(criteria.getCustomer()))
			builder.append("AND so.customer.fullName LIKE :customer ");

		if (SiriusValidator.validateParam(criteria.getTax()))
			builder.append("AND so.tax.taxName LIKE :tax ");

		if (SiriusValidator.validateParam(criteria.getApproved())) {
			builder.append("AND (so.approvable.approvalDecision.approvalDecisionStatus = :approveAndFinish ");
			builder.append("OR so.approvable.approvalDecision.approvalDecisionStatus = :approveAndForward) ");
		}

		builder.append(" ORDER BY so.id DESC ");

		Query query = getSession().createQuery(builder.toString());
		query.setReadOnly(true);
		
		if (SiriusValidator.validateParam(criteria.getCode()))
			query.setParameter("code", "%" + criteria.getCode() + "%");
		
		if (SiriusValidator.validateParam(criteria.getApprover()))
			query.setParameter("approver", "%" + criteria.getApprover() + "%");
		
		if (SiriusValidator.validateParam(criteria.getCustomer()))
			query.setParameter("customer", "%" + criteria.getCustomer() + "%");
		
		if (SiriusValidator.validateParam(criteria.getTax()))
			query.setParameter("tax", "%" + criteria.getTax() + "%");

		if (SiriusValidator.validateParam(criteria.getApproved())) {
			query.setParameter("approveAndFinish", ApprovalDecisionStatus.APPROVE_AND_FINISH);
			query.setParameter("approveAndForward", ApprovalDecisionStatus.APPROVE_AND_FORWARD);
		}

		return query;
	}
}

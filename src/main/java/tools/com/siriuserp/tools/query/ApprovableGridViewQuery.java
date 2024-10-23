package com.siriuserp.tools.query;

import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.dm.ApprovalDecisionStatus;
import com.siriuserp.sdk.utility.SiriusValidator;
import com.siriuserp.tools.criteria.ApprovableFilterCriteria;
import org.hibernate.Query;

public class ApprovableGridViewQuery extends AbstractGridViewQuery {
    @Override
    public Query getQuery(ExecutorType type) {
        ApprovableFilterCriteria criteria = (ApprovableFilterCriteria)getFilterCriteria();

        StringBuilder builder = new StringBuilder();

        if(type.equals(ExecutorType.COUNT))
            builder.append("SELECT COUNT(app.id) ");

        builder.append("FROM Approvable app WHERE app.approvalDecision.approvalDecisionStatus != :finish AND app.approvalDecision.approvalDecisionStatus != :rejected");

        if(!criteria.isOver())
            builder.append(" AND app.approvalDecision.forwardTo.id = "+criteria.getActivePerson());

        if(SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()))
            builder.append(" AND app.organization.id =:org");

        if(SiriusValidator.validateParamWithZeroPosibility(criteria.getCode()))
            builder.append(" AND app.code like '%"+criteria.getCode()+"%'");

        if(SiriusValidator.validateParamWithZeroPosibility(criteria.getType()))
            builder.append(" AND app.approvableType = '"+criteria.getType()+"'");

        if(SiriusValidator.validateParamWithZeroPosibility(criteria.getApprover()))
            builder.append(" AND app.approvalDecision.forwardTo.fullName like '%"+criteria.getApprover()+"%'");

        if(SiriusValidator.validateDate(criteria.getDateFrom()))
        {
            if(SiriusValidator.validateDate(criteria.getDateTo()))
                builder.append(" AND app.date BETWEEN :startDate AND :endDate");
            else
                builder.append(" AND app.date > :startDate");
        }
        else if(SiriusValidator.validateDate(criteria.getDateTo()))
            builder.append(" AND app.date < :endDate");

        if(type.equals(ExecutorType.HQL))
            builder.append(" ORDER BY app.id DESC");

        Query query = getSession().createQuery(builder.toString());
        query.setCacheable(true);
        query.setReadOnly(true);
        query.setParameter("finish", ApprovalDecisionStatus.APPROVE_AND_FINISH);
        query.setParameter("rejected", ApprovalDecisionStatus.REJECTED);

        if(SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()))
            query.setParameter("org", criteria.getOrganization());

        if(SiriusValidator.validateDate(criteria.getDateFrom()))
            query.setParameter("startDate",criteria.getDateFrom());

        if(SiriusValidator.validateDate(criteria.getDateTo()))
            query.setParameter("endDate",criteria.getDateTo());

        return query;
    }
}

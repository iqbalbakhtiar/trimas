package com.siriuserp.inventory.query;

import com.siriuserp.inventory.criteria.GoodsIssueFilterCriteria;
import com.siriuserp.inventory.dm.GoodsIssueManualType;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.utility.SiriusValidator;
import org.hibernate.Query;

public class GoodsIssueManualGridViewQuery extends AbstractGridViewQuery {
    @Override
    public Query getQuery(ExecutorType executorType) {
        GoodsIssueFilterCriteria criteria = (GoodsIssueFilterCriteria) getFilterCriteria();

        StringBuilder builder = new StringBuilder();

        if (executorType.equals(ExecutorType.COUNT))
            builder.append("SELECT COUNT(DISTINCT gi) ");
        else
            builder.append("SELECT DISTINCT(gi) ");

        builder.append("FROM GoodsIssueManual gi WHERE 1=1 ");

        if (SiriusValidator.validateParam(criteria.getCode()))
            builder.append("AND gi.code LIKE :code ");

        if (SiriusValidator.validateDate(criteria.getDateFrom()))
        {
            if (SiriusValidator.validateDate(criteria.getDateTo()))
                builder.append("AND gi.date BETWEEN :startDate AND :endDate ");
            else
                builder.append("AND gi.date >= :startDate ");
        } else if (SiriusValidator.validateDate(criteria.getDateTo()))
            builder.append("AND gi.date <= :endDate ");

        if (SiriusValidator.validateParam(criteria.getOrg())) {
            builder.append("AND gi.organization.fullName LIKE :org ");
        }

        if (SiriusValidator.validateParam(criteria.getCreatedBy())) {
            builder.append("AND gi.createdBy.fullName LIKE :createdby ");
        }

        if (SiriusValidator.validateParam(criteria.getSource())) {
            builder.append("AND gi.source.name LIKE :source ");
        }

        if (SiriusValidator.validateParam(criteria.getRecipient())) {
            builder.append("AND gi.recipient.fullName LIKE :recipient ");
        }

        if (SiriusValidator.validateParam(criteria.getIssueType())) {
            builder.append("AND gi.issueType = :issueType ");
        }

        builder.append(" ORDER BY gi.id DESC ");

        Query query = getSession().createQuery(builder.toString());
        query.setReadOnly(true);

        if (SiriusValidator.validateParam(criteria.getCode()))
            query.setParameter("code", "%" + criteria.getCode() + "%");

        if (SiriusValidator.validateDate(criteria.getDateFrom()))
            query.setParameter("startDate", criteria.getDateFrom());

        if (SiriusValidator.validateDate(criteria.getDateTo()))
            query.setParameter("endDate", criteria.getDateTo());

        if (SiriusValidator.validateParam(criteria.getOrg()))
            query.setParameter("org", "%" + criteria.getOrg() + "%");

        if (SiriusValidator.validateParam(criteria.getCreatedBy())) {
            query.setParameter("createdby", "%" + criteria.getCreatedBy() + "%");
        }

        if (SiriusValidator.validateParam(criteria.getSource())) {
            query.setParameter("source", "%" + criteria.getSource() + "%");
        }

        if (SiriusValidator.validateParam(criteria.getRecipient())) {
            query.setParameter("recipient", "%" + criteria.getRecipient() + "%");
        }

        if (SiriusValidator.validateParam(criteria.getIssueType())) {
            query.setParameter("issueType", GoodsIssueManualType.valueOf(criteria.getIssueType()));
        }

        return query;
    }
}

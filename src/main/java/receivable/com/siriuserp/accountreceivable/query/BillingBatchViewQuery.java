package com.siriuserp.accountreceivable.query;

import com.siriuserp.accountreceivable.criteria.BillingFilterCriteria;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.utility.SiriusValidator;
import org.hibernate.Query;

public class BillingBatchViewQuery extends AbstractGridViewQuery {
    @Override
    public Query getQuery(ExecutorType executorType)
    {
        BillingFilterCriteria criteria = (BillingFilterCriteria) getFilterCriteria();

        StringBuilder builder = new StringBuilder();

        if (executorType.equals(ExecutorType.COUNT))
            builder.append("SELECT COUNT(DISTINCT batch) ");
        else
            builder.append("SELECT DISTINCT(batch) ");

        builder.append("FROM BillingBatch batch WHERE 1=1 ");

        if (SiriusValidator.validateParam(criteria.getCode()))
            builder.append("AND batch.code LIKE :code ");

        // Date Range
        if (SiriusValidator.validateDate(criteria.getDateFrom()))
        {
            if (SiriusValidator.validateDate(criteria.getDateTo()))
                builder.append("AND batch.date BETWEEN :startDate AND :endDate ");
            else
                builder.append("AND batch.date >= :startDate ");
        } else if (SiriusValidator.validateDate(criteria.getDateTo()))
            builder.append("AND batch.date <= :endDate ");

        if (executorType.equals(ExecutorType.HQL))
            builder.append("ORDER BY batch.date DESC");

        Query query = getSession().createQuery(builder.toString());
        query.setReadOnly(true);

        if (SiriusValidator.validateParam(criteria.getCode()))
            query.setParameter("code", "%" + criteria.getCode() + "%");

        if (SiriusValidator.validateDate(criteria.getDateFrom()))
            query.setParameter("startDate", criteria.getDateFrom());

        if (SiriusValidator.validateDate(criteria.getDateTo()))
            query.setParameter("endDate", criteria.getDateTo());

        return query;
    }
}

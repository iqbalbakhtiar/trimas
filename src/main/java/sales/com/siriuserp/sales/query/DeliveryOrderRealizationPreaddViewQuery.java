package com.siriuserp.sales.query;

import org.hibernate.Query;

import com.siriuserp.sales.dm.SOStatus;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;

public class DeliveryOrderRealizationPreaddViewQuery extends AbstractGridViewQuery {
    @Override
    public Query getQuery(ExecutorType executorType) {

        StringBuilder builder = new StringBuilder();

        if (executorType.equals(ExecutorType.COUNT))
            builder.append("SELECT COUNT(DISTINCT do) ");
        else
            builder.append("SELECT DISTINCT(do) ");

        builder.append("FROM DeliveryOrder do WHERE do.status =:status ");

        // WHERE CLAUSE

        builder.append("ORDER BY do.id DESC ");

        Query query = getSession().createQuery(builder.toString());
        query.setReadOnly(true);

        // SET PARAMETER
        query.setParameter("status", SOStatus.SENT);

        return query;
    }
}

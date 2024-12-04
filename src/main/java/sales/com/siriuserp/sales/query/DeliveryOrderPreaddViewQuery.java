package com.siriuserp.sales.query;

import org.hibernate.Query;

import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;

public class DeliveryOrderPreaddViewQuery extends AbstractGridViewQuery {
    @Override
    public Query getQuery(ExecutorType executorType) {

        StringBuilder builder = new StringBuilder();

        if (executorType.equals(ExecutorType.COUNT))
            builder.append("SELECT COUNT(DISTINCT so) ");
        else
            builder.append("SELECT DISTINCT(so) ");

        builder.append("FROM SalesOrder so ");
        builder.append("WHERE NOT EXISTS (");
        builder.append("    SELECT 1 FROM SalesOrderItem soi ");
        builder.append("    WHERE soi.salesOrder = so ");
        builder.append("    AND (soi.deliverable = false OR soi.approved = false)");
        builder.append(") "); // Cek semua Sales Order Item approved dan deliverable harus bernilai true

        // WHERE CLAUSE

        builder.append(" ORDER BY so.id DESC ");

        Query query = getSession().createQuery(builder.toString());
        query.setReadOnly(true);

        // SET PARAMETER

        return query;
    }
}

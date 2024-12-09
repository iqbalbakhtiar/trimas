package com.siriuserp.accountpayable.query;

import com.siriuserp.accountpayable.criteria.PaymentFilterCriteria;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.utility.SiriusValidator;
import org.hibernate.Query;

public class PaymentGridViewQuery extends AbstractGridViewQuery {
    @Override
    public Query getQuery(ExecutorType type) {
        PaymentFilterCriteria criteria = (PaymentFilterCriteria) getFilterCriteria();

        StringBuilder builder = new StringBuilder();

        if (type.compareTo(ExecutorType.COUNT) == 0)
            builder.append("SELECT COUNT(DISTINCT payment)");
        else
            builder.append("SELECT DISTINCT payment");

        builder.append(" FROM Payment payment WHERE 1=1");

        if (SiriusValidator.validateParam(criteria.getCode()))
            builder.append(" AND payment.code like '%" + criteria.getCode() + "%'");

        if (SiriusValidator.validateParam(criteria.getSupplierName()))
            builder.append(" AND payment.supplier.fullName like '%" + criteria.getSupplierName() + "%' ");

        if (criteria.getDateFrom() != null)
        {
            if (criteria.getDateTo() != null)
                builder.append(" AND payment.date BETWEEN :startDate AND :endDate");
            else
                builder.append(" AND payment.date >= :startDate");
        }

        if (criteria.getDateTo() != null)
            builder.append(" AND payment.date <= :endDate");

        if (type.compareTo(ExecutorType.HQL) == 0)
            builder.append(" ORDER BY payment.id DESC");

        Query query = getSession().createQuery(builder.toString());
        query.setCacheable(true);

        if (criteria.getDateFrom() != null)
            query.setParameter("startDate", criteria.getDateFrom());

        if (criteria.getDateTo() != null)
            query.setParameter("endDate", criteria.getDateTo());

        return query;
    }
}

package com.siriuserp.accountreceivable.query;

import org.hibernate.Query;

import com.siriuserp.accountpayable.dm.PaymentMethodType;
import com.siriuserp.accountreceivable.criteria.ReceiptFilterCriteria;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.utility.SiriusValidator;

public class ReceiptViewQuery extends AbstractGridViewQuery {
    @Override
    public Query getQuery(ExecutorType executorType) {
        ReceiptFilterCriteria criteria = (ReceiptFilterCriteria) getFilterCriteria();

        StringBuilder builder = new StringBuilder();

        if (executorType.equals(ExecutorType.COUNT))
            builder.append("SELECT COUNT(DISTINCT receipt) ");
        else
            builder.append("SELECT DISTINCT(receipt) ");

        builder.append("FROM Receipt receipt WHERE 1=1 ");

        // WHERE CLAUSE
        if (SiriusValidator.validateParam(criteria.getCode()))
            builder.append("AND receipt.code LIKE :code ");

        if (SiriusValidator.validateDate(criteria.getDateFrom()))
        {
            if (SiriusValidator.validateDate(criteria.getDateTo()))
                builder.append("AND receipt.date BETWEEN :startDate AND :endDate ");
            else
                builder.append("AND receipt.date >= :startDate ");
        } else if (SiriusValidator.validateDate(criteria.getDateTo()))
            builder.append("AND receipt.date <= :endDate ");

        if (SiriusValidator.validateParam(criteria.getCustomerName()))
            builder.append("AND receipt.customer.fullName LIKE :customer ");

        if (SiriusValidator.validateParam(criteria.getType()))
            builder.append("AND receipt.receiptInformation.paymentMethodType = :type ");

        builder.append("ORDER BY receipt.id DESC");

        // SET QUERY PARAM
        Query query = getSession().createQuery(builder.toString());
        query.setReadOnly(true);

        if (SiriusValidator.validateParam(criteria.getCode()))
            query.setParameter("code", "%" + criteria.getCode() + "%");

        if (SiriusValidator.validateDate(criteria.getDateFrom()))
            query.setParameter("startDate", criteria.getDateFrom());

        if (SiriusValidator.validateDate(criteria.getDateTo()))
            query.setParameter("endDate", criteria.getDateTo());

        if (SiriusValidator.validateParam(criteria.getCustomerName()))
            query.setParameter("customer", "%" + criteria.getCustomerName() + "%");

        if (SiriusValidator.validateParam(criteria.getType()))
            query.setParameter("type", PaymentMethodType.valueOf(criteria.getType()));

        return query;
    }
}

package com.siriuserp.sales.query;

import com.siriuserp.accounting.criteria.BillingFilterCriteria;
import com.siriuserp.accounting.dm.FinancialStatus;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.utility.SiriusValidator;
import org.hibernate.Query;

public class BillingViewQuery extends AbstractGridViewQuery {
    @Override
    public Query getQuery(ExecutorType executorType)
    {
        BillingFilterCriteria criteria = (BillingFilterCriteria) getFilterCriteria();

        StringBuilder builder = new StringBuilder();

        if (executorType.equals(ExecutorType.COUNT))
            builder.append("SELECT COUNT(DISTINCT bill) ");
        else
            builder.append("SELECT DISTINCT(bill) ");

        builder.append("FROM Billing bill WHERE 1=1 ");
//        builder.append("bill.billingType.id =:billingType ");

        if (SiriusValidator.validateParam(criteria.getCode()))
            builder.append("AND bill.code LIKE :code ");

//        if (SiriusValidator.validateParam(criteria.getStatus()))
//            builder.append("AND bill.financialStatus =:status ");

        if (SiriusValidator.validateDate(criteria.getDateFrom()))
        {
            if (SiriusValidator.validateDate(criteria.getDateTo()))
                builder.append("AND bill.date BETWEEN :startDate AND :endDate ");
            else
                builder.append("AND bill.date >= :startDate ");
        } else if (SiriusValidator.validateDate(criteria.getDateTo()))
            builder.append("AND bill.date <= :endDate ");

        if (SiriusValidator.validateParam(criteria.getCustomerName())) {
            builder.append("AND bill.customer.fullName LIKE :customer ");
        }

        if (SiriusValidator.validateParam(criteria.getStatus())) {
            builder.append("AND bill.financialStatus = :status ");
        }

        if (executorType.equals(ExecutorType.HQL))
            builder.append("ORDER BY bill.date DESC");

        Query query = getSession().createQuery(builder.toString());
        query.setReadOnly(true);
//        query.setParameter("billingType", BillingType.SALES_CONTRACT);

        if (SiriusValidator.validateParam(criteria.getCode()))
            query.setParameter("code", "%" + criteria.getCode() + "%");

//        if (SiriusValidator.validateParam(criteria.getStatus()))
//            query.setParameter("status", CostType.valueOf(criteria.getStatus()));

        if (SiriusValidator.validateDate(criteria.getDateFrom()))
            query.setParameter("startDate", criteria.getDateFrom());

        if (SiriusValidator.validateDate(criteria.getDateTo()))
            query.setParameter("endDate", criteria.getDateTo());

        if (SiriusValidator.validateParam(criteria.getCustomerName())) {
            query.setParameter("customer", "%" + criteria.getCustomerName() + "%");
        }

        if (SiriusValidator.validateParam(criteria.getStatus())) {
            query.setParameter("status", FinancialStatus.valueOf(criteria.getStatus()));
        }

        return query;
    }
}

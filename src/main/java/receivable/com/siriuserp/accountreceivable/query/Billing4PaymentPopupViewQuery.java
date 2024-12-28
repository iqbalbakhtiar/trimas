package com.siriuserp.accountreceivable.query;

import org.hibernate.Query;

import com.siriuserp.accountreceivable.criteria.BillingFilterCriteria;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.utility.SiriusValidator;

public class Billing4PaymentPopupViewQuery extends AbstractGridViewQuery {
    @Override
    public Query getQuery(ExecutorType type)
    {
        BillingFilterCriteria criteria = (BillingFilterCriteria) getFilterCriteria();
        StringBuilder builder = new StringBuilder();

        if (type.equals(ExecutorType.COUNT))
            builder.append("SELECT COUNT(bill.id) ");

        builder.append("FROM Billing bill ");
        builder.append("WHERE (bill.unpaid - bill.clearing) > 0 ");

        if (SiriusValidator.validateLongParam(criteria.getOrganization()))
            builder.append("AND bill.organization.id =:organization ");

        if (SiriusValidator.validateLongParam(criteria.getBillingType()))
            builder.append("AND bill.billingType.id :billingType ");

        if (SiriusValidator.validateParam(criteria.getCode()))
            builder.append("AND bill.code LIKE :code ");

        if (SiriusValidator.validateDate(criteria.getDateTo()))
            builder.append("AND bill.date =:date ");

        if (SiriusValidator.validateLongParam(criteria.getCustomer())) {
            builder.append("AND (bill.customer.id = :customerId ");
            builder.append("OR bill.customer.partyGroup.id = :customerId) ");
        }

        if (SiriusValidator.validateParam(criteria.getCustomerName()))
            builder.append("AND bill.customer.fullName LIKE :customerName ");

        if (type.equals(ExecutorType.HQL))
            builder.append("ORDER BY bill.id DESC ");

        Query query = getSession().createQuery(builder.toString());
        query.setCacheable(true);
        query.setReadOnly(true);

        if (SiriusValidator.validateLongParam(criteria.getOrganization()))
            query.setParameter("organization", criteria.getOrganization());

        if (SiriusValidator.validateLongParam(criteria.getBillingType()))
            query.setParameter("billingType", criteria.getBillingType());

        if (SiriusValidator.validateParam(criteria.getCode()))
            query.setParameter("code", "%" + criteria.getCode() + "%");

        if (SiriusValidator.validateLongParam(criteria.getCustomer()))
            query.setParameter("customerId", criteria.getCustomer());

        if (SiriusValidator.validateParam(criteria.getCustomerName()))
            query.setParameter("customerName", "%" + criteria.getCustomerName() + "%");

        if (SiriusValidator.validateDate(criteria.getDateTo()))
            query.setParameter("date", criteria.getDateTo());

        return query;
    }
}

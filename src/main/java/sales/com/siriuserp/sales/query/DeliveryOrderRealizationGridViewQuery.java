package com.siriuserp.sales.query;

import org.hibernate.Query;

import com.siriuserp.sales.criteria.DeliveryOrderRealizationFilterCriteria;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.utility.SiriusValidator;

public class DeliveryOrderRealizationGridViewQuery extends AbstractGridViewQuery {
    @Override
    public Query getQuery(ExecutorType executorType) {
        DeliveryOrderRealizationFilterCriteria criteria = (DeliveryOrderRealizationFilterCriteria) getFilterCriteria();

        StringBuilder builder = new StringBuilder();

        if (executorType.equals(ExecutorType.COUNT))
            builder.append("SELECT COUNT(DISTINCT dor) ");
        else
            builder.append("SELECT DISTINCT(dor) ");

        builder.append("FROM DeliveryOrderRealization dor WHERE 1=1 ");

        // WHERE CLAUSE
        // CODE
        if (SiriusValidator.validateParam(criteria.getCode())) {
            builder.append("AND dor.code LIKE :code ");
        }
        // ORGANIZATION
        if (SiriusValidator.validateParam(criteria.getOrg())) {
            builder.append("AND dor.organization.fullName LIKE :organization ");
        }
        // Facility
        if (SiriusValidator.validateParam(criteria.getFacility())) {
            builder.append("AND dor.facility.name LIKE :facility ");
        }
        // Customer
        if (SiriusValidator.validateParam(criteria.getCustomer())) {
            builder.append("AND dor.customer.fullName LIKE :customer ");
        }
        // Driver
        if (SiriusValidator.validateParam(criteria.getDriver())) {
            builder.append("AND dor.driver LIKE :driver ");
        }

        builder.append(" ORDER BY dor.id DESC ");

        Query query = getSession().createQuery(builder.toString());
        query.setReadOnly(true);

        // SET PARAMETER
        // Code
        if (SiriusValidator.validateParam(criteria.getCode())) {
            query.setParameter("code", "%" + criteria.getCode() + "%");
        }
        // Organization
        if (SiriusValidator.validateParam(criteria.getOrg())) {
            query.setParameter("organization", "%" + criteria.getOrg() + "%");
        }
        // Facility
        if (SiriusValidator.validateParam(criteria.getFacility())) {
            query.setParameter("facility", "%" + criteria.getFacility() + "%");
        }
        // Customer
        if (SiriusValidator.validateParam(criteria.getCustomer())) {
            query.setParameter("customer", "%" + criteria.getCustomer() + "%");
        }
        // Driver
        if (SiriusValidator.validateParam(criteria.getDriver())) {
            query.setParameter("driver", "%" + criteria.getDriver() + "%");
        }

        return query;
    }
}

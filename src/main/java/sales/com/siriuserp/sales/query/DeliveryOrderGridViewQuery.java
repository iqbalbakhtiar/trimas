package com.siriuserp.sales.query;

import com.siriuserp.sales.criteria.DeliveryOrderFilterCriteria;
import com.siriuserp.sales.dm.SOStatus;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.utility.SiriusValidator;
import org.hibernate.Query;

public class DeliveryOrderGridViewQuery extends AbstractGridViewQuery {
    @Override
	public Query getQuery(ExecutorType executorType) {
		DeliveryOrderFilterCriteria criteria = (DeliveryOrderFilterCriteria) getFilterCriteria();

		StringBuilder builder = new StringBuilder();

		if (executorType.equals(ExecutorType.COUNT))
			builder.append("SELECT COUNT(DISTINCT do) ");
		else
			builder.append("SELECT DISTINCT(do) ");

		builder.append("FROM DeliveryOrder do WHERE 1=1 ");

        // WHERE CLAUSE
		// CODE
		if (SiriusValidator.validateParam(criteria.getCode())) {
			builder.append("AND do.code LIKE :code ");
		}
		// Facility
		if (SiriusValidator.validateParam(criteria.getFacility())) {
			builder.append("AND do.facility.name LIKE :facility ");
		}
		// Customer
		if (SiriusValidator.validateParam(criteria.getCustomer())) {
			builder.append("AND do.customer.fullName LIKE :customer ");
		}
		// Shipping
		if (SiriusValidator.validateParam(criteria.getShippingAddress())) {
			builder.append("AND do.shippingAddress.addressName LIKE :shippingAddress ");
		}
		// Status DO
		if (SiriusValidator.validateParam(criteria.getStatus())) {
			builder.append("AND do.status = :status");
		}

		builder.append(" ORDER BY do.id DESC ");

		Query query = getSession().createQuery(builder.toString());
		query.setReadOnly(true);

		// SET PARAMETER
		// Code
		if (SiriusValidator.validateParam(criteria.getCode())) {
			query.setParameter("code", "%" + criteria.getCode() + "%");
		}
		// Facility
		if (SiriusValidator.validateParam(criteria.getFacility())) {
			query.setParameter("facility", "%" + criteria.getFacility() + "%");
		}
		// Customer
		if (SiriusValidator.validateParam(criteria.getCustomer())) {
			query.setParameter("customer", "%" + criteria.getCustomer() + "%");
		}
		// Shipping
		if (SiriusValidator.validateParam(criteria.getShippingAddress())) {
			query.setParameter("shippingAddress", "%" + criteria.getShippingAddress() + "%");
		}
		// Status DO
		if (SiriusValidator.validateParam(criteria.getStatus())) {
			query.setParameter("status", SOStatus.valueOf(criteria.getStatus()));
		}

		return query;
	}
}

/**
 * File Name  : DeliveryOrderAddViewQuery.java
 * Created On : Mar 18, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.sales.query;

import org.hibernate.Query;

import com.siriuserp.sales.criteria.DeliveryOrderFilterCriteria;
import com.siriuserp.sales.dm.SOStatus;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

public class DeliveryOrderAddViewQuery extends AbstractGridViewQuery
{
	@Override
	public Query getQuery(ExecutorType executorType)
	{
		DeliveryOrderFilterCriteria criteria = (DeliveryOrderFilterCriteria) getFilterCriteria();
		StringBuilder builder = new StringBuilder();

		if (executorType.equals(ExecutorType.COUNT))
			builder.append("SELECT COUNT(DISTINCT referenceItem) ");

		builder.append("FROM DeliveryOrderReferenceItem referenceItem ");
		builder.append("WHERE referenceItem.deliverable = 'Y' ");
		builder.append("AND referenceItem.approved = 'Y' ");

		if (SiriusValidator.validateParam(criteria.getCode()))
			builder.append("AND referenceItem.code LIKE :code ");

		if (SiriusValidator.validateParam(criteria.getFacility()))
			builder.append("AND referenceItem.facility.name LIKE :facility ");

		if (SiriusValidator.validateParam(criteria.getCustomer()))
			builder.append("AND referenceItem.customer.fullName LIKE :customer ");

		if (SiriusValidator.validateParam(criteria.getShippingAddress()))
			builder.append("AND referenceItem.shippingAddress.addressName LIKE :shippingAddress ");

		if (SiriusValidator.validateParam(criteria.getStatus()))
			builder.append("AND referenceItem.status = :status ");

		builder.append("ORDER BY referenceItem.id DESC ");

		Query query = getSession().createQuery(builder.toString());
		query.setReadOnly(true);

		if (SiriusValidator.validateParam(criteria.getCode()))
			query.setParameter("code", "%" + criteria.getCode() + "%");

		if (SiriusValidator.validateParam(criteria.getFacility()))
			query.setParameter("facility", "%" + criteria.getFacility() + "%");

		if (SiriusValidator.validateParam(criteria.getCustomer()))
			query.setParameter("customer", "%" + criteria.getCustomer() + "%");

		if (SiriusValidator.validateParam(criteria.getShippingAddress()))
			query.setParameter("shippingAddress", "%" + criteria.getShippingAddress() + "%");

		if (SiriusValidator.validateParam(criteria.getStatus()))
			query.setParameter("status", SOStatus.valueOf(criteria.getStatus()));

		return query;
	}
}

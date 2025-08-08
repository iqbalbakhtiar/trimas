/**
 * File Name  : MachineViewQuery.java
 * Created On : Aug 7, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.production.query;

import org.hibernate.Query;

import com.siriuserp.production.criteria.MachineFilterCriteria;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

public class MachineViewQuery extends AbstractGridViewQuery
{
	@Override
	public Query getQuery(ExecutorType type)
	{
		MachineFilterCriteria criteria = (MachineFilterCriteria) getFilterCriteria();

		StringBuilder builder = new StringBuilder();

		if (ExecutorType.COUNT.equals(type))
			builder.append("SELECT COUNT(mac.id) ");

		builder.append("FROM Machine mac WHERE mac.id IS NOT NULL ");

		if (SiriusValidator.validateParam(criteria.getCode()))
			builder.append("AND mac.code LIKE :code ");

		if (SiriusValidator.validateParam(criteria.getName()))
			builder.append("AND mac.name LIKE :name ");

		if (SiriusValidator.validateParam(criteria.getStatus()))
			builder.append("AND mac.enabled =:status ");

		if (ExecutorType.HQL.equals(type))
			builder.append("ORDER BY mac.id DESC");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setReadOnly(true);

		if (SiriusValidator.validateParam(criteria.getCode()))
			query.setParameter("code", "%" + criteria.getCode() + "%");

		if (SiriusValidator.validateParam(criteria.getName()))
			query.setParameter("name", "%" + criteria.getName() + "%");

		if (SiriusValidator.validateParam(criteria.getStatus()))
			query.setParameter("status", Boolean.valueOf(criteria.getStatus()));

		return query;
	}
}

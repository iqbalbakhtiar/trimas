/**
 * 
 */
package com.siriuserp.inventory.query;

import org.hibernate.Query;

import com.siriuserp.inventory.criteria.StockAdjustmentFilterCriteria;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

public class StockAdjustmentViewQuery extends AbstractGridViewQuery
{
	@Override
	public Query getQuery(ExecutorType executorType)
	{
		StockAdjustmentFilterCriteria criteria = (StockAdjustmentFilterCriteria) getFilterCriteria();
		StringBuilder builder = new StringBuilder();

		if (executorType.equals(ExecutorType.COUNT))
			builder.append("SELECT COUNT(stock) ");

		builder.append("FROM StockAdjustment stock ");
		builder.append("WHERE stock.id IS NOT NULL ");

		if (SiriusValidator.validateParam(criteria.getOrganizationName()))
			builder.append("AND stock.organization.fullName LIKE :organizationName ");

		if (SiriusValidator.validateParam(criteria.getFacilityName()))
			builder.append("AND stock.facility.name LIKE :facilityName ");

		if (SiriusValidator.validateParam(criteria.getCode()))
			builder.append("AND stock.code LIKE :code ");

		if (SiriusValidator.validateDate(criteria.getDateFrom()))
		{
			if (SiriusValidator.validateDate(criteria.getDateTo()))
				builder.append("AND stock.date BETWEEN :dateFrom AND :dateTo ");
			else
				builder.append("AND stock.date >= :dateFrom ");
		} else if (SiriusValidator.validateDate(criteria.getDateTo()))
			builder.append("AND stock.date <= :dateTo ");

		if (executorType.equals(ExecutorType.HQL))
			builder.append("ORDER BY stock.date DESC, stock.id DESC");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setReadOnly(true);

		if (SiriusValidator.validateParam(criteria.getOrganizationName()))
			query.setParameter("organizationName", "%" + criteria.getOrganizationName() + "%");

		if (SiriusValidator.validateParam(criteria.getFacilityName()))
			query.setParameter("facilityName", "%" + criteria.getFacilityName() + "%");

		if (SiriusValidator.validateParam(criteria.getCode()))
			query.setParameter("code", "%" + criteria.getCode() + "%");

		if (SiriusValidator.validateDate(criteria.getDateFrom()))
			query.setParameter("dateFrom", criteria.getDateFrom());

		if (SiriusValidator.validateDate(criteria.getDateTo()))
			query.setParameter("dateTo", criteria.getDateTo());

		return query;
	}
}

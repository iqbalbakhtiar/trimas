/**
 * Jan 19, 2009 5:02:51 PM
 * com.siriuserp.administration.query
 * GeographicGridViewQuery.java
 */
package com.siriuserp.administration.query;

import org.hibernate.Query;

import com.siriuserp.administration.criteria.GeographicFilterCriteria;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public class GeographicGridViewQuery extends AbstractGridViewQuery
{
	public Query getQuery(ExecutorType type)
	{
		GeographicFilterCriteria criteria = (GeographicFilterCriteria) filterCriteria;
		StringBuilder builder = new StringBuilder();

		if (type.equals(ExecutorType.COUNT))
			builder.append("SELECT COUNT(*) ");
		else
			builder.append("SELECT geo ");

		builder.append("FROM Geographic geo WHERE geo.id IS NOT NULL ");

		if (SiriusValidator.validateParam(criteria.getCode()))
			builder.append("AND geo.code LIKE :code ");

		if (SiriusValidator.validateParam(criteria.getName()))
			builder.append("AND geo.name LIKE :name ");

		if (SiriusValidator.validateParam(criteria.getGeoParent()))
			builder.append("AND geo.parent.name LIKE :geoParent ");

		if (SiriusValidator.validateParam(criteria.getParentId()))
			builder.append("AND geo.parent.id =:parent ");

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getGeoType()))
			builder.append("AND geo.geographicType.id =:type ");

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getType()))
			builder.append("AND geo.geographicType.id =:type ");

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getLevel()))
			builder.append("AND geo.geographicType.level =:level ");

		if (type.equals(ExecutorType.HQL))
			builder.append("ORDER BY geo.geographicType.id, geo.name ASC ");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setReadOnly(true);

		if (SiriusValidator.validateParam(criteria.getCode()))
			query.setParameter("code", "%" + criteria.getCode() + "%");

		if (SiriusValidator.validateParam(criteria.getName()))
			query.setParameter("name", "%" + criteria.getName() + "%");

		if (SiriusValidator.validateParam(criteria.getGeoParent()))
			query.setParameter("geoParent", "%" + criteria.getGeoParent() + "%");

		if (SiriusValidator.validateParam(criteria.getParentId()))
			query.setParameter("parent", criteria.getParentId());

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getGeoType()))
			query.setParameter("type", Long.valueOf(criteria.getGeoType()));

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getType()))
			query.setParameter("type", Long.valueOf(criteria.getType()));

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getLevel()))
			query.setParameter("level", criteria.getLevel());

		return query;
	}
}

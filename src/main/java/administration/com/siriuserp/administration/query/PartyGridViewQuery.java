/**
 * Dec 18, 2008 6:46:21 PM
 * com.siriuserp.administration.query
 * PartyGridViewQuery.java
 */
package com.siriuserp.administration.query;

import org.hibernate.Query;

import com.siriuserp.administration.criteria.PartyFilterCriteria;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.dm.FlagLevel;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public class PartyGridViewQuery extends AbstractGridViewQuery
{
	@Override
	public Query getQuery(ExecutorType type)
	{
		PartyFilterCriteria filter = (PartyFilterCriteria) getFilterCriteria();

		StringBuilder param = new StringBuilder();

		if (type.compareTo(ExecutorType.COUNT) == 0)
			param.append("SELECT COUNT(DISTINCT party) ");

		if (type.compareTo(ExecutorType.HQL) == 0)
			param.append("SELECT DISTINCT party ");

		param.append("FROM Party party WHERE party.flagLevel =:level ");

		if (SiriusValidator.validateParam(filter.getName()))
			param.append("AND party.fullName LIKE '%" + filter.getName() + "%' ");

		if (SiriusValidator.validateParam(filter.getCode()))
			param.append(" AND party.code LIKE '%" + filter.getCode() + "%' ");

		if (type.compareTo(ExecutorType.HQL) == 0)
			param.append(" ORDER BY party.code ASC");

		Query query = getSession().createQuery(param.toString());
		query.setParameter("level", FlagLevel.USERLEVEL);

		return query;
	}
}

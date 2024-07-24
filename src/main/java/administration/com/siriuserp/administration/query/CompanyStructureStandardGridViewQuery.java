/**
 * Apr 14, 2009 3:23:56 PM
 * com.siriuserp.popup.controller
 * CompanyStructureStandardGridViewQuery.java
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

public class CompanyStructureStandardGridViewQuery extends AbstractGridViewQuery
{
	@Override
	public Query getQuery(ExecutorType executorType)
	{
		PartyFilterCriteria criteria = (PartyFilterCriteria) getFilterCriteria();

		StringBuilder builder = new StringBuilder();

		if (executorType.equals(ExecutorType.COUNT))
			builder.append("SELECT COUNT(DISTINCT party) ");
		else
			builder.append("SELECT DISTINCT(party) ");

		builder.append("FROM Party party WHERE party.flagLevel =:level ");

		if (SiriusValidator.validateParam(criteria.getName()))
		{
			builder.append("AND (party.firstName LIKE '%" + criteria.getName() + "%' OR ");
			builder.append("party.middleName LIKE '%" + criteria.getName() + "%' OR ");
			builder.append("party.lastName LIKE '%" + criteria.getName() + "%' OR ");
			builder.append("party.code LIKE '%" + criteria.getName() + "%') ");
		}

		if (executorType.equals(ExecutorType.HQL))
			builder.append("ORDER BY party.code ASC");

		Query query = getSession().createQuery(builder.toString());
		query.setReadOnly(true);
		query.setParameter("level", FlagLevel.USERLEVEL);

		return query;
	}
}

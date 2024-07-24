/**
 * File Name  : EmployeeViewQuery.java
 * Created On : Jan 31, 2019
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.administration.query;

import org.hibernate.Query;

import com.siriuserp.administration.criteria.PartyFilterCriteria;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.dm.PartyRelationshipType;
import com.siriuserp.sdk.dm.PartyRoleType;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

public class EmployeeGridViewQuery extends AbstractGridViewQuery
{
	@Override
	public Query getQuery(ExecutorType executorType)
	{
		PartyFilterCriteria criteria = (PartyFilterCriteria) getFilterCriteria();

		StringBuilder builder = new StringBuilder();

		if (executorType.equals(ExecutorType.COUNT))
			builder.append("SELECT COUNT(DISTINCT relationship) ");
		else
			builder.append("SELECT DISTINCT(relationship) ");

		builder.append("FROM PartyRelationship relationship ");
		builder.append("WHERE relationship.partyRoleTypeFrom.id =:fromType ");
		builder.append("AND relationship.partyRoleTypeTo.id =:toType ");
		builder.append("AND relationship.relationshipType.id =:relationshipType ");

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()))
			builder.append("AND relationship.partyFrom.id =:org ");

		if (SiriusValidator.validateParam(criteria.getCode()))
			builder.append("AND relationship.partyTo.code LIKE :code ");

		if (SiriusValidator.validateParam(criteria.getName()))
			builder.append("AND relationship.partyTo.fullName LIKE :name ");

		if (SiriusValidator.validateParam(criteria.getStatus()))
		{
			if (Boolean.valueOf(criteria.getStatus()))
				builder.append("AND relationship.active = 'Y' ");
			else
				builder.append("AND relationship.active = 'N' ");
		}

		if (executorType.equals(ExecutorType.HQL))
			builder.append("ORDER BY relationship.partyTo.firstName ASC, relationship.id DESC");

		Query query = getSession().createQuery(builder.toString());
		query.setReadOnly(true);
		query.setParameter("fromType", PartyRoleType.EMPLOYEE);
		query.setParameter("toType", PartyRoleType.COMPANY);
		query.setParameter("relationshipType", PartyRelationshipType.EMPLOYMENT_RELATIONSHIP);

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()))
			query.setParameter("org", criteria.getOrganization());

		if (SiriusValidator.validateParam(criteria.getCode()))
			query.setParameter("code", "%" + criteria.getCode() + "%");

		if (SiriusValidator.validateParam(criteria.getName()))
			query.setParameter("name", "%" + criteria.getName() + "%");

		return query;
	}
}

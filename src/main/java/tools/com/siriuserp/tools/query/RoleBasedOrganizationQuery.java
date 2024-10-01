/**
 * Jan 21, 2009 2:33:13 PM
 * com.siriuserp.popup.query
 * RoleBasedOrganizationQuery.java
 */
package com.siriuserp.tools.query;

import org.hibernate.Query;

import com.siriuserp.administration.criteria.PartyFilterCriteria;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.dm.PartyRelationshipType;
import com.siriuserp.sdk.dm.PartyRoleType;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public class RoleBasedOrganizationQuery extends AbstractGridViewQuery
{
	@Override
	public Query getQuery(ExecutorType type)
	{
		PartyFilterCriteria criteria = (PartyFilterCriteria) getFilterCriteria();

		StringBuilder builder = new StringBuilder();

		if (type.compareTo(ExecutorType.COUNT) == 0)
			builder.append("SELECT COUNT(DISTINCT relationship.partyFrom) ");
		else
			builder.append("SELECT DISTINCT(relationship.partyFrom) ");

		builder.append("FROM PartyRelationship relationship ");
		builder.append("WHERE relationship.partyRoleTypeFrom.id =:roleTypeFrom ");
		builder.append("AND relationship.partyRoleTypeTo.id =:roleTypeTo ");
		builder.append("AND relationship.relationshipType.id =:relationshipType ");

		if (!getAccessibleOrganizations().isEmpty())
			builder.append("AND relationship.partyTo.id IN(:orgs) ");

		if (SiriusValidator.validateParam(criteria.getName()))
		{
			builder.append("AND (relationship.partyFrom.code LIKE '%" + criteria.getName() + "%' ");
			builder.append("OR relationship.partyFrom.fullName LIKE '%" + criteria.getName() + "%') ");
		}

		if (type.compareTo(ExecutorType.HQL) == 0)
			builder.append("ORDER BY relationship.partyFrom.fullName ASC");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setReadOnly(true);
		query.setParameter("roleTypeFrom", PartyRoleType.FACILITY);
		query.setParameter("roleTypeTo", PartyRoleType.COMPANY);
		query.setParameter("relationshipType", PartyRelationshipType.ORGANIZATION_STRUCTURE_RELATIONSHIP);

		if (!getAccessibleOrganizations().isEmpty())
			query.setParameterList("orgs", getAccessibleOrganizations());

		return query;
	}
}

/**
 * File Name  : EmployeePopupViewQuery.java
 * Created On : Feb 1, 2019
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.administration.query;

import org.hibernate.Query;

import com.siriuserp.administration.criteria.PartyFilterCriteria;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.dm.PartyRelationshipType;
import com.siriuserp.sdk.dm.PartyRoleType;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

public class EmployeePopupGridViewQuery extends AbstractPartySourceGridViewQuery
{
	@Override
	public Query getQuery(ExecutorType executorType)
	{
		if (!getAccessibleOrganizations().isEmpty())
		{
			PartyFilterCriteria criteria = (PartyFilterCriteria) getFilterCriteria();

			StringBuilder builder = new StringBuilder();

			if (executorType.compareTo(ExecutorType.COUNT) == 0)
				builder.append("SELECT COUNT(DISTINCT relationship) ");

			if (executorType.compareTo(ExecutorType.HQL) == 0)
				builder.append("SELECT DISTINCT(relationship) ");

			builder.append("FROM PartyRelationship relationship ");
			builder.append("JOIN relationship.toRole role ");
			builder.append("JOIN relationship.fromRole fromrole ");
			builder.append("WHERE fromrole.partyRoleType.id =:fromType ");
			builder.append("AND role.partyRoleType.id =:toType ");
			builder.append("AND relationship.relationshipType.id =:relationshipType ");
			builder.append("AND relationship.active = 'Y' ");
			builder.append("AND role.party.id IN (:orgs) ");

			if (SiriusValidator.validateParam(criteria.getSource()) && !getAccessibleParties().isEmpty())
				builder.append("AND fromrole.party.id NOT IN(:existIds) ");

			if ((SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()) && !SiriusValidator.validateParam(criteria.getSource())) || !criteria.getSource().equalsIgnoreCase("EMPLOYEE"))
				builder.append("AND role.party.id =:org ");

			if (SiriusValidator.validateParam(criteria.getCode()))
				builder.append("AND fromrole.party.code LIKE :code ");

			if (SiriusValidator.validateParam(criteria.getName()))
			{
				builder.append("AND (fromrole.party.firstName LIKE :name ");
				builder.append("OR fromrole.party.middleName LIKE :name ");
				builder.append("OR fromrole.party.lastName LIKE :name) ");
			}

			if (executorType.equals(ExecutorType.HQL))
				builder.append("ORDER BY fromrole.party.firstName ASC");

			Query query = getSession().createQuery(builder.toString());
			query.setReadOnly(true);
			query.setParameter("fromType", PartyRoleType.EMPLOYEE);
			query.setParameter("toType", PartyRoleType.COMPANY);
			query.setParameter("relationshipType", PartyRelationshipType.EMPLOYMENT_RELATIONSHIP);
			query.setParameterList("orgs", getAccessibleOrganizations());

			if (SiriusValidator.validateParam(criteria.getSource()) && !getAccessibleParties().isEmpty())
				query.setParameterList("existIds", getAccessibleParties());

			if ((SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()) && !SiriusValidator.validateParam(criteria.getSource())) || !criteria.getSource().equalsIgnoreCase("EMPLOYEE"))
				query.setParameter("org", criteria.getOrganization());

			if (SiriusValidator.validateParam(criteria.getCode()))
				query.setParameter("code", "%" + criteria.getCode() + "%");

			if (SiriusValidator.validateParam(criteria.getName()))
				query.setParameter("name", "%" + criteria.getName() + "%");

			return query;
		}

		return null;
	}
}

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
 * @author Rama Almer Felix
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

public class EmployeePopupGridViewQuery extends AbstractPartySourceGridViewQuery {
	@Override
	public Query getQuery(ExecutorType executorType) {
		PartyFilterCriteria criteria = (PartyFilterCriteria) getFilterCriteria();

		StringBuilder builder = new StringBuilder();

		if (executorType.compareTo(ExecutorType.COUNT) == 0)
			builder.append("SELECT COUNT(DISTINCT relationship) ");

		if (executorType.compareTo(ExecutorType.HQL) == 0)
			builder.append("SELECT DISTINCT(relationship) ");

		builder.append("FROM PartyRelationship relationship ");
		builder.append("JOIN relationship.partyTo toParty ");
		builder.append("JOIN relationship.partyFrom fromParty ");
//		builder.append("WHERE relationship.partyRoleTypeFrom.id = :fromType ");
		builder.append("WHERE 1=1 ");
		builder.append("AND relationship.partyRoleTypeTo.id = :toType ");
		builder.append("AND relationship.relationshipType.id = :relationshipType ");
		builder.append("AND relationship.active = 'Y' ");
//		builder.append("AND toParty.id IN (:orgs) ");

		if (SiriusValidator.validateParam(criteria.getSource()) && !getAccessibleParties().isEmpty())
			builder.append("AND fromParty.id NOT IN(:existIds) ");

		// Kondisi tambahan untuk organisasi
		// Jika ada organisasi dan sumber tertentu, maka filter dengan org
		if ((SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()) &&
				!SiriusValidator.validateParam(criteria.getSource()))
				|| !criteria.getSource().equalsIgnoreCase("EMPLOYEE")) {
			builder.append("AND toParty.id = :org ");
		}

		if (SiriusValidator.validateParam(criteria.getCode()))
			builder.append("AND fromParty.code LIKE :code ");

		if (SiriusValidator.validateParam(criteria.getName())) {
			builder.append("AND fromParty.fullName LIKE :name ");
		}

		if (SiriusValidator.validateParam(criteria.getResponsibility()))
			builder.append("AND fromParty.id IN (SELECT resp.person.id FROM FacilityResponsibility resp WHERE resp.facility.id = :responsibility ) ");

		if (executorType.equals(ExecutorType.HQL))
			builder.append("ORDER BY fromParty.fullName ASC");

		Query query = getSession().createQuery(builder.toString());
		query.setReadOnly(true);
//		query.setParameter("fromType", PartyRoleType.EMPLOYEE);
		query.setParameter("toType", PartyRoleType.COMPANY);
		query.setParameter("relationshipType", PartyRelationshipType.EMPLOYMENT_RELATIONSHIP);
//		query.setParameterList("orgs", getAccessibleOrganizations());

		if (SiriusValidator.validateParam(criteria.getSource()) && !getAccessibleParties().isEmpty())
			query.setParameterList("existIds", getAccessibleParties());

		if ((SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()) &&
				!SiriusValidator.validateParam(criteria.getSource()))
				|| !criteria.getSource().equalsIgnoreCase("EMPLOYEE")) {
			query.setParameter("org", criteria.getOrganization());
		}

		if (SiriusValidator.validateParam(criteria.getCode()))
			query.setParameter("code", "%" + criteria.getCode() + "%");

		if (SiriusValidator.validateParam(criteria.getName()))
			query.setParameter("name", "%" + criteria.getName() + "%");

		if (SiriusValidator.validateParam(criteria.getResponsibility()))
			query.setParameter("responsibility", criteria.getResponsibility());

		return query;
	}
}


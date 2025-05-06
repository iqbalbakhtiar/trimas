/**
 * File Name  : CustomerPopupViewQuery2.java
 * Created On : Apr 17, 2018
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.administration.query;

import org.hibernate.Query;

import com.siriuserp.administration.adapter.CustomerAdapter;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.dm.PartyRelationshipType;
import com.siriuserp.sdk.dm.PartyRoleType;
import com.siriuserp.sdk.filter.CustomerFilterCriteria;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastList;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@SuppressWarnings("unchecked")
public class CustomerPopupGridViewQuery2 extends AbstractGridViewQuery
{
	@Override
	public Long count()
	{
		if (!getAccessibleOrganizations().isEmpty())
		{
			CustomerFilterCriteria criteria = (CustomerFilterCriteria) getFilterCriteria();
			StringBuilder builder = new StringBuilder();

			if (SiriusValidator.validateParam(criteria.getClean()))
				builder.append("SELECT COUNT(DISTINCT relationship.partyFrom)");
			else
				builder.append("SELECT COUNT(relationship.partyFrom)");

			Object object = createQuery(builder, criteria).uniqueResult();
			if (object != null)
				return (Long) object;
		}

		return Long.valueOf(0);
	}

	@Override
	public Object execute()
	{
		FastList<CustomerAdapter> party = new FastList<CustomerAdapter>();
		
		CustomerFilterCriteria criteria = (CustomerFilterCriteria) getFilterCriteria();
		StringBuilder builder = new StringBuilder();

		if (SiriusValidator.validateParam(criteria.getClean()))
			builder.append("SELECT DISTINCT NEW com.siriuserp.administration.adapter.CustomerAdapter(relationship.partyFrom) ");
		else
			builder.append("SELECT DISTINCT NEW com.siriuserp.administration.adapter.CustomerAdapter(relationship.partyFrom, relationship) ");

		Query query = createQuery(builder, criteria);
		query.setFirstResult(criteria.start());
		query.setMaxResults(criteria.getMax());

		party.addAll(query.list());
		
		return party;
	}

	public Query createQuery(StringBuilder builder, CustomerFilterCriteria criteria)
	{
		builder.append("FROM PartyRelationship relationship ");
		builder.append("WHERE relationship.partyRoleTypeFrom.id =:fromType ");
		builder.append("AND relationship.partyRoleTypeTo.id =:toType ");
		builder.append("AND relationship.relationshipType.id =:relationshipType ");
		builder.append("AND relationship.partyTo.id =:org ");
		builder.append("AND relationship.active = 'Y' ");

		//if (SiriusValidator.validateLongParam(criteria.getFacility()))
			//builder.append("AND relationship.partyFrom.facility.id =:facility ");

		if (SiriusValidator.validateParam(criteria.getCode()))
			builder.append("AND relationship.partyFrom.code LIKE :code ");

		if (SiriusValidator.validateParam(criteria.getName()))
			builder.append("AND (relationship.partyFrom.fullName LIKE :name) ");

		if (SiriusValidator.validateDate(criteria.getDate()))
			builder.append("AND (plafon.validTo >= :date OR plafon.validTo IS NULL) ");

		builder.append("ORDER BY relationship.partyFrom.fullName ASC");

		Query query = getSession().createQuery(builder.toString());
		query.setParameter("fromType", PartyRoleType.CUSTOMER);
		query.setParameter("toType", PartyRoleType.SUPPLIER);
		query.setParameter("relationshipType", PartyRelationshipType.CUSTOMER_RELATIONSHIP);
		query.setParameter("org", criteria.getOrganization());
		//query.setParameterList("orgs", getAccessibleOrganizations());

		//if (SiriusValidator.validateLongParam(criteria.getFacility()))
		//	query.setParameter("facility", criteria.getFacility());

		if (SiriusValidator.validateParam(criteria.getCode()))
			query.setParameter("code", "%" + criteria.getCode() + "%");

		if (SiriusValidator.validateParam(criteria.getName()))
			query.setParameter("name", "%" + criteria.getName() + "%");

		if (SiriusValidator.validateDate(criteria.getDate()))
			query.setParameter("date", criteria.getDate());

		return query;
	}
}

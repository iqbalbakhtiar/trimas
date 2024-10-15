/**
 * 
 */
package com.siriuserp.administration.query;

import org.hibernate.Query;

import com.siriuserp.administration.criteria.PartyFilterCriteria;
import com.siriuserp.administration.util.RelationshipHelper;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.dm.PartyRelationshipType;
import com.siriuserp.sdk.dm.PartyRoleType;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastList;

/**
 * @author
 * Betsu Brahmana Restu 
 * Sirius Indonesia, PT
 * betsu@siriuserp.com
 */

@SuppressWarnings("unchecked")
public abstract class AbstractPartySourceGridViewQuery extends AbstractGridViewQuery
{
	protected FastList<Long> accessibleParties = new FastList<Long>();

	public FastList<Long> getAccessibleParties()
	{
		return accessibleParties;
	}

	public void setAccessibleParties(FastList<Long> accessibleParties)
	{
		this.accessibleParties = accessibleParties;
	}

	@Override
	public void init()
	{
		PartyFilterCriteria criteria = (PartyFilterCriteria) getFilterCriteria();

		if (SiriusValidator.validateParam(criteria.getOrganization()) && SiriusValidator.validateParam(criteria.getSource()))
		{
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT DISTINCT(fromrole.party.id) ");
			builder.append("FROM PartyRelationship relationship ");
			builder.append("JOIN relationship.toRole role ");
			builder.append("JOIN relationship.fromRole fromrole ");
			builder.append("WHERE fromrole.partyRoleType.id =:rel0 ");
			builder.append("AND role.partyRoleType.id =:rel1 ");
			builder.append("AND relationship.relationshipType.id =:rel2 ");
			builder.append("AND role.party.id =:org ");

			Query query = getSession().createQuery(builder.toString());
			query.setCacheable(true);
			query.setReadOnly(true);
			query.setParameter("org", criteria.getOrganization());

			if (criteria.getSource().equalsIgnoreCase("SALESPERSON"))
				RelationshipHelper.build(query, PartyRoleType.SALES_PERSON, PartyRoleType.COMPANY, PartyRelationshipType.EMPLOYMENT_RELATIONSHIP);
			else if (criteria.getSource().equalsIgnoreCase("CUSTOMER"))
				RelationshipHelper.build(query, PartyRoleType.CUSTOMER, PartyRoleType.SUPPLIER, PartyRelationshipType.CUSTOMER_RELATIONSHIP);
			else if (criteria.getSource().equalsIgnoreCase("SUPPLIER"))
				RelationshipHelper.build(query, PartyRoleType.SUPPLIER, PartyRoleType.CUSTOMER, PartyRelationshipType.SUPPLIER_RELATIONSHIP);
			else if (criteria.getSource().equalsIgnoreCase("EMPLOYEE"))
				RelationshipHelper.build(query, PartyRoleType.EMPLOYEE, PartyRoleType.COMPANY, PartyRelationshipType.EMPLOYMENT_RELATIONSHIP);
			else if (criteria.getSource().equalsIgnoreCase("DRIVER"))
				RelationshipHelper.build(query, PartyRoleType.DRIVER, PartyRoleType.COMPANY, PartyRelationshipType.EMPLOYMENT_RELATIONSHIP);

			getAccessibleParties().addAll(query.list());
		}

		super.init();
	}
}

/**
 * Apr 18, 2006
 * CompanyStructureDaoImpl.java
 */
package com.siriuserp.sdk.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.siriuserp.sdk.dao.CompanyStructureDao;
import com.siriuserp.sdk.db.DaoHelper;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.PartyRelationshipType;
import com.siriuserp.sdk.dm.PartyRoleType;
import com.siriuserp.tools.adapter.RoleDetailUIAdapter;

import javolution.util.FastList;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@SuppressWarnings("unchecked")
@Component
public class CompanyStructureDaoImpl extends DaoHelper<Party> implements CompanyStructureDao
{
	public List<Party> loadAll()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT DISTINCT party FROM PartyRelationship AS prole JOIN prole.partyTo AS party ");
		builder.append("WHERE prole.partyRoleTypeTo.id IN(4,5,6,7,9) ");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setReadOnly(true);

		return query.list();
	}

	@Override
	public List<Long> loadAllID()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT DISTINCT party.id FROM PartyRelationship AS prole JOIN prole.partyTo AS party ");
		builder.append("WHERE prole.partyRoleType.id IN(4,5,6,7,9) ");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setReadOnly(true);

		return query.list();
	}

	@Override
	public Party loadParent(Long child)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT party FROM PartyRelationship rel JOIN rel.partyTo party ");
		builder.append("WHERE party.id =:org ");
		builder.append("AND rel.relationshipType.id =:relationshipType ");
		builder.append("AND rel.partyRoleTypeTo.id =:roleType ");

		Query query = getSession().createQuery(builder.toString());
		query.setReadOnly(true);
		query.setParameter("org", child);
		query.setParameter("roleType", PartyRoleType.COMPANY);
		query.setParameter("relationshipType", PartyRelationshipType.ORGANIZATION_STRUCTURE_RELATIONSHIP);
		query.setMaxResults(1);

		Object object = query.uniqueResult();
		if (object != null)
			return (Party) object;

		return null;
	}

	@Override
	public List<Party> loadAllVerticalDown(Party parent)
	{
		FastList<Party> list = new FastList<Party>();

		StringBuilder builder = new StringBuilder();
		builder.append("SELECT DISTINCT(rel.partyFrom) ");
		builder.append("FROM PartyRelationship rel ");
		builder.append("WHERE rel.relationshipType.id =:relationshipType ");
		builder.append("AND rel.partyTo.id =:org ");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setReadOnly(true);
		query.setParameter("org", parent.getId());
		query.setParameter("relationshipType", PartyRelationshipType.ORGANIZATION_STRUCTURE_RELATIONSHIP);

		list.addAll(query.list());

		return list;
	}

	@Override
	public List<RoleDetailUIAdapter> loadIsMapped(Long role)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT NEW com.siriuserp.tools.adapter.RoleDetailUIAdapter(access.enabled, organization.id, organization.code, organization.fullName, access.id) ");
		builder.append("FROM OrganizationRole access JOIN access.organization organization WHERE access.role.id =:role ");
		builder.append("ORDER BY organization.code ASC");

		Query query = getSession().createQuery(builder.toString());
		query.setReadOnly(true);
		query.setCacheable(true);
		query.setParameter("role", role);

		return query.list();
	}

	@Override
	public List<RoleDetailUIAdapter> loadIsNotMapped(List<Long> orgs)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT DISTINCT NEW com.siriuserp.tools.adapter.RoleDetailUIAdapter(org.id, org.code, org.fullName) ");
		builder.append("FROM Party org, PartyRelationship rel WHERE rel.relationshipType.id =:relationshipType AND (rel.partyFrom.id = org.id OR rel.partyTo.id = org.id) ");
		builder.append(!orgs.isEmpty() ? " AND org.id NOT IN(:orgs) " : " ");
		builder.append("ORDER BY org.code ASC");

		Query query = getSession().createQuery(builder.toString());
		query.setReadOnly(true);
		query.setCacheable(true);
		query.setParameter("relationshipType", PartyRelationshipType.ORGANIZATION_STRUCTURE_RELATIONSHIP);

		if (!orgs.isEmpty())
			query.setParameterList("orgs", orgs);

		return query.list();
	}

	@Override
	public List<Long> loadIDVerticalDown(Long parent)
	{
		FastList<Long> ids = new FastList<Long>();

		StringBuilder builder = new StringBuilder();
		builder.append("SELECT DISTINCT(party.id) FROM PartyRelationship rel JOIN rel.partyFrom party ");
		builder.append("WHERE rel.relationshipType.id =:relationshipType ");
		builder.append("AND rel.partyRoleTypeTo.id =:roleType ");
		builder.append("AND rel.partyTo.id =:org ");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setReadOnly(true);
		query.setParameter("org", parent);
		query.setParameter("relationshipType", PartyRelationshipType.ORGANIZATION_STRUCTURE_RELATIONSHIP);
		query.setParameter("roleType", PartyRoleType.COMPANY);

		ids.addAll(query.list());

		return ids;
	}

	@Override
	public List<Long> loadIDVerticalUp(Long parent)
	{
		FastList<Long> ids = new FastList<Long>();

		StringBuilder builder = new StringBuilder();
		builder.append("SELECT DISTINCT(party.id) FROM PartyRelationship rel JOIN rel.partyTo party ");
		builder.append("WHERE rel.relationshipType.id =:relationshipType AND rel.partyFrom.id =:org ");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setReadOnly(true);
		query.setParameter("org", parent);
		query.setParameter("relationshipType", PartyRelationshipType.ORGANIZATION_STRUCTURE_RELATIONSHIP);

		ids.add(parent);
		ids.addAll(query.list());

		return ids;
	}

	@Override
	public Party loadHolding()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT DISTINCT(rel.partyTo) FROM PartyRelationship rel ");
		builder.append("WHERE rel.partyRoleTypeTo.id =:roleType");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setReadOnly(true);
		query.setParameter("roleType", PartyRoleType.HOLDING);
		query.setMaxResults(1);

		Object object = query.uniqueResult();
		if (object != null)
			return (Party) object;

		return null;
	}
}

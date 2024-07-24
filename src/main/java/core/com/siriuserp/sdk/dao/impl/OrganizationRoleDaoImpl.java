/**
 * Dec 16, 2008 3:34:56 PM
 * com.siriuserp.sdk.dao.impl
 * OrganizationRoleDaoImpl.java
 */
package com.siriuserp.sdk.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.siriuserp.sdk.dao.OrganizationRoleDao;
import com.siriuserp.sdk.db.DaoHelper;
import com.siriuserp.sdk.dm.OrganizationRole;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.Role;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
public class OrganizationRoleDaoImpl extends DaoHelper<OrganizationRole> implements OrganizationRoleDao
{
	@Override
	public Long loadLastSequence(Role role)
	{
		Query query = getSession().createQuery("SELECT MAX(org.sequence) FROM OrganizationRole org WHERE org.role.id =:role");
		query.setCacheable(true);
		query.setReadOnly(true);
		query.setParameter("role", role.getId());

		Object object = query.uniqueResult();
		if (object != null)
			return (Long) object;

		return Long.valueOf(0);
	}

	@SuppressWarnings("unchecked")
	public List<OrganizationRole> loadAll(Long role)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT DISTINCT orgRole FROM OrganizationRole orgRole WHERE orgRole.role.id = :id ");
		builder.append("ORDER BY orgRole.organization.code ");

		Query query = getSession().createQuery(builder.toString());
		query.setParameter("id", role);

		return query.list();
	}

	@Override
	public OrganizationRole getOrgRole(Role role, Party org)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT DISTINCT orgRole FROM OrganizationRole orgRole WHERE orgRole.organization.id = :org ");
		builder.append("AND orgRole.role.id = :role ");
		builder.append("ORDER BY orgRole.organization.code ");

		Query query = getSession().createQuery(builder.toString());
		query.setParameter("org", org.getId());
		query.setParameter("role", role.getId());

		return (OrganizationRole) query.uniqueResult();
	}
}

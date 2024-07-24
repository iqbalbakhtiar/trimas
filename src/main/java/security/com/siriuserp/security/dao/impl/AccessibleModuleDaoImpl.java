package com.siriuserp.security.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.siriuserp.sdk.dao.AccessibleModuleDao;
import com.siriuserp.sdk.db.DaoHelper;
import com.siriuserp.sdk.dm.AccessType;
import com.siriuserp.sdk.dm.AccessibleModule;

@SuppressWarnings("unchecked")
@Component
public class AccessibleModuleDaoImpl extends DaoHelper<AccessibleModule> implements AccessibleModuleDao
{
	@Override
	public List<AccessibleModule> loadAll(Long role)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("FROM AccessibleModule access ");
		builder.append("WHERE access.role.id =:role ");
		builder.append("AND access.accessType.id !=:noaccess AND access.module.enabled =:enabled ");
		builder.append("ORDER BY access.module.menuIndex,access.module.code ");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setReadOnly(true);
		query.setParameter("role", role);
		query.setParameter("enabled", Boolean.TRUE);
		query.setParameter("noaccess", AccessType.NO_ACCESS);

		return query.list();
	}

	@Override
	public List<String> findAccessibleRoleNameByLocation(String location)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT access.role.name FROM AccessibleModule access ");
		builder.append("WHERE access.module.defaultUri =:location ");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setReadOnly(true);
		query.setParameter("location", location);

		return query.list();
	}

	@Override
	public List<Long> getAccessibleOrgs(Long role)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT DISTINCT role.organization.id FROM OrganizationRole role ");
		builder.append("WHERE role.role.id = :userrole ");
		builder.append("AND role.enabled = :enabled ");

		Query organizations = getSession().createQuery(builder.toString());
		organizations.setCacheable(true);
		organizations.setReadOnly(true);
		organizations.setParameter("userrole", role);
		organizations.setParameter("enabled", Boolean.TRUE);

		return organizations.list();
	}
}

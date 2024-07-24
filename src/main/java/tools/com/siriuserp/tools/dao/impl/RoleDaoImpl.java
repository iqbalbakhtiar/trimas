package com.siriuserp.tools.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.siriuserp.sdk.dao.RoleDao;
import com.siriuserp.sdk.db.DaoHelper;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.dm.Role;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@SuppressWarnings("unchecked")
@Component
public class RoleDaoImpl extends DaoHelper<Role> implements RoleDao
{
	@Override
	public List<Role> loadAllByMandatory(boolean mandatory)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("FROM Role r WHERE r.mandatory =:mandatory ORDER BY r.id");

		Query query = getSession().createQuery(builder.toString());
		query.setParameter("mandatory", mandatory);

		return query.list();
	}

	public List<Role> loadAllUnmandatory()
	{
		Criteria criteria = getSession().createCriteria(Role.class);
		criteria.add(Restrictions.eq("mandatory", Boolean.FALSE));
		criteria.addOrder(Order.asc("name"));

		return criteria.list();
	}

	@Override
	public List<Role> filter(GridViewQuery query)
	{
		query.setSession(getSession());
		query.init();
		return (List<Role>) query.execute();
	}

	@Override
	public Long getMax(GridViewQuery query)
	{
		query.setSession(getSession());
		query.init();
		return query.count();
	}
}

/**
 * Oct 2, 2009 9:58:55 AM
 * com.siriuserp.tools.query
 * RoleGridViewQuery.java
 */
package com.siriuserp.tools.query;

import org.hibernate.Query;

import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.utility.SiriusValidator;
import com.siriuserp.tools.criteria.RoleFilterCriteria;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public class RoleGridViewQuery extends AbstractGridViewQuery
{

	@Override
	public Query getQuery(ExecutorType type)
	{
		RoleFilterCriteria criteria = (RoleFilterCriteria)getFilterCriteria();
		
		StringBuilder builder = new StringBuilder();
		
		if (type.compareTo(ExecutorType.COUNT) == 0)
			builder.append("SELECT COUNT(role) ");
		
		builder.append("FROM Role role WHERE role.id IS NOT NULL ");
		
		if (SiriusValidator.validateParam(criteria.getCode()))
			builder.append(" AND role.roleId like :code");
		
		if (SiriusValidator.validateParam(criteria.getName()))
			builder.append(" AND role.name like :name");
		
		if (type.compareTo(ExecutorType.HQL) == 0)
			builder.append(" ORDER BY role.roleId ASC");
		
		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setReadOnly(true);
		
		if (SiriusValidator.validateParam(criteria.getCode()))
			query.setParameter("code", "%" + criteria.getCode() + "%");
		
		if (SiriusValidator.validateParam(criteria.getName()))
			query.setParameter("name", "%" + criteria.getName() + "%");

		return query;
	}

}

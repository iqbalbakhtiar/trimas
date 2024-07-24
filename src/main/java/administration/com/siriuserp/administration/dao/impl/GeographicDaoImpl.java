/**
 * Oct 30, 2008 3:26:57 PM
 * com.siriuserp.sdk.dao.impl
 * GeographicDaoImpl.java
 */
package com.siriuserp.administration.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.siriuserp.administration.dm.Geographic;
import com.siriuserp.sdk.dao.GeographicDao;
import com.siriuserp.sdk.db.DaoHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@SuppressWarnings("unchecked")
@Component
public class GeographicDaoImpl extends DaoHelper<Geographic> implements GeographicDao
{
	@Override
	public List<Geographic> loadAllNotIN(List<Long> excludes)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("FROM Geographic geo ");

		if (!excludes.isEmpty())
			builder.append("WHERE geo.id not in(:geos)");

		Query query = getSession().createQuery(builder.toString());

		if (!excludes.isEmpty())
			query.setParameterList("geos", excludes);

		return query.list();
	}

	@Override
	public List<Geographic> loadByType(Long typeId)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("FROM Geographic geo ");
		builder.append("WHERE geo.geographicType.id =:typeId ");
		builder.append("ORDER BY geo.name ASC");

		Query query = getSession().createQuery(builder.toString());
		query.setParameter("typeId", typeId);

		return query.list();
	}

	@Override
	public List<Geographic> loadByTypeAndParent(Long typeId, Long parentId)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("FROM Geographic geo ");
		builder.append("WHERE geo.geographicType.id =:typeId ");
		builder.append("AND geo.parent.id =:parentId ");
		builder.append("ORDER BY geo.name ASC");

		Query query = getSession().createQuery(builder.toString());
		query.setParameter("typeId", typeId);
		query.setParameter("parentId", parentId);

		return query.list();
	}
}

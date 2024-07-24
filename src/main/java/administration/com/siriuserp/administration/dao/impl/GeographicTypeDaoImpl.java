/**
 * Oct 31, 2008 2:48:58 PM
 * com.siriuserp.sdk.dao.impl
 * GeographicTypeDaoImpl.java
 */
package com.siriuserp.administration.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.siriuserp.administration.dm.GeographicType;
import com.siriuserp.sdk.dao.GeographicTypeDao;
import com.siriuserp.sdk.db.DaoHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
public class GeographicTypeDaoImpl extends DaoHelper<GeographicType> implements GeographicTypeDao
{
	@SuppressWarnings("unchecked")
	@Override
	public List<GeographicType> loadAll(boolean enabled)
	{
		return getSession().createQuery("FROM GeographicType type WHERE type.enabled =:enabled ORDER BY type.id ASC").setParameter("enabled", enabled).list();
	}
}

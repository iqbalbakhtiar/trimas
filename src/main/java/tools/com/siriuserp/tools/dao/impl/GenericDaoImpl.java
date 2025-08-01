/**
 * May 8, 2009 1:58:29 PM
 * com.siriuserp.tools.dao.impl
 * GenericDaiImpl.java
 */
package com.siriuserp.tools.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.DaoHelper;
import com.siriuserp.sdk.utility.StringHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
@SuppressWarnings({"unchecked" })
public class GenericDaoImpl extends DaoHelper<Object> implements GenericDao
{
	@Override
	public <T> T get(Class<T> tClass, Long id)
	{
		return (T) getSession().get(tClass, id);
	}

	@Override
	public <T> T load(Class<T> tClass, Long id)
	{
		return (T) getSession().load(tClass, id);
	}

	@Override
	public void merge(Object object)
	{
		getSession().merge(object);
	}

	@Override
	public <T> List<T> loadAll(Class<T> tClass)
	{
		return getSession().createQuery("FROM "+tClass.getName()).list();
	}
	
	@Override
	public <T> T getUniqeField(Class<T> tClass, String field, Object param)
	{
		List<T> list = getUniqeFields(tClass, new String[] {field}, new Object[] {param}, new String[] {"id"}, new String[] {"DESC"});
		
		if (!list.isEmpty())
			return list.get(0);
		
		return null;
	}
	
	@Override
	public <T> T getUniqeField(Class<T> tClass, String[] field, Object[] param) {
		List<T> list = getUniqeFields(tClass, field, param, new String[] {"id"}, new String[] {"DESC"});
		
		if (!list.isEmpty())
			return list.get(0);
		
		return null;
	}
	
	@Override
    public <T> List<T> getUniqeFields(Class<T> tClass, String field[], Object param[])
	{
		return getUniqeFields(tClass, field, param, new String[] {"id"}, new String[] {"DESC"});
	}
	
	@Override
    public <T> List<T> getUniqeFields(Class<T> tClass, String field[], Object param[], String[] fieldOrder, String[] orderType)
	{
		StringBuilder builder = new StringBuilder();
		
		int countField = 0;
		if (param != null)
			countField = field.length > param.length ? param.length : field.length;

		builder.append("FROM "+tClass.getName()+" ");
		
		if(param != null)
			for(int i=0;i<countField;i++)
				builder.append((i == 0 ? "WHERE " : "AND " ) + field[i]+(StringHelper.OPERATORS.matcher(field[i]).find() ? "" : " =")+":param" + i +" ");
	
		if(fieldOrder.length > 0) {
			builder.append("ORDER BY ");
		
			for(int j=0;j<fieldOrder.length;j++)
				builder.append((j > 0 ? ", " : "" ) + fieldOrder[j] + " " + orderType[j]);
		}
	
		Query query = getSession().createQuery(builder.toString());
		
		for(int i=0;i<countField;i++)
			query.setParameter("param"+i, param[i]);
		
		return query.list();
	}

	@Override
	public <T> Long getCount(Class<T> tClass, String[] field, Object[] param) {
		
		StringBuilder builder = new StringBuilder();
		int countField = field.length > param.length ? param.length : field.length;
		
		builder.append("SELECT COALESCE(COUNT(*),0) ");
		builder.append("FROM "+tClass.getName()+" ");
		
		for(int i=0;i<countField;i++)
			builder.append((i == 0 ? "WHERE " : "AND " ) + field[i]+(StringHelper.OPERATORS.matcher(field[i]).find() ? "" : " =")+":param" + i +" ");
		
		Query query = getSession().createQuery(builder.toString());
		
		for(int i=0;i<countField;i++)
			query.setParameter("param"+i, param[i]);
		
		return (Long) query.uniqueResult();
	}

}
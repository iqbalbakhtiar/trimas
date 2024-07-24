/**
 * May 8, 2009 1:57:43 PM
 * com.siriuserp.sdk.dao
 * GenericDao.java
 */
package com.siriuserp.sdk.dao;

import java.util.List;

import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.base.Filterable;
import com.siriuserp.sdk.base.ReportDao;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public interface GenericDao extends Dao<Object>, Filterable, ReportDao
{
	public <T> T load(Class<T> tClass, Long id);
	public <T> T get(Class<T> tClass, Long id);
	public <T> List<T> loadAll(Class<T> tClass);
	
	public <T> T getUniqeField(Class<T> tClass, String field, Object param);
	public <T> T getUniqeField(Class<T> tClass, String field[], Object param[]);

	public <T> List<T> getUniqeFields(Class<T> tClass, String field[], Object param[]);
    public <T> List<T> getUniqeFields(Class<T> tClass, String field[], Object param[], String[] fieldOrder, String[] orderType);

	public <T> Long getCount(Class<T> tClass, String field[], Object param[]);
}

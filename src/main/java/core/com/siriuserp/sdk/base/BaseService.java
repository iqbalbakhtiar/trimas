/**
 * Sep 26, 2006 12:08:16 PM
 * net.konsep.sirius.utils
 * BaseService.java
 */
package com.siriuserp.sdk.base;

import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;

import javolution.util.FastMap;


/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public interface BaseService<T>
{
    public FastMap<String,Object> view(GridViewFilterCriteria filterCriteria);
    public FastMap<String,Object> preadd();
    public FastMap<String,Object> preedit(String id);
    public void add(T t)throws ServiceException;
    public void edit(T t)throws ServiceException;
    public void delete(T t)throws ServiceException;
    public T load(String id);
}

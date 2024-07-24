/**
 * Mar 6, 2009 11:12:01 AM
 * com.siriuserp.sdk.utility
 * QueryFactory.java
 */
package com.siriuserp.sdk.utility;

import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public class QueryFactory
{
    public static synchronized GridViewQuery create(GridViewFilterCriteria filterCriteria,Class<? extends GridViewQuery> queryclass)throws ServiceException
    {
        GridViewQuery query = null;
        
        try
        {
            query = queryclass.getDeclaredConstructor().newInstance();
            query.setFilterCriteria(filterCriteria);
        }
        catch (Exception e)
        {
            throw new ServiceException(e);
        }
        
        return query;
    }
}

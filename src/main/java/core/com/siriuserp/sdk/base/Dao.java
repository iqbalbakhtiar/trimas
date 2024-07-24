/**
 * Oct 28, 2008 3:09:04 PM
 * com.siriuserp.sdk.base
 * Dao.java
 */
package com.siriuserp.sdk.base;

import com.siriuserp.sdk.exceptions.DataAdditionException;
import com.siriuserp.sdk.exceptions.DataDeletionException;
import com.siriuserp.sdk.exceptions.DataEditException;
import com.siriuserp.sdk.exceptions.ServiceException;


/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public interface Dao<T>
{
    public void add(T bean)throws DataAdditionException;
    public void update(T bean)throws DataEditException;
    public void delete(T bean)throws DataDeletionException;

    public void merge(T bean)throws DataEditException;
    
    public void clear()throws ServiceException;
}

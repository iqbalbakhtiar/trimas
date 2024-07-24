/**
 * Dec 5, 2008 2:39:41 PM
 * com.siriuserp.sdk.db
 * StandardQuery.java
 */
package com.siriuserp.sdk.db;

import org.hibernate.Query;

import com.siriuserp.sdk.filter.GridViewFilterCriteria;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public interface GridViewQuery extends GridQuery
{
    public <T extends GridViewFilterCriteria> T getFilterCriteria();
    public void setFilterCriteria(GridViewFilterCriteria filterCriteria);

    public void init();
    
    public Long count();
    public Object execute();
    
    public Query getQuery(ExecutorType type);
}

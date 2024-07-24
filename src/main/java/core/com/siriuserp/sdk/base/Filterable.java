/**
 * Oct 28, 2008 3:10:19 PM
 * com.siriuserp.sdk.base
 * Filterable.java
 */
package com.siriuserp.sdk.base;

import java.util.List;

import com.siriuserp.sdk.db.GridViewQuery;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public interface Filterable
{
	@SuppressWarnings("rawtypes")
    public List filter(GridViewQuery query);
    public Long getMax(GridViewQuery query);
}

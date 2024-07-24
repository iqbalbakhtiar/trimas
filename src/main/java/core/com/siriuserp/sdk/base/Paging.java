/**
 * Dec 21, 2007 4:46:58 PM
 * com.siriuserp.sdk.base
 * Paging.java
 */
package com.siriuserp.sdk.base;

import com.siriuserp.sdk.filter.GridViewFilterCriteria;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public interface Paging<D,F extends GridViewFilterCriteria>
{
    public D loadAll(F f);
}

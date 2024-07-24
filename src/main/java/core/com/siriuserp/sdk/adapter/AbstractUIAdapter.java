/**
 * Nov 15, 2008 11:36:04 AM
 * com.siriuserp.sdk.adapter
 * AbstrcatUIAdapter.java
 */
package com.siriuserp.sdk.adapter;

import com.siriuserp.sdk.dm.AccessType;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public abstract class AbstractUIAdapter implements UIAdapter
{
    private static final long serialVersionUID = 6526480116714667640L;
    
    protected AccessType accessType;
    
    @Override
    public AccessType getAccessType()
    {
        return this.accessType;
    }

    @Override
    public void setAccessType(AccessType accessType)
    {
        this.accessType = accessType;
    }
}

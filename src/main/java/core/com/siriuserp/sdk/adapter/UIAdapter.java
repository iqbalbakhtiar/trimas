/**
 * Nov 15, 2008 11:32:35 AM
 * com.siriuserp.sdk.base
 * UIAdapter.java
 */
package com.siriuserp.sdk.adapter;

import com.siriuserp.sdk.dm.AccessType;


/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public interface UIAdapter extends Adapter
{
    public void setAccessType(AccessType accessType);
    public AccessType getAccessType();
}

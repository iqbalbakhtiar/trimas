/**
 * May 25, 2009 11:17:24 AM
 * com.siriuserp.sdk.filter
 * ReportFilterCriteria.java
 */
package com.siriuserp.sdk.filter;

import javolution.util.FastList;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public interface ReportFilterCriteria extends FilterCriteria
{
    public Long getOrganization();
    public void setOrganization(Long organization);
    
    public FastList<Long> getOrganizations();
    public void setOrganizations(FastList<Long> orgnaizations);
}

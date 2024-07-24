/**
 * May 25, 2009 11:30:27 AM
 * com.siriuserp.sdk.filter
 * StandardReportQuery.java
 */
package com.siriuserp.sdk.filter;

import com.siriuserp.sdk.db.ReportQuery;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public interface StandardReportQuery extends ReportQuery
{
    public void setFilterCriteria(ReportFilterCriteria criteria);
    public ReportFilterCriteria getFilterCriteria();
}

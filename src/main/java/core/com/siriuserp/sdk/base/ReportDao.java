/**
 * Dec 3, 2008 9:39:44 AM
 * com.siriuserp.sdk.base
 * ReportDao.java
 */
package com.siriuserp.sdk.base;

import java.util.List;

import com.siriuserp.sdk.db.ReportQuery;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@SuppressWarnings("rawtypes")
public interface ReportDao
{
    public List generateReport(ReportQuery reportQuery);

    public Object generate(ReportQuery reportQuery);
}

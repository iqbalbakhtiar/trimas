/**
 * May 25, 2009 11:14:44 AM
 * com.siriuserp.sdk.db
 * AbstractReportQuery.java
 */
package com.siriuserp.sdk.db;

import org.hibernate.Session;

import com.siriuserp.sdk.filter.ReportFilterCriteria;
import com.siriuserp.sdk.filter.StandardReportQuery;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public abstract class AbstractStandardReportQuery implements StandardReportQuery
{
	protected Session session;
	protected ReportFilterCriteria filterCriteria;

	public Session getSession()
	{
		return session;
	}

	public void setSession(Session session)
	{
		this.session = session;
	}

	@Override
	public ReportFilterCriteria getFilterCriteria()
	{
		return this.filterCriteria;
	}

	@Override
	public void setFilterCriteria(ReportFilterCriteria criteria)
	{
		this.filterCriteria = criteria;
	}
}

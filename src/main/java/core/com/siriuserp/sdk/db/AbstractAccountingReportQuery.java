/**
 * Dec 3, 2008 9:27:22 AM
 * com.siriuserp.sdk.db
 * AbstractReportQuery.java
 */
package com.siriuserp.sdk.db;

import org.hibernate.Session;

import com.siriuserp.accounting.adapter.AbstractReportAdapter;
import com.siriuserp.accounting.criteria.AccountingReportFilterCriteria;

/**
 * @author Muhammad Rizal
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public abstract class AbstractAccountingReportQuery implements ReportQuery
{
    protected Session session;
    
    protected AbstractReportAdapter reportAdapter;
    
    protected AccountingReportFilterCriteria criteria;    
    
    public Session getSession()
    {
        return session;
    }

    public void setSession(Session session)
    {
        this.session = session;
    }

    public AbstractReportAdapter getReportAdapter()
    {
        return reportAdapter;
    }

    public void setReportAdapter(AbstractReportAdapter reportAdapter)
    {
        this.reportAdapter = reportAdapter;
    }

    public AccountingReportFilterCriteria getCriteria()
    {
        return criteria;
    }

    public void setCriteria(AccountingReportFilterCriteria criteria)
    {
        this.criteria = criteria;
    }
}

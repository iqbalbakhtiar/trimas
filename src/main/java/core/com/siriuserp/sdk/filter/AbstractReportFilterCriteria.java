/**
 * May 25, 2009 11:18:54 AM
 * com.siriuserp.sdk.filter
 * AbstractReportFilterCriteria.java
 */
package com.siriuserp.sdk.filter;

import java.util.Date;

import javolution.util.FastList;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public abstract class AbstractReportFilterCriteria implements ReportFilterCriteria
{
    private static final long serialVersionUID = -5889228147708087349L;
    
    protected Long organization;
    protected FastList<Long> organizations = new FastList<Long>();
    
    protected Date date;
    protected Date next;
    protected Date prev;
    
    public Long getOrganization()
    {
        return this.organization;
    }

    public void setOrganization(Long organization)
    {
        this.organization = organization;
    }
    
    public FastList<Long> getOrganizations()
    {
        return this.organizations;
    }
    
    public void setOrganizations(FastList<Long> orgnaizations)
    {
        this.organizations = orgnaizations;
    }

	public Date getDate() 
	{
		return date;
	}

	public void setDate(Date date) 
	{
		this.date = date;
	}

	public Date getNext() 
	{
		return next;
	}

	public void setNext(Date next) 
	{
		this.next = next;
	}

	public Date getPrev() 
	{
		return prev;
	}

	public void setPrev(Date prev)
	{
		this.prev = prev;
	}
}

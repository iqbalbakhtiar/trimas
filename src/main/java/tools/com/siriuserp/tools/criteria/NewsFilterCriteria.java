/**
 * Nov 26, 2007 4:14:08 PM
 * com.siriuserp.tools.dto
 * NewsFilterCriteria.java
 */
package com.siriuserp.tools.criteria;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;
import com.siriuserp.sdk.utility.SiriusValidator;
import com.siriuserp.sdk.utility.StringHelper;

/**
 * @author Ersi Agustin
 */
public class NewsFilterCriteria extends AbstractFilterCriteria
{
    private static final long serialVersionUID = 1L;
    
    private Date dateFrom;
    private Date dateTo;
    private String title;
    private String postBy;
    
    
    public Date getDateFrom()
    {
        return dateFrom;
    }
    public void setDateFrom(Date dateFrom)
    {
        this.dateFrom = dateFrom;
    }
    public Date getDateTo()
    {
        return dateTo;
    }
    public void setDateTo(Date dateTo)
    {
        this.dateTo = dateTo;
    }
    public String getPostBy()
    {
        return postBy;
    }
    public void setPostBy(String postBy)
    {
        this.postBy = postBy;
    }
    public String getTitle()
    {
        return title;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }

    public static final synchronized NewsFilterCriteria newInstance(HttpServletRequest request,int max)
    {
        
        NewsFilterCriteria criteria = new NewsFilterCriteria();
        
        criteria.setTitle(request.getParameter("title"));
        criteria.setPostBy(request.getParameter("postBy"));
        criteria.setDateFrom(StringHelper.toDate(request.getParameter("dateFrom")));
        criteria.setDateTo(StringHelper.toDate(request.getParameter("dateTo")));

        criteria.setMax(max);
        criteria.setPage(SiriusValidator.getValidPageIndex(request.getParameter("page")));
        
        return criteria;
    }
    
    
    
}

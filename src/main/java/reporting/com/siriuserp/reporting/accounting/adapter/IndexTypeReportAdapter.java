/**
 * Dec 2, 2008 9:59:51 AM
 * com.siriuserp.reporting.accounting.dto
 * GeneralJournalIndex.java
 */
package com.siriuserp.reporting.accounting.adapter;

import java.io.Serializable;

import com.siriuserp.accounting.dm.IndexType;

/**
 * @author Muhammad Rizal
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public class IndexTypeReportAdapter implements Serializable
{
    private static final long serialVersionUID = -6328603106469073413L;

    private boolean used;
    
    private IndexType indexType;
    
    private String content;
    
    public IndexTypeReportAdapter(){}

    public boolean isUsed()
    {
        return used;
    }

    public void setUsed(boolean used)
    {
        this.used = used;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public IndexType getIndexType()
    {
        return indexType;
    }

    public void setIndexType(IndexType indexType)
    {
        this.indexType = indexType;
    }
}

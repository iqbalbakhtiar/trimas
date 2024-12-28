/**
 * Nov 12, 2008 2:44:58 PM
 * com.siriuserp.accounting.dto.filter
 * JournalSchemaFilterCriteria.java
 */
package com.siriuserp.accounting.criteria;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public class StandardJournalSchemaFilterCriteria extends AbstractFilterCriteria
{
    private static final long serialVersionUID = -6147262136051772648L;

    private String code;
    
    private String name;
    
    public StandardJournalSchemaFilterCriteria(){}

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}

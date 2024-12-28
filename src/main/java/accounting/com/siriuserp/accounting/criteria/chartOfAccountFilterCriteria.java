/**
 * Nov 11, 2008 12:52:49 PM
 * com.siriuserp.accounting.dto.filter
 * AccountingSchemaFilterCriteria.java
 */
package com.siriuserp.accounting.criteria;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public class chartOfAccountFilterCriteria extends AbstractFilterCriteria
{
    private static final long serialVersionUID = 1790045808706856716L;

    private String code;
    private String name;
    
    public chartOfAccountFilterCriteria(){}

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

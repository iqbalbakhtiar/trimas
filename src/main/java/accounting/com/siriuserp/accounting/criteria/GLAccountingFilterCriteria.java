/**
 * Nov 11, 2008 2:27:11 PM
 * com.siriuserp.accounting.dto.filter
 * GLAccountingFilterCriteria.java
 */
package com.siriuserp.accounting.criteria;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public class GLAccountingFilterCriteria extends AbstractFilterCriteria
{
    private static final long serialVersionUID = -6506013808087973605L;

    private Long coa;
    private String code;
    private String name;
    private String level;
    private String cashType;
    
    public GLAccountingFilterCriteria(){}

    public String getCashType()
    {
        return cashType;
    }

    public void setCashType(String cashType)
    {
        this.cashType = cashType;
    }

    public Long getCoa()
    {
        return coa;
    }

    public void setCoa(Long coa)
    {
        this.coa = coa;
    }

    public String getLevel()
    {
        return level;
    }

    public void setLevel(String level)
    {
        this.level = level;
    }

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

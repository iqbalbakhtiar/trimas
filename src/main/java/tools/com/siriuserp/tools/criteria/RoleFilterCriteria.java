/**
 * Nov 27, 2008 4:06:36 PM
 * com.siriuserp.administration.dto.filter
 * RoleFilterCriteria.java
 */
package com.siriuserp.tools.criteria;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public class RoleFilterCriteria extends AbstractFilterCriteria
{
    private static final long serialVersionUID = -4421779690463358737L;

    private String name;
    private String code;
    
    public RoleFilterCriteria(){}

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

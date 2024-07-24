/**
 * Jan 21, 2009 2:35:58 PM
 * com.siriuserp.tools.dto
 * OrganizationRoleFilterCriteria.java
 */
package com.siriuserp.tools.criteria;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public class OrganizationRoleFilterCriteria extends AbstractFilterCriteria
{
    private static final long serialVersionUID = 6351073788487254526L;

    private String name;
    
    public OrganizationRoleFilterCriteria(){}

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}

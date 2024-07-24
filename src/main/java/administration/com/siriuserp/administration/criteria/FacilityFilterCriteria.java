/**
 * Apr 24, 2009 4:48:44 PM
 * com.siriuserp.administration.criteria
 * FacilityFilterCriteria.java
 */
package com.siriuserp.administration.criteria;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public class FacilityFilterCriteria extends AbstractFilterCriteria
{
    private static final long serialVersionUID = -4166628104889203763L;

    private String name;
    
    private Long type;
    
    private String implementation;
    
    public FacilityFilterCriteria(){}

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Long getType()
    {
        return type;
    }

    public void setType(Long type)
    {
        this.type = type;
    }

    public String getImplementation()
    {
        return implementation;
    }

    public void setImplementation(String implementation)
    {
        this.implementation = implementation;
    }
}

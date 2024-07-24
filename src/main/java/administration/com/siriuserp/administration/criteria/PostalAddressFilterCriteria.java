/**
 * Apr 21, 2009 6:22:06 PM
 * com.siriuserp.administration.criteria
 * PostalAddressFilterCriteria.java
 */
package com.siriuserp.administration.criteria;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public class PostalAddressFilterCriteria extends AbstractFilterCriteria
{
    private static final long serialVersionUID = 1568744051569288321L;

    private Long party;
    
    private String type;
    
    public PostalAddressFilterCriteria(){}

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public Long getParty()
    {
        return party;
    }

    public void setParty(Long party)
    {
        this.party = party;
    }
}

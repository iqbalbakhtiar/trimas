/**
 * Jun 29, 2009 2:07:16 PM
 * com.siriuserp.administration.criteria
 * ContactMechanismFilterCriteria.java
 */
package com.siriuserp.administration.criteria;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public class ContactMechanismFilterCriteria extends AbstractFilterCriteria
{
    private static final long serialVersionUID = 471725627130712074L;

    private Long party;
    
    private String type;
    
    private String target;
    
    public ContactMechanismFilterCriteria(){}

    public String getTarget()
    {
        return target;
    }

    public void setTarget(String target)
    {
        this.target = target;
    }

    public Long getParty()
    {
        return party;
    }

    public void setParty(Long party)
    {
        this.party = party;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }
}

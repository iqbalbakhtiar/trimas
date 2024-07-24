/**
 * Mar 27, 2009 2:20:42 PM
 * com.siriuserp.administration.criteria
 * PartyRelationshipFilterCriteria.java
 */
package com.siriuserp.administration.criteria;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public class PartyRelationshipFilterCriteria extends AbstractFilterCriteria
{
    private static final long serialVersionUID = -4302045797949125123L;

    private Long party;
    
    private Long type;
    
    public PartyRelationshipFilterCriteria(){}

    public Long getType()
    {
        return type;
    }

    public void setType(Long type)
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

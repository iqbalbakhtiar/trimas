/**
 * Apr 24, 2009 4:48:44 PM
 * com.siriuserp.administration.criteria
 * FacilityFilterCriteria.java
 */
package com.siriuserp.administration.criteria;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Setter
@Getter
public class FacilityFilterCriteria extends AbstractFilterCriteria
{
    private static final long serialVersionUID = -4166628104889203763L;

    private String name;
    
    private Long type;
    
    private String implementation;
    
    public FacilityFilterCriteria(){}

}

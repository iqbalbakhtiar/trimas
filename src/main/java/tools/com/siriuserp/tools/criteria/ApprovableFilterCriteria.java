/**
 * Apr 22, 2009 3:11:30 PM
 * com.siriuserp.tools.criteria
 * ApprovableFilterCriteria.java
 */
package com.siriuserp.tools.criteria;

import java.util.Date;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Getter
@Setter
public class ApprovableFilterCriteria extends AbstractFilterCriteria
{
    private static final long serialVersionUID = -7767625718601533896L;

    private String code;
    private String type;
    private String approver;
    
    private Date dateFrom;
    private Date dateTo;
    
    private boolean over = false;
}

/**
 * Mar 29, 2007 11:55:12 AM
 * net.konsep.sirius.presentation.DTO.filter
 * ActivityHistoryFilterCriteria.java
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
public class AuditTrailsFilterCriteria extends AbstractFilterCriteria
{
    private static final long serialVersionUID = -6984809407334332024L;
    
    private String menu;
    
    private Date dateFrom;
    private Date dateTo;
    
    private String id;
    private String action;
    private Long person;
}

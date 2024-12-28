/**
 * Nov 11, 2008 12:52:49 PM
 * com.siriuserp.accounting.dto.filter
 * AccountingSchemaFilterCriteria.java
 */
package com.siriuserp.accounting.criteria;

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
public class AccountingSchemaFilterCriteria extends AbstractFilterCriteria
{
    private static final long serialVersionUID = 1790045808706856716L;

    private String code;
    private String name;
    private Long organization;
    private String target;
}

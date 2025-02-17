package com.siriuserp.inventory.criteria;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author Ferdinand
 * @author Rama Almer Felix
 *  Sirius Indonesia, PT
 *  www.siriuserp.com
 */

@Getter
@Setter
@NoArgsConstructor
public class BarcodeGroupFilterCriteria extends AbstractFilterCriteria {
    private static final long serialVersionUID = 1892378946751428367L;

    private String facility;
    private String code;
    private String createdBy;
    private String type;


    private Date dateFrom;
    private Date dateTo;
}

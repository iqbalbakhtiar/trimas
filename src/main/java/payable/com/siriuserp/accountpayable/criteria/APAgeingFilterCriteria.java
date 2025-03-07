package com.siriuserp.accountpayable.criteria;

import com.siriuserp.sdk.filter.AbstractReportFilterCriteria;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author Agung Dodi Perdana
 * @author Rama Almer Felix
 * Sirius Indonesia, PT
 * www.siriuserp.com
 * Version 1.5
 */

@Getter
@Setter
@NoArgsConstructor
public class APAgeingFilterCriteria extends AbstractReportFilterCriteria {
    private static final long serialVersionUID = 4000827227419883395L;

    private Date date;
    private Long supplier;
    private Long currency;
}

package com.siriuserp.accountpayable.criteria;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PaymentFilterCriteria extends AbstractFilterCriteria {
    private static final long serialVersionUID = -1934188273917609072L;

    private String code;
    private String supplierName;

    private Date dateFrom;
    private Date dateTo;
}

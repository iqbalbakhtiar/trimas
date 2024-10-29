package com.siriuserp.accounting.criteria;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BillingFilterCriteria extends AbstractFilterCriteria {

    private static final long serialVersionUID = -5980939306935334308L;

    private Long customer;
    private Long billingType;

    private String code;
    private String customerName;
    private String status;

    private Date dateFrom;
    private Date dateTo;
}

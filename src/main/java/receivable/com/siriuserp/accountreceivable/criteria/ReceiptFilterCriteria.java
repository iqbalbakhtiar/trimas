package com.siriuserp.accountreceivable.criteria;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ReceiptFilterCriteria extends AbstractFilterCriteria {
    private static final long serialVersionUID = -2419497867452495281L;

    private String code;
    private String customerName;
    private String type;

    private Date dateFrom;
    private Date dateTo;
}

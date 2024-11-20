package com.siriuserp.inventory.criteria;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class GoodsIssueFilterCriteria extends AbstractFilterCriteria {
    private static final long serialVersionUID = -2204126906158876735L;

    private String code;
    private String org;
    private String createdBy;

    private Date dateFrom;
    private Date dateTo;
}

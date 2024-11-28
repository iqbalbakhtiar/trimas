package com.siriuserp.inventory.criteria;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class GoodsReceiptFilterCriteria extends AbstractFilterCriteria {
    private static final long serialVersionUID = 6499149154596770266L;

    private Long facility;
    private Long supplier;
    private Long currency;
    private Long tax;

    private String code;
    private String source;
    private String org;
    private String createdBy;
    private String reference;

    private String referenceType;

    private Date dateFrom;
    private Date dateTo;
}

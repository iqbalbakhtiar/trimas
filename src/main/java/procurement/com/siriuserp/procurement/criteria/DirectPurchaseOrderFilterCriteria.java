package com.siriuserp.procurement.criteria;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class DirectPurchaseOrderFilterCriteria extends AbstractFilterCriteria {
    private static final long serialVersionUID = -2340778574501739520L;

    private String code;
    private String supplier;
    private String tax;
    private String approver;
    private String approvalDecisionStatus;
    private String billToAddress;
    private String shipToFacility;

    private Date dateFrom;
    private Date dateTo;
}

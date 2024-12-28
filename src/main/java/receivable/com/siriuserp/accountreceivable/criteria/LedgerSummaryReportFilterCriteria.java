package com.siriuserp.accountreceivable.criteria;

import com.siriuserp.sdk.filter.AbstractReportFilterCriteria;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class LedgerSummaryReportFilterCriteria extends AbstractReportFilterCriteria {
    private static final long serialVersionUID = 2723350756451443566L;

    private Date dateTo;
    private Date dateFrom;

    private Long customer;
}

package com.siriuserp.inventory.criteria;

import com.siriuserp.sdk.dm.Month;
import com.siriuserp.sdk.filter.AbstractReportFilterCriteria;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class WasteReportFilterCriteria extends AbstractReportFilterCriteria {
    private static final long serialVersionUID = 8000526244437654278L;

    private Month month;
    private Integer year;

    private Date dateFrom;
    private Date dateTo;

    private Long product;
    private Long facility;
    private Long container;
}

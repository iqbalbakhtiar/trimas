package com.siriuserp.accountpayable.criteria;

import com.siriuserp.inventory.dm.GoodsType;
import com.siriuserp.sdk.filter.AbstractFilterCriteria;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class InvoiceVerificationFilterCriteria extends AbstractFilterCriteria {
    private static final long serialVersionUID = 3015084793648578032L;

    private String code;
    private String reference;
    private String supplierName;
    private String taxName;
    private String documentNo;
    private String currencyName;
    private String referenceCode;
    private String financialStatus;
    private String invoiceType;

    private Long organization;
    private Long supplier;

    private Date dateFrom;
    private Date dateTo;

    private Boolean verification;

    private GoodsType goodsType;
}

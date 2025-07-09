package com.siriuserp.accountpayable.criteria;

import java.util.Date;

import com.siriuserp.sdk.dm.Month;
import com.siriuserp.sdk.filter.AbstractFilterCriteria;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentFilterCriteria extends AbstractFilterCriteria
{
	private static final long serialVersionUID = -1934188273917609072L;

	private String code;
	private String name;
	private String reference;
	private String supplierName;
	private String account;
	private String referenceType;
	private String paymentType;
	private String paymentMethodType;
	private String status;

	private Integer year;

	private Date dateFrom;
	private Date dateTo;

	private Month month;
}

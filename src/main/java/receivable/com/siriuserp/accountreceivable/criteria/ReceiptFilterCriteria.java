package com.siriuserp.accountreceivable.criteria;

import com.siriuserp.sdk.dm.Month;
import com.siriuserp.sdk.filter.AbstractFilterCriteria;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ReceiptFilterCriteria extends AbstractFilterCriteria
{
	private static final long serialVersionUID = -2419497867452495281L;

	private String code;
	private String name;
	private String customerName;
	private String account;
	private String type;
	private String paymentMethodType;
	private String referenceType;
	private String reference;
	private String status;

	private Integer year;

	private Date dateFrom;
	private Date dateTo;

	private Month month;
}

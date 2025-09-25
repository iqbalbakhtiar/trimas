package com.siriuserp.accountreceivable.criteria;

import java.util.Date;

import com.siriuserp.sdk.dm.Month;
import com.siriuserp.sdk.filter.AbstractFilterCriteria;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ferdinand
 */

@Getter
@Setter
public class PrepaymentFilterCriteria extends AbstractFilterCriteria 
{
	private static final long serialVersionUID = 3195237504258704567L;

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

package com.siriuserp.accounting.criteria;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankAccountFilterCriteria extends AbstractFilterCriteria {

	private static final long serialVersionUID = 4275956669369433818L;
	
	private String code;
	private String accountName;
	private String bankName;
	private String holderName;
	private String type;

}

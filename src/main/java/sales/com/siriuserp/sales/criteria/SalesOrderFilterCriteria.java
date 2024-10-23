package com.siriuserp.sales.criteria;

import java.util.Date;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SalesOrderFilterCriteria extends AbstractFilterCriteria {
	
	private static final long serialVersionUID = 4812990284661889240L;
	
	private String code;
	private String customer;
	private String tax;
	private String approver;

	// For Checking if SO Approve and finish and Approve and Forward
	private Boolean approved;
	
	private Date date;
}

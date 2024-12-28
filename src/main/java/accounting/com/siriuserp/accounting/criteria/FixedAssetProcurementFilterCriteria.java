package com.siriuserp.accounting.criteria;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;

public class FixedAssetProcurementFilterCriteria extends AbstractFilterCriteria {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4140249446594129645L;
	
    private String code;
    
    private Long approver;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getApprover() {
		return approver;
	}

	public void setApprover(Long approver) {
		this.approver = approver;
	}
    
    

}

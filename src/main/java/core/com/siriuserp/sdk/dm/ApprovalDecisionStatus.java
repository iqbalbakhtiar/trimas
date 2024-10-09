package com.siriuserp.sdk.dm;

public enum ApprovalDecisionStatus {
	REQUESTED,
	FORWARD,
    APPROVE_AND_FORWARD,
    REJECTED,
    APPROVE_AND_FINISH;
    
    public String getNormalizedName()
	{
		return this.toString().replace("_", " ");
	}
	
	public String getMessage()
	{
		return this.toString().replace("_", "").toLowerCase();
	}
}

package com.siriuserp.sales.dm;

public enum SOStatus
{
	OPEN, CLOSE, LOCK, UNLOCK, PLANNING, SENT, DELIVERED;

	public String getNormalizedName()
	{
		return this.toString().replace("_", " ");
	}

	public String getMessageName()
	{
		return this.toString().replace("_", "").toLowerCase();
	}
}

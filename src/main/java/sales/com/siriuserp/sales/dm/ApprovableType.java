package com.siriuserp.sales.dm;

public enum ApprovableType {
	SALES_ORDER, PURCHASE_ORDER;

	public String getNormalizedName()
	{
		return this.toString().replace("_", " ");
	}
}

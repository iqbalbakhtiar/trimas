package com.siriuserp.sales.dm;

public enum ApprovableType
{
	SALES_ORDER,
	PURCHASE_ORDER,
	INTER_TRANSACTION_REQUISITION,
	INTER_TRANSACTION_ACKNOWLEDGEMENT,
	INTER_TRANSACTION_AP,
	FIXED_ASSET_DISPOSAL,
	FIXED_ASSET_FROM_PROCUREMENT;

	public String getNormalizedName()
	{
		return this.toString().replace("_", " ");
	}
}

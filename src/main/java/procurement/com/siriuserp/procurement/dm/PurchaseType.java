package com.siriuserp.procurement.dm;

import com.siriuserp.inventory.dm.WarehouseTransactionSource;

public enum PurchaseType
{
	STANDARD, DIRECT;

	public String getNormalizedName()
	{
		return this.toString().replace("_", " ");
	}

	public String getMessageName()
	{
		return this.toString().replace("_", "").toLowerCase();
	}

	public WarehouseTransactionSource getTransactionSource()
	{
		switch (this)
		{
		case STANDARD:
			return WarehouseTransactionSource.STANDARD_PURCHASE_ORDER;
		case DIRECT:
			return WarehouseTransactionSource.DIRECT_PURCHASE_ORDER;
		default:
			return null;
		}
	}
}

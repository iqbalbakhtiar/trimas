/**
 * May 1, 2009 10:45:46 AM
 * com.siriuserp.sdk.dm
 * WarehouseTransactionSource.java
 */
package com.siriuserp.inventory.dm;

import org.apache.commons.lang.WordUtils;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public enum WarehouseTransactionSource 
{
	//Procurement
	DIRECT_PURCHASE_ORDER,
	STANDARD_PURCHASE_ORDER,
	DELIVERY_ORDER_REALIZATION,

	//Inventory
	STOCK_ADJUSTMENT,
	TRANSFER_ORDER,
	ITEM_CONVERSION,
	GOODS_RECEIPT_MANUAL,
	GOODS_ISSUE_MANUAL,
	
	//Production
	WORK_ORDER;
	
	public String getNormalizedName()
    {
    	return this.toString().replace("_", " ");
    }
    
    public String getCapitalizedName()
    {
    	return WordUtils.capitalize(getNormalizedName().toLowerCase());
    }
    
    public String getConfigName()
	{
		return getCapitalizedName().replace(" ", "");
	}
    
    public String getMessage() 
	{
		return this.toString().replace("_", "").toLowerCase();
	}
    
    public String getUri()
    {
    	return this.toString().replace("_", "").toLowerCase();
    }

	public boolean isInternal()
	{
		switch(this)
		{
			case TRANSFER_ORDER:
				return true;
			default:
				return false;
		}
	}

	public boolean isReservable()
	{
		switch(this)
		{
			case DELIVERY_ORDER_REALIZATION:
			case WORK_ORDER:
				return true;
			default:
				return false;
		}
	}

	public boolean isAutoLocked()
	{
		switch(this)
		{
			default:
				return false;
		}
	}
}

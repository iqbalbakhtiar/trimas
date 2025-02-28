/**
 * May 1, 2009 10:45:46 AM
 * com.siriuserp.inventory.dm
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
	STOCK_ADJUSTMENT,
	TRANSFER_ORDER,
	ITEM_CONVERSION,
	STANDARD_PURCHASE_ORDER,
	DIRECT_PURCHASE_ORDER,
	DELIVERY_ORDER_REALIZATION;
	
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
				return true;
			default:
				return false;
		}
	}

	public boolean isAutoLocked()
	{
		switch(this)
		{
			case DIRECT_PURCHASE_ORDER:
				return true;
			default:
				return false;
		}
	}
}

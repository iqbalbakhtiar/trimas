/**
 * 
 */
package com.siriuserp.inventory.dm;

import org.apache.commons.lang.WordUtils;

/**
 * @author ferdinand
 */

public enum WarehouseTransactionSource 
{
	STOCK_ADJUSTMENT,
	TRANSFER_ORDER,
	ITEM_CONVERSION,
	PURCHASE_ORDER,
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
}

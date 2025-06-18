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
	DELIVERY_ORDER_REALIZATION,
	STANDARD_PURCHASE_ORDER,
	SERVICE_PURCHASE_ORDER,
	ASSET_PURCHASE_ORDER,
	INVENTARIS_PURCHASE_ORDER,

	TRANSFER_ORDER_REFILL,
	TRANSFER_ORDER_REQUEST,
	TRANSFER_ORDER_LOADING,
	MOVING_CONTAINER_ISSUE_COMPLETION,
	MOVING_CONTAINER_RECEIPT_COMPLETION,
	PURCHASE_ORDER_CONFIRMATION,
	GOODS_RECEIPT_MANUAL,
	GOODS_ISSUE_MANUAL;
	
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

	public boolean isComplete()
	{
		switch(this)
		{
			case TRANSFER_ORDER_REFILL:
			case TRANSFER_ORDER_REQUEST:
			case TRANSFER_ORDER_LOADING:
			case MOVING_CONTAINER_ISSUE_COMPLETION:
			case MOVING_CONTAINER_RECEIPT_COMPLETION:
				return true;
			default:
				return false;
		}
	}

	public boolean isInternal()
	{
		switch(this)
		{
			case TRANSFER_ORDER:
//			case TRANSFER_ORDER_AREA:
//			case TRANSFER_ORDER_MANUAL:
//			case TRANSFER_ORDER_CONVERSION:
//			case TRANSFER_ORDER_BYPASS:
			case TRANSFER_ORDER_REQUEST:
			case TRANSFER_ORDER_LOADING:
//			case TRANSFER_ORDER_EXTERNAL:
//			case PACK_TO_CONTAINER:
//			case MOVING_CONTAINER_ISSUE:
			case MOVING_CONTAINER_ISSUE_COMPLETION:
//			case MOVING_CONTAINER_ISSUE_SEQUENCE:
//			case MOVING_CONTAINER_RECEIPT:
			case MOVING_CONTAINER_RECEIPT_COMPLETION:
//			case MOVING_CONTAINER_DUMP_ITEM:
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
//			case DELIVERY_ORDER_GROUP_REALIZATION:
//			case SHIFT_MANAGEMENT:
//			case TRANSFER_ORDER_MANUAL:
//			case TRANSFER_ORDER_CONVERSION:
//			case TRANSFER_ORDER_BYPASS:
			case TRANSFER_ORDER_REQUEST:
			case TRANSFER_ORDER_LOADING:
//			case TRANSFER_ORDER_EXTERNAL:
//			case MOVING_CONTAINER_ISSUE:
//			case MOVING_CONTAINER_ISSUE_SEQUENCE:
//			case PURCHASE_RETURN:
//			case PURCHASE_RETURN_MANUAL:
				return true;
			default:
				return false;
		}
	}

	public boolean isAutoLocked()
	{
		switch(this)
		{
			case PURCHASE_ORDER_CONFIRMATION:
				return true;
			default:
				return false;
		}
	}
}

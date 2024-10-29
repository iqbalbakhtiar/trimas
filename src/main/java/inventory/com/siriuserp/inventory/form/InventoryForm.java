/**
 * 
 */
package com.siriuserp.inventory.form;

import com.siriuserp.inventory.dm.GoodsType;
import com.siriuserp.inventory.dm.StockAdjustment;
import com.siriuserp.inventory.dm.WarehouseTransactionSource;
import com.siriuserp.inventory.dm.WarehouseTransactionType;
import com.siriuserp.sdk.dm.Form;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ferdinand
 */

@Getter
@Setter
public class InventoryForm extends Form 
{
	private static final long serialVersionUID = -4381658249533315535L;

	private StockAdjustment stockAdjustment;
	
	private boolean receiptable = true;
	private boolean issuedable;
	private boolean verificated;
	private boolean locked;
	private GoodsType goodsType;
	private WarehouseTransactionType type;
	private WarehouseTransactionSource transactionSource;
}

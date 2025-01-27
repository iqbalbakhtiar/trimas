/**
 * 
 */
package com.siriuserp.inventory.form;

import com.siriuserp.inventory.dm.GoodsType;
import com.siriuserp.inventory.dm.ProductCategory;
import com.siriuserp.inventory.dm.ProductType;
import com.siriuserp.inventory.dm.StockAdjustment;
import com.siriuserp.inventory.dm.UnitOfMeasure;
import com.siriuserp.inventory.dm.WarehouseTransactionSource;
import com.siriuserp.inventory.dm.WarehouseTransactionType;
import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.Facility;
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

	private UnitOfMeasure unitOfMeasure;
	private ProductCategory productCategory;
	private StockAdjustment stockAdjustment;
	//private TransferOrder transferOrder;
	private Facility source;
	private Facility destination;
	private Container container;
	//private TransferType transferType;

	private ProductType productType;
	private GoodsType goodsType;
	private WarehouseTransactionType transactionType;
	private WarehouseTransactionSource transactionSource;

	private boolean receiptable = true;
	private boolean issuedable;
	private boolean verificated;
	private boolean locked;
}

/**
 * File Name  : StandardPurchaseOrderService.java
 * Created On : Sept 23, 2023
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.inventory.form;

import com.siriuserp.accountpayable.dm.InvoiceVerification;
import com.siriuserp.inventory.dm.GoodsIssue;
import com.siriuserp.inventory.dm.GoodsReceipt;
import com.siriuserp.inventory.dm.GoodsReceiptManual;
import com.siriuserp.inventory.dm.GoodsType;
import com.siriuserp.inventory.dm.ProductCategory;
import com.siriuserp.inventory.dm.ProductType;
import com.siriuserp.inventory.dm.StockAdjustment;
import com.siriuserp.inventory.dm.UnitOfMeasure;
import com.siriuserp.inventory.dm.WarehouseTransactionSource;
import com.siriuserp.inventory.dm.WarehouseTransactionType;
import com.siriuserp.procurement.dm.PurchaseOrder;
import com.siriuserp.sdk.dm.BarcodeGroup;
import com.siriuserp.sdk.dm.BarcodeGroupType;
import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.Form;
import com.siriuserp.sdk.dm.Grid;
import com.siriuserp.sdk.dm.StatusType;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Getter
@Setter
public class InventoryForm extends Form
{
	private static final long serialVersionUID = 7445610996434139452L;

	private String invoiceNo;

	private UnitOfMeasure unitOfMeasure;
	private ProductCategory productCategory;
	private StockAdjustment stockAdjustment;
	private GoodsIssue goodsIssue;
	private GoodsReceipt goodsReceipt;
	private GoodsReceiptManual goodsReceiptManual;
	//private TransferOrder transferOrder;

	private PurchaseOrder purchaseOrder;
	private InvoiceVerification invoiceVerification;

	private BarcodeGroup barcodeGroup;
	private BarcodeGroupType barcodeGroupType;
	private StatusType statusType;
	//private TransferType transferType;

	private Facility source;
	private Facility destination;
	private Container container;
	private Grid grid;

	private ProductType productType;
	private GoodsType goodsType;
	private WarehouseTransactionType transactionType;
	private WarehouseTransactionSource transactionSource;

	private boolean receiptable = true;
	private boolean issuedable;
	private boolean verificated;
	private boolean locked;
	private boolean active;
}

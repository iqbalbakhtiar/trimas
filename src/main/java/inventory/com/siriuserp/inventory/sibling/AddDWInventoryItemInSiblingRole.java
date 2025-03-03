package com.siriuserp.inventory.sibling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siriuserp.inventory.dm.DWInventoryItemBalance;
import com.siriuserp.inventory.dm.DWInventoryItemBalanceDetail;
import com.siriuserp.inventory.dm.GoodsReceipt;
import com.siriuserp.inventory.dm.GoodsReceiptItem;
import com.siriuserp.inventory.dm.ProductInOutTransaction;
import com.siriuserp.inventory.dm.WarehouseTransactionItem;
import com.siriuserp.inventory.util.InventoryBalanceDetailUtil;
import com.siriuserp.inventory.util.InventoryBalanceUtil;
import com.siriuserp.sdk.dm.AbstractSiblingRole;

@Component
public class AddDWInventoryItemInSiblingRole extends AbstractSiblingRole
{
	@Autowired
	private InventoryBalanceUtil balanceUtil;

	@Autowired
	private InventoryBalanceDetailUtil balanceDetailUtil;

	@Override
	public void execute() throws Exception
	{
		GoodsReceipt goodsReceipt = (GoodsReceipt) getSiblingable();

		for (GoodsReceiptItem item : goodsReceipt.getAllItems())
		{
			if (!item.getInOuts().isEmpty())
			{
				WarehouseTransactionItem transactionItem = genericDao.load(WarehouseTransactionItem.class, item.getWarehouseTransactionItem().getId());
				balanceUtil.in(DWInventoryItemBalance.class, item, item.getReceipted());

				for (ProductInOutTransaction inOut : item.getInOuts())
					balanceDetailUtil.in(DWInventoryItemBalanceDetail.class, item, transactionItem, goodsReceipt, inOut.getQuantity(), inOut.getPrice(), goodsReceipt.getNote());
			}
		}
	}
}

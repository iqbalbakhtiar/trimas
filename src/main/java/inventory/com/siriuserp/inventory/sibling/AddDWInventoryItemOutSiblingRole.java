package com.siriuserp.inventory.sibling;

import com.siriuserp.inventory.dm.*;
import com.siriuserp.inventory.util.InventoryBalanceDetailUtil;
import com.siriuserp.inventory.util.InventoryBalanceUtil;
import com.siriuserp.sdk.dm.AbstractSiblingRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddDWInventoryItemOutSiblingRole extends AbstractSiblingRole {

    @Autowired
    private InventoryBalanceUtil balanceUtil;

    @Autowired
    private InventoryBalanceDetailUtil balanceDeatilUtil;

    @Override
    public void execute() throws Exception {
        System.out.println("AddDWInventoryItemOutSiblingRole executed!!");
        GoodsIssue goodsIssue = (GoodsIssue) getSiblingable();

        for (GoodsIssueItem item : goodsIssue.getItems())
        {
            WarehouseTransactionItem transactionItem = genericDao.load(WarehouseTransactionItem.class, item.getWarehouseTransactionItem().getId());

            balanceUtil.out(DWInventoryItemBalance.class, item, item.getIssued());

            for (StockControl stockControl : item.getStockControls())
                balanceDeatilUtil.out(DWInventoryItemBalanceDetail.class, item, transactionItem, goodsIssue, stockControl.getQuantity(), stockControl.getPrice(), goodsIssue.getNote());
        }
    }
}

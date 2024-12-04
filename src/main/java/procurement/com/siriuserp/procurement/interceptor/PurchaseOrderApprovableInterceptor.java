package com.siriuserp.procurement.interceptor;

import com.siriuserp.inventory.dm.WarehouseTransactionItem;
import com.siriuserp.procurement.dm.PurchaseOrder;
import com.siriuserp.procurement.dm.PurchaseOrderItem;
import com.siriuserp.sdk.base.AbstractApprovableInterceptor;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dm.ApprovalDecisionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(rollbackFor = Exception.class)
public class PurchaseOrderApprovableInterceptor extends AbstractApprovableInterceptor {

    @Autowired
    private GenericDao genericDao;

    @Override
    public void execute() throws Exception {
        PurchaseOrder purchaseOrder = genericDao.load(PurchaseOrder.class, getApprovable().getNormalizedID());
        if (purchaseOrder != null && getStatus().equals(ApprovalDecisionStatus.APPROVE_AND_FINISH)) {
            for (PurchaseOrderItem item : purchaseOrder.getItems()) {
                item.setLocked(false);
                genericDao.update(item);

                WarehouseTransactionItem warehouseTransactionItem = item.getTransactionItem();
                warehouseTransactionItem.setLocked(false);
                genericDao.update(warehouseTransactionItem);
            }
        }
    }
}

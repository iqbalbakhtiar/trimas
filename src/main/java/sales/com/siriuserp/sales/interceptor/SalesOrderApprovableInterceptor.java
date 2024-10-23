package com.siriuserp.sales.interceptor;

import com.siriuserp.sales.dm.SalesOrder;
import com.siriuserp.sales.dm.SalesOrderItem;
import com.siriuserp.sdk.base.AbstractApprovableInterceptor;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dm.ApprovalDecisionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(rollbackFor = Exception.class)
public class SalesOrderApprovableInterceptor extends AbstractApprovableInterceptor {
    @Autowired
    private GenericDao genericDao;

    @Override
    public void execute() throws Exception {
        SalesOrder salesOrder = genericDao.load(SalesOrder.class, this.getApprovable().getNormalizedID());

        // Set Approved pada Sales Reference di Sales Order menjadi True
        if (status == ApprovalDecisionStatus.APPROVE_AND_FORWARD ||
                status == ApprovalDecisionStatus.APPROVE_AND_FINISH) {

            for (SalesOrderItem item : salesOrder.getItems()) {
                item.setApproved(true);
            }

            genericDao.update(salesOrder);
        }
    }
}

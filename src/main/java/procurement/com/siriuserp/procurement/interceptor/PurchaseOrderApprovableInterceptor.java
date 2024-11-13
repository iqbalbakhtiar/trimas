package com.siriuserp.procurement.interceptor;

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

    }
}

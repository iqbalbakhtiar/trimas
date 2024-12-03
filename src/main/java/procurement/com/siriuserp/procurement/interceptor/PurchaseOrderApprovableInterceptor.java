package com.siriuserp.procurement.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.sdk.base.AbstractApprovableInterceptor;
import com.siriuserp.sdk.dao.GenericDao;

@Component
@Transactional(rollbackFor = Exception.class)
public class PurchaseOrderApprovableInterceptor extends AbstractApprovableInterceptor {

    @Autowired
    private GenericDao genericDao;

    @Override
    public void execute() throws Exception {

    }
}

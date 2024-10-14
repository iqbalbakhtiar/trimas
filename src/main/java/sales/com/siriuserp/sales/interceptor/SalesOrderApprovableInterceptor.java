package com.siriuserp.sales.interceptor;

import com.siriuserp.sdk.base.AbstractApprovableInterceptor;
import com.siriuserp.sdk.dao.GenericDao;
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

    }
}

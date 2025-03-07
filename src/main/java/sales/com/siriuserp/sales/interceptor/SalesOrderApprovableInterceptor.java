package com.siriuserp.sales.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.sales.dm.SalesOrder;
import com.siriuserp.sdk.base.AbstractApprovableInterceptor;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dm.ApprovalDecisionStatus;

@Component
@Transactional(rollbackFor = Exception.class)
public class SalesOrderApprovableInterceptor extends AbstractApprovableInterceptor
{
	@Autowired
	private GenericDao genericDao;

	@Override
	public void execute() throws Exception
	{
		SalesOrder salesOrder = genericDao.load(SalesOrder.class, this.getApprovable().getNormalizedID());
		if (status == ApprovalDecisionStatus.APPROVE_AND_FORWARD || status == ApprovalDecisionStatus.APPROVE_AND_FINISH)
		{
			genericDao.update(salesOrder);
		}
	}
}

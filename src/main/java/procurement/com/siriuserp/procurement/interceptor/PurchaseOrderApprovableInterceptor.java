/**
 * File Name  : PurchaseOrderApprovableInterceptor.java
 * Created On : Feb 26, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.procurement.interceptor;

import com.siriuserp.inventory.dm.WarehouseTransactionItem;
import com.siriuserp.procurement.dm.PurchaseOrder;
import com.siriuserp.procurement.dm.PurchaseOrderItem;
import com.siriuserp.procurement.dm.PurchaseType;
import com.siriuserp.procurement.service.StandardPurchaseOrderService;
import com.siriuserp.sdk.base.AbstractApprovableInterceptor;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dm.ApprovalDecisionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class PurchaseOrderApprovableInterceptor extends AbstractApprovableInterceptor
{
	@Autowired
	private GenericDao genericDao;

	@Autowired
	private StandardPurchaseOrderService purchaseOrderService;

	@Override
	public void execute() throws Exception
	{
		PurchaseOrder purchaseOrder = genericDao.load(PurchaseOrder.class, getApprovable().getNormalizedID());
		if (purchaseOrder != null && getStatus().equals(ApprovalDecisionStatus.APPROVE_AND_FINISH))
		{
			for (PurchaseOrderItem item : purchaseOrder.getItems())
			{
				item.setLocked(false);
				genericDao.update(item);

				if (item.getTransactionItem() != null)
				{
					WarehouseTransactionItem warehouseTransactionItem = item.getTransactionItem();
					warehouseTransactionItem.setLocked(false);
					genericDao.update(warehouseTransactionItem);
				}
			}

			if (purchaseOrder.isInvoiceBeforeReceipt() && purchaseOrder.getPurchaseType().equals(PurchaseType.STANDARD))
				purchaseOrderService.createInvoice(purchaseOrder);
		}
	}
}

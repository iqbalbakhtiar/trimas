/**
 * 
 */
package com.siriuserp.inventory.sibling;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siriuserp.inventory.dm.GoodsReceipt;
import com.siriuserp.inventory.dm.Receiptable;
import com.siriuserp.inventory.dm.WarehouseReferenceItem;
import com.siriuserp.inventory.dm.WarehouseTransactionItem;
import com.siriuserp.inventory.form.InventoryForm;
import com.siriuserp.inventory.service.GoodsReceiptService;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dm.AbstractSiblingRole;
import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.Item;
import com.siriuserp.sdk.utility.FormHelper;

import javolution.util.FastMap;

/**
 * @author Betsu Brahmana Restu
 * Sirius Indonesia, PT
 * betsu@siriuserp.com
 *
 */

@Component
public class AddInventorySiblingRole extends AbstractSiblingRole
{
	@Autowired
	private GenericDao genericDao;

	@Autowired
	private GoodsReceiptService goodsReceiptService;

	@Override
	public void execute() throws Exception
	{
		Object object = (Object) getSiblingable();

		Receiptable warehouse = (Receiptable) object;
		
		FastMap<Long, InventoryForm> receipts = new FastMap<Long, InventoryForm>();
		
		for (WarehouseReferenceItem refItem : warehouse.getReceiptables())
		{
			WarehouseReferenceItem transItem = genericDao.load(WarehouseReferenceItem.class, refItem.getId());
			if (transItem.getTransactionItem() != null)
			{
				InventoryForm form = receipts.get(transItem.getFacilityDestination().getId());
				if (form == null)
				{
					form = new InventoryForm();
					form.setDate(warehouse.getDate());
					form.setNote("AUTO GOODS RECEIPT FROM " + warehouse.getSelf().toUpperCase() + " ");
					form.setOrganization(warehouse.getOrganization());
					form.setFacility(transItem.getFacilityDestination());
					form.setGrid(transItem.getDestinationGrid());
					form.setContainer(transItem.getContainer());
					
					receipts.put(transItem.getFacilityDestination().getId(), form);
				}

				WarehouseTransactionItem transactionItem = genericDao.load(WarehouseTransactionItem.class, transItem.getTransactionItem().getId());
				if (transactionItem.getUnreceipted().compareTo(BigDecimal.ZERO) > 0)
				{
					Item item = new Item();
					item.setReceipted(transactionItem.getUnreceipted());
					item.setWarehouseTransactionItem(transactionItem);
					item.setGrid(transItem.getDestinationGrid());

					Container container = null;
					if (transItem.getDestinationContainer() != null)
						container = genericDao.load(Container.class, transItem.getDestinationContainer().getId());

					if (container != null)
						item.setContainer(container);

					form.getItems().add(item);
				}
			}
		}

		for (InventoryForm form : receipts.values()) {
			goodsReceiptService.add(FormHelper.create(GoodsReceipt.class, form));
		}
	}
}

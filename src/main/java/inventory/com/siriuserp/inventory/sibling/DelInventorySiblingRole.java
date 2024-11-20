package com.siriuserp.inventory.sibling;

import com.siriuserp.inventory.dm.GoodsIssue;
import com.siriuserp.inventory.dm.Issueable;
import com.siriuserp.inventory.dm.WarehouseReferenceItem;
import com.siriuserp.inventory.dm.WarehouseTransactionItem;
import com.siriuserp.inventory.form.TransactionForm;
import com.siriuserp.inventory.service.GoodsIssueService;
import com.siriuserp.sdk.dm.AbstractSiblingRole;
import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.Item;
import com.siriuserp.sdk.dm.StatusType;
import com.siriuserp.sdk.utility.FormHelper;
import javolution.util.FastMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DelInventorySiblingRole extends AbstractSiblingRole {

    @Autowired
    private GoodsIssueService goodsIssueService;

    @Override
    public void execute() throws Exception {
		System.out.println("DelInventorySiblingRole is Executed!!");
        Object object = (Object) getSiblingable();

		Issueable warehouse = (Issueable) object;

		FastMap<Long, TransactionForm> issues = new FastMap<Long, TransactionForm>();

		for (WarehouseReferenceItem refItem : warehouse.getIssueables())
		{
			WarehouseReferenceItem transItem = genericDao.load(WarehouseReferenceItem.class, refItem.getId());

			if(transItem.getTransactionItem() != null)
			{
				TransactionForm form = issues.get(transItem.getFacilitySource().getId());

				if(form == null)
				{
					form = new TransactionForm();
					form.setDate(warehouse.getDate());
					form.setNote("AUTO GOODS ISSUE FROM " + warehouse.getSelf().toUpperCase() + " ");
					form.setOrganization(warehouse.getOrganization());
					form.setFacility(transItem.getFacilitySource());

					if(transItem.getTransactionSource().isComplete())
						form.setStatusType(StatusType.COMPLETION);

					issues.put(transItem.getFacilitySource().getId(), form);
				}

				WarehouseTransactionItem transactionItem = genericDao.load(WarehouseTransactionItem.class, transItem.getTransactionItem().getId());

				if (transactionItem.getUnissued().compareTo(BigDecimal.ZERO) > 0)
				{
					Item item = new Item();
					item.setIssued(transactionItem.getUnissued());
					item.setWarehouseTransactionItem(transactionItem);
					item.setGrid(transItem.getSourceGrid());

					Container container = genericDao.load(Container.class, transItem.getSourceContainer().getId());
					item.setContainer(container);

					form.getItems().add(item);
				}
			}
		}

		for(TransactionForm form : issues.values())
			goodsIssueService.add(FormHelper.create(GoodsIssue.class, form));
    }
}

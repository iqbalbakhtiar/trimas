package com.siriuserp.sdk.utility;

import com.siriuserp.inventory.dm.*;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.Tax;

import java.math.BigDecimal;

public class ReferenceItemHelper {
    public static WarehouseTransactionItem init(GenericDao genericDao, BigDecimal quantity, WarehouseTransactionType type, WarehouseReferenceItem referenceItem) throws Exception {
		WarehouseTransactionItem item = new WarehouseTransactionItem();
		item.setReferenceItem(referenceItem);
		item.setQuantity(quantity);
		item.setType(type);
		item.setTransactionSource(referenceItem.getTransactionSource());
		item.setGoodsType(referenceItem.getGoodsType());

		if(item.getType().compareTo(WarehouseTransactionType.IN) == 0)
			item.setUnreceipted(item.getQuantity());
		else
			item.setUnissued(item.getQuantity());

		ReferenceItemHelper.bind(genericDao, referenceItem, item);

		return item;
	}

	public static void bind(GenericDao genericDao, WarehouseReferenceItem item, WarehouseTransactionItem transactionItem)
	{
		WarehouseTransaction transaction = item.getWarehouseTransaction();
		item.setTransactionItem(transactionItem);
		item.setReferenceCode(transaction.getCode());
		item.setOrganization(transaction.getOrganization());
		item.setParty(transaction.getParty());
		item.setDate(transaction.getDate());

		item.getMoney().setCurrency(genericDao.load(Currency.class, transaction.getCurrency().getId()));
		item.setTax(genericDao.load(Tax.class, transaction.getTax().getId()));

		if(transaction instanceof Issueable && item.getSourceContainer() != null) {
			Container source = genericDao.load(Container.class, item.getSourceContainer().getId());

			if(item.getFacilitySource() == null)
				item.setFacilitySource(source.getGrid().getFacility());

			if(item.getSourceGrid() == null)
				item.setSourceGrid(source.getGrid());
		}

		if(transaction instanceof Receiptable && item.getDestinationContainer() != null) {
			Container destination = genericDao.load(Container.class, item.getDestinationContainer().getId());

			if(item.getFacilityDestination() == null)
				item.setFacilityDestination(destination.getGrid().getFacility());

			if(item.getDestinationGrid() == null)
				item.setDestinationGrid(destination.getGrid());
		}

		//source and destination must be filled for first before, because RefTo or RefFrom is default from source and destination

		if(transaction instanceof Issueable)
			item.setReferenceTo(item.getRefTo());

		if(transaction instanceof Receiptable)
			item.setReferenceFrom(item.getRefFrom());

		if(SiriusValidator.validateParam(transaction.getNote()))
			item.setNote(SiriusValidator.validateParam(item.getNote()) ? item.getNote() : "" +" ["+transaction.getNote()+"]");
	}
}

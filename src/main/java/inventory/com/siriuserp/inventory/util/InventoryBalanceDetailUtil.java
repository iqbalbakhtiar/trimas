/**
 * Mar 5, 2010 4:06:02 PM
 * com.siriuserp.inventory.service
 * InventoryBalanceUtil.java
 */
package com.siriuserp.inventory.util;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.inventory.dao.DataWarehouseDao;
import com.siriuserp.inventory.dm.DataWarehouseDetail;
import com.siriuserp.inventory.dm.Inventoriable;
import com.siriuserp.inventory.dm.Product;
import com.siriuserp.inventory.dm.Transaction;
import com.siriuserp.inventory.dm.TransactionItem;
import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.Grid;
import com.siriuserp.sdk.dm.Lot;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.Tag;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.utility.DateHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 * Version 1.5
 */
@Component
@Transactional(rollbackFor = Exception.class)
@SuppressWarnings("unchecked")
public class InventoryBalanceDetailUtil
{
	@Autowired
	private DataWarehouseDao dataWarehouseDao;

	private static final int IN = 0;
	private static final int OUT = 1;

	public <T> T in(Class<T> dataWarehouse, Inventoriable inventoriable, TransactionItem transactionItem, Transaction transaction, BigDecimal quantity, BigDecimal cogs, String note) throws Exception {
		return doBalanceCogs(dataWarehouse, inventoriable, transactionItem, transaction, quantity, IN, cogs, note);
	}

	public <T> T out(Class<T> dataWarehouse,Inventoriable inventoriable, TransactionItem transactionItem, Transaction transaction, BigDecimal quantity, BigDecimal cogs, String note) throws Exception {
		return doBalanceCogs(dataWarehouse, inventoriable, transactionItem, transaction, quantity, OUT, cogs, note);
	}

	private <T> T doBalanceCogs(Class<T> dataWarehouse, Inventoriable inventoriable, TransactionItem transactionItem, Transaction transaction, 
		BigDecimal quantity, int type, BigDecimal cogs, String note) throws Exception {
		if (inventoriable.getOrganization() == null)
			throw new ServiceException("Organization empty !");

		if (inventoriable.getContainer() == null)
			throw new ServiceException("Container empty !");

		if (inventoriable.getProduct() == null)
			throw new ServiceException("Product empty !");

		if (inventoriable.getDate() == null)
			throw new ServiceException("Date empty !");

		if (quantity == null)
			throw new ServiceException("Quantity empty !");

		Product product = dataWarehouseDao.load(Product.class, inventoriable.getProduct().getId());
		Container container = dataWarehouseDao.load(Container.class, inventoriable.getContainer().getId());
		Grid grid = dataWarehouseDao.load(Grid.class, inventoriable.getGrid().getId());
		Party organization = dataWarehouseDao.load(Party.class, inventoriable.getOrganization().getId());
		Lot lot = inventoriable.getLot();
		Tag tag = inventoriable.getTag();

		if (grid == null)
			throw new ServiceException("Grid empty !");

		DataWarehouseDetail balance = (DataWarehouseDetail) Class.forName(dataWarehouse.getCanonicalName()).getDeclaredConstructor().newInstance();
		balance.setContainerCode(container.getCode());
		balance.setContainerId(container.getId());
		balance.setContainerName(container.getName());
		balance.setDate(inventoriable.getDate());
		balance.setCogs(cogs);
		balance.setFacilityCode(grid.getFacility().getCode());
		balance.setFacilityId(grid.getFacility().getId());
		balance.setFacilityName(grid.getFacility().getName());
		balance.setGridCode(grid.getCode());
		balance.setGridId(grid.getId());
		balance.setGridName(grid.getName());
	
		balance.setMonth(DateHelper.toMonthEnum(inventoriable.getDate()));
		balance.setOrganizationCode(organization.getCode());
		balance.setOrganizationId(organization.getId());
		balance.setOrganizationName(organization.getFullName());
		balance.setProductCategoryCode(product.getProductCategory().getCode());
		balance.setProductCategoryId(product.getProductCategory().getId());
		balance.setProductCategoryName(product.getProductCategory().getName());
		balance.setProductCode(product.getCode());
		balance.setProductId(product.getId());
		balance.setProductName(product.getName());
		balance.setReferenceCode(transaction.getCode());
		balance.setReferenceId(transaction.getId());
		balance.setReferenceType(transaction.getSelf());

		if (transaction.getSelf().equals("Goods Issue"))
			balance.setReferenceUri("goodsissuepreedit.htm");
		else if (transaction.getSelf().equals("Goods Receipt"))
			balance.setReferenceUri("goodsreceiptpreedit.htm");
		else if (transaction.getSelf().equals("Fix COGS"))
			balance.setReferenceUri("fixcogspreedit.htm");
		else if (transaction.getSelf().equals("Stock Adjustment"))
			balance.setReferenceUri("stockadjustmentpreedit.htm");
		else
			balance.setReferenceUri("stockopnamepreedit.htm");

		if(transactionItem != null)
		{
			balance.setWarehouseId(transactionItem.getTransactionId());
			balance.setWarehouseCode(transactionItem.getTransactionCode());
			balance.setWarehouseType(transactionItem.getTransactionSource().getNormalizedName());
			balance.setWarehouseUri(transactionItem.getTransactionSource().getUri() + "preedit.htm");
		}

		balance.setUom(product.getUnitOfMeasure().getMeasureId());
		balance.setYear(DateHelper.toYear(inventoriable.getDate()));
		
		if(lot != null)
		{
			balance.setSerial(lot.getSerial());
			balance.setLotCode(lot.getCode());
			balance.setLotInfo(lot.getInfo());
		}
		
		if(tag != null)
			balance.setTag(tag.getInventoryType().toString());
			
		balance.setNote(note);
		
		switch (type)
		{
		case IN:
			balance.setIn(quantity);
			break;

		case OUT:
			balance.setOut(quantity);
			break;
		}

		dataWarehouseDao.add(balance);

		return (T) balance;
	}
}

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
import com.siriuserp.inventory.dm.DataWarehouse;
import com.siriuserp.inventory.dm.Inventoriable;
import com.siriuserp.inventory.dm.Product;
import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.Grid;
import com.siriuserp.sdk.dm.Party;
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
@SuppressWarnings({ "rawtypes", "unchecked" })
public class InventoryBalanceUtil
{
	@Autowired
	private DataWarehouseDao dataWarehouseDao;

	private static final int IN = 0;
	private static final int OUT = 1;

	public void in(Class dataWarehouse, Inventoriable inventoriable, BigDecimal quantity) throws Exception {
		doBalance(dataWarehouse, inventoriable, quantity, IN);
	}
	
	public void out(Class dataWarehouse, Inventoriable inventoriable, BigDecimal quantity) throws Exception {
		doBalance(dataWarehouse, inventoriable, quantity, OUT);
	}

	private void doBalance(Class dataWarehouse, Inventoriable inventoriable, BigDecimal quantity, int type) throws Exception {
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
		
		DataWarehouse balance = (DataWarehouse) dataWarehouseDao.loadDataWarehouse(dataWarehouse, organization.getId(), container.getId(), 
			product.getId(), DateHelper.toMonthEnum(inventoriable.getDate()), DateHelper.toYear(inventoriable.getDate()));

		if (grid == null)
			throw new ServiceException("Grid empty !");

		if (balance != null)
		{
			switch (type)
			{
			case OUT:
				balance.setOut(balance.getOut().add(quantity));
				break;

			case IN:
				balance.setIn(balance.getIn().add(quantity));
				break;
			}

			dataWarehouseDao.update(balance);
		} 
		else
		{
			balance = (DataWarehouse) Class.forName(dataWarehouse.getCanonicalName()).getDeclaredConstructor().newInstance();
			balance.setContainerCode(container.getCode());
			balance.setContainerId(container.getId());
			balance.setContainerName(container.getName());
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
			balance.setUom(product.getUnitOfMeasure().getMeasureId());
			balance.setYear(DateHelper.toYear(inventoriable.getDate()));
			balance.setDate(DateHelper.toStartDate(inventoriable.getDate()));

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
		}
	}

}

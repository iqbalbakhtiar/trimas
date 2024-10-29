/**
 * 
 */
package com.siriuserp.inventory.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.siriuserp.inventory.dao.DataWarehouseDao;
import com.siriuserp.inventory.dm.StockControlType;
import com.siriuserp.sdk.db.DaoHelper;
import com.siriuserp.sdk.db.OrderType;
import com.siriuserp.sdk.dm.Lot;
import com.siriuserp.sdk.dm.Month;
import com.siriuserp.sdk.dm.Tag;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author
 * Betsu Brahmana Restu
 * PT. Sirius Indonesia
 * betsu@siriuserp.com
 */

@Component
@SuppressWarnings("unchecked")
public class DataWarehouseDaoImpl extends DaoHelper<Object> implements DataWarehouseDao
{
	@Override
	public <T> T get(Class<T> tClass, Long id)
	{
		return (T) getSession().get(tClass, id);
	}

	@Override
	public <T> T load(Class<T> tClass, Long id)
	{
		return (T) getSession().load(tClass, id);
	}

	/**
	 * @param warehouse for implement Inventory
	 * @param product target Product
	 * @param organization target organization
	 * @param container target Container
	 * @return Inventory
	 */
	@Override
	public <T> T loadInventory(Class<T> warehouse, Long product, Long grid, Long container, Lot lot, Tag tag)
	{
		StringBuilder builder = new StringBuilder("FROM " + warehouse.getName() + " inventory WHERE inventory.id IS NOT NULL");
		builder.append(" AND inventory.product.id =:product");
		
		if(SiriusValidator.validateParam(grid))
			builder.append(" AND inventory.grid.id =:grid");
		
		if(SiriusValidator.validateParam(container))
			builder.append(" AND inventory.container.id =:container");
		
		if(lot != null && SiriusValidator.validateParam(lot.getSerial()))
			builder.append(" AND inventory.lot.serial =:serial");
		else
			builder.append(" AND inventory.lot.serial IS NULL");
		
		if(lot != null && SiriusValidator.validateParam(lot.getCode()))
			builder.append(" AND inventory.lot.code =:code");
		else
			builder.append(" AND inventory.lot.code IS NULL");
		
		if(lot != null && SiriusValidator.validateParam(lot.getInfo()))
			builder.append(" AND (inventory.lot.info =:info OR inventory.lot.info IS NULL)");
		
		if(tag != null && tag.getInventoryType() != null)
			builder.append(" AND inventory.tag.inventoryType =:inventoryType ");

		Query criteria = getSession().createQuery(builder.toString());
		criteria.setCacheable(true);
		criteria.setMaxResults(1);
		criteria.setParameter("product", product);
		
		if(SiriusValidator.validateParam(grid))
			criteria.setParameter("grid", grid);
		
		if(SiriusValidator.validateParam(container))
			criteria.setParameter("container", container);

		if(lot != null && SiriusValidator.validateParam(lot.getSerial()))
			criteria.setParameter("serial", lot.getSerial());

		if(lot != null && SiriusValidator.validateParam(lot.getCode()))
			criteria.setParameter("code", lot.getCode());
				
		if(lot != null && SiriusValidator.validateParam(lot.getInfo()))
			criteria.setParameter("info", lot.getInfo());
	
		if(tag != null && tag.getInventoryType() != null)
			criteria.setParameter("inventoryType", tag.getInventoryType());
		
		return (T) criteria.uniqueResult();
	}
	
	/**
	 * @param warehouse for implement Inventory
	 * @param product target Product
	 * @param organization target organization
	 * @param container target Container
	 * @return Inventory
	 */
	@Override
	public <T> List<T> loadInventories(Class<T> warehouse, Long product, Long grid, Long container, Lot lot, Tag tag, OrderType orderType)
	{
		StringBuilder builder = new StringBuilder("FROM " + warehouse.getName() + " inventory WHERE inventory.total > 0");
		builder.append(" AND inventory.product.id =:product");
		
		if(SiriusValidator.validateParam(grid))
			builder.append(" AND inventory.grid.id =:grid");
		
		if(SiriusValidator.validateParam(container))
			builder.append(" AND inventory.container.id =:container");
		
		if(lot != null && SiriusValidator.validateParam(lot.getSerial()))
			builder.append(" AND inventory.lot.serial =:serial");
		else
			builder.append(" AND inventory.lot.serial IS NULL");

		if(lot != null && SiriusValidator.validateParam(lot.getCode()))
			builder.append(" AND inventory.lot.code =:code");
		else
			builder.append(" AND inventory.lot.code IS NULL");
		
		if(lot != null && SiriusValidator.validateParam(lot.getInfo()))
			builder.append(" AND (inventory.lot.info =:info OR inventory.lot.info IS NULL)");
		
		if(tag != null && tag.getInventoryType() != null)
			builder.append(" AND inventory.tag.inventoryType =:type");

		builder.append(" ORDER BY inventory.tag.inventoryType, inventory.tag.inventoryReference ");
		builder.append(orderType);
		
		Query criteria = getSession().createQuery(builder.toString());
		criteria.setCacheable(true);
		criteria.setParameter("product", product);
		
		if(SiriusValidator.validateParam(grid))
			criteria.setParameter("grid", grid);

		if(SiriusValidator.validateParam(container))
			criteria.setParameter("container", container);

		if(lot != null && SiriusValidator.validateParam(lot.getSerial()))
			criteria.setParameter("serial", lot.getSerial());

		if(lot != null && SiriusValidator.validateParam(lot.getCode()))
			criteria.setParameter("code", lot.getCode());
				
		if(lot != null && SiriusValidator.validateParam(lot.getInfo()))
			criteria.setParameter("info", lot.getInfo());
		
		if(tag != null && tag.getInventoryType() != null)
			criteria.setParameter("type", tag.getInventoryType());
		
		return criteria.list();
	}
	
	/**
	 * @param warehouse for implement Inventory
	 * @param product target Product
	 * @param organization target organization
	 * @param container target Container
	 * @return Inventory
	 */
	@Override
	public <T> List<T> loadReserves(Class<T> warehouse, Long product, Long grid, Long container, Lot lot, Tag tag, OrderType orderType)
	{
		StringBuilder builder = new StringBuilder("FROM " + warehouse.getName() + " inventory WHERE inventory.availableSale > 0");
		builder.append(" AND inventory.product.id =:product");
		
		if(SiriusValidator.validateParam(grid))
			builder.append(" AND inventory.grid.id =:grid");
		
		if(SiriusValidator.validateParam(container))
			builder.append(" AND inventory.container.id =:container");
		
		if(lot != null && SiriusValidator.validateParam(lot.getSerial()))
			builder.append(" AND (inventory.lot.serial =:serial OR inventory.lot.serial IS NULL)");
		
		if(lot != null && SiriusValidator.validateParam(lot.getCode()))
			builder.append(" AND (inventory.lot.code =:code OR inventory.lot.code IS NULL)");
		
		if(lot != null && SiriusValidator.validateParam(lot.getInfo()))
			builder.append(" AND (inventory.lot.info =:info OR inventory.lot.info IS NULL)");

		if(tag != null && tag.getInventoryType() != null)
			builder.append(" AND inventory.tag.inventoryType =:type");

		builder.append(" ORDER BY inventory.tag.inventoryType, inventory.tag.inventoryReference ");
		builder.append(orderType);

		Query criteria = getSession().createQuery(builder.toString());
		criteria.setCacheable(true);
		criteria.setParameter("product", product);
		
		if(SiriusValidator.validateParam(grid))
			criteria.setParameter("grid", grid);
		
		if(SiriusValidator.validateParam(container))
			criteria.setParameter("container", container);

		if(lot != null && SiriusValidator.validateParam(lot.getSerial()))
			criteria.setParameter("serial", lot.getSerial());

		if(lot != null && SiriusValidator.validateParam(lot.getCode()))
			criteria.setParameter("code", lot.getCode());
				
		if(lot != null && SiriusValidator.validateParam(lot.getInfo()))
			criteria.setParameter("info", lot.getInfo());

		if(tag != null && tag.getInventoryType() != null)
			criteria.setParameter("type", tag.getInventoryType());

		return criteria.list();
	}

	/**
	 * @param warehouse for implement DataWarehouse
	 * @param product target Product
	 * @param month target Month
	 * @param year target year
	 * @param container target Container
	 * @return DataWarehouse
	 */
	@Override
	public <T> T loadDataWarehouse(Class<T> warehouse, Long organization, Long container, Long product, Month month, Integer year)
	{
		StringBuilder builder = new StringBuilder("FROM " + warehouse.getName() + " balance WHERE balance.id IS NOT NULL");
		builder.append(" AND balance.organizationId =:org");
		builder.append(" AND balance.containerId =:container");
		builder.append(" AND balance.productId =:product");
		builder.append(" AND balance.month =:month");
		builder.append(" AND balance.year =:year");

		Query criteria = getSession().createQuery(builder.toString());
		criteria.setCacheable(true);
		criteria.setMaxResults(1);
		criteria.setParameter("org", organization);
		criteria.setParameter("container", container);
		criteria.setParameter("product", product);
		criteria.setParameter("month", month);
		criteria.setParameter("year", year);

		return (T) criteria.uniqueResult();
	}

	/**
	 * @param warehouse for implement DataWarehouseDetail
	 * @param product target Product
	 * @param dateFrom target date
	 * @param dateTo target date
	 * @param container target Container
	 * @return List<DataWarehouseDetail>
	 */
	@Override
	public <T> List<T> loadDefaultDataWarehouse(Class<T> warehouse, Long container, Long product, Date dateFrom, Date dateTo)
	{
		StringBuilder builder = new StringBuilder("FROM " + warehouse.getName() + " detail WHERE detail.date BETWEEN :dateFrom AND :dateTo");
		builder.append(" AND detail.productId =:productId");
		builder.append(" AND detail.containerId =:containerId");
		builder.append(" ORDER BY detail.date ASC, detail.in DESC");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setParameter("containerId", container);
		query.setParameter("productId", product);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("dateTo", dateTo);

		return query.list();
	}

	/**
	 * @param warehouse for implement ProductTranscation
	 * @param product target Product
	 * @param organization target organization
	 * @param container target Container
	 * @param stockControlType StockControlType(FIFO/LIFO/AVERAGE)
	 * @return List<ProductTranscation> created from FIFO/LIFO process
	 */
	@Override
	public <T> List<T> loadAll(Class<T> warehouse, Long organization, Long container, Long product, Lot lot, Date date, StockControlType controlType)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("FROM " + warehouse.getName() + " trans ");
		builder.append("WHERE trans.quantity > 0 ");
		builder.append("AND trans.product.id =:prod ");
		
		if (SiriusValidator.validateDate(date))
			builder.append("AND trans.date <=:date ");
		
		if (SiriusValidator.validateLongParam(organization))
			builder.append("AND trans.organization.id =:org ");

		if (SiriusValidator.validateLongParam(container))
			builder.append("AND trans.container.id =:container ");
	
		if (lot != null && SiriusValidator.validateParam(lot.getCode()))
			builder.append("AND trans.code =:code ");
		else
			builder.append("AND trans.code IS NULL ");
		
		if (lot != null && SiriusValidator.validateParam(lot.getSerial()))
			builder.append("AND trans.serial =:serial ");
		else
			builder.append("AND trans.serial IS NULL ");

		if (lot != null && SiriusValidator.validateParam(lot.getInfo()))
			builder.append("AND (trans.info =:info OR trans.info IS NULL) ");

		if (controlType.equals(StockControlType.FIFO))
			builder.append("ORDER BY trans.date ASC, trans.id ASC");
		else
			builder.append("ORDER BY trans.id DESC");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setParameter("prod", product);

		if (SiriusValidator.validateDate(date))
			query.setParameter("date", date);
		
		if (SiriusValidator.validateLongParam(organization))
			query.setParameter("org", organization);
		
		if (SiriusValidator.validateLongParam(container))
			query.setParameter("container", container);

		if (lot != null && SiriusValidator.validateParam(lot.getCode()))
			query.setParameter("code", lot.getCode());
			
		if (lot != null && SiriusValidator.validateParam(lot.getSerial()))
			query.setParameter("serial", lot.getSerial());
		
		if (lot != null && SiriusValidator.validateParam(lot.getInfo()))
			query.setParameter("info", lot.getInfo());

		return query.list();
	}

	@Override
	public <T> BigDecimal loadByWarehouse(Class<T> warehouse, Long id, Long container, Long product)
	{
		Query query = getSession().createQuery(
				"SELECT detail.cogs FROM " + warehouse.getName() + " detail WHERE detail.warehouseId =:id AND detail.containerId =:container AND detail.productId =:product ORDER BY detail.id ASC");
		query.setParameter("id", id);
		query.setParameter("container", container);
		query.setParameter("product", product);
		query.setMaxResults(1);
		query.setCacheable(true);

		return (BigDecimal) query.uniqueResult();
	}
	
	@Override
	public <T> T loadSummary(Class<T> warehouse, Long product, Date date, StockControlType controlType)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("FROM " + warehouse.getName() + " trans ");
		builder.append("WHERE trans.quantity > 0 ");
		builder.append("AND trans.product.id =:prod ");
		
		if (SiriusValidator.validateDate(date))
			builder.append("AND (trans.date <=:date OR trans.date IS NULL) ");

		if (controlType.equals(StockControlType.FIFO))
			builder.append("ORDER BY trans.date, trans.id ASC");
		else
			builder.append("ORDER BY trans.id DESC");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setParameter("prod", product);
		query.setMaxResults(1);
		query.setCacheable(true);
		
		if (SiriusValidator.validateDate(date))
			query.setParameter("date", date);

		return (T) query.uniqueResult();
	}
}

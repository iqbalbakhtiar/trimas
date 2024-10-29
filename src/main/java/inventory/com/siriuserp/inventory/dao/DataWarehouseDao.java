/**
 * 
 */
package com.siriuserp.inventory.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.siriuserp.inventory.dm.StockControlType;
import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.db.OrderType;
import com.siriuserp.sdk.dm.Lot;
import com.siriuserp.sdk.dm.Month;
import com.siriuserp.sdk.dm.Tag;


/**
 * @author
 * Betsu Brahmana Restu
 * PT. Sirius Indonesia
 * betsu@siriuserp.com
 */

public interface DataWarehouseDao extends Dao<Object>
{
	public <T> T load(Class<T> tClass, Long id);
	public <T> T get(Class<T> tClass, Long id);
	
	public <T> T loadInventory(Class<T> warehouse, Long product, Long grid, Long container, Lot lot, Tag tag);
	public <T> List<T> loadInventories(Class<T> warehouse, Long product, Long grid, Long container, Lot lot, Tag tag, OrderType orderType);
	public <T> List<T> loadReserves(Class<T> warehouse, Long product, Long grid, Long container, Lot lot, Tag tag, OrderType orderType);
	
	public <T> T loadDataWarehouse(Class<T> warehouse, Long organization, Long container, Long product, Month month, Integer year);
	
	public <T> List<T> loadDefaultDataWarehouse(Class<T> warehouse, Long container, Long product, Date dateFrom, Date dateTo);
	public <T> List<T> loadAll(Class<T> warehouse, Long organization, Long container, Long product,  Lot lot, Date date, StockControlType controlType);
	
	public <T> BigDecimal loadByWarehouse(Class<T> warehouse, Long id, Long container, Long product);
	
	public <T> T loadSummary(Class<T> warehouse, Long product, Date date, StockControlType controlType);
}

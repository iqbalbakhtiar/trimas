/**
 * 
 */
package com.siriuserp.inventory.dao;

import java.math.BigDecimal;
import java.util.List;

import com.siriuserp.inventory.criteria.OnHandQuantityFilterCriteria;
import com.siriuserp.inventory.dm.InventoryItem;
import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.base.Filterable;

/**
 * @author ferdinand
 */

public interface InventoryItemDao extends Dao<InventoryItem>, Filterable 
{
	public BigDecimal getOnHand(OnHandQuantityFilterCriteria criteria);
	public InventoryItem getItemBySerial(String serial, boolean available);
	public List<InventoryItem> getAllItem(Long productId, Long containerId);
}

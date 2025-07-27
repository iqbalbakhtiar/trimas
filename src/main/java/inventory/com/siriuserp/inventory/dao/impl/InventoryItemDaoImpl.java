/**
 * 
 */
package com.siriuserp.inventory.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.siriuserp.inventory.criteria.OnHandQuantityFilterCriteria;
import com.siriuserp.inventory.dao.InventoryItemDao;
import com.siriuserp.inventory.dm.InventoryItem;
import com.siriuserp.sdk.db.DaoHelper;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author ferdinand
 */

@Component
public class InventoryItemDaoImpl extends DaoHelper<InventoryItem> implements InventoryItemDao
{
	@Override
	public BigDecimal getOnHand(OnHandQuantityFilterCriteria criteria)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT SUM(item.onHand) FROM InventoryItem item ");
		builder.append("WHERE item.onHand > 0 ");
		builder.append("AND item.container.id =:container ");
		builder.append("AND item.product.id =:product ");

		if (SiriusValidator.validateParam(criteria.getSerial()))
			builder.append("AND item.lot.serial =:serial ");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setParameter("product", criteria.getProductId());
		query.setParameter("container", criteria.getContainerId());

		if (SiriusValidator.validateParam(criteria.getSerial()))
			query.setParameter("serial", criteria.getSerial());

		Object obj = query.uniqueResult();

		return obj != null ? (BigDecimal) obj : BigDecimal.ZERO;
	}

	@Override
	public InventoryItem getItemBySerial(String serial, boolean available)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("FROM InventoryItem item ");
		builder.append("WHERE item.lot.serial =:serial ");

		if (available)
			builder.append("AND item.onHand > 0 ");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setParameter("serial", serial);

		Object obj = query.uniqueResult();
		if (obj != null)
			return (InventoryItem) obj;

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InventoryItem> getAllItem(Long productId, Long containerId, boolean availableOnly)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("FROM InventoryItem item ");
		builder.append("WHERE item.product.id =:productId ");
		builder.append("AND item.container.id =:containerId ");

		if (availableOnly)
			builder.append("AND item.availableSale > 0 ");

		builder.append("ORDER BY item.lot.serial ASC");

		Query query = getSession().createQuery(builder.toString());

		query.setParameter("productId", productId);
		query.setParameter("containerId", containerId);
		query.setCacheable(true);
		query.setReadOnly(true);

		return query.list();
	}
}

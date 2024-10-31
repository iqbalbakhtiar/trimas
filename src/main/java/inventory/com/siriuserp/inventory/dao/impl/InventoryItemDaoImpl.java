/**
 * 
 */
package com.siriuserp.inventory.dao.impl;

import java.math.BigDecimal;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.siriuserp.inventory.dao.InventoryItemDao;
import com.siriuserp.inventory.dm.InventoryItem;
import com.siriuserp.sdk.db.DaoHelper;

/**
 * @author ferdinand
 */

@Component
public class InventoryItemDaoImpl extends DaoHelper<InventoryItem> implements InventoryItemDao 
{
	@Override
	public BigDecimal getOnHand(Long productId, Long containerId) 
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT SUM(item.onHand) FROM InventoryItem item ");
		builder.append("WHERE item.onHand > 0 ");
		builder.append("AND item.container.id =:container ");
		builder.append("AND item.product.id =:product ");
		builder.append("GROUP BY item.product.id");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setParameter("product", productId);
		query.setParameter("container", containerId);

		Object obj = query.uniqueResult();
		if (obj != null)
			return (BigDecimal) obj;

		return BigDecimal.ZERO;
	}
}

/**
 * 
 */
package com.siriuserp.inventory.query;

import org.hibernate.Query;

import com.siriuserp.inventory.adapter.OnhandQuantityUIAdapter;
import com.siriuserp.inventory.criteria.OnHandQuantityFilterCriteria;
import com.siriuserp.sdk.db.AbstractGridViewQuery;

import javolution.util.FastList;

/**
 * @author ferdinand
 */

@SuppressWarnings("unchecked")
public class OnHandQuantityDetailViewQuery extends AbstractGridViewQuery
{
	@Override
	public Long count()
	{
		return Long.valueOf(0);
	}

	@Override
	public Object execute()
	{
		FastList<OnhandQuantityUIAdapter> list = new FastList<OnhandQuantityUIAdapter>();

		OnHandQuantityFilterCriteria criteria = (OnHandQuantityFilterCriteria) getFilterCriteria();

		StringBuilder builder = new StringBuilder();
		builder.append("SELECT new com.siriuserp.inventory.adapter.OnhandQuantityUIAdapter(inv.container, inv.lot, inv.product, SUM(inv.onHand), SUM(inv.onTransfer), SUM(inv.reserved), inv.organization) ");
		builder.append("FROM InventoryItem inv WHERE inv.id IS NOT NULL AND inv.product.id =:product ");
		builder.append("GROUP BY inv.lot.fromProduct.id, inv.container.id, inv.organization.id HAVING SUM(inv.onHand + inv.onTransfer + inv.reserved) > 0 ");
		builder.append("ORDER BY inv.container.grid.facility.id ASC, inv.container.grid.id ASC, inv.container.id ASC, inv.lot.fromProduct.id");
		
		Query query = getSession().createQuery(builder.toString());
		query.setParameter("product", criteria.getProduct());
		query.setReadOnly(true);

		list.addAll(query.list());

		return list;
	}
}

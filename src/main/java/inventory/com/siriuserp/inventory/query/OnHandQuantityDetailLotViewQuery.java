/**
 * File Name  : OnHandQuantityDetailLotViewQuery.java
 * Created On : May 17, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.inventory.query;

import org.hibernate.Query;

import com.siriuserp.inventory.criteria.OnHandQuantityFilterCriteria;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

public class OnHandQuantityDetailLotViewQuery extends AbstractGridViewQuery
{
	@Override
	public Query getQuery(ExecutorType type)
	{
		OnHandQuantityFilterCriteria criteria = (OnHandQuantityFilterCriteria) getFilterCriteria();
		StringBuilder builder = new StringBuilder();

		if (type.compareTo(ExecutorType.COUNT) == 0)
			builder.append("SELECT COUNT(DISTINCT inv.id) ");

		if (type.compareTo(ExecutorType.HQL) == 0)
			builder.append("SELECT NEW com.siriuserp.inventory.adapter.OnHandQuantityLotAdapter(inv.organization, inv.container, inv.product, inv.lot, SUM(inv.onHand), SUM(inv.onTransfer), SUM(inv.reserved)) ");

		builder.append("FROM InventoryItem inv ");
		builder.append("WHERE inv.product.id =:product ");
		builder.append("AND inv.lot.code =:lotCode ");
		builder.append("GROUP BY inv.lot.serial, inv.lot.code, inv.container.id, inv.organization.id ");
		builder.append("HAVING SUM(inv.onHand + inv.onTransfer + inv.reserved) > 0 ");
		builder.append("ORDER BY inv.container.grid.facility.id ASC, inv.container.grid.id ASC, inv.container.id ASC, inv.lot.code ASC, inv.lot.serial ASC");

		Query query = getSession().createQuery(builder.toString());
		query.setReadOnly(true);
		query.setParameter("product", criteria.getProduct());
		query.setParameter("lotCode", criteria.getLotCode());

		return query;
	}
}

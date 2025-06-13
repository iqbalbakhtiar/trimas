/**
 * 
 */
package com.siriuserp.inventory.query;

import org.hibernate.Query;

import com.siriuserp.inventory.criteria.OnHandQuantityFilterCriteria;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author ferdinand
 */

public class OnHandQuantityGridViewQuery extends AbstractGridViewQuery
{
	@Override
	public Query getQuery(ExecutorType type)
	{
		OnHandQuantityFilterCriteria criteria = (OnHandQuantityFilterCriteria) getFilterCriteria();

		StringBuilder builder = new StringBuilder();

		if (type.compareTo(ExecutorType.COUNT) == 0)
			builder.append("SELECT COUNT(DISTINCT inv.product) ");

		if (type.compareTo(ExecutorType.HQL) == 0)
			builder.append("SELECT NEW com.siriuserp.inventory.adapter.OnhandQuantityUIAdapter(inv.product, inv.lot, SUM(inv.onHand), SUM(inv.onTransfer), SUM(inv.reserved)) ");

		builder.append("FROM InventoryItem inv WHERE inv.total > 0 ");

		if (SiriusValidator.validateLongParam(criteria.getProduct()))
			builder.append("AND inv.product.id =:productId ");

		if (SiriusValidator.validateParam(criteria.getCode()))
			builder.append("AND inv.product.code LIKE :code ");

		if (SiriusValidator.validateParam(criteria.getName()))
			builder.append("AND inv.product.name LIKE :name ");

		if (SiriusValidator.validateParam(criteria.getProductCategory()))
			builder.append("AND inv.product.productCategory.name LIKE :category ");

		if (SiriusValidator.validateParam(criteria.getUom()))
			builder.append("AND inv.product.unitOfMeasure.measureId LIKE :uom ");

		if (SiriusValidator.validateParam(criteria.getLotCode()))
			builder.append("AND inv.lot.code =:lotCode ");

		if (type.compareTo(ExecutorType.HQL) == 0)
			builder.append("GROUP BY inv.product.id, inv.lot.code ORDER BY inv.product.name ASC, inv.lot.code ASC");

		Query query = getSession().createQuery(builder.toString());
		query.setReadOnly(true);
		query.setCacheable(true);

		if (SiriusValidator.validateLongParam(criteria.getProduct()))
			query.setParameter("productId", criteria.getProduct());

		if (SiriusValidator.validateParam(criteria.getCode()))
			query.setParameter("code", "%" + criteria.getCode() + "%");

		if (SiriusValidator.validateParam(criteria.getName()))
			query.setParameter("name", "%" + criteria.getName() + "%");

		if (SiriusValidator.validateParam(criteria.getProductCategory()))
			query.setParameter("category", "%" + criteria.getProductCategory() + "%");

		if (SiriusValidator.validateParam(criteria.getUom()))
			query.setParameter("uom", "%" + criteria.getUom() + "%");

		if (SiriusValidator.validateParam(criteria.getLotCode()))
			query.setParameter("lotCode", criteria.getLotCode());

		return query;
	}
}

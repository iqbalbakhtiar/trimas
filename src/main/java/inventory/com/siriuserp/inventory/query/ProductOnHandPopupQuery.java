/**
 * File Name  : ProductOnHandPopupQuery.java
 * Created On : Oct 3, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.inventory.query;

import org.hibernate.Query;

import com.siriuserp.inventory.criteria.ProductFilterCriteria;
import com.siriuserp.inventory.dm.ProductCategoryType;
import com.siriuserp.inventory.dm.ProductType;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

public class ProductOnHandPopupQuery extends AbstractGridViewQuery
{
	@Override
	public Query getQuery(ExecutorType type)
	{
		ProductFilterCriteria criteria = (ProductFilterCriteria) getFilterCriteria();
		StringBuilder builder = new StringBuilder();

		if (type.compareTo(ExecutorType.COUNT) == 0)
			builder.append("SELECT COUNT(DISTINCT inv.product) ");

		if (type.compareTo(ExecutorType.HQL) == 0)
			builder.append("SELECT NEW com.siriuserp.inventory.adapter.OnhandQuantityUIAdapter(inv.product, inv.grid, inv.container, SUM(inv.onHand), SUM(inv.onTransfer), SUM(inv.reserved)) ");

		builder.append("FROM InventoryItem inv WHERE inv.total > 0 ");

		if (SiriusValidator.validateParam(criteria.getId()))
			builder.append("AND inv.product.id = :productId ");

		if (SiriusValidator.validateParam(criteria.getCode()))
			builder.append("AND inv.product.code LIKE :code ");

		if (SiriusValidator.validateParam(criteria.getName()))
			builder.append("AND inv.product.name LIKE :name ");

		if (SiriusValidator.validateParam(criteria.getUnitOfMeasure()))
			builder.append("AND inv.product.unitOfMeasure.measureId LIKE :uom ");

		if (SiriusValidator.validateParam(criteria.getType()))
			builder.append("AND inv.product.type = :type ");

		if (SiriusValidator.validateParam(criteria.getProductCategory()))
			builder.append("AND inv.product.productCategory.name LIKE :productCategory ");

		if (SiriusValidator.validateParam(criteria.getProductCategoryType()))
			builder.append("AND inv.product.productCategory.type = :productCategoryType ");

		if (SiriusValidator.validateParam(criteria.getStatus()))
			builder.append("AND inv.product.enabled =:status ");

		if (SiriusValidator.validateParam(criteria.getSerial()))
			builder.append("AND inv.product.serial =:serial ");

		if (SiriusValidator.validateParam(criteria.getFacility()))
			builder.append("AND inv.grid.facility.id =:facility ");

		if (SiriusValidator.validateParam(criteria.getGrid()))
			builder.append("AND inv.grid.id =:grid ");

		if (SiriusValidator.validateParam(criteria.getContainer()))
			builder.append("AND inv.container.id =:container ");

		if (criteria.getBase() != null)
			builder.append("AND inv.product.base =:base ");

		if (ExecutorType.HQL.equals(type))
			builder.append("GROUP BY inv.product.id, inv.container.id ORDER BY inv.product.id DESC");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setReadOnly(true);

		if (SiriusValidator.validateParam(criteria.getId()))
			query.setParameter("productId", criteria.getId());

		if (SiriusValidator.validateParam(criteria.getCode()))
			query.setParameter("code", "%" + criteria.getCode() + "%");

		if (SiriusValidator.validateParam(criteria.getName()))
			query.setParameter("name", "%" + criteria.getName() + "%");

		if (SiriusValidator.validateParam(criteria.getUnitOfMeasure()))
			query.setParameter("uom", "%" + criteria.getUnitOfMeasure() + "%");

		if (SiriusValidator.validateParam(criteria.getType()))
			query.setParameter("type", ProductType.valueOf(criteria.getType()));

		if (SiriusValidator.validateParam(criteria.getProductCategory()))
			query.setParameter("productCategory", "%" + criteria.getProductCategory() + "%");

		if (SiriusValidator.validateParam(criteria.getProductCategoryType()))
			query.setParameter("productCategoryType", ProductCategoryType.valueOf(criteria.getProductCategoryType()));

		if (SiriusValidator.validateParam(criteria.getStatus()))
			query.setParameter("status", criteria.getStatus());

		if (SiriusValidator.validateParam(criteria.getSerial()))
			query.setParameter("serial", criteria.getSerial());

		if (SiriusValidator.validateParam(criteria.getFacility()))
			query.setParameter("facility", criteria.getFacility());

		if (SiriusValidator.validateParam(criteria.getGrid()))
			query.setParameter("grid", criteria.getGrid());

		if (SiriusValidator.validateParam(criteria.getContainer()))
			query.setParameter("container", criteria.getContainer());

		if (criteria.getBase() != null)
			query.setParameter("base", criteria.getBase());

		return query;
	}
}

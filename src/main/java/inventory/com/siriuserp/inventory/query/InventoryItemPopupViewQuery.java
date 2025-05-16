/**
 * File Name  : InventoryItemPopupViewQuery.java
 * Created On : Mar 24, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.inventory.query;

import org.hibernate.Query;

import com.siriuserp.inventory.criteria.ProductFilterCriteria;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

public class InventoryItemPopupViewQuery extends AbstractGridViewQuery
{
	@Override
	public Query getQuery(ExecutorType type)
	{
		ProductFilterCriteria criteria = (ProductFilterCriteria) getFilterCriteria();
		StringBuilder builder = new StringBuilder();

		if (type.compareTo(ExecutorType.COUNT) == 0)
			builder.append("SELECT COUNT(inv) ");

		builder.append("FROM InventoryItem inv WHERE inv.product.enabled =:enabled ");

		if (criteria.isOnHand())
			builder.append("AND inv.onHand > 0 ");

		if (criteria.isAvailableSales())
			builder.append("AND inv.availableSale > 0 ");

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()))
			builder.append("AND inv.organization.id =:org ");

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getProductId()))
			builder.append("AND inv.product.id =:productId ");

		if (SiriusValidator.validateParam(criteria.getCode()))
			builder.append("AND inv.product.code LIKE '%" + criteria.getCode() + "%' ");

		if (SiriusValidator.validateParam(criteria.getName()))
			builder.append("AND inv.product.name LIKE '%" + criteria.getName() + "%' ");

		if (SiriusValidator.validateParam(criteria.getCategoryName()))
			builder.append("AND inv.product.productCategory.name LIKE '%" + criteria.getCategoryName() + "%' ");

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getContainer()))
			builder.append("AND inv.container.id =:container ");

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getGrid()))
			builder.append("AND inv.container.grid.id =:grid ");

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getFacility()))
			builder.append("AND inv.container.grid.facility.id =:facility ");

		if (SiriusValidator.validateParam(criteria.getSerialNo()))
			builder.append("AND inv.lot.serial LIKE :serial ");

		if (SiriusValidator.validateParam(criteria.getLotCode()))
			builder.append("AND inv.lot.code LIKE :lotCode ");

		if (SiriusValidator.validateParam(criteria.getBarcodes()))
			builder.append("AND inv.lot.serial NOT IN(:serials) ");

		if (type.compareTo(ExecutorType.HQL) == 0)
			builder.append("ORDER BY inv.lot.code, inv.lot.serial, inv.product.name ");

		Query query = getSession().createQuery(builder.toString());
		query.setParameter("enabled", Boolean.TRUE);

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()))
			query.setParameter("org", criteria.getOrganization());

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getProductId()))
			query.setParameter("productId", criteria.getProductId());

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getGrid()))
			query.setParameter("grid", criteria.getGrid());

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getFacility()))
			query.setParameter("facility", criteria.getFacility());

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getContainer()))
			query.setParameter("container", criteria.getContainer());

		if (SiriusValidator.validateParam(criteria.getSerialNo()))
			query.setParameter("serial", "%" + criteria.getSerialNo() + "%");

		if (SiriusValidator.validateParam(criteria.getLotCode()))
			query.setParameter("lotCode", "%" + criteria.getLotCode() + "%");

		if (SiriusValidator.validateParam(criteria.getBarcodes()))
			query.setParameterList("serials", criteria.getBarcodes());

		return query;
	}
}

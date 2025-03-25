package com.siriuserp.inventory.query;

import org.hibernate.Query;

import com.siriuserp.inventory.criteria.ProductFilterCriteria;
import com.siriuserp.inventory.dm.ProductCategoryType;
import com.siriuserp.inventory.dm.ProductType;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public class ProductGridViewQuery extends AbstractGridViewQuery
{
	@Override
	public Query getQuery(ExecutorType type)
	{
		ProductFilterCriteria criteria = (ProductFilterCriteria) getFilterCriteria();
		StringBuilder builder = new StringBuilder();

		if (type.compareTo(ExecutorType.COUNT) == 0)
			builder.append("SELECT COUNT (pro.id)  ");

		builder.append("FROM Product pro WHERE pro.id IS NOT NULL ");

		if (SiriusValidator.validateParam(criteria.getCode()))
			builder.append("AND pro.code LIKE :code ");

		if (SiriusValidator.validateParam(criteria.getName()))
			builder.append("AND pro.name LIKE :name ");

		if (SiriusValidator.validateParam(criteria.getUnitOfMeasure()))
			builder.append("AND pro.unitOfMeasure.measureId LIKE :uom ");

		if (SiriusValidator.validateParam(criteria.getType()))
			builder.append("AND pro.type = :type ");

		if (SiriusValidator.validateParam(criteria.getCategoryName()))
			builder.append("AND pro.productCategory.name LIKE :productCategory ");

		if (SiriusValidator.validateParam(criteria.getProductCategoryType()))
			builder.append("AND pro.productCategory.type = :productCategoryType ");

		if (SiriusValidator.validateParam(criteria.getStatus()))
			builder.append("AND pro.enabled =:status ");

		if (SiriusValidator.validateParam(criteria.getSerial()))
			builder.append("AND pro.serial =:serial ");

		if (ExecutorType.HQL.equals(type))
			builder.append("ORDER BY pro.id DESC");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setReadOnly(true);

		if (SiriusValidator.validateParam(criteria.getCode()))
			query.setParameter("code", "%" + criteria.getCode() + "%");

		if (SiriusValidator.validateParam(criteria.getName()))
			query.setParameter("name", "%" + criteria.getName() + "%");

		if (SiriusValidator.validateParam(criteria.getUnitOfMeasure()))
			query.setParameter("uom", "%" + criteria.getUnitOfMeasure() + "%");

		if (SiriusValidator.validateParam(criteria.getType()))
			query.setParameter("type", ProductType.valueOf(criteria.getType()));

		if (SiriusValidator.validateParam(criteria.getCategoryName()))
			query.setParameter("productCategory", "%" + criteria.getCategoryName() + "%");

		if (SiriusValidator.validateParam(criteria.getProductCategoryType()))
			query.setParameter("productCategoryType", ProductCategoryType.valueOf(criteria.getProductCategoryType()));

		if (SiriusValidator.validateParam(criteria.getStatus()))
			query.setParameter("status", criteria.getStatus());

		if (SiriusValidator.validateParam(criteria.getSerial()))
			query.setParameter("serial", criteria.getSerial());

		return query;
	}
}

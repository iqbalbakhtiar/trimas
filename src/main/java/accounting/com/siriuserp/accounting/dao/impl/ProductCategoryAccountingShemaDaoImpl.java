/**
 * Mar 31, 2009 2:54:52 PM
 * com.siriuserp.accounting.dao.impl
 * ProductCategoryAccountingShemaDaoImpl.java
 */
package com.siriuserp.accounting.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.siriuserp.accounting.dao.ProductCategoryAccountingSchemaDao;
import com.siriuserp.accounting.dm.ProductCategoryAccountingSchema;
import com.siriuserp.sdk.db.DaoHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
public class ProductCategoryAccountingShemaDaoImpl extends DaoHelper<ProductCategoryAccountingSchema> implements ProductCategoryAccountingSchemaDao
{
	@Override
	public ProductCategoryAccountingSchema load(Long schema, Long category)
	{
		Criteria criteria = getSession().createCriteria(ProductCategoryAccountingSchema.class);
		criteria.createCriteria("accountingSchema").add(Restrictions.eq("id", schema));
		criteria.createCriteria("category").add(Restrictions.eq("id", category));

		Object object = criteria.uniqueResult();
		if (object != null)
			return (ProductCategoryAccountingSchema) object;

		return null;
	}
}

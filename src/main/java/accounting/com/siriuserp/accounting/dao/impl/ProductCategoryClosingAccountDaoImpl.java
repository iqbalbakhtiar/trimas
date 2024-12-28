/**
 * Mar 31, 2009 4:28:53 PM
 * com.siriuserp.accounting.dao.impl
 * ProductCategoryClosingAccountDaoImpl.java
 */
package com.siriuserp.accounting.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.siriuserp.accounting.dao.ProductCategoryClosingAccountDao;
import com.siriuserp.accounting.dm.ProductCategoryClosingAccount;
import com.siriuserp.sdk.db.DaoHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
public class ProductCategoryClosingAccountDaoImpl extends DaoHelper<ProductCategoryClosingAccount> implements ProductCategoryClosingAccountDao
{
	@Override
	public ProductCategoryClosingAccount loadBySchemaAndAccountType(Long schema, Long type)
	{
		Criteria criteria = getSession().createCriteria(ProductCategoryClosingAccount.class);
		criteria.createCriteria("schema").add(Restrictions.eq("id", schema));
		criteria.createCriteria("closingAccount").createCriteria("closingAccountType").add(Restrictions.eq("id", type));

		Object object = criteria.uniqueResult();
		if (object != null)
			return (ProductCategoryClosingAccount) object;

		return null;
	}

	@Override
	public ProductCategoryClosingAccount loadByOrganizationAndType(Long org, Long type, Long prodCategory)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT pcca FROM ProductCategoryClosingAccount pcca ");
		builder.append("WHERE pcca.schema.accountingSchema.organization.id = :org ");
		builder.append("AND pcca.closingAccount.closingAccountType.id = :type ");
		builder.append("AND pcca.schema.category.id = :category ");

		Query query = getSession().createQuery(builder.toString());
		query.setParameter("org", org);
		query.setParameter("type", type);
		query.setParameter("category", prodCategory);

		Object object = query.uniqueResult();
		if (object != null)
			return (ProductCategoryClosingAccount) object;

		return null;
	}
}

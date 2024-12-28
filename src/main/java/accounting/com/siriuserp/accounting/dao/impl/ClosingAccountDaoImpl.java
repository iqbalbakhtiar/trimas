/**
 * Nov 1, 2007 3:19:34 PM
 * net.konsep.sirius.accounting.dao.hibernate
 * ClosingAccountDaoImpl.java
 */
package com.siriuserp.accounting.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.siriuserp.accounting.dao.ClosingAccountDao;
import com.siriuserp.accounting.dm.ClosingAccount;
import com.siriuserp.sdk.db.DaoHelper;
import com.siriuserp.sdk.exceptions.DataDeletionException;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
public class ClosingAccountDaoImpl extends DaoHelper<ClosingAccount> implements ClosingAccountDao
{
	@Override
	public ClosingAccount loadByParentAndType(Long parent, Long type)
	{
		Query query = getSession().createQuery("FROM ClosingAccount ca WHERE ca.accountingSchema.id =:parent AND ca.closingAccountType.id =:type ");
		query.setParameter("parent", parent);
		query.setParameter("type", type);

		Object object = query.uniqueResult();
		if (object != null)
			return (ClosingAccount) object;

		return null;
	}

	@Override
	public ClosingAccount loadByOrganizationAndType(Long org, Long type)
	{
		Query query = getSession().createQuery("FROM ClosingAccount ca WHERE ca.accountingSchema.organization.id =:org AND ca.closingAccountType.id =:type ");
		query.setParameter("org", org);
		query.setParameter("type", type);

		Object object = query.uniqueResult();
		if (object != null)
			return (ClosingAccount) object;

		return null;
	}

	@Override
	public void deleteByType(Long id) throws DataDeletionException
	{
		Query query = getSession().createQuery("DELETE FROM ClosingAccount close WHERE close.closingAccountType.id =:close");
		query.setParameter("close", id);

		query.executeUpdate();
	}

	@Override
	public ClosingAccount load(Long accountingSchema, Long productCategory, Long type)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT category.closingAccount FROM ProductCategoryClosingAccount category");
		builder.append(" WHERE category.schema.accountingSchema.id =:schema");
		builder.append(" AND category.closingAccount.closingAccountType.id =:type");
		builder.append(" AND category.schema.category.id =:category");

		Query query = getSession().createQuery(builder.toString());
		query.setParameter("schema", accountingSchema);
		query.setParameter("type", type);
		query.setParameter("category", productCategory);

		Object object = query.uniqueResult();
		if (object != null)
			return (ClosingAccount) object;

		return null;
	}

	@Override
	public ClosingAccount load(Long fixedGroup, Long type)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT fixed.closingAccount FROM FixedAssetClosingInformation fixed");
		builder.append(" WHERE fixed.fixedAssetGroup.id =:fixedGroup");
		builder.append(" AND fixed.closingAccount.closingAccountType.id =:type");

		Query query = getSession().createQuery(builder.toString());
		query.setParameter("fixedGroup", fixedGroup);
		query.setParameter("type", type);

		Object object = query.uniqueResult();
		if (object != null)
			return (ClosingAccount) object;

		return null;
	}
}

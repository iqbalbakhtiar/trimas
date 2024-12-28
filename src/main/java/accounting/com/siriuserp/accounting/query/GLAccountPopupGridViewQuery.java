/**
 * Feb 13, 2009 2:31:33 PM
 * com.siriuserp.accounting.query
 * GLAccountPopupGridViewQuery.java
 */
package com.siriuserp.accounting.query;

import org.hibernate.Query;

import com.siriuserp.accounting.criteria.GLAccountingFilterCriteria;
import com.siriuserp.accounting.dm.GLCashType;
import com.siriuserp.accounting.dm.GLLevel;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public class GLAccountPopupGridViewQuery extends AbstractGridViewQuery
{
	@Override
	public Long count()
	{
		GLAccountingFilterCriteria filter = (GLAccountingFilterCriteria) getFilterCriteria();

		StringBuilder builder = new StringBuilder();
		builder.append("SELECT COUNT(account.id) FROM GLAccount account WHERE account.level =:level");

		if (SiriusValidator.validateParamWithZeroPosibility(filter.getCoa()))
			builder.append(" AND account.coa.id =:coa");

		if (SiriusValidator.validateParam(filter.getName()))
			builder.append(" AND (account.code like '%" + filter.getName() + "%' OR account.name like '%" + filter.getName() + "%')");

		if (SiriusValidator.validateParam(filter.getCashType()))
			builder.append(" AND account.cashType =:cashType");

		Query query = getSession().createQuery(builder.toString());
		query.setParameter("level", GLLevel.ACCOUNT);

		if (SiriusValidator.validateParamWithZeroPosibility(filter.getCoa()))
			query.setParameter("coa", filter.getCoa());

		if (SiriusValidator.validateParam(filter.getCashType()))
			query.setParameter("cashType", GLCashType.valueOf(filter.getCashType()));

		Object object = query.uniqueResult();
		if (object != null)
			return (Long) object;

		return Long.valueOf(0);
	}

	@Override
	public Object execute()
	{
		GLAccountingFilterCriteria filter = (GLAccountingFilterCriteria) getFilterCriteria();

		StringBuilder builder = new StringBuilder();
		builder.append("FROM GLAccount account WHERE account.level =:level");

		if (SiriusValidator.validateParamWithZeroPosibility(filter.getCoa()))
			builder.append(" AND account.coa.id =:coa");

		if (SiriusValidator.validateParam(filter.getName()))
			builder.append(" AND (account.code like '%" + filter.getName() + "%' OR account.name like '%" + filter.getName() + "%')");

		if (SiriusValidator.validateParam(filter.getCashType()))
			builder.append(" AND account.cashType =:cashType");

		builder.append(" ORDER BY account.code ASC");

		Query query = getSession().createQuery(builder.toString());
		query.setParameter("level", GLLevel.ACCOUNT);
		query.setFirstResult(filter.start());
		query.setMaxResults(filter.getMax());

		if (SiriusValidator.validateParamWithZeroPosibility(filter.getCoa()))
			query.setParameter("coa", filter.getCoa());

		if (SiriusValidator.validateParam(filter.getCashType()))
			query.setParameter("cashType", GLCashType.valueOf(filter.getCashType()));

		return query.list();
	}
}

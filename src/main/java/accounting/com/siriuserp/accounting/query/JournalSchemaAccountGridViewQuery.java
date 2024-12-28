/**
 * Aug 24, 2009 10:36:15 AM
 * com.siriuserp.accounting.query
 * JournalSchemaAccountGridViewQuery.java
 */
package com.siriuserp.accounting.query;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.siriuserp.accounting.criteria.JournalSchemaAccountFilterCriteria;
import com.siriuserp.accounting.dm.GLPostingType;
import com.siriuserp.accounting.dm.JournalSchemaAccount;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public class JournalSchemaAccountGridViewQuery extends AbstractGridViewQuery
{
	@Override
	public Long count()
	{
		JournalSchemaAccountFilterCriteria filter = (JournalSchemaAccountFilterCriteria) filterCriteria;

		StringBuilder builder = new StringBuilder();
		builder.append("SELECT COUNT(account) FROM JournalSchemaAccount account ");
		builder.append("WHERE account.id IS NOT NULL ");

		if (SiriusValidator.validateParamWithZeroPosibility(filter.getSchema()))
			builder.append("AND account.journalSchema.id = " + filter.getSchema());

		if (SiriusValidator.validateParam(filter.getName()))
		{
			builder.append("AND account.account.code like '%" + filter.getName() + "%' ");
			builder.append("OR account.account.name like '%" + filter.getName() + "%' ");
		}

		if (SiriusValidator.validateParam(filter.getTypeValue()))
			builder.append("AND account.postingType =:postingType ");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setReadOnly(true);

		if (SiriusValidator.validateParam(filter.getTypeValue()))
			query.setParameter("postingType", GLPostingType.valueOf(filter.getTypeValue()));

		Object object = query.uniqueResult();
		if (object != null)
			return (Long) object;

		return Long.valueOf(0);
	}

	@Override
	public Object execute()
	{
		JournalSchemaAccountFilterCriteria filter = (JournalSchemaAccountFilterCriteria) filterCriteria;

		Criteria criteria = getSession().createCriteria(JournalSchemaAccount.class);

		Criteria account = criteria.createCriteria("account");
		account.setCacheable(true);
		account.addOrder(Order.asc("code"));

		if (SiriusValidator.validateParamWithZeroPosibility(filter.getSchema()))
			criteria.createCriteria("journalSchema").add(Restrictions.eq("id", filter.getSchema()));

		if (SiriusValidator.validateParam(filter.getName()))
			account.add(Restrictions.or(Restrictions.like("code", filter.getName(), MatchMode.ANYWHERE), Restrictions.like("name", filter.getName(), MatchMode.ANYWHERE)));

		if (SiriusValidator.validateParam(filter.getTypeValue()))
			criteria.add(Restrictions.eq("postingType", GLPostingType.valueOf(filter.getTypeValue())));

		criteria.setFirstResult(filter.start());
		criteria.setMaxResults(filter.getMax());

		return criteria.list();
	}
}

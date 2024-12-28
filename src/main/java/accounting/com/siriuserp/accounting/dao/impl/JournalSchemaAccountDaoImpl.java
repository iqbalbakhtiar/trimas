/**
 * Nov 13, 2008 4:31:13 PM
 * com.siriuserp.sdk.dao.impl
 * JournalSchemaAccountDaoImpl.java
 */
package com.siriuserp.accounting.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.siriuserp.accounting.dao.JournalSchemaAccountDao;
import com.siriuserp.accounting.dm.GLPostingType;
import com.siriuserp.accounting.dm.JournalSchemaAccount;
import com.siriuserp.sdk.db.DaoHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
public class JournalSchemaAccountDaoImpl extends DaoHelper<JournalSchemaAccount> implements JournalSchemaAccountDao
{
	@Override
	public JournalSchemaAccount load(Long schema, Long account, GLPostingType postingType)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("FROM JournalSchemaAccount account ");
		builder.append("WHERE account.journalSchema.id =:schema ");
		builder.append("AND account.account.id =:acnt ");
		builder.append("AND account.postingType =:postingType ");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setReadOnly(true);
		query.setParameter("schema", schema);
		query.setParameter("acnt", account);
		query.setParameter("postingType", postingType);

		Object object = query.uniqueResult();
		if (object != null)
			return (JournalSchemaAccount) object;

		return null;
	}

	@Override
	public JournalSchemaAccount load(Long schema, String code)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT DISTINCT account FROM JournalSchemaAccount account ");
		builder.append("WHERE account.journalSchema.id =:schema ");
		builder.append("AND account.account.code =:code ");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setReadOnly(true);
		query.setParameter("schema", schema);
		query.setParameter("code", code);
		query.setMaxResults(1);

		Object object = query.uniqueResult();
		if (object != null)
			return (JournalSchemaAccount) object;

		return null;
	}
}

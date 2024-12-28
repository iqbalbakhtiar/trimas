package com.siriuserp.accounting.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.siriuserp.accounting.dao.AccountingSchemaDao;
import com.siriuserp.accounting.dm.AccountingSchema;
import com.siriuserp.accounting.dm.ClosingDistribution;
import com.siriuserp.sdk.db.DaoHelper;
import com.siriuserp.sdk.dm.Party;

@Component
public class AccountingSchemaDaoImpl extends DaoHelper<AccountingSchema> implements AccountingSchemaDao
{
	@SuppressWarnings("unchecked")
	public List<ClosingDistribution> loadAllDistribution(Party organization)
	{
		Query query = getSession().createQuery("SELECT schema.closingDistribution FROM AccountingSchema schema WHERE schema.organization.id =:org AND schema.closingDistribution is not null");
		query.setParameter("org", organization.getId());
		return query.list();
	}

	@Override
	public AccountingSchema loadDistributed(Party organization)
	{
		Criteria criteria = getSession().createCriteria(AccountingSchema.class);
		criteria.createCriteria("organization").add(Restrictions.eq("id", organization.getId()));
		criteria.add(Restrictions.isNotNull("closingDistribution"));

		Object object = criteria.uniqueResult();
		if (object != null)
			return (AccountingSchema) object;

		return null;
	}

	@Override
	public AccountingSchema load(Party organization)
	{
		Query query = getSession().createQuery("FROM AccountingSchema schema WHERE schema.organization.id =:org");
		query.setParameter("org", organization.getId());
		query.setMaxResults(1);

		Object object = query.uniqueResult();
		if (object != null)
			return (AccountingSchema) object;

		return null;
	}

	@Override
	public Object loadCode(String code)
	{
		Query query = getSession().createQuery("SELECT schema.code FROM AccountingSchema schema WHERE schema.code =:code");
		query.setParameter("code", code);

		return query.uniqueResult();
	}
}

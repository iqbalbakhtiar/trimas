package com.siriuserp.administration.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.siriuserp.sdk.dao.CurrencyDao;
import com.siriuserp.sdk.db.DaoHelper;
import com.siriuserp.sdk.dm.Currency;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Component
public class CurrencyDaoImpl extends DaoHelper<Currency> implements CurrencyDao
{
	public Currency loadDefaultCurrency()
	{
		Criteria criteria = getSession().createCriteria(Currency.class);
		criteria.add(Restrictions.eq("base", Boolean.TRUE));
		return (Currency) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Currency> loadNonDefault()
	{
		Criteria criteria = getSession().createCriteria(Currency.class);
		criteria.add(Restrictions.eq("base", Boolean.FALSE));

		return criteria.list();
	}

	@Override
	public Currency loadIfNot(Long id)
	{
		Criteria criteria = getSession().createCriteria(Currency.class);
		criteria.add(Restrictions.eq("base", Boolean.TRUE));
		criteria.add(Restrictions.ne("id", id));

		Object object = criteria.uniqueResult();
		if (object != null)
			return (Currency) object;

		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Currency> loadAll()
	{
		return getSession().createQuery("FROM Currency").list();
	}
}

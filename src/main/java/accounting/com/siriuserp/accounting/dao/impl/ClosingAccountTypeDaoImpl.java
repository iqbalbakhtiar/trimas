/**
 * Sep 25, 2007 3:42:25 PM
 * net.konsep.sirius.accounting.dao.hibernate
 * ClosingAccountTypeDaoImpl.java
 */
package com.siriuserp.accounting.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.siriuserp.accounting.dao.ClosingAccountTypeDao;
import com.siriuserp.accounting.dm.ClosingAccountType;
import com.siriuserp.accounting.dm.GroupType;
import com.siriuserp.sdk.db.DaoHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
public class ClosingAccountTypeDaoImpl extends DaoHelper<ClosingAccountType> implements ClosingAccountTypeDao
{
	@SuppressWarnings("unchecked")
	public List<ClosingAccountType> loadAllAsset()
	{
		Criteria criteria = getSession().createCriteria(ClosingAccountType.class);
		criteria.add(Restrictions.eq("groupType", GroupType.ASSET));

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<ClosingAccountType> loadAllNonAsset()
	{
		Criteria criteria = getSession().createCriteria(ClosingAccountType.class);
		criteria.add(Restrictions.or(Restrictions.eq("groupType", GroupType.TRANSPORT_OUTSOURCE), Restrictions.or(Restrictions.eq("groupType", GroupType.NONASSET), Restrictions.or(Restrictions.eq("groupType", GroupType.RECEIVABLES),
				Restrictions.or(Restrictions.eq("groupType", GroupType.PAYABLES), Restrictions.or(Restrictions.eq("groupType", GroupType.INVENTORY), Restrictions.eq("groupType", GroupType.PROCUREMENT)))))));

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<ClosingAccountType> loadAll(GroupType groupType)
	{
		Criteria criteria = getSession().createCriteria(ClosingAccountType.class);
		criteria.add(Restrictions.eq("groupType", groupType));

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<ClosingAccountType> loadAllReceivables()
	{
		Criteria criteria = getSession().createCriteria(ClosingAccountType.class);
		criteria.add(Restrictions.eq("groupType", GroupType.RECEIVABLES));

		return criteria.list();
	}
}

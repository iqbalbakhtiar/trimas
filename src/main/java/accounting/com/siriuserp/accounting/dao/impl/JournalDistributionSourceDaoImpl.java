/**
 * Nov 14, 2008 3:21:46 PM
 * com.siriuserp.sdk.dao.impl
 * JournalDistributionSourceDaoImpl.java
 */
package com.siriuserp.accounting.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.siriuserp.accounting.dao.JournalDistributionSourceDao;
import com.siriuserp.accounting.dm.JournalDistributionSource;
import com.siriuserp.sdk.db.DaoHelper;
import com.siriuserp.sdk.dm.Party;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
public class JournalDistributionSourceDaoImpl extends DaoHelper<JournalDistributionSource> implements JournalDistributionSourceDao
{
	@Override
	public JournalDistributionSource load(Party organization)
	{
		Criteria criteria = getSession().createCriteria(JournalDistributionSource.class);
		criteria.setCacheable(true);
		criteria.createCriteria("organization").add(Restrictions.eq("id", organization.getId()));

		Object object = criteria.uniqueResult();
		if (object != null)
			return (JournalDistributionSource) object;

		return null;
	}
}

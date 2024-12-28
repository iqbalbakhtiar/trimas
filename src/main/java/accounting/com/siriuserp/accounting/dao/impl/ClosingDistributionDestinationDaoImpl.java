/**
 * Nov 24, 2008 11:45:17 AM
 * com.siriuserp.sdk.dao.impl
 * ClosingDistributionDestinationDaoImpl.java
 */
package com.siriuserp.accounting.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.siriuserp.accounting.dao.ClosingDistributionDestinationDao;
import com.siriuserp.accounting.dm.ClosingDistributionDestination;
import com.siriuserp.sdk.db.DaoHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
public class ClosingDistributionDestinationDaoImpl extends DaoHelper<ClosingDistributionDestination> implements ClosingDistributionDestinationDao
{
	@Override
	public ClosingDistributionDestination loadByParentAndOrg(Long parent, Long org)
	{
		Criteria criteria = getSession().createCriteria(ClosingDistributionDestination.class);
		criteria.setCacheable(true);
		criteria.createCriteria("closingDistribution").add(Restrictions.eq("id", parent));
		criteria.createCriteria("organization").add(Restrictions.eq("id", org));

		Object object = criteria.uniqueResult();
		if (object != null)
			return (ClosingDistributionDestination) object;

		return null;
	}

	@SuppressWarnings("unchecked")
	public List<ClosingDistributionDestination> loadByOrg(Long org)
	{
		Criteria criteria = getSession().createCriteria(ClosingDistributionDestination.class);
		criteria.setCacheable(true);
		criteria.createCriteria("organization").add(Restrictions.eq("id", org));

		return criteria.list();
	}
}

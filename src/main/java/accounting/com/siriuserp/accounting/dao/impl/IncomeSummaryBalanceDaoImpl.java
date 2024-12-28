/**
 * Dec 1, 2008 11:53:56 AM
 * com.siriuserp.sdk.dao.impl
 * IncomeSummaryBalanceDaoImpl.java
 */
package com.siriuserp.accounting.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.siriuserp.accounting.dao.IncomeSummaryBalanceDao;
import com.siriuserp.accounting.dm.AccountingPeriod;
import com.siriuserp.accounting.dm.IncomeSummaryBalance;
import com.siriuserp.sdk.db.DaoHelper;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.exceptions.DataDeletionException;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
public class IncomeSummaryBalanceDaoImpl extends DaoHelper<IncomeSummaryBalance> implements IncomeSummaryBalanceDao
{
	public IncomeSummaryBalance load(Long accountingPeriod, Long organization)
	{
		Criteria criteria = getSession().createCriteria(IncomeSummaryBalance.class);
		criteria.createCriteria("accountingPeriod").add(Restrictions.eq("id", accountingPeriod));
		criteria.createCriteria("organization").add(Restrictions.eq("id", organization));

		Object object = criteria.uniqueResult();
		if (object != null)
			return (IncomeSummaryBalance) object;

		return null;
	}

	public void delete(AccountingPeriod accountingPeriod, Party organization) throws DataDeletionException
	{
		Query summarybalance = getSession().createQuery("DELETE FROM IncomeSummaryBalance balance WHERE balance.organization.id =:org AND balance.accountingPeriod.id =:period");
		summarybalance.setParameter("org", organization.getId());
		summarybalance.setParameter("period", accountingPeriod.getId());

		summarybalance.executeUpdate();
	}
}

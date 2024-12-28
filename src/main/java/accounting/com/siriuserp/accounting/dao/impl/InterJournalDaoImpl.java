/**
 * Nov 26, 2008 9:47:25 AM
 * com.siriuserp.sdk.dao.impl
 * InterJournalDaoImpl.java
 */
package com.siriuserp.accounting.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.siriuserp.accounting.dao.InterJournalDao;
import com.siriuserp.accounting.dm.InterJournal;
import com.siriuserp.sdk.db.DaoHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
public class InterJournalDaoImpl extends DaoHelper<InterJournal> implements InterJournalDao
{
	@Override
	public Long loadUnfinishInterJournal(Long period)
	{
		Query query = getSession().createQuery("SELECT COUNT(inter) FROM InterJournal inter WHERE inter.completed =:completed AND inter.entryTo.accountingPeriod.id =:period");
		query.setParameter("completed", Boolean.TRUE);
		query.setParameter("period", period);

		Object object = query.uniqueResult();
		if (object != null)
			return (Long) object;

		return Long.valueOf(0);
	}
}

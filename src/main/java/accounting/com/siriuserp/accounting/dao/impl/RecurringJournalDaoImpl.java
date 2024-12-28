/**
 * Nov 25, 2008 10:18:46 AM
 * com.siriuserp.sdk.dao.impl
 * RecurringJournalDaoImpl.java
 */
package com.siriuserp.accounting.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.siriuserp.accounting.dao.RecurringJournalDao;
import com.siriuserp.accounting.dm.RecurringJournal;
import com.siriuserp.sdk.db.DaoHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
public class RecurringJournalDaoImpl extends DaoHelper<RecurringJournal> implements RecurringJournalDao
{
	@SuppressWarnings("unchecked")
	public List<RecurringJournal> loadToday(Integer day)
	{
		Criteria criteria = getSession().createCriteria(RecurringJournal.class);
		criteria.add(Restrictions.eq("day", day));
		criteria.add(Restrictions.eq("enabled", Boolean.TRUE));
		return criteria.list();
	}
}

/**
 * Nov 25, 2008 10:18:06 AM
 * com.siriuserp.sdk.dao
 * RecurringJournalDao.java
 */
package com.siriuserp.accounting.dao;

import java.util.List;

import com.siriuserp.accounting.dm.RecurringJournal;
import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.base.Filterable;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public interface RecurringJournalDao extends Dao<RecurringJournal>, Filterable
{
	public List<RecurringJournal> loadToday(Integer day);
}

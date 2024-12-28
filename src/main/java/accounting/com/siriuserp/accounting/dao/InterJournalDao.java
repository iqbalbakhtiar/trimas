/**
 * Nov 26, 2008 9:46:33 AM
 * com.siriuserp.sdk.dao
 * InterJournalDao.java
 */
package com.siriuserp.accounting.dao;

import com.siriuserp.accounting.dm.InterJournal;
import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.base.Filterable;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public interface InterJournalDao extends Dao<InterJournal>, Filterable
{
	public Long loadUnfinishInterJournal(Long period);
}

/**
 * Nov 14, 2008 3:21:03 PM
 * com.siriuserp.sdk.dao
 * JournalDistributionSourceDao.java
 */
package com.siriuserp.accounting.dao;

import com.siriuserp.accounting.dm.JournalDistributionSource;
import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.dm.Party;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public interface JournalDistributionSourceDao extends Dao<JournalDistributionSource>
{
	public JournalDistributionSource load(Party organization);
}

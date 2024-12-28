/**
 * Nov 13, 2008 4:29:06 PM
 * com.siriuserp.sdk.dao
 * JournalSchemaAccountDao.java
 */
package com.siriuserp.accounting.dao;

import com.siriuserp.accounting.dm.GLPostingType;
import com.siriuserp.accounting.dm.JournalSchemaAccount;
import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.base.Filterable;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public interface JournalSchemaAccountDao extends Dao<JournalSchemaAccount>, Filterable
{
	public JournalSchemaAccount load(Long schema, Long account, GLPostingType postingType);
	public JournalSchemaAccount load(Long schema, String code);
}

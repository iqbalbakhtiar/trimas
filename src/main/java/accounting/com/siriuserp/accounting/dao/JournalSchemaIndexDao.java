/**
 * Nov 12, 2008 4:33:51 PM
 * com.siriuserp.sdk.dao
 * JournalSchemaIndexDao.java
 */
package com.siriuserp.accounting.dao;

import com.siriuserp.accounting.dm.JournalSchemaIndex;
import com.siriuserp.sdk.base.Dao;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public interface JournalSchemaIndexDao extends Dao<JournalSchemaIndex>
{
	public JournalSchemaIndex loadByParentAndType(Long parent, Long type);
}

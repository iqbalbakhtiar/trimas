/**
 * Nov 1, 2007 3:18:30 PM
 * net.konsep.sirius.accounting.dao
 * ClosingAccountDao.java
 */
package com.siriuserp.accounting.dao;

import com.siriuserp.accounting.dm.ClosingAccount;
import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.exceptions.DataDeletionException;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public interface ClosingAccountDao extends Dao<ClosingAccount>
{
	public ClosingAccount loadByParentAndType(Long schema, Long type);
	public ClosingAccount loadByOrganizationAndType(Long org, Long type);
	public ClosingAccount load(Long accountingSchema, Long productCategory, Long type);
	public ClosingAccount load(Long fixedGroup, Long type);
	public void deleteByType(Long id) throws DataDeletionException;
}

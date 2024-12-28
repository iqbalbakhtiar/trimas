/**
 * Dec 21, 2009 3:01:40 PM
 * com.siriuserp.sdk.dao
 * TaxPostingAccountDao.java
 */
package com.siriuserp.accounting.dao;

import com.siriuserp.accounting.dm.TaxPostingAccount;
import com.siriuserp.sdk.base.Dao;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public interface TaxPostingAccountDao extends Dao<TaxPostingAccount>
{
	public TaxPostingAccount load(Long accountingSchema, Long tax, Long closingAccountType);
	public TaxPostingAccount loadByOrganization(Long org, Long tax, Long closingAccountType);
}

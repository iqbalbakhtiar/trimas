/**
 * Dec 19, 2007 2:26:32 PM
 * net.konsep.sirius.accounting.dao
 * FixedAssetGroupDao.java
 */
package com.siriuserp.accounting.dao;

import java.util.List;

import com.siriuserp.accounting.dm.ClosingAccount;
import com.siriuserp.accounting.dm.FixedAssetGroup;
import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.base.Filterable;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public interface FixedAssetGroupDao extends Dao<FixedAssetGroup>, Filterable
{
	public List<FixedAssetGroup> loadAllByOrgs(List<Long> orgs);
	public ClosingAccount loadByOrganizationAndType(Long parent, Long type);
}

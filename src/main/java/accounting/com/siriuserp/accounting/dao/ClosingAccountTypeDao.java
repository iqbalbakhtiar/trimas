/**
 * Sep 25, 2007 3:41:03 PM
 * net.konsep.sirius.accounting.dao
 * ClosingAccountType.java
 */
package com.siriuserp.accounting.dao;

import java.util.List;

import com.siriuserp.accounting.dm.ClosingAccountType;
import com.siriuserp.accounting.dm.GroupType;
import com.siriuserp.sdk.base.Dao;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public interface ClosingAccountTypeDao extends Dao<ClosingAccountType>
{
	public List<ClosingAccountType> loadAllNonAsset();
	public List<ClosingAccountType> loadAllAsset();
	public List<ClosingAccountType> loadAllReceivables();
	public List<ClosingAccountType> loadAll(GroupType groupType);
}

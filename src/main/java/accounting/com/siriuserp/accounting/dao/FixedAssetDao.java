/**
 * Dec 21, 2007 4:40:23 PM
 * net.konsep.sirius.accounting.dao
 * FixedAssetDao.java
 */
package com.siriuserp.accounting.dao;

import java.util.Date;
import java.util.List;

import com.siriuserp.accounting.dm.AccountingPeriod;
import com.siriuserp.accounting.dm.FixedAsset;
import com.siriuserp.accounting.dm.FixedAssetGroup;
import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.base.Filterable;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public interface FixedAssetDao extends Dao<FixedAsset>, Filterable
{
	public List<FixedAsset> loadFixedAssetBefore(AccountingPeriod period, Long organization);
	public List<FixedAsset> loadFixedAssetBefore(FixedAssetGroup group, Date date, Long organization);
}

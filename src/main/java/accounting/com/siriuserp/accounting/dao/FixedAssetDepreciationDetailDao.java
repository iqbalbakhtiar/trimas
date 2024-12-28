/**
 * Mar 24, 2009 11:25:43 AM
 * com.siriuserp.sdk.dao
 * FixedAssetDepreciationDetailDao.java
 */
package com.siriuserp.accounting.dao;

import java.util.Date;
import java.util.List;

import com.siriuserp.accounting.dm.FixedAssetDepreciationDetail;
import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.dm.Month;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public interface FixedAssetDepreciationDetailDao extends Dao<FixedAssetDepreciationDetail>
{
	public List<FixedAssetDepreciationDetail> loadByGroupAndMonth(Long group, Month month, Date date);
}

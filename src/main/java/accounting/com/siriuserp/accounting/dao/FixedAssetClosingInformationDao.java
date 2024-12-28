/**
 * Dec 1, 2008 9:59:43 AM
 * com.siriuserp.sdk.dao
 * FixedAssetClosingInformationDao.java
 */
package com.siriuserp.accounting.dao;

import com.siriuserp.accounting.dm.FixedAssetClosingInformation;
import com.siriuserp.sdk.base.Dao;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public interface FixedAssetClosingInformationDao extends Dao<FixedAssetClosingInformation>
{
	public FixedAssetClosingInformation loadByGroupAndType(Long group, Long type);
}

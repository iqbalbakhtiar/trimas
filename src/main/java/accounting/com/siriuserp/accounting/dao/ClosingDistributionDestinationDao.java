/**
 * Nov 24, 2008 11:44:42 AM
 * com.siriuserp.sdk.dao
 * ClosingDistributionDestinationDao.java
 */
package com.siriuserp.accounting.dao;

import java.util.List;

import com.siriuserp.accounting.dm.ClosingDistributionDestination;
import com.siriuserp.sdk.base.Dao;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public interface ClosingDistributionDestinationDao extends Dao<ClosingDistributionDestination>
{
	public ClosingDistributionDestination loadByParentAndOrg(Long parent, Long org);
	public List<ClosingDistributionDestination> loadByOrg(Long org);
}

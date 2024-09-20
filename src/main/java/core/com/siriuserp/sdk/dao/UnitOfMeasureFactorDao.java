/**
 * Oct 24, 2008 11:01:15 AM
 * com.siriuserp.sdk.dao
 * UnitOfMeasureFactorDao.java
 */
package com.siriuserp.sdk.dao;

import com.siriuserp.inventory.dm.UnitofMeasureFactor;
import com.siriuserp.sdk.base.Dao;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public interface UnitOfMeasureFactorDao extends Dao<UnitofMeasureFactor>
{
    public UnitofMeasureFactor load(Long from,Long to);
}

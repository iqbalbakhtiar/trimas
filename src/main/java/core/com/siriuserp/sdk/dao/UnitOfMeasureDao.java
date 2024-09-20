/**
 * Apr 7, 2006
 * UnitOfMeasureDao.java
 */
package com.siriuserp.sdk.dao;

import java.util.List;

import com.siriuserp.inventory.dm.UnitOfMeasure;
import com.siriuserp.inventory.dm.UnitType;
import com.siriuserp.sdk.base.Dao;


/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public interface UnitOfMeasureDao extends Dao<UnitOfMeasure>
{
    public List<UnitOfMeasure> loadAll(UnitType type);
    public List<UnitOfMeasure> loadAll(boolean pack);
    public UnitOfMeasure loadDefault();
}

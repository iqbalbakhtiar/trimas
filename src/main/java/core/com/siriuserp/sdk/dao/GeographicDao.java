/**
 * Oct 30, 2008 3:26:20 PM
 * com.siriuserp.sdk.dao
 * GeographicDao.java
 */
package com.siriuserp.sdk.dao;

import java.util.List;

import com.siriuserp.administration.dm.Geographic;
import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.base.Filterable;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public interface GeographicDao extends Dao<Geographic>,Filterable
{
    public List<Geographic> loadAllNotIN(List<Long> excludes);
    public List<Geographic> loadByType(Long typeId);
    public List<Geographic> loadByTypeAndParent(Long typeId, Long parentId);
}

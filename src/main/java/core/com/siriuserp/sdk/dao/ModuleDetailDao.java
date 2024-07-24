/**
 * Nov 20, 2008 5:49:35 PM
 * com.siriuserp.sdk.dao
 * ModuleDetailDao.java
 */
package com.siriuserp.sdk.dao;

import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.dm.ModuleDetail;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public interface ModuleDetailDao extends Dao<ModuleDetail>
{
    public ModuleDetail load(String uri,Long module);
    public ModuleDetail load(String uri);
}

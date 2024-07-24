/**
 * Dec 22, 2008 2:51:31 PM
 * com.siriuserp.sdk.dao
 * LocalDao.java
 */
package com.siriuserp.sdk.dao;

import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.dm.Locale;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public interface LocaleDao extends Dao<Locale>
{
    public Locale loadDefault();
}

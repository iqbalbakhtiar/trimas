/**
 * Oct 31, 2008 2:48:12 PM
 * com.siriuserp.sdk.dao
 * GeographicTypeDao.java
 */
package com.siriuserp.sdk.dao;

import java.util.List;

import com.siriuserp.administration.dm.GeographicType;
import com.siriuserp.sdk.base.Dao;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public interface GeographicTypeDao extends Dao<GeographicType>
{
	public List<GeographicType> loadAll(boolean enabled);
}

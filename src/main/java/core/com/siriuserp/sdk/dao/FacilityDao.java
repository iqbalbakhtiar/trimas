/**
 * Apr 24, 2009 4:03:57 PM
 * com.siriuserp.sdk.dao
 * FacilityDao.java
 */
package com.siriuserp.sdk.dao;

import java.util.List;

import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.base.Filterable;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.tools.adapter.RoleDetailUIAdapter;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public interface FacilityDao extends Dao<Facility>, Filterable
{
	public List<RoleDetailUIAdapter> loadIsNotMapped(List<Long> facilities);
	public List<RoleDetailUIAdapter> loadIsMapped(Long role);
}

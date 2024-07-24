/**
 * Apr 20, 2006
 * GridDao.java
 */
package com.siriuserp.sdk.dao;

import java.util.List;

import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.base.Filterable;
import com.siriuserp.sdk.dm.Grid;
import com.siriuserp.tools.adapter.RoleDetailUIAdapter;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public interface GridDao extends Dao<Grid>, Filterable
{
	public Grid loadByFacility(Long facilityId);
	public List<RoleDetailUIAdapter> loadIsNotMapped(List<Long> grids);
	public List<RoleDetailUIAdapter> loadIsMapped(Long role);
}

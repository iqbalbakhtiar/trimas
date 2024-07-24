/**
 * Apr 18, 2006
 * CompanyStructureDao.java
 */
package com.siriuserp.sdk.dao;

import java.util.List;

import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.base.Filterable;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.tools.adapter.RoleDetailUIAdapter;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public interface CompanyStructureDao extends Dao<Party>, Filterable
{
	public Party loadParent(Long child);
	public List<Party> loadAllVerticalDown(Party parent);
	public List<Long> loadIDVerticalDown(Long parent);
	public List<Long> loadIDVerticalUp(Long parent);
	public List<RoleDetailUIAdapter> loadIsMapped(Long role);
	public List<RoleDetailUIAdapter> loadIsNotMapped(List<Long> orgs);
	public Party loadHolding();
	public List<Long> loadAllID();
}

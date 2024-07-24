/**
 * Dec 16, 2008 3:34:04 PM
 * com.siriuserp.sdk.dao
 * OrganizationRoleDao.java
 */
package com.siriuserp.sdk.dao;

import java.util.List;

import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.base.Filterable;
import com.siriuserp.sdk.dm.OrganizationRole;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.Role;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public interface OrganizationRoleDao extends Dao<OrganizationRole>, Filterable
{
	public Long loadLastSequence(Role role);
	public List<OrganizationRole> loadAll(Long role);
	public OrganizationRole getOrgRole(Role role, Party org);
}

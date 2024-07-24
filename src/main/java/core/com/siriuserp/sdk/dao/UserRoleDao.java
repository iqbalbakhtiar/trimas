/**
 * Dec 10, 2008 2:59:02 PM
 * com.siriuserp.sdk.dao
 * UserRoleDao.java
 */
package com.siriuserp.sdk.dao;

import java.util.List;

import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.dm.UserRole;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public interface UserRoleDao extends Dao<UserRole>
{
    public UserRole load(Long user,Long role);	
    public List<Long> loadAllID(Long user);
}

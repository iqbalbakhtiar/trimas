package com.siriuserp.sdk.dao;

import java.util.List;

import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.base.Filterable;
import com.siriuserp.sdk.dm.User;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public interface UserDao extends Dao<User>,Filterable
{
	public User loadByName(String username);
	public void refreshUser(User user);
    public User getUniqueUser(String userName);
    public List<User> loadSupervisor(boolean status);
}

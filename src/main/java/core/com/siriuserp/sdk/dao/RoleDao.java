package com.siriuserp.sdk.dao;


import java.util.List;

import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.base.Filterable;
import com.siriuserp.sdk.dm.Role;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public interface RoleDao extends Dao<Role>,Filterable
{
    public List<Role> loadAllByMandatory(boolean mandatory);
    public List<Role> loadAllUnmandatory();
}

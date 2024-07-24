package com.siriuserp.sdk.dao;

import java.util.List;

import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.dm.AccessibleModule;


public interface AccessibleModuleDao extends Dao<AccessibleModule>
{
    public List<AccessibleModule> loadAll(Long role);
    public List<String> findAccessibleRoleNameByLocation(String location);
    
    public List<Long> getAccessibleOrgs(Long roleId);
}

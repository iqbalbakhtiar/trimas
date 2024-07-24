package com.siriuserp.sdk.dao;

import java.util.List;

import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.dm.Module;
import com.siriuserp.sdk.dm.ModuleGroup;
import com.siriuserp.tools.adapter.RoleDetailUIAdapter;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public interface ModuleDao extends Dao<Module>
{
    public Module findModuleByLocation(String location);
    public List<Module> findModuleByModuleCode(String moduleCode);
    public List<String> loadDisplay(Long user);
    public List<ModuleGroup> loadGroupDisplay(Long user);
    public List<RoleDetailUIAdapter> loadIsMapped(Long role);
    public List<RoleDetailUIAdapter> loadIsNotMapped(List<Long> modules);
    public Long loadLastSequenceNotMapped(Long role);
    public List<Module> loadALLUnmandatory();
    public List<Module> loadALLMandatory();
}

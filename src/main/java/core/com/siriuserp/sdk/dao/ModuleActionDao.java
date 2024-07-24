package com.siriuserp.sdk.dao;

import java.util.List;

import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.dm.ModuleAction;


/**
 * @author Ronny Mailindra
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public interface ModuleActionDao extends Dao<ModuleAction>
{
	public List<ModuleAction> getAllModuleActions();
	public ModuleAction getModuleAction(Integer id);
}

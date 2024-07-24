package com.siriuserp.tools.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.siriuserp.sdk.dao.ModuleActionDao;
import com.siriuserp.sdk.db.DaoHelper;
import com.siriuserp.sdk.dm.ModuleAction;

/**
 * @author Ronny Mailindra
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Component("moduleActionDao")
public class ModuleActionDaoImpl extends DaoHelper<ModuleAction> implements ModuleActionDao
{
    @SuppressWarnings("unchecked")
	public List<ModuleAction> getAllModuleActions() 
    {
        Query query = getSession().createQuery("FROM ModuleAction");
        query.setCacheable(true);
        query.setReadOnly(true);
        
		return query.list();
	}
	
	public ModuleAction getModuleAction(Integer id)
    {
		return (ModuleAction)getSession().load(ModuleAction.class,id);
	}
}

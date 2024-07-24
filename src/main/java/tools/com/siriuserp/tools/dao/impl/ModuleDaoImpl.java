package com.siriuserp.tools.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.siriuserp.sdk.dao.ModuleDao;
import com.siriuserp.sdk.db.DaoHelper;
import com.siriuserp.sdk.dm.Module;
import com.siriuserp.sdk.dm.ModuleGroup;
import com.siriuserp.tools.adapter.RoleDetailUIAdapter;

import javolution.util.FastList;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@SuppressWarnings({"unchecked","rawtypes"})
@Component
public class ModuleDaoImpl extends DaoHelper<Module> implements ModuleDao
{
    public Module findModuleByLocation(String location) 
    {
        Query query = getSession().createQuery("SELECT distinct(m) FROM Module m where m.defaultUri =:uri");
        query.setString("uri",location);
        query.setCacheable(true);
        query.setReadOnly(true);
        
        Object object = query.uniqueResult();
        if(object != null)
            return (Module)object;

        return null;
    }

    public List<Module> findModuleByModuleCode(String moduleCode) 
    {
        Query query = getSession().createQuery("FROM Module m WHERE m.code =:code");
        query.setCacheable(true);
        query.setReadOnly(true);
        query.setParameter("code",moduleCode);

        return query.list();
    }

    public Module loadSearchableByCode(String code)
    {
        Criteria criteria = getSession().createCriteria(Module.class);
        criteria.setCacheable(true);
        criteria.add(Restrictions.eq("code",code));
        criteria.add(Restrictions.eq("seachable",Boolean.TRUE));

        Object object = criteria.uniqueResult();
        if(object != null)
            return (Module)object;
        
        return null;
    }

    public List<String> loadDisplay(Long user)
    {
        List<String> list = new FastList<String>();
        
        Query query = getSession().createQuery("SELECT distinct(am.module.name) FROM AccessibleModule am join am.role.users u Where u.id =:user AND am.module.enabled =:enabled AND am.module.mandatory =:mandatory AND am.accessType.id != 1");
        query.setCacheable(true);
        query.setReadOnly(true);
        query.setParameter("user", user);
        query.setParameter("enabled",Boolean.TRUE);
        query.setParameter("mandatory",Boolean.FALSE);
        
        List outs = query.list();
        if(outs != null && !outs.isEmpty())
            list.addAll(outs);
            
        return list;
    }
    
    public List<ModuleGroup> loadGroupDisplay(Long user)
    {
        Query query = getSession().createQuery("SELECT distinct(am.module.moduleGroup) FROM AccessibleModule am join am.role.users u Where u.user.id =:user AND am.module.enabled =:enabled");
        query.setParameter("user", user);
        query.setParameter("enabled",Boolean.TRUE);
        query.setCacheable(true);
        query.setReadOnly(true);
                    
        return query.list();
    }

    public List<RoleDetailUIAdapter> loadIsMapped(Long role)
    {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT new com.siriuserp.tools.adapter.RoleDetailUIAdapter(access.enabled, module.id, module.code, module.name, access.id, access.accessType) ");
        builder.append("FROM AccessibleModule access JOIN access.module module WHERE access.role.id =:role ");
        builder.append("ORDER BY module.code ASC");
        
        Query query = getSession().createQuery(builder.toString());
        query.setReadOnly(true);
        query.setCacheable(true);
        query.setParameter("role",role);
        
        return query.list();
    }
    
    public List<RoleDetailUIAdapter> loadIsNotMapped(List<Long> modules)
    {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT new com.siriuserp.tools.adapter.RoleDetailUIAdapter(module.id, module.code, module.name) FROM Module module WHERE module.mandatory =:mandatory");
        builder.append(!modules.isEmpty() ? " AND module.id NOT IN(:modules) " : " ");
        builder.append("ORDER BY module.code ASC");
        
        Query query = getSession().createQuery(builder.toString());
        query.setParameter("mandatory",Boolean.FALSE);
        query.setReadOnly(true);
        query.setCacheable(true);
        
        if(!modules.isEmpty())
        	query.setParameterList("modules",modules);
        
        return query.list();
    }

    public List<Module> loadALLMandatory()
    {
        Query query = getSession().createQuery("FROM Module m WHERE m.mandatory =:mandatory ORDER BY m.code ASC");
        query.setReadOnly(true);
        query.setCacheable(true);
        query.setParameter("mandatory",Boolean.TRUE);

        return query.list();
    }

    public List<Module> loadALLUnmandatory()
    {
        Query query = getSession().createQuery("FROM Module m WHERE m.mandatory =:mandatory ORDER BY m.code ASC");
        query.setParameter("mandatory",Boolean.FALSE);
        query.setCacheable(true);
        query.setReadOnly(true);
        
        return query.list();
    }

    @Override
    public Long loadLastSequenceNotMapped(Long role)
    {
        Query query = getSession().createQuery("SELECT MAX(module.sequence) FROM Module module WHERE module.id not in(SELECT access.module.id FROM AccessibleModule access WHERE access.role.id =:role)");
        query.setParameter("role",role);
        query.setCacheable(true);
        query.setReadOnly(true);
        
        Object object = query.uniqueResult();
        if(object != null)
            return (Long)object;
        
        return Long.valueOf(0);
    }
}

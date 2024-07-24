/**
 * Nov 20, 2008 5:50:05 PM
 * com.siriuserp.sdk.dao.impl
 * ModuleDetailDaoImpl.java
 */
package com.siriuserp.tools.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.siriuserp.sdk.dao.ModuleDetailDao;
import com.siriuserp.sdk.db.DaoHelper;
import com.siriuserp.sdk.dm.ModuleDetail;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Component
public class ModuleDetailDaoImpl extends DaoHelper<ModuleDetail> implements ModuleDetailDao
{
    @Override
    public ModuleDetail load(String uri,Long module)
    {
        Query query = getSession().createQuery("FROM ModuleDetail detail WHERE detail.uri =:uri AND detail.module.id =:module");
        query.setCacheable(true);
        query.setParameter("uri",uri);
        query.setParameter("module",module);
        
        Object object = query.uniqueResult();
        if(object != null)
            return (ModuleDetail)object;
        
        return null;
    }

    @Override
    public ModuleDetail load(String uri)
    {
        Criteria criteria = getSession().createCriteria(ModuleDetail.class);
        criteria.setCacheable(true);
        criteria.add(Restrictions.eq("uri",uri));
        criteria.setMaxResults(1);
        
        Object object = criteria.uniqueResult();
        if(object != null)
            return (ModuleDetail)object;
        
        return null;
    }
}

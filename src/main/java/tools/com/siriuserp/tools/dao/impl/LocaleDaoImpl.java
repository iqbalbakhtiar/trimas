/**
 * Dec 22, 2008 2:52:14 PM
 * com.siriuserp.sdk.dao.impl
 * LocaleDaoImpl.java
 */
package com.siriuserp.tools.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.siriuserp.sdk.dao.LocaleDao;
import com.siriuserp.sdk.db.DaoHelper;
import com.siriuserp.sdk.dm.Locale;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Component
public class LocaleDaoImpl extends DaoHelper<Locale> implements LocaleDao
{
    @Override
    public Locale loadDefault()
    {
        Criteria criteria = getSession().createCriteria(Locale.class);
        criteria.setCacheable(true);
        criteria.add(Restrictions.eq("base",Boolean.TRUE));
        
        Object object = criteria.uniqueResult();
        if(object != null)
            return (Locale)object;
        
        return null;
    }

}

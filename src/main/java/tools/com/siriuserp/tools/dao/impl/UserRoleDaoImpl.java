/**
 * Dec 10, 2008 3:00:03 PM
 * com.siriuserp.sdk.dao.impl
 * UserRoleDaoImpl.java
 */
package com.siriuserp.tools.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.siriuserp.sdk.dao.UserRoleDao;
import com.siriuserp.sdk.db.DaoHelper;
import com.siriuserp.sdk.dm.UserRole;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@SuppressWarnings("unchecked")
@Component
public class UserRoleDaoImpl extends DaoHelper<UserRole> implements UserRoleDao
{

    @Override
    public UserRole load(Long user, Long role)
    {
        Criteria criteria = getSession().createCriteria(UserRole.class);
        criteria.setCacheable(true);
        criteria.createCriteria("user").add(Restrictions.eq("id",user));
        criteria.createCriteria("role").add(Restrictions.eq("id",role));
        
        Object object = criteria.uniqueResult();
        if(object != null)
            return (UserRole)object;
        
        return null;
    }

    public List<Long> loadAllID(Long user)
    {
        Query query = getSession().createQuery("SELECT role.id FROM UserRole role Where role.enabled =:enabled AND role.user.id =:user");
        query.setCacheable(true);
        query.setReadOnly(true);
        query.setParameter("enabled",Boolean.TRUE);
        query.setParameter("user",user);
        
        return query.list();
    }

}

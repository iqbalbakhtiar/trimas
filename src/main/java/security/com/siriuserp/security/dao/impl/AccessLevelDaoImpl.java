package com.siriuserp.security.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.siriuserp.sdk.dao.AccessLevelDao;
import com.siriuserp.sdk.db.DaoHelper;
import com.siriuserp.sdk.dm.AccessLevel;

@Component
public class AccessLevelDaoImpl extends DaoHelper<AccessLevel> implements AccessLevelDao
{
    public AccessLevel findAccessLevelByLevelName(String levelName) 
    {
        Query query = getSession().createQuery("FROM AccessLevel ac WHERE ac.level=?");
        query.setString(0,levelName);

        return (AccessLevel)query.uniqueResult();
    }

}

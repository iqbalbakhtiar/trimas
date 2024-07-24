/**
 * Mar 29, 2007 11:47:37 AM
 * net.konsep.sirius.administration.service
 * ActivityHistoryService.java
 */
package com.siriuserp.tools.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.dm.ActivityHistory;
import com.siriuserp.sdk.dm.User;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.QueryFactory;
import com.siriuserp.sdk.utility.UserHelper;

import javolution.util.FastMap;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Component
@Transactional(rollbackFor=Exception.class)
public class AuditTrailService
{
    @Autowired
    private GenericDao genericDao;

    @Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
    public FastMap<String,Object> view(GridViewFilterCriteria filterCriteria,Class<? extends GridViewQuery> queryclass)throws ServiceException
    {
        FastMap<String,Object> map = new FastMap<String, Object>();
        map.put("filterCriteria", filterCriteria);
        map.put("trails",FilterAndPaging.filter(genericDao,QueryFactory.create(filterCriteria, queryclass)));

        return map;
    }
    
    public void add(ActivityHistory activityHistory)throws ServiceException
    {
        activityHistory.setActivePerson(UserHelper.activePerson());
        genericDao.add(activityHistory);
    }
    public void delete(String id)throws ServiceException
    {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal !=null && principal instanceof User)
        {
            ActivityHistory activityHistory = genericDao.load(ActivityHistory.class, Long.valueOf(id));
            if(activityHistory != null)
            	genericDao.delete(activityHistory);
        }
    }
}

/**
 * Dec 11, 2009 11:27:00 AM
 * com.siriuserp.sdk.aspect
 * ExceptionViewerAspect.java
 */
package com.siriuserp.sdk.aspect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.dm.ExceptionEvent;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.DateHelper;
import com.siriuserp.sdk.utility.QueryFactory;
import com.siriuserp.sdk.utility.UserHelper;

import javolution.util.FastMap;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Component
@Transactional(noRollbackFor=Exception.class)
public class ExceptionViewerService	
{
    @Autowired
    private GenericDao dao;
    
    public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends AbstractGridViewQuery> queryclass) throws Exception
    {
        FastMap<String,Object> map = new FastMap<String, Object>();
        map.put("filterCriteria", filterCriteria);
        map.put("events", FilterAndPaging.filter(dao, QueryFactory.create(filterCriteria, queryclass)));
        
        return map;
    }
    
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void writeEvent(Exception ex,Object handler)throws Exception {
    	ExceptionEvent event = new ExceptionEvent();
        event.setErrorCode("000x0");
        event.setErrorTime(DateHelper.now());
        event.setLoggedin(UserHelper.activePerson());
        event.setExceptionType(ex.getClass().getSimpleName());
        event.setLocation(handler.getClass().getSimpleName());
        event.setStacktrace(ex.toString());
        event.setErrorMessage(ex.getMessage());

        dao.add(event);
    }
}

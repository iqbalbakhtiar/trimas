package com.siriuserp.tools.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dao.NewsDao;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.dm.News;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.QueryFactory;

import javolution.util.FastMap;

/**
 * @author Ersi Agustin
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Component
@Transactional(rollbackFor=Exception.class)
public class NewsService
{
    @Autowired
    private NewsDao newsDao;
    
    @Autowired
    private GenericDao genericDao;
    
    public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends AbstractGridViewQuery> queryclass) throws Exception
    {
    	FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("filterCriteria", filterCriteria);
		map.put("news", FilterAndPaging.filter(newsDao, QueryFactory.create(filterCriteria,queryclass)));
		
        return map;
    }
    
    @Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
    public FastMap<String, Object> preadd()
    {
    	FastMap<String, Object> map = new FastMap<String, Object>();
    	map.put("news", new News());

        return map;
    }
    
    @AuditTrails(className=News.class,actionType=AuditTrailsActionType.CREATE)
    public void add(News news) throws ServiceException
    {
        newsDao.add(news);
    }
    
    @AuditTrails(className=News.class,actionType=AuditTrailsActionType.DELETE)
    public void delete(News news) throws ServiceException
    {
    	newsDao.delete(news);
    }

    @Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
    public FastMap<String, Object> preedit(Long id)
    {
    	FastMap<String, Object> map = new FastMap<String, Object>();
    	map.put("news", load(id));

        return map;
    }
    
    @AuditTrails(className=News.class,actionType=AuditTrailsActionType.UPDATE)
    public void update(News news) throws ServiceException
    {
        newsDao.update(news);
    }

    @Transactional(readOnly=false)
    public News load(Long id)
    {
        return genericDao.load(News.class, id);
    }
}
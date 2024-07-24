/**
 * Nov 15, 2008 5:03:30 PM
 * com.siriuserp.tools.service
 * ModuleGroupService.java
 */
package com.siriuserp.tools.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.dm.ModuleGroup;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.QueryFactory;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastMap;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Component
@Transactional(rollbackFor=Exception.class)
public class ModuleGroupService
{
    @Autowired
    private GenericDao genericDao;

    @Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
    public FastMap<String,Object> preview()
    {
    	FastMap<String,Object> map = new FastMap<String, Object>();
    	map.put("alls", genericDao.loadAll(ModuleGroup.class));
        
        return map;
    }
    
    @Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
    public FastMap<String,Object> view(GridViewFilterCriteria filterCriteria,Class<? extends GridViewQuery> queryclass) throws ServiceException
    {
        FastMap<String,Object> map = new FastMap<String, Object>();
        map.put("filterCriteria",filterCriteria);
        map.put("alls",FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));
        
        return map;
    }
    
    @Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
    public FastMap<String,Object> preadd(String id)
    {
        FastMap<String,Object> map = new FastMap<String, Object>();
        
        ModuleGroup moduleGroup = new ModuleGroup();
        
        if(SiriusValidator.validateParamWithZeroPosibility(id))
            moduleGroup.setParent(load(Long.valueOf(id)));
        
        map.put("moduleGroup",moduleGroup);
        
        return map;
    }
    
    @Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
    public FastMap<String,Object> preedit(Long id)
    {
        FastMap<String,Object> map = new FastMap<String, Object>();
        map.put("moduleGroup",load(id));
        
        return map;
    }
    
    @AuditTrails(className=ModuleGroup.class,actionType=AuditTrailsActionType.CREATE)
    public void add(ModuleGroup moduleGroup)throws ServiceException
    {
    	genericDao.add(moduleGroup); 
    }
    
    @AuditTrails(className=ModuleGroup.class,actionType=AuditTrailsActionType.UPDATE)
    public void edit(ModuleGroup moduleGroup)throws ServiceException
    {
    	genericDao.update(moduleGroup); 
    }
    
    @AuditTrails(className=ModuleGroup.class,actionType=AuditTrailsActionType.DELETE)
    public void delete(ModuleGroup moduleGroup)throws ServiceException
    {
    	genericDao.delete(moduleGroup); 
    }
 
    @Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
    public ModuleGroup load(Long id)
    {
        return genericDao.load(ModuleGroup.class, id);
    }
}

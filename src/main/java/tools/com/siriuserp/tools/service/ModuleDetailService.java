/**
 * Nov 20, 2008 5:48:59 PM
 * com.siriuserp.tools.service
 * ModuleDetailService.java
 */
package com.siriuserp.tools.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dao.ModuleDetailDao;
import com.siriuserp.sdk.dm.Module;
import com.siriuserp.sdk.dm.ModuleDetail;
import com.siriuserp.sdk.exceptions.ServiceException;

import javolution.util.FastMap;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Component
@Transactional(rollbackFor=Exception.class)
public class ModuleDetailService
{
    @Autowired
    private GenericDao genericDao;
    
    @Autowired
    private ModuleDetailDao moduleDetailDao;

    @Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
    public FastMap<String,Object> preadd(String parent)
    {
        FastMap<String,Object> map = new FastMap<String, Object>();
        
        ModuleDetail moduleDetail = new ModuleDetail();
        moduleDetail.setModule(genericDao.load(Module.class, Long.valueOf(parent)));
        
        map.put("moduleDetail",moduleDetail);
        
        return map;
    }
    
    @Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
    public FastMap<String,Object> preedit(String id)
    {
        FastMap<String,Object> map = new FastMap<String, Object>();
        map.put("moduleDetail",genericDao.load(ModuleDetail.class, Long.valueOf(id)));
        
        return map;
    }
    
    @AuditTrails(className=ModuleDetail.class,actionType=AuditTrailsActionType.CREATE)
    public void add(ModuleDetail moduleDetail)throws ServiceException
    {
        Module module = genericDao.load(Module.class, moduleDetail.getModule().getId());
        if(module != null)
        {
            moduleDetail.setModule(module);
            module.getDetails().add(moduleDetail);
            
            genericDao.update(module);
        }
    }
    
    @AuditTrails(className=ModuleDetail.class,actionType=AuditTrailsActionType.UPDATE)
    public void edit(ModuleDetail moduleDetail)throws ServiceException
    {
        moduleDetailDao.update(moduleDetail);
    }
    
    @AuditTrails(className=ModuleDetail.class,actionType=AuditTrailsActionType.DELETE)
    public void delete(ModuleDetail moduleDetail)throws ServiceException
    {
        moduleDetailDao.delete(moduleDetail);
    }
    
    @Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
    public ModuleDetail load(String id)
    {
        return genericDao.load(ModuleDetail.class, Long.valueOf(id));
    }
}

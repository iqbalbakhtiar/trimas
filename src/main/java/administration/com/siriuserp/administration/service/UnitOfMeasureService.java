/**
 * Mar 14, 2008 5:24:56 PM
 * com.siriuserp.administration.service
 * UnitOfMeasureService.java
 */
package com.siriuserp.administration.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dm.UnitOfMeasure;
import com.siriuserp.sdk.dm.UnitType;
import com.siriuserp.sdk.exceptions.ServiceException;

import javolution.util.FastMap;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Component
@Transactional(rollbackFor=Exception.class)
public class UnitOfMeasureService
{      
    @Autowired
    protected GenericDao genericDao;
    
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<UnitOfMeasure> loadAllUoM()
    {
        return genericDao.loadAll(UnitOfMeasure.class);
    }
    
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public UnitOfMeasure load(Long id)
    {
        return genericDao.load(UnitOfMeasure.class, id);
    }
    
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preadd()
	{
    	FastMap<String, Object> map = new FastMap<String, Object>();
    	map.put("unitOfMeasure_add", new UnitOfMeasure());
    	map.put("types", UnitType.values());
    	
    	return map;
	}
    
    @AuditTrails(className=UnitOfMeasure.class,actionType=AuditTrailsActionType.CREATE)
    public void add(UnitOfMeasure unitOfMeasure)throws ServiceException
    {
    	genericDao.add(unitOfMeasure);
    }
    
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
   	public FastMap<String, Object> preedit(Long id)
   	{
       	FastMap<String, Object> map = new FastMap<String, Object>();
       	map.put("unitOfMeasure_edit", load(id));
       	map.put("types", UnitType.values());
       	
       	return map;
   	}
    
    @AuditTrails(className=UnitOfMeasure.class,actionType=AuditTrailsActionType.UPDATE)
    public void edit(UnitOfMeasure measure)throws ServiceException
    {
    	genericDao.update(measure);
    }
    
    @AuditTrails(className=UnitOfMeasure.class,actionType=AuditTrailsActionType.DELETE)
    public void delete(UnitOfMeasure unitOfMeasure)throws ServiceException
    {
    	genericDao.delete(unitOfMeasure);
    }
}

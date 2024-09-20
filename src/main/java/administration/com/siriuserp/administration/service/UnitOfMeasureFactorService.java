/**
 * Feb 22, 2011 11:23:49 AM
 * com.siriuserp.administration.service
 * UnitOfMeasureFactorService.java
 */
package com.siriuserp.administration.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.inventory.dm.UnitOfMeasure;
import com.siriuserp.inventory.dm.UnitofMeasureFactor;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dao.UnitOfMeasureDao;
import com.siriuserp.sdk.exceptions.ServiceException;

import javolution.util.FastMap;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia,PT
 * Sinch version 1.5
 */
@Component
@Transactional(rollbackFor=Exception.class)
public class UnitOfMeasureFactorService
{
    @Autowired
    private GenericDao genericDao;
    
    @Autowired
    private UnitOfMeasureDao unitOfMeasureDao;
    
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Map<String,Object> preadd(Long id)
    {
        Map<String,Object> map = new FastMap<String, Object>();
        
        UnitofMeasureFactor factor = new UnitofMeasureFactor();
        factor.setFrom(genericDao.load(UnitOfMeasure.class, id));
        
        map.put("unit_of_measure_factor_add",factor);
        map.put("uoms",unitOfMeasureDao.loadAll(factor.getFrom().getType()));
        
        return map;
    }
    
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Map<String,Object> preedit(Long id)
    {
        Map<String,Object> map = new FastMap<String, Object>();
        map.put("unit_of_measure_factor_edit",load(id));
        
        return map;
    }
    
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public UnitofMeasureFactor load(Long id)
    {
        return genericDao.load(UnitofMeasureFactor.class, id);
    }
    
    @AuditTrails(className=UnitofMeasureFactor.class,actionType=AuditTrailsActionType.CREATE)
    public void add(UnitofMeasureFactor factor) throws ServiceException
    {
    	genericDao.add(factor);
    }
    
    @AuditTrails(className=UnitofMeasureFactor.class,actionType=AuditTrailsActionType.UPDATE)
    public void edit(UnitofMeasureFactor factor) throws ServiceException
    {
    	genericDao.update(factor);
    }
    
    @AuditTrails(className=UnitofMeasureFactor.class,actionType=AuditTrailsActionType.DELETE)
    public void delete(UnitofMeasureFactor factor) throws ServiceException
    {
    	genericDao.delete(factor);
    }
}

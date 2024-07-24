/**
 * Feb 12, 2009 11:46:32 AM
 * com.siriuserp.administration.service
 * TaxService.java
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
import com.siriuserp.sdk.dm.Tax;
import com.siriuserp.sdk.exceptions.ServiceException;

import javolution.util.FastMap;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Component
@Transactional(rollbackFor=Exception.class)
public class TaxService
{
    @Autowired
    private GenericDao genericDao;

    @Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
    public List<Tax> loadAllTax()
    {
        return genericDao.loadAll(Tax.class);
    }

    @Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
    public Tax load(String id)
    {
        return genericDao.load(Tax.class, Long.valueOf(id));
    }

    @Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
    public FastMap<String,Object> preadd()
    {
        FastMap<String,Object> map = new FastMap<String, Object>();
        map.put("tax_add",new Tax());

        return map;
    }

    @AuditTrails(className=Tax.class,actionType=AuditTrailsActionType.CREATE)
    public void add(Tax tax)throws ServiceException
    {
        try
        {
        	genericDao.add(tax);                           
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(),e);
        }
    }


    @AuditTrails(className=Tax.class,actionType=AuditTrailsActionType.UPDATE)
    public void edit(Tax tax)throws ServiceException
    {
        try
        {
        	genericDao.update(tax);
        }
        catch (Exception e)
        {
            throw new ServiceException(e);
        }
    }


    @AuditTrails(className=Tax.class,actionType=AuditTrailsActionType.DELETE)
    public void delete(Tax tax)throws ServiceException
    {
        try
        {
            if(tax != null)
            	genericDao.delete(tax);
        }
        catch (Exception e)
        {
            throw new ServiceException(e);
        }
    }

    @Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
    public FastMap<String,Object> preedit(String id)
    {
        FastMap<String,Object> map = new FastMap<String, Object>();
        map.put("tax_edit", load(id));

        return map;
    }
}

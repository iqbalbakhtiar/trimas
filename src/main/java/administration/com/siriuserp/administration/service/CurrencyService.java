/**
 * Mar 14, 2008 5:03:32 PM
 * com.siriuserp.administration.service
 * CurrencyService.java
 */
package com.siriuserp.administration.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.dao.CurrencyDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.exceptions.ServiceException;

import javolution.util.FastMap;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Component
@Transactional(rollbackFor=Exception.class)
public class CurrencyService
{   
    @Autowired
    protected GenericDao genericDao;
    
    @Autowired
    protected CurrencyDao currencyDao;
    
    @Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
    public List<Currency> loadAllCurrency()
    {
        return genericDao.loadAll(Currency.class);
    }
    
    @Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
    public Currency load(String id)
    {
        return genericDao.load(Currency.class, Long.valueOf(id));
    }
    

    @AuditTrails(className=Currency.class,actionType=AuditTrailsActionType.CREATE)
    public void add(Currency currency)throws ServiceException
    {
        try
        {
            if(currency.isBase())
            {
                Currency _base = currencyDao.loadDefaultCurrency();
                if(_base != null)
                {
                    _base.setBase(false);
                    currencyDao.update(_base);
                }                
            }

            currencyDao.add(currency);
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
        map.put("currency_edit",load(id));
        
        return map;
    }

    @AuditTrails(className=Currency.class,actionType=AuditTrailsActionType.UPDATE)
    public void edit(Currency currency)throws ServiceException
    {
        try
        {
            if(currency.isBase())
            {
                Currency _base = currencyDao.loadIfNot(currency.getId());
                if(_base != null)
                {
                    _base.setBase(false);
                    currencyDao.update(_base);
                }                
            }

            currencyDao.update(currency);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }
    
    @AuditTrails(className=Currency.class,actionType=AuditTrailsActionType.DELETE)
    public void delete(Currency currency)throws ServiceException
    {
        try
        {
            if(currency != null)
                currencyDao.delete(currency);
        }
        catch (Exception e)
        {
            throw new ServiceException(e);
        }
    }
}

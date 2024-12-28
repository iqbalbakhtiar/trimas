/**
 * Nov 11, 2008 9:53:00 AM
 * com.siriuserp.accounting.service
 * GeneralLedgerAccountService.java
 */
package com.siriuserp.accounting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.accounting.dao.GLAccountDao;
import com.siriuserp.accounting.dm.ChartOfAccount;
import com.siriuserp.accounting.dm.ClosingAccountType;
import com.siriuserp.accounting.dm.GLAccount;
import com.siriuserp.accounting.dm.GLLevel;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.GridViewQuery;
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
public class GeneralLedgerAccountService
{
    @Autowired
    private GLAccountDao accountDao;

    @Autowired
    private GenericDao genericDao;

    @Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
    public FastMap<String,Object> view(GridViewFilterCriteria filterCriteria,Class<? extends GridViewQuery>  queryclass)throws ServiceException
    {
        FastMap<String,Object> map = new FastMap<String, Object>();
        map.put("accounts",FilterAndPaging.filter(accountDao, QueryFactory.create(filterCriteria, queryclass)));
        map.put("filterCriteria",filterCriteria);

        return map;
    }

    @Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
    public FastMap<String,Object> preadd(String coa,String parent)
    {
        FastMap<String,Object> map = new FastMap<String, Object>();

        GLAccount account = new GLAccount();
        account.setCoa(genericDao.load(ChartOfAccount.class, Long.valueOf(coa)));

        if(SiriusValidator.validateParamWithZeroPosibility(parent))
            account.setParent(genericDao.load(GLAccount.class, Long.valueOf(parent)));

        map.put("account",account);
        map.put("types",genericDao.loadAll(ClosingAccountType.class));

        return map;
    }

    @AuditTrails(className=GLAccount.class,actionType=AuditTrailsActionType.CREATE)
    public void add(GLAccount account) throws ServiceException
    {
        accountDao.add(account);
    }

    @Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
    public FastMap<String,Object> preedit(String id)
    {
        FastMap<String,Object> map = new FastMap<String, Object>();
        GLAccount account = load(Long.valueOf(id));
        
        if(account.getLevel().equals(GLLevel.ACCOUNTGROUP))
        	map.put("deleteable", account.getChildrens().size() == 0 ? true : false);
        else
        	map.put("deleteable", accountDao.getCountJournalEntryDetail(account.getId()) == 0 ? true : false);

        map.put("account",account);
        map.put("types",genericDao.loadAll(ClosingAccountType.class));

        return map;
    }

    @AuditTrails(className=GLAccount.class,actionType=AuditTrailsActionType.UPDATE)
    public void edit(GLAccount account) throws ServiceException
    {
        accountDao.update(account);
    }

    @AuditTrails(className=GLAccount.class,actionType=AuditTrailsActionType.DELETE)
    public void delete(Long id) throws ServiceException
    {
        accountDao.delete(load(id));
    }

    @Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
    public GLAccount load(Long id)
    {
        return genericDao.load(GLAccount.class, id);
    }

    @Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
    public GLAccount load(String code)
    {
        return accountDao.load(code);
    }
}

/**
 * Jan 21, 2010 10:14:26 AM
 * com.siriuserp.reporting.accounting.util
 * AccountingReportUtil.java
 */
package com.siriuserp.reporting.accounting.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siriuserp.accounting.adapter.SimpleAccountingReportAdapter;
import com.siriuserp.accounting.criteria.DefaultAccountingReportFilterCriteria;
import com.siriuserp.accounting.dao.AccountingPeriodDao;
import com.siriuserp.accounting.dm.AccountingPeriod;
import com.siriuserp.reporting.accounting.adapter.ComplexAccountingReportAdapter;
import com.siriuserp.sdk.dao.CompanyStructureDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dm.Party;

import javolution.util.FastList;

/**
 * @author Muhammad Rizal
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Component
public class AccountingReportUtil
{
    @Autowired
    private AccountingPeriodDao accountingPeriodDao;

    @Autowired
    private CompanyStructureDao companyStructureDao;
    
    @Autowired
    private GenericDao genericDao;
    
    public SimpleAccountingReportAdapter createAdapter(DefaultAccountingReportFilterCriteria filterCriteria)
    {
        SimpleAccountingReportAdapter adapter = new SimpleAccountingReportAdapter();
        
        FastList.recycle(filterCriteria.getOrganizations());
        FastList.recycle(filterCriteria.getAccountingPeriods());
        
        for(Long organization:filterCriteria.getOrganizationIds())
        {
            adapter.getOrganizations().add(organization);
            adapter.getOrganizations().addAll(companyStructureDao.loadIDVerticalDown(organization));
        }
        
        Date date = accountingPeriodDao.loadMin(filterCriteria.getAccountingPeriodIds());

		filterCriteria.getAccountingPeriodIds().remove(null);
        for(Long id:filterCriteria.getAccountingPeriodIds())
        {
            AccountingPeriod period = genericDao.load(AccountingPeriod.class, id);
            if(period != null)
            {
                filterCriteria.getAccountingPeriods().add(period);
                
                populate(period,adapter.getPeriods());
                
                if(period.getStartDate().equals(date))
                {
                    adapter.getOpeningPeriodObj().add(period);

                    populate(period,adapter.getOpeningPeriods());
                }
            }
        }

		filterCriteria.getOrganizationIds().remove(null);
        for(Long org:filterCriteria.getOrganizationIds())
            filterCriteria.getOrganizations().add(genericDao.load(Party.class, org));
    
        return adapter;
    }
    
    public ComplexAccountingReportAdapter complex(ComplexAccountingReportAdapter adapter, DefaultAccountingReportFilterCriteria filterCriteria)
    {
        FastList.recycle(filterCriteria.getOrganizations());
        FastList.recycle(filterCriteria.getAccountingPeriods());
        
        for(Long organization:filterCriteria.getOrganizationIds())
        {
            adapter.getOrganizations().add(organization);
            adapter.getOrganizations().addAll(companyStructureDao.loadIDVerticalDown(organization));
        }
 
        for(Long id:filterCriteria.getAccountingPeriodIds())
        {
        	AccountingPeriod period = genericDao.load(AccountingPeriod.class, id);
        	if(period != null)
            {
                filterCriteria.getAccountingPeriods().add(period);
                populate(period, adapter.getPeriods());
            }
        }
        
        for(Long id:filterCriteria.getPrevAccountingPeriodIds())
        {
        	AccountingPeriod period = genericDao.load(AccountingPeriod.class, id);
            if(period != null)
            {
                filterCriteria.getPrevAccountingPeriods().add(period);
                populate(period, adapter.getPrevPeriods());
            }
        }
        
        for(Long org:filterCriteria.getOrganizationIds())
            filterCriteria.getOrganizations().add(genericDao.load(Party.class, org));
    
        return adapter;
    }
    
    public void populate(AccountingPeriod period,FastList<Long> buffer)
    {
        buffer.add(period.getId());
        if(!period.getChilds().isEmpty())
        {
            for(AccountingPeriod accountingPeriod:period.getChilds())
                populate(accountingPeriod, buffer);
        }
    }
}

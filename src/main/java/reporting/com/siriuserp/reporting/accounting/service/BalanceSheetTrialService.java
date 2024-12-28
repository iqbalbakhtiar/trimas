/**
 * Dec 4, 2008 3:47:00 PM
 * com.siriuserp.reporting.accounting.service
 * BalanceSheetService.java
 */
package com.siriuserp.reporting.accounting.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.accounting.dao.AccountingPeriodDao;
import com.siriuserp.accounting.dm.AccountingPeriod;
import com.siriuserp.reporting.accounting.adapter.ComplexAccountingReportAdapter;
import com.siriuserp.reporting.accounting.adapter.ProfitLossReportAdapter;
import com.siriuserp.reporting.accounting.criteria.ProfitLossFilterCriteria;
import com.siriuserp.reporting.accounting.query.BalanceSheetTrialReportQuery;
import com.siriuserp.reporting.accounting.util.AccountingReportUtil;
import com.siriuserp.sdk.dao.GenericDao;

import javolution.util.FastMap;

/**
 * @author Muhammad Khairullah
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Component
@Transactional(readOnly=true)
public class BalanceSheetTrialService
{
    @Autowired
    private GenericDao genericDao;
    
    @Autowired
    private AccountingPeriodDao accountingPeriodDao;

    @Autowired
    private AccountingReportUtil util;

    public FastMap<String,Object> pre()
    {
        FastMap<String,Object> map = new FastMap<String, Object>();
        map.put("filterCriteria",new ProfitLossFilterCriteria());

        return map;
    }

    @SuppressWarnings("unchecked")
    public FastMap<String,Object> view(ProfitLossFilterCriteria filterCriteria)
    {
        FastMap<String,Object> map = new FastMap<String, Object>();
        
        for(Long id:filterCriteria.getAccountingPeriodIds())
        {
        	filterCriteria.getPrevAccountingPeriodIds().add(id);
        	filterCriteria.getPrevAccountingPeriodIds().addAll(accountingPeriodDao.loadAllIDPrevInYear(genericDao.load(AccountingPeriod.class, id)));
        }
        
        ComplexAccountingReportAdapter reportAdapter = util.complex(new ComplexAccountingReportAdapter(), filterCriteria);

        BalanceSheetTrialReportQuery query = new BalanceSheetTrialReportQuery();
        query.setReportAdapter(reportAdapter);
        
        List<ProfitLossReportAdapter> data = genericDao.generateReport(query);
        
        for(ProfitLossReportAdapter adapter : data)
        {
        	if (adapter.getAccount().getAccountType().getId().equals(Long.valueOf(1))) 
        		reportAdapter.getAssets().add(adapter);
			else if(adapter.getAccount().getAccountType().getId().equals(Long.valueOf(2)))	
				reportAdapter.getLiabilities().add(adapter);
			else if(adapter.getAccount().getAccountType().getId().equals(Long.valueOf(3)))
				reportAdapter.getEquitys().add(adapter);
        }
        
        map.put("adapter", reportAdapter);
        map.put("criteria",filterCriteria);

        return map;
    }
}

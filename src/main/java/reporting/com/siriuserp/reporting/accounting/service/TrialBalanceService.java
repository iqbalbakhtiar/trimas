/**
 * Dec 3, 2008 4:12:55 PM
 * com.siriuserp.reporting.accounting.service
 * TrialBalanceService.java
 */
package com.siriuserp.reporting.accounting.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.accounting.adapter.SimpleAccountingReportAdapter;
import com.siriuserp.accounting.criteria.DefaultAccountingReportFilterCriteria;
import com.siriuserp.reporting.accounting.adapter.ComplexConsecutiveReportAdapter;
import com.siriuserp.reporting.accounting.adapter.GLAccountProfitLossAdapter;
import com.siriuserp.reporting.accounting.adapter.TrialBalanceReportAdapter;
import com.siriuserp.reporting.accounting.query.PostClosingTrialBalanceReportQuery;
import com.siriuserp.reporting.accounting.query.PrePostingTrialBalanceReportQuery;
import com.siriuserp.reporting.accounting.query.TrialBalanceReportQuery;
import com.siriuserp.reporting.accounting.util.AccountingReportUtil;
import com.siriuserp.sdk.dao.GenericDao;

import javolution.util.FastMap;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Component
@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
public class TrialBalanceService
{
    @Autowired
    private GenericDao genericDao;
    
    @Autowired
    private AccountingReportUtil util;
    
    public FastMap<String,Object> pre()
    {
        FastMap<String,Object> map = new FastMap<String, Object>();
        map.put("filterCriteria",new DefaultAccountingReportFilterCriteria());
        
        return map;
    }

    public FastMap<String,Object> view(DefaultAccountingReportFilterCriteria filterCriteria)
    {
        SimpleAccountingReportAdapter adapter = util.createAdapter(filterCriteria);
        
        FastMap<String,Object> map = new FastMap<String, Object>();
        map.put("adapter", genericDao.generate(new TrialBalanceReportQuery(adapter)));
        map.put("criteria",filterCriteria);
        
        return map;
    }
    
    public FastMap<String,Object> viewpost(DefaultAccountingReportFilterCriteria filterCriteria)
    {
    	SimpleAccountingReportAdapter adapter = util.createAdapter(filterCriteria);

        FastMap<String,Object> map = new FastMap<String, Object>();
        map.put("adapter", genericDao.generate(new PostClosingTrialBalanceReportQuery(adapter)));
        map.put("criteria", filterCriteria);
        
        return map;
    }
    
    public FastMap<String,Object> viewprepost(DefaultAccountingReportFilterCriteria filterCriteria)
    {
        SimpleAccountingReportAdapter adapter = util.createAdapter(filterCriteria);

        FastMap<String,Object> map = new FastMap<String, Object>();
        map.put("adapter", genericDao.generate(new PrePostingTrialBalanceReportQuery(adapter)));
        map.put("criteria", filterCriteria);
        
        return map;
    }
    
    @SuppressWarnings("unchecked")
	public FastMap<String,Object> viewadvance(DefaultAccountingReportFilterCriteria filterCriteria)
    {
    	SimpleAccountingReportAdapter adapter = util.createAdapter(filterCriteria);
        
        FastMap<String,Object> map = new FastMap<String, Object>();
        
        ComplexConsecutiveReportAdapter complexAdapter = new ComplexConsecutiveReportAdapter();
        FastMap<String, Object> data = (FastMap<String, Object>)genericDao.generate(new TrialBalanceReportQuery(adapter));
        
        for(TrialBalanceReportAdapter reportAdapter : (List<TrialBalanceReportAdapter>)data.get("reports"))
        {
        	GLAccountProfitLossAdapter adapt = new GLAccountProfitLossAdapter();
        	adapt.setAccount(reportAdapter.getAccount());
        	adapt.setJanuary(reportAdapter.getOpening());
        	adapt.setFebuary(reportAdapter.getCredit());
        	adapt.setMarch(reportAdapter.getDebet());
        	adapt.setApril(reportAdapter.getClosing());
        	
        	complexAdapter.getExpenses().add(adapt);
        }
        
        map.put("adapter", complexAdapter);
        map.put("criteria",filterCriteria);
        
        return map;
    }
}

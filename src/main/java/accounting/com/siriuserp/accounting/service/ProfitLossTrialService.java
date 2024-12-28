/**
 * Dec 12, 2008 9:22:59 AM
 * com.siriuserp.reporting.accounting.service
 * ProfitLossService.java
 */
package com.siriuserp.accounting.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.accounting.dm.GLAccount;
import com.siriuserp.accounting.dm.IndexType;
import com.siriuserp.reporting.accounting.adapter.ComplexAccountingReportAdapter;
import com.siriuserp.reporting.accounting.adapter.IndexTypeReportAdapter;
import com.siriuserp.reporting.accounting.adapter.ProfitLossReportAdapter;
import com.siriuserp.reporting.accounting.criteria.ProfitLossFilterCriteria;
import com.siriuserp.reporting.accounting.query.ProfitLossTrialReportQuery;
import com.siriuserp.reporting.accounting.util.AccountingReportUtil;
import com.siriuserp.sdk.dao.GenericDao;

import javolution.util.FastMap;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Component
@Transactional(readOnly=true)
public class ProfitLossTrialService
{
    @Autowired
    private GenericDao genericDao;

    @Autowired
    private AccountingReportUtil util;

    public FastMap<String,Object> pre()
    {
        FastMap<String,Object> map = new FastMap<String, Object>();

        ProfitLossFilterCriteria filterCriteria = new ProfitLossFilterCriteria();

        for(IndexType indexType:genericDao.loadAll(IndexType.class))
        {
            IndexTypeReportAdapter reportAdapter = new IndexTypeReportAdapter();
            reportAdapter.setIndexType(indexType);

            filterCriteria.getIndexes().add(reportAdapter);
        }

        map.put("filterCriteria", filterCriteria);

        return map;
    }

    @SuppressWarnings("unchecked")
    public FastMap<String,Object> view(ProfitLossFilterCriteria filterCriteria)
    {
        FastMap<String,Object> map = new FastMap<String, Object>();

        ComplexAccountingReportAdapter reportAdapter = util.complex(new ComplexAccountingReportAdapter(), filterCriteria);

        ProfitLossTrialReportQuery query = new ProfitLossTrialReportQuery();
        query.setReportAdapter(reportAdapter);

        List<ProfitLossReportAdapter> data = genericDao.generateReport(query);

        for(ProfitLossReportAdapter adapter : data)
        {
        	GLAccount acc=genericDao.load(GLAccount.class,adapter.getAccount().getId());
        	adapter.setAccount(acc);
        	if (adapter.getAccount().getAccountType().getId().equals(Long.valueOf(4)))
        		reportAdapter.getRevenues().add(adapter);
			else if(adapter.getAccount().getAccountType().getId().equals(Long.valueOf(5)))
				reportAdapter.getExpenses().add(adapter);
			else if(adapter.getAccount().getAccountType().getId().equals(Long.valueOf(6)))
				reportAdapter.getCogs().add(adapter);
			else if(adapter.getAccount().getAccountType().getId().equals(Long.valueOf(7)))
				reportAdapter.getOtherrevenues().add(adapter);
			else if(adapter.getAccount().getAccountType().getId().equals(Long.valueOf(8)))
				reportAdapter.getOtherexpenses().add(adapter);
        }

        map.put("adapter", reportAdapter);
        map.put("criteria",filterCriteria);
        
        return map;
    }
}

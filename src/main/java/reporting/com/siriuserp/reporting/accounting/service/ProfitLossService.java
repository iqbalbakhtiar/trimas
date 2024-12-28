/**
 * Dec 12, 2008 9:22:59 AM
 * com.siriuserp.reporting.accounting.service
 * ProfitLossService.java
 */
package com.siriuserp.reporting.accounting.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.accounting.dao.AccountingPeriodDao;
import com.siriuserp.accounting.dm.IndexType;
import com.siriuserp.reporting.accounting.adapter.ComplexAccountingReportAdapter;
import com.siriuserp.reporting.accounting.adapter.ComplexConsecutiveReportAdapter;
import com.siriuserp.reporting.accounting.adapter.GLAccountProfitLossAdapter;
import com.siriuserp.reporting.accounting.adapter.IndexTypeReportAdapter;
import com.siriuserp.reporting.accounting.adapter.ProfitLossReportAdapter;
import com.siriuserp.reporting.accounting.criteria.ProfitLossFilterCriteria;
import com.siriuserp.reporting.accounting.query.DashboardNetIncomeQuery;
import com.siriuserp.reporting.accounting.query.DashboardTotalIncomeQuery;
import com.siriuserp.reporting.accounting.query.ProfitLossConsecutiveReportQuery;
import com.siriuserp.reporting.accounting.query.ProfitLossReportQuery;
import com.siriuserp.reporting.accounting.util.AccountingReportUtil;
import com.siriuserp.sdk.dao.CompanyStructureDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dm.Month;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.tools.util.ChartHelper;

import javolution.util.FastList;
import javolution.util.FastMap;

/**
 * @author Muhammad Rizal
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Component
@Transactional(readOnly=true)
@SuppressWarnings("unchecked")
public class ProfitLossService
{
    @Autowired
    private GenericDao genericDao;
    
    @Autowired 
    private AccountingPeriodDao accountingPeriodDao;
    
    @Autowired
    private CompanyStructureDao companyStructureDao;
    
    @Autowired
    private AccountingReportUtil util;
    
    @Autowired
    private GenericDao dao;
    
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
        
        map.put("years", accountingPeriodDao.loadAllAsYear());
        map.put("filterCriteria", filterCriteria);
        
        return map;
    }
    
    public FastMap<String,Object> view(ProfitLossFilterCriteria filterCriteria)
    {
        FastMap<String,Object> map = new FastMap<String, Object>();

        ComplexAccountingReportAdapter reportAdapter = util.complex(new ComplexAccountingReportAdapter(), filterCriteria);
        
        ProfitLossReportQuery query = new ProfitLossReportQuery();
        query.setReportAdapter(reportAdapter);
        
        List<ProfitLossReportAdapter> data = genericDao.generateReport(query);

        for(ProfitLossReportAdapter adapter : data)
        {
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
    
    public Map<String,Object> genProfitLossConsecutive(ProfitLossFilterCriteria filterCriteria)
    {
        Map<String,Object> map = new FastMap<String, Object>();
        
        if(filterCriteria.getOrg() != null)
        {
            FastList.recycle(filterCriteria.getOrganizationIds());
            	filterCriteria.getOrganizationIds().add(Long.valueOf(filterCriteria.getOrg()));
        }

        ComplexConsecutiveReportAdapter reportAdapter = (ComplexConsecutiveReportAdapter)util.complex(new ComplexConsecutiveReportAdapter(), filterCriteria);
        if(filterCriteria.getYear() != null)
        {
        	FastList.recycle(reportAdapter.getPeriods());
        		reportAdapter.getPeriods().addAll(accountingPeriodDao.getAllIdByYearAndMonth(filterCriteria.getYear(), null, filterCriteria.getOrg()));

        	FastList.recycle(reportAdapter.getJanPeriods());	
	        	reportAdapter.getJanPeriods().addAll(accountingPeriodDao.getAllIdByYearAndMonth(filterCriteria.getYear(), Month.JANUARY, filterCriteria.getOrg()));
	
	    	FastList.recycle(reportAdapter.getFebPeriods());	    
	    		reportAdapter.getFebPeriods().addAll(accountingPeriodDao.getAllIdByYearAndMonth(filterCriteria.getYear(), Month.FEBRUARY, filterCriteria.getOrg()));
	    
	    	FastList.recycle(reportAdapter.getMarPeriods());	    
	    		reportAdapter.getMarPeriods().addAll(accountingPeriodDao.getAllIdByYearAndMonth(filterCriteria.getYear(), Month.MARCH, filterCriteria.getOrg()));
	    	
		    FastList.recycle(reportAdapter.getAprPeriods());	    
		    	reportAdapter.getAprPeriods().addAll(accountingPeriodDao.getAllIdByYearAndMonth(filterCriteria.getYear(), Month.APRIL, filterCriteria.getOrg()));
		    	
		    FastList.recycle(reportAdapter.getMayPeriods());
		    	reportAdapter.getMayPeriods().addAll(accountingPeriodDao.getAllIdByYearAndMonth(filterCriteria.getYear(), Month.MAY, filterCriteria.getOrg()));
		    
		    FastList.recycle(reportAdapter.getJunPeriods());	
		    	reportAdapter.getJunPeriods().addAll(accountingPeriodDao.getAllIdByYearAndMonth(filterCriteria.getYear(), Month.JUNE, filterCriteria.getOrg()));
		    
		    FastList.recycle(reportAdapter.getJulPeriods());
		    	reportAdapter.getJulPeriods().addAll(accountingPeriodDao.getAllIdByYearAndMonth(filterCriteria.getYear(), Month.JULY, filterCriteria.getOrg()));
		    	
		    FastList.recycle(reportAdapter.getAugPeriods());
		    	reportAdapter.getAugPeriods().addAll(accountingPeriodDao.getAllIdByYearAndMonth(filterCriteria.getYear(), Month.AUGUST, filterCriteria.getOrg()));
		    	
		    FastList.recycle(reportAdapter.getSepPeriods());     
		    	reportAdapter.getSepPeriods().addAll(accountingPeriodDao.getAllIdByYearAndMonth(filterCriteria.getYear(), Month.SEPTEMBER, filterCriteria.getOrg()));
		    	
		    FastList.recycle(reportAdapter.getOctPeriods()); 
		    	reportAdapter.getOctPeriods().addAll(accountingPeriodDao.getAllIdByYearAndMonth(filterCriteria.getYear(), Month.OCTOBER, filterCriteria.getOrg()));
		    
		    FastList.recycle(reportAdapter.getNovPeriods());
		    	reportAdapter.getNovPeriods().addAll(accountingPeriodDao.getAllIdByYearAndMonth(filterCriteria.getYear(), Month.NOVEMBER, filterCriteria.getOrg()));
		
		    FastList.recycle(reportAdapter.getDecPeriods());
		    	reportAdapter.getDecPeriods().addAll(accountingPeriodDao.getAllIdByYearAndMonth(filterCriteria.getYear(), Month.DECEMBER, filterCriteria.getOrg()));
        }
        
        ProfitLossConsecutiveReportQuery query = new ProfitLossConsecutiveReportQuery();
        query.setReportAdapter(reportAdapter);

        List<GLAccountProfitLossAdapter> data = genericDao.generateReport(query);
     
        for(GLAccountProfitLossAdapter adapter : data)
        {
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
        map.put("criteria", filterCriteria);
        map.put("months", Month.values());
        
        return map;
    }
    
    @Transactional(readOnly=true,isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
    public FastMap<String,Object> loadprofitlosscons(String base, Long org, String period, String alias)
    {
    	Long company = null;
    	FastMap<String,Object> map = new FastMap<String, Object>();
    	
    	List<Party> orgs = new FastList<Party>();
    
    	try{
    	
    		orgs.addAll(companyStructureDao.loadAllVerticalDown(genericDao.load(Party.class, org)));
    	
    	}catch (NullPointerException e) {
			e.printStackTrace();
		}

    	if(orgs.size() == 0){
    		try{
    			company = (Long)companyStructureDao.loadParent(org).getId();
    		}catch (Exception e) {
    			company = org;
			}
    	}else{
    		company = org;
    	}
    	
        map.put("organization",genericDao.load(Party.class, org));
        map.put("period",period);
        map.put("alias", alias);
        map.put("reports", dao.generate(new DashboardNetIncomeQuery(org, Integer.parseInt(period), orgs, company)));
        
        ChartHelper.processProfitLoss(base, map);
        
        return map;
    }
    
    @Transactional(readOnly=true,isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
    public FastMap<String,Object> totalincome(String base, Long org, String period, String alias)
    {
    	FastMap<String,Object> map = new FastMap<String, Object>();
    	Long company = null;
    	List<Party> orgs = new FastList<Party>();
    	
    	try{
    		orgs.addAll(companyStructureDao.loadAllVerticalDown(genericDao.load(Party.class, org)));
    	}catch (NullPointerException e) {
			e.printStackTrace();
		}
    	

    	if(orgs.size() == 0){
    		try{
    			company = (Long)companyStructureDao.loadParent(org).getId();
    		}catch (Exception e) {
    			company = org;
			}
    	}else{
    		company = org;
    	}
    	
        map.put("organization",genericDao.load(Party.class, org));
        map.put("period",period);
        map.put("alias", alias);
        map.put("reports",dao.generateReport(new DashboardTotalIncomeQuery(org, Integer.parseInt(period), orgs, company)));
        
        ChartHelper.processTotalIncome(base, map);
        
        return map;
    }
}

/**
 * 
 */
package com.siriuserp.reporting.accounting.query;

import org.hibernate.Query;

import com.siriuserp.accounting.dm.GLPostingType;
import com.siriuserp.reporting.accounting.adapter.ComplexConsecutiveReportAdapter;
import com.siriuserp.sdk.db.AbstractAccountingReportQuery;

/**
 * @author
 * Betsu Brahmana Restu
 * PT. Sirius Indonesia
 * betsu@siriuserp.com
 */
public class BalanceSheetConsecutiveReportQuery extends AbstractAccountingReportQuery
{
	@Override
	public Object execute() 
	{
		ComplexConsecutiveReportAdapter accountingReportAdapter = (ComplexConsecutiveReportAdapter)reportAdapter;
		
		 StringBuilder builder = new StringBuilder();
	        builder.append("SELECT new com.siriuserp.reporting.accounting.adapter.GLAccountProfitLossAdapter(detail.account, " +
	        /*january*/ "SUM( CASE WHEN detail.postingType = :debet AND detail.journalEntry.accountingPeriod.id in(:jan) THEN (detail.amount*detail.journalEntry.exchange.rate) ELSE 0 END )-SUM( CASE WHEN detail.postingType = :credit AND detail.journalEntry.accountingPeriod.id in(:jan) THEN (detail.amount*detail.journalEntry.exchange.rate) ELSE 0 END ), " +
	        /*febuary*/	"SUM( CASE WHEN detail.postingType = :debet AND detail.journalEntry.accountingPeriod.id in(:feb) THEN (detail.amount*detail.journalEntry.exchange.rate) ELSE 0 END )-SUM( CASE WHEN detail.postingType = :credit AND detail.journalEntry.accountingPeriod.id in(:feb) THEN (detail.amount*detail.journalEntry.exchange.rate) ELSE 0 END ), " +
	        /*march*/	"SUM( CASE WHEN detail.postingType = :debet AND detail.journalEntry.accountingPeriod.id in(:mar) THEN (detail.amount*detail.journalEntry.exchange.rate) ELSE 0 END )-SUM( CASE WHEN detail.postingType = :credit AND detail.journalEntry.accountingPeriod.id in(:mar) THEN (detail.amount*detail.journalEntry.exchange.rate) ELSE 0 END ), " +  		
	        /*april*/	"SUM( CASE WHEN detail.postingType = :debet AND detail.journalEntry.accountingPeriod.id in(:apr) THEN (detail.amount*detail.journalEntry.exchange.rate) ELSE 0 END )-SUM( CASE WHEN detail.postingType = :credit AND detail.journalEntry.accountingPeriod.id in(:apr) THEN (detail.amount*detail.journalEntry.exchange.rate) ELSE 0 END ), " +
	        /*may*/		"SUM( CASE WHEN detail.postingType = :debet AND detail.journalEntry.accountingPeriod.id in(:may) THEN (detail.amount*detail.journalEntry.exchange.rate) ELSE 0 END )-SUM( CASE WHEN detail.postingType = :credit AND detail.journalEntry.accountingPeriod.id in(:may) THEN (detail.amount*detail.journalEntry.exchange.rate) ELSE 0 END ), " +
	        /*june*/	"SUM( CASE WHEN detail.postingType = :debet AND detail.journalEntry.accountingPeriod.id in(:jun) THEN (detail.amount*detail.journalEntry.exchange.rate) ELSE 0 END )-SUM( CASE WHEN detail.postingType = :credit AND detail.journalEntry.accountingPeriod.id in(:jun) THEN (detail.amount*detail.journalEntry.exchange.rate) ELSE 0 END ), " +
	        /*july*/	"SUM( CASE WHEN detail.postingType = :debet AND detail.journalEntry.accountingPeriod.id in(:jul) THEN (detail.amount*detail.journalEntry.exchange.rate) ELSE 0 END )-SUM( CASE WHEN detail.postingType = :credit AND detail.journalEntry.accountingPeriod.id in(:jul) THEN (detail.amount*detail.journalEntry.exchange.rate) ELSE 0 END ), " +
	        /*august*/	"SUM( CASE WHEN detail.postingType = :debet AND detail.journalEntry.accountingPeriod.id in(:aug) THEN (detail.amount*detail.journalEntry.exchange.rate) ELSE 0 END )-SUM( CASE WHEN detail.postingType = :credit AND detail.journalEntry.accountingPeriod.id in(:aug) THEN (detail.amount*detail.journalEntry.exchange.rate) ELSE 0 END ), " +
	        /*septmber*/"SUM( CASE WHEN detail.postingType = :debet AND detail.journalEntry.accountingPeriod.id in(:sep) THEN (detail.amount*detail.journalEntry.exchange.rate) ELSE 0 END )-SUM( CASE WHEN detail.postingType = :credit AND detail.journalEntry.accountingPeriod.id in(:sep) THEN (detail.amount*detail.journalEntry.exchange.rate) ELSE 0 END ), " +
	        /*october*/	"SUM( CASE WHEN detail.postingType = :debet AND detail.journalEntry.accountingPeriod.id in(:oct) THEN (detail.amount*detail.journalEntry.exchange.rate) ELSE 0 END )-SUM( CASE WHEN detail.postingType = :credit AND detail.journalEntry.accountingPeriod.id in(:oct) THEN (detail.amount*detail.journalEntry.exchange.rate) ELSE 0 END ), " +
	        /*november*/"SUM( CASE WHEN detail.postingType = :debet AND detail.journalEntry.accountingPeriod.id in(:nov) THEN (detail.amount*detail.journalEntry.exchange.rate) ELSE 0 END )-SUM( CASE WHEN detail.postingType = :credit AND detail.journalEntry.accountingPeriod.id in(:nov) THEN (detail.amount*detail.journalEntry.exchange.rate) ELSE 0 END ), " +
	        /*december*/"SUM( CASE WHEN detail.postingType = :debet AND detail.journalEntry.accountingPeriod.id in(:dec) THEN (detail.amount*detail.journalEntry.exchange.rate) ELSE 0 END )-SUM( CASE WHEN detail.postingType = :credit AND detail.journalEntry.accountingPeriod.id in(:dec) THEN (detail.amount*detail.journalEntry.exchange.rate) ELSE 0 END )) " +
	        
	        "FROM JournalEntryDetail detail WHERE detail.journalEntry.accountingPeriod.id in(:all) AND detail.journalEntry.entrySourceType != 'CLOSING' AND detail.journalEntry.entryStatus = 'POSTED' ");
	    
	        if(!accountingReportAdapter.getOrganizations().isEmpty())
	            builder.append("AND detail.journalEntry.organization.id in(:orgs) ");
	        
	        builder.append("GROUP BY detail.account.id ORDER BY detail.account.code ASC, detail.account.name ASC");
	        
	        Query query = getSession().createQuery(builder.toString());
	        query.setCacheable(true);
	        query.setReadOnly(true);
	 
	        query.setParameterList("all", accountingReportAdapter.getPeriods());
	        query.setParameterList("jan", accountingReportAdapter.getJanPeriods());
	        query.setParameterList("feb", accountingReportAdapter.getFebPeriods());
	        query.setParameterList("mar", accountingReportAdapter.getMarPeriods());
	        query.setParameterList("apr", accountingReportAdapter.getAprPeriods());
	        query.setParameterList("may", accountingReportAdapter.getMayPeriods());
	        query.setParameterList("jun", accountingReportAdapter.getJunPeriods());
	        query.setParameterList("jul", accountingReportAdapter.getJulPeriods());
	        query.setParameterList("aug", accountingReportAdapter.getAugPeriods());
	        query.setParameterList("sep", accountingReportAdapter.getSepPeriods());
	        query.setParameterList("oct", accountingReportAdapter.getOctPeriods());
	        query.setParameterList("nov", accountingReportAdapter.getNovPeriods());
	        query.setParameterList("dec", accountingReportAdapter.getDecPeriods());
	         
	        query.setParameter("debet",GLPostingType.DEBET);
	        query.setParameter("credit",GLPostingType.CREDIT);
	         
	        if(!accountingReportAdapter.getOrganizations().isEmpty())
	            query.setParameterList("orgs",accountingReportAdapter.getOrganizations());
	         
	        return query.list();
	}
}

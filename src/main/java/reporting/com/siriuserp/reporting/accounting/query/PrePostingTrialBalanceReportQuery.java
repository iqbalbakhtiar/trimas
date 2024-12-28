/**
 * Dec 3, 2008 4:15:10 PM
 * com.siriuserp.reporting.accounting.query
 * TrialBalanceReportQuery.java
 */
package com.siriuserp.reporting.accounting.query;

import java.util.List;

import org.hibernate.Query;

import com.siriuserp.accounting.adapter.SimpleAccountingReportAdapter;
import com.siriuserp.accounting.dm.GLPostingType;
import com.siriuserp.reporting.accounting.adapter.GLRegisterReportAdapter;
import com.siriuserp.sdk.db.AbstractAccountingReportQuery;

import javolution.util.FastList;
import javolution.util.FastMap;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@SuppressWarnings("unchecked")
public class PrePostingTrialBalanceReportQuery extends AbstractAccountingReportQuery
{
    public PrePostingTrialBalanceReportQuery(SimpleAccountingReportAdapter simpleAccountingReportAdapter)
    {
        this.reportAdapter = simpleAccountingReportAdapter;
    }
    
    public Object execute()
    {
    	FastMap<String, Object> map = new FastMap<String, Object>();

    	SimpleAccountingReportAdapter adapter = (SimpleAccountingReportAdapter) reportAdapter;
    
        map.put("reports", trial(adapter.getOpeningPeriods(), adapter.getPeriods(), adapter.getOrganizations()));
        
        return map;
    }

    //rewrite code
    private List<GLRegisterReportAdapter> trial(FastList<Long> openings, FastList<Long> periods, FastList<Long> orgs)
    {
        StringBuilder sql = new StringBuilder("SELECT new com.siriuserp.reporting.accounting.adapter.TrialBalanceReportAdapter(detail.account, " +
        		"SUM( CASE WHEN detail.postingType = :debet AND detail.journalEntry.accountingPeriod.id in(:opens) AND detail.journalEntry.entrySourceType = 'OPENING' THEN (detail.amount) ELSE 0 END )-SUM( CASE WHEN detail.postingType = :credit AND detail.journalEntry.accountingPeriod.id in(:opens) AND detail.journalEntry.entrySourceType = 'OPENING' THEN (detail.amount) ELSE 0 END ), " +
                "SUM( CASE WHEN detail.postingType = :debet AND detail.journalEntry.accountingPeriod.id in(:perds) AND detail.journalEntry.entrySourceType != 'CLOSING' AND detail.journalEntry.entrySourceType != 'OPENING' THEN (detail.amount) ELSE 0 END ), SUM( CASE WHEN detail.postingType = :credit AND detail.journalEntry.accountingPeriod.id in(:perds) AND detail.journalEntry.entrySourceType != 'CLOSING' AND detail.journalEntry.entrySourceType != 'OPENING' THEN (detail.amount) ELSE 0 END )) " +
        	"FROM JournalEntryDetail detail WHERE detail.id IS NOT NULL ");
        
        sql.append("AND detail.journalEntry.organization.id in(:orgs) ");
        sql.append("AND detail.journalEntry.accountingPeriod.id in(:all) ");
        sql.append(" GROUP BY detail.account.id ORDER BY detail.account.code ASC, detail.account.name ASC");
        
        FastList<Long> supers = new FastList<Long>();
        supers.addAll(openings);
        supers.addAll(periods);
        
        Query query = getSession().createQuery(sql.toString());
        query.setCacheable(true);
        query.setParameterList("orgs",orgs);
        query.setParameterList("opens", openings);
        query.setParameterList("perds", periods);
        query.setParameterList("all", supers);
        query.setParameter("debet",GLPostingType.DEBET);
        query.setParameter("credit",GLPostingType.CREDIT);

        return query.list();
    }
}

/**
 * Dec 4, 2008 3:47:49 PM
 * com.siriuserp.reporting.accounting.query
 * BalanceSheetReportQuery.java
 */
package com.siriuserp.reporting.accounting.query;

import java.util.List;

import org.hibernate.Query;

import com.siriuserp.accounting.dm.GLPostingType;
import com.siriuserp.reporting.accounting.adapter.ComplexAccountingReportAdapter;
import com.siriuserp.sdk.db.AbstractAccountingReportQuery;

/**
 * @author Muhammad Khairullah
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@SuppressWarnings("rawtypes")
public class BalanceSheetTrialReportQuery extends AbstractAccountingReportQuery
{
	public List execute()
	{
		ComplexAccountingReportAdapter accountingReportAdapter = (ComplexAccountingReportAdapter) reportAdapter;

		StringBuilder builder = new StringBuilder();
		builder.append("SELECT new com.siriuserp.reporting.accounting.adapter.ProfitLossReportAdapter(detail.account, ");
		builder.append("SUM(CASE WHEN detail.postingType = :debet THEN (detail.amount) ELSE 0 END )-SUM( CASE WHEN detail.postingType = :credit THEN (detail.amount) ELSE 0 END )) ");
		builder.append("FROM JournalEntryDetail detail WHERE detail.journalEntry.entrySourceType != 'CLOSING' ");

		if (!accountingReportAdapter.getPeriods().isEmpty())
			builder.append("AND detail.journalEntry.accountingPeriod.id IN(:periods) ");

		if (!accountingReportAdapter.getOrganizations().isEmpty())
			builder.append("AND detail.journalEntry.organization.id IN(:orgs) ");

		builder.append("GROUP BY detail.account.id ORDER BY detail.account.code ASC, detail.account.name ASC");

		Query query = getSession().createQuery(builder.toString());
		query.setParameter("debet", GLPostingType.DEBET);
		query.setParameter("credit", GLPostingType.CREDIT);

		if (!accountingReportAdapter.getPeriods().isEmpty())
			query.setParameterList("periods", accountingReportAdapter.getPeriods());

		if (!accountingReportAdapter.getOrganizations().isEmpty())
			query.setParameterList("orgs", accountingReportAdapter.getOrganizations());

		query.setReadOnly(true);
		query.setCacheable(true);
		
		return query.list();
	}
}

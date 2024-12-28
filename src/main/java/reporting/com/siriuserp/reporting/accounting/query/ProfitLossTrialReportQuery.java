/**
 * Dec 12, 2008 9:23:27 AM
 * com.siriuserp.reporting.accounting.query
 * ProfitLossReportQuery.java
 */
package com.siriuserp.reporting.accounting.query;

import java.util.List;

import org.hibernate.Query;

import com.siriuserp.accounting.dm.GLPostingType;
import com.siriuserp.reporting.accounting.adapter.ComplexAccountingReportAdapter;
import com.siriuserp.reporting.accounting.adapter.IndexTypeReportAdapter;
import com.siriuserp.reporting.accounting.adapter.ProfitLossReportAdapter;
import com.siriuserp.sdk.db.AbstractAccountingReportQuery;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@SuppressWarnings("unchecked")
public class ProfitLossTrialReportQuery extends AbstractAccountingReportQuery
{
	public List<ProfitLossReportAdapter> execute()
	{
		ComplexAccountingReportAdapter accountingReportAdapter = (ComplexAccountingReportAdapter) getReportAdapter();

		boolean and = true;

		String indexJoin = "";
		String org = "";

		if (!accountingReportAdapter.getAppliedIndex().isEmpty())
			indexJoin = " join detail.journalEntry.indexes eindex";

		if (!accountingReportAdapter.getOrganizations().isEmpty())
			org = " detail.journalEntry.organization.id in(:orgs) AND ";
		
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT new com.siriuserp.reporting.accounting.adapter.ProfitLossReportAdapter(detail.account,"
						+ " SUM( CASE WHEN detail.postingType = :debet THEN (detail.amount) ELSE 0 END )"
						+ " -SUM( CASE WHEN detail.postingType = :credit THEN (detail.amount) ELSE 0 END ))"
						+ " FROM JournalEntryDetail detail " + indexJoin
						+ " WHERE detail.journalEntry.entrySourceType != 'CLOSING'"
						+ " AND (" + org + " detail.journalEntry.entryStatus = 'POSTED'"
						+ " OR detail.journalEntry.entryStatus = 'BATCHED')"
						+ " AND detail.journalEntry.accountingPeriod.id in(:periods)");

		if (!accountingReportAdapter.getAppliedIndex().isEmpty())
		{
			int index = accountingReportAdapter.getAppliedIndex().size();

			if (and)
				builder.append(" AND ");

			builder.append("(");
			for (IndexTypeReportAdapter adapter : accountingReportAdapter.getAppliedIndex())
			{
				builder.append("(");
				builder.append("eindex.content = '" + adapter.getContent() + "'");
				builder.append(" AND ");
				builder.append("eindex.indexType.id = " + adapter.getIndexType().getId());
				builder.append(")");

				if (index > 1)
				{
					builder.append(" OR ");
					index--;
				}
			}

			builder.append(")");
		}

		builder.append(" GROUP BY detail.account.id ORDER BY detail.account.code ASC, detail.account.name ASC");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setParameterList("periods", accountingReportAdapter.getPeriods());
		query.setParameter("credit",GLPostingType.DEBET);
        query.setParameter("debet",GLPostingType.CREDIT);

		if (!accountingReportAdapter.getOrganizations().isEmpty())
			query.setParameterList("orgs", accountingReportAdapter.getOrganizations());
		
		return query.list();
	}
}

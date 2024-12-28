/**
 * Dec 11, 2008 2:30:31 PM
 * com.siriuserp.reporting.accounting.query
 * GLRegisterReportQuery.java
 */
package com.siriuserp.reporting.accounting.query;

import org.hibernate.Query;

import com.siriuserp.accounting.adapter.SimpleAccountingReportAdapter;
import com.siriuserp.accounting.dm.EntrySourceType;
import com.siriuserp.accounting.dm.GLPostingType;
import com.siriuserp.reporting.accounting.adapter.GLRegisterReportAdapter;
import com.siriuserp.sdk.db.AbstractAccountingReportQuery;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Muhammad Rizal
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public class GLRegisterReportQuery extends AbstractAccountingReportQuery
{
	public GLRegisterReportQuery(SimpleAccountingReportAdapter simpleAccountingReportAdapter)
	{
		setReportAdapter(simpleAccountingReportAdapter);
	}

	public GLRegisterReportAdapter execute()
	{
		SimpleAccountingReportAdapter adapter = (SimpleAccountingReportAdapter) reportAdapter;

		return doDefault(adapter);
	}

	private GLRegisterReportAdapter doDefault(SimpleAccountingReportAdapter adapter)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT NEW com.siriuserp.reporting.accounting.adapter.GLRegisterReportAdapter(d.account, ");
		builder.append("SUM(CASE WHEN d.postingType =:postingDebet THEN (d.amount) ELSE 0 END), ");
		builder.append("SUM(CASE WHEN d.postingType =:postingCredit THEN (d.amount) ELSE 0 END)) ");
		builder.append("FROM JournalEntryDetail d WHERE d.account.id =:account ");

		if (SiriusValidator.validateDate(adapter.getDateFrom()) || SiriusValidator.validateDate(adapter.getDateTo()))
		{
			if (SiriusValidator.validateDate(adapter.getDateFrom()))
			{
				if (SiriusValidator.validateDate(adapter.getDateTo()))
					builder.append("AND d.journalEntry.entryDate BETWEEN :startDate AND :endDate ");
				else
					builder.append("AND d.journalEntry.entryDate >= :startDate ");
			} else if (SiriusValidator.validateDate(adapter.getDateTo()))
				builder.append("AND d.journalEntry.entryDate <= :endDate ");
		} else if (!adapter.getOpeningPeriods().isEmpty())
			builder.append("AND d.journalEntry.accountingPeriod.id IN(:periods) ");

		builder.append("AND d.journalEntry.organization.id IN(:orgs) ");
		builder.append("AND d.journalEntry.entrySourceType =:entrySourceType ");
		builder.append("GROUP BY d.account.id ");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setReadOnly(true);

		if (SiriusValidator.validateDate(adapter.getDateFrom()) || SiriusValidator.validateDate(adapter.getDateTo()))
		{
			if (SiriusValidator.validateDate(adapter.getDateFrom()))
				query.setParameter("startDate", adapter.getDateFrom());

			if (SiriusValidator.validateDate(adapter.getDateTo()))
				query.setParameter("endDate", adapter.getDateTo());
		} else if (!adapter.getOpeningPeriods().isEmpty())
			query.setParameterList("periods", adapter.getOpeningPeriods());

		query.setParameterList("orgs", adapter.getOrganizations());
		query.setParameter("account", adapter.getAccount().getId());
		query.setParameter("entrySourceType", EntrySourceType.OPENING);
		query.setParameter("postingDebet", GLPostingType.DEBET);
		query.setParameter("postingCredit", GLPostingType.CREDIT);

		return (GLRegisterReportAdapter) query.uniqueResult();
	}
}

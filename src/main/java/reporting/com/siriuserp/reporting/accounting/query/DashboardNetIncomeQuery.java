package com.siriuserp.reporting.accounting.query;

import java.util.List;

import org.hibernate.Query;

import com.siriuserp.reporting.accounting.adapter.NetIncomeDashboardReportAdapter;
import com.siriuserp.sdk.db.AbstractStandardReportQuery;
import com.siriuserp.sdk.dm.Party;

import javolution.util.FastList;
@SuppressWarnings({"unchecked","unused"})
public class DashboardNetIncomeQuery  extends AbstractStandardReportQuery
{	
	private Long organization;

	private int period;
	
	private List<Party> subsidiaries;
	
	private Long parent;
	
	public DashboardNetIncomeQuery(Long organization, int period, List<Party> subsidiaries, Long parent) {
		super();
		this.organization = organization;
		this.period = period;
		this.subsidiaries = subsidiaries;
		this.parent = parent;
	}

	@Override
	public Object execute() 
	{
		List<NetIncomeDashboardReportAdapter> list = new FastList<NetIncomeDashboardReportAdapter>();
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT DISTINCT new com.siriuserp.reporting.accounting.adapter.NetIncomeDashboardReportAdapter(SUM(acb.systemTransaction.closingdebet-acb.systemTransaction.closingcredit), acb.accountingPeriod.month ) FROM GLAccountBalance acb ");
		
		if(subsidiaries.size() > 0)
			builder.append("WHERE acb.accountingPeriod.organization.id = :org ");
		else
			builder.append("WHERE acb.organization.id  = :org ");

		builder.append("AND acb.accountingPeriod.year = :periodYear ");
		builder.append("AND acb.account.accountType.id > 3 ");
		builder.append("GROUP BY acb.accountingPeriod.month ");
		builder.append("ORDER BY acb.accountingPeriod.month.id ASC ");

		Query query = getSession().createQuery(builder.toString());
		
		if(subsidiaries.size() > 0)
			query.setParameter("org", organization);
		else
			query.setParameter("org", organization);

		query.setParameter("periodYear", period);
		list.addAll(query.list());
		
		return list;
	}
}

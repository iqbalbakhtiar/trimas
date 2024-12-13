package com.siriuserp.accounting.query;

import com.siriuserp.accounting.adapter.LedgerDetailAdapter;
import com.siriuserp.accounting.criteria.LedgerSummaryReportFilterCriteria;
import com.siriuserp.sdk.db.AbstractStandardReportQuery;
import com.siriuserp.sdk.utility.DateHelper;
import javolution.util.FastList;
import javolution.util.FastMap;
import org.hibernate.Query;

import java.util.Date;

@SuppressWarnings("unchecked")
public class LedgerSummaryReportQuery extends AbstractStandardReportQuery {
    @Override
    public Object execute() {
        FastMap<String,Object> map = new FastMap<String, Object>();

        LedgerSummaryReportFilterCriteria criteria = (LedgerSummaryReportFilterCriteria) getFilterCriteria();

        Date dateTo = DateHelper.toEndDate(criteria.getDate());

        if (dateTo == null)
            dateTo = DateHelper.today();

        Date dateFrom = DateHelper.toStartDate(dateTo);

        map.put("dateTo", dateTo);
        map.put("dateFrom", dateFrom);
        map.put("details", getAdapters(criteria.getOrganization(), dateFrom, dateTo));

        return map;
    }

    public FastList<LedgerDetailAdapter> getAdapters(Long org, Date dateFrom, Date dateTo)
    {
        FastList<LedgerDetailAdapter> adapters = new FastList<LedgerDetailAdapter>();

        StringBuilder builder = new StringBuilder();
        builder.append("SELECT new com.siriuserp.accounting.adapter.LedgerDetailAdapter(bill.customer, ");
        builder.append("SUM(CASE WHEN bill.date <:dateFrom THEN (bill.money.amount*bill.money.rate) ELSE 0 END), ");
        builder.append("(SELECT SUM((application.paidAmount*application.receipt.rate)-(application.writeOff*application.receipt.rate)) ")
                .append("FROM ReceiptApplication application JOIN application.receipt receipt WHERE receipt.realDate < :dateFrom ")
                .append("AND application.billing.organization.id =:org AND application.billing.customer.id = bill.customer.id), ");

        builder.append("(SELECT SUM(memo.amount*memo.billing.money.rate) FROM CreditMemo memo ")
                .append("WHERE memo.organization.id =:org AND memo.date < :dateFrom AND memo.customer.id = bill.customer.id), ");

        builder.append("SUM(CASE WHEN bill.date BETWEEN :dateFrom AND :dateTo THEN (bill.money.amount*bill.money.rate) ELSE 0 END), ");
        builder.append("(SELECT SUM((application.paidAmount*application.receipt.rate)-(application.writeOff*application.receipt.rate)) ")
                .append("FROM ReceiptApplication application JOIN application.receipt receipt WHERE receipt.realDate BETWEEN :dateFrom AND :dateTo ");

        builder.append("AND application.billing.organization.id =:org AND application.billing.customer.id = bill.customer.id), ");
        builder.append("(SELECT SUM(memo.amount*memo.billing.money.rate) ")
                .append("FROM CreditMemo memo WHERE memo.organization.id =:org AND memo.date BETWEEN :dateFrom AND :dateTo AND memo.customer.id = bill.customer.id)) ");

        builder.append("FROM Billing bill WHERE bill.organization.id = :org ");
        builder.append("AND bill.date <=:dateTo ");
        //builder.append("AND bill.unpaid > 1 ");
        //builder.append("AND bill.status =:status ");
        builder.append("GROUP BY bill.customer.id ORDER BY bill.customer.fullName ASC");

        Query query = getSession().createQuery(builder.toString());
        query.setCacheable(true);
        query.setReadOnly(true);
        //query.setParameter("type", PaymentMethodType.CLEARING);
        //query.setParameter("enabled", Boolean.TRUE);
        query.setParameter("dateFrom", dateFrom);
        query.setParameter("dateTo", dateTo);
        query.setParameter("org", org);
        //query.setParameter("status", FinancialStatus.UNPAID);

        adapters.addAll(query.list());

        return adapters;
    }
}

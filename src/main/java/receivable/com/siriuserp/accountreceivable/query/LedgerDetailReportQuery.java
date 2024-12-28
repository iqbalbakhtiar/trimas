package com.siriuserp.accountreceivable.query;

import com.siriuserp.accountreceivable.adapter.LedgerDetailAdapter;
import com.siriuserp.accountreceivable.adapter.LedgerDetailAdapterComparator;
import com.siriuserp.accountreceivable.criteria.LedgerSummaryReportFilterCriteria;
import com.siriuserp.sdk.db.AbstractStandardReportQuery;
import com.siriuserp.sdk.dm.PaymentMethodType;
import com.siriuserp.sdk.utility.DateHelper;
import com.siriuserp.sdk.utility.SiriusValidator;
import javolution.util.FastList;
import javolution.util.FastMap;
import org.hibernate.Query;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;

/**
 * This class provides query logic to generate ledger detail reports based on customer transactions.
 */
@SuppressWarnings("unchecked")
public class LedgerDetailReportQuery extends AbstractStandardReportQuery {
    /**
     * Executes the query and returns a detailed ledger report.
     *
     * @return Report data containing customer transactions and balances.
     */
    @Override
    public Object execute()
    {
        FastMap<String, Object> report = new FastMap<String, Object>();
        LedgerSummaryReportFilterCriteria criteria = (LedgerSummaryReportFilterCriteria) getFilterCriteria();

        Date dateTo = criteria.getDate();

        if (dateTo == null)
            dateTo = DateHelper.today();

        Date dateFrom = DateHelper.toStartDate(dateTo);

        FastList<FastMap<String, Object>> reports = new FastList<FastMap<String, Object>>();

        BigDecimal total = BigDecimal.ZERO;
        BigDecimal credit = BigDecimal.ZERO;
        BigDecimal debit = BigDecimal.ZERO;

        for (LedgerDetailAdapter adapter : getCustomer(criteria.getOrganization(), criteria.getCustomer(), dateFrom, dateTo))
        {
            if (adapter.getOpeningBalance().compareTo(BigDecimal.ZERO) > 0 || adapter.getCredit().compareTo(BigDecimal.ZERO) > 0 || adapter.getClosingBalance().compareTo(BigDecimal.ZERO) > 0)
            {
                FastMap<String, Object> map = new FastMap<String, Object>();
                map.put("customer", adapter.getCustomer().getFullName());
                map.put("opening", adapter.getOpeningBalance());
                map.put("closing", adapter.getClosingBalance());

                FastList<LedgerDetailAdapter> details = new FastList<LedgerDetailAdapter>();
                details.addAll(getBillings(adapter.getCustomer().getId(), criteria.getOrganization(), dateFrom, dateTo));
                details.addAll(getMemos(adapter.getCustomer().getId(), criteria.getOrganization(), dateFrom, dateTo));
                details.addAll(getApplications(adapter.getCustomer().getId(), criteria.getOrganization(), dateFrom, dateTo));

                Collections.sort(details, new LedgerDetailAdapterComparator());

                if (!details.isEmpty())
                    map.put("details", details);

                reports.add(map);

                debit = debit.add(adapter.getDebit());
                credit = credit.add(adapter.getCredit());
                total = total.add(adapter.getClosingBalance());
            }
        }

        if (!reports.isEmpty())
        {
            report.put("reports", reports);
            report.put("debit", debit);
            report.put("credit", credit);
        }

        report.put("total", total);
        report.put("dateTo", dateTo);
        report.put("dateFrom", dateFrom);

        return report;
    }

    /**
     * Retrieves customer balances and opening/closing amounts.
     * Can be more than 1 customer depending on the filter or customer group
     */
    public FastList<LedgerDetailAdapter> getCustomer(Long org, Long cust, Date dateFrom, Date dateTo) {
        FastList<LedgerDetailAdapter> partys = new FastList<LedgerDetailAdapter>();

        StringBuilder builder = new StringBuilder();
        // Build Adapter with 7 Param Constructor
        builder.append("SELECT new com.siriuserp.accountreceivable.adapter.LedgerDetailAdapter(");
        // Set customer from bill
        builder.append("bill.customer, ");

        // (OpeningStd) Sum Of Billing Amount before the start date
        builder.append("SUM(CASE WHEN bill.date <:dateFrom THEN (bill.money.amount * bill.money.rate) ELSE 0 END), ");

        // (OpeningMan) Sum Of Receipt Amount before the start date
        builder.append("(SELECT SUM((application.paidAmount * application.receipt.rate) - (application.writeOff * application.receipt.rate)) ")
                .append("FROM ReceiptApplication application JOIN application.receipt receipt WHERE receipt.realDate < :dateFrom ")
                .append("AND application.billing.organization.id = :org AND application.billing.customer.id = bill.customer.id), ");

        // (OpeningCredit) Sum Of Credit Memo before the start date
        builder.append("(SELECT SUM(memo.amount * memo.billing.money.rate) FROM CreditMemo memo ")
                .append("WHERE memo.organization.id = :org AND memo.date < :dateFrom AND memo.customer.id = bill.customer.id), ");

        // (Debit) Sum Of billing within the date range
        builder.append("SUM(CASE WHEN bill.date BETWEEN :dateFrom AND :dateTo THEN (bill.money.amount * bill.money.rate) ELSE 0 END), ");

        // (Credit) Sum of receipt within the date range
        builder.append("(SELECT SUM((application.paidAmount * application.receipt.rate) - (application.writeOff * application.receipt.rate)) ")
                .append("FROM ReceiptApplication application JOIN application.receipt receipt WHERE receipt.realDate BETWEEN :dateFrom AND :dateTo ")
                .append("AND application.billing.organization.id = :org AND application.billing.customer.id = bill.customer.id), ");

        // (Memo) Sum of Credit Memo within the date range
        builder.append("(SELECT SUM(memo.amount * memo.billing.money.rate) ")
                .append("FROM CreditMemo memo WHERE memo.organization.id = :org AND memo.date BETWEEN :dateFrom AND :dateTo AND memo.customer.id = bill.customer.id)) ");

        builder.append("FROM Billing bill WHERE bill.organization.id = :org ");
        builder.append("AND bill.date <= :dateTo ");

        if (SiriusValidator.validateLongParam(cust)) {
            // Check if the bill is directly linked to the given customer ID
            builder.append("AND (bill.customer.id = :cust ");

            // Check if the bill is linked to a customer that belongs to the specified party group
            builder.append("OR bill.customer.id IN (");
            builder.append("SELECT party.id FROM Party party WHERE party.partyGroup.id = :cust)) ");
        }

        // Each adapter will be group by customer
        builder.append("GROUP BY bill.customer.id ORDER BY bill.customer.fullName ASC");

        Query query = getSession().createQuery(builder.toString());

        if (SiriusValidator.validateLongParam(cust))
            query.setParameter("cust", cust);

        query.setParameter("dateFrom", dateFrom);
        query.setParameter("dateTo", dateTo);
        query.setParameter("org", org);

        query.setCacheable(true);
        query.setReadOnly(true);

        partys.addAll(query.list());

        return partys;
    }

    /**
     * Retrieves customer billing records within the specified date range.
     * Filters by customer ID or customers belonging to a specified party group.
     */
    public FastList<LedgerDetailAdapter> getBillings(Long cust, Long org, Date dateFrom, Date dateTo)
    {
        FastList<LedgerDetailAdapter> billing = new FastList<LedgerDetailAdapter>();

        // Build the query to retrieve billing records
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT new com.siriuserp.accountreceivable.adapter.LedgerDetailAdapter(");
        builder.append("bill.date, bill, bill.money.amount * bill.money.rate) ");
        builder.append("FROM Billing bill ");
        builder.append("WHERE bill.date BETWEEN :dateFrom AND :dateTo ");
        builder.append("AND bill.organization.id = :org ");

        // Filter by individual customer or customer group
        builder.append("AND (bill.customer.id = :cust ");
        builder.append("OR bill.customer.id IN (");
        builder.append("SELECT party.id FROM Party party WHERE party.partyGroup.id = :cust)) ");

        builder.append("ORDER BY bill.date ");

        Query query = getSession().createQuery(builder.toString());
        query.setParameter("cust", cust);
        query.setParameter("dateFrom", dateFrom);
        query.setParameter("dateTo", dateTo);
        query.setParameter("org", org);

        query.setCacheable(true);
        query.setReadOnly(true);

        billing.addAll(query.list());

        return billing;
    }

    /**
     * Retrieves customer memos within the specified date range.
     * Filters by customer ID or customers belonging to a specified party group.
     */
    public FastList<LedgerDetailAdapter> getMemos(Long cust, Long org, Date dateFrom, Date dateTo)
    {
        FastList<LedgerDetailAdapter> memos = new FastList<LedgerDetailAdapter>();

        // Build the query to retrieve memo records
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT new com.siriuserp.accountreceivable.adapter.LedgerDetailAdapter(");
        builder.append("memo.date, memo, memo.amount * memo.billing.money.rate) ");
        builder.append("FROM CreditMemo memo ");
        builder.append("WHERE memo.date BETWEEN :dateFrom AND :dateTo ");
        builder.append("AND memo.organization.id = :org ");

        // Filter by individual customer or customer group
        builder.append("AND (memo.customer.id = :cust ");
        builder.append("OR memo.customer.id IN (");
        builder.append("SELECT party.id FROM Party party WHERE party.partyGroup.id = :cust)) ");

        builder.append("ORDER BY memo.date ");

        Query query = getSession().createQuery(builder.toString());
        query.setParameter("cust", cust);
        query.setParameter("dateFrom", dateFrom);
        query.setParameter("dateTo", dateTo);
        query.setParameter("org", org);

        query.setCacheable(true);
        query.setReadOnly(true);

        memos.addAll(query.list());

        return memos;
    }

    /**
     * Retrieves applications of customer payments (receipt) within the specified date range.
     * Filters by billed customer ID or customers belonging to a specified party group.
     */
    public FastList<LedgerDetailAdapter> getApplications(Long cust, Long org, Date dateFrom, Date dateTo)
    {
        FastList<LedgerDetailAdapter> applications = new FastList<LedgerDetailAdapter>();

        // Build the query to retrieve payment applications
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT new com.siriuserp.accountreceivable.adapter.LedgerDetailAdapter(");
        builder.append("application.receipt.realDate, application, ");
        builder.append("(application.paidAmount * application.receipt.rate) - (application.writeOff * application.receipt.rate)) ");
        builder.append("FROM ReceiptApplication application JOIN application.receipt receipt ");
        builder.append("WHERE receipt.realDate BETWEEN :dateFrom AND :dateTo ");
        builder.append("AND ((receipt.receiptInformation.paymentMethodType = :type AND receipt.cleared = :enabled) ");
        builder.append("OR (receipt.receiptInformation.paymentMethodType != :type AND receipt.cleared != :enabled)) ");
        builder.append("AND receipt.organization.id = :org ");

        // Filter based on billed customer, either directly or through a party group
        builder.append("AND (application.billing.customer.id = :cust ");
        builder.append("OR application.billing.customer.id IN (");
        builder.append("SELECT party.id FROM Party party WHERE party.partyGroup.id = :cust)) ");

        builder.append("ORDER BY receipt.realDate ");

        Query query = getSession().createQuery(builder.toString());
        query.setParameter("cust", cust);
        query.setParameter("dateFrom", dateFrom);
        query.setParameter("dateTo", dateTo);
        query.setParameter("org", org);
        query.setParameter("type", PaymentMethodType.CLEARING);
        query.setParameter("enabled", Boolean.TRUE);

        applications.addAll(query.list());

        return applications;
    }
}

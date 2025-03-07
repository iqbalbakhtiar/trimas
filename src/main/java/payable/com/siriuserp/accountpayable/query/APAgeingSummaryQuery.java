package com.siriuserp.accountpayable.query;

import com.siriuserp.accountpayable.adapter.APAgeingDetailAdapter;
import com.siriuserp.accountpayable.criteria.APAgeingFilterCriteria;
import com.siriuserp.accountpayable.dm.Payable;
import com.siriuserp.accountpayable.dm.PaymentApplication;
import com.siriuserp.sdk.db.AbstractStandardReportQuery;
import com.siriuserp.sdk.utility.DateHelper;
import com.siriuserp.sdk.utility.SiriusValidator;
import javolution.util.FastList;
import javolution.util.FastMap;
import org.hibernate.Query;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

/**
 * @author Agung Dodi Perdana
 * @author Betsu Brahmana Restu
 * @author Rama Almer Felix
 * Sirius Indonesia, PT
 * www.siriuserp.com
 * Version 1.5
 */

@SuppressWarnings("unchecked")
public class APAgeingSummaryQuery extends AbstractStandardReportQuery {
    @Override
    public Object execute()
    {
        FastMap<String, Object> mapList = new FastMap<String, Object>();
        FastList<APAgeingDetailAdapter> list = new FastList<APAgeingDetailAdapter>();

        APAgeingFilterCriteria criteria = (APAgeingFilterCriteria) getFilterCriteria();
        Date end = criteria.getDate();

        BigDecimal totBalance = BigDecimal.ZERO;
        BigDecimal totNyd = BigDecimal.ZERO;
        BigDecimal totFsod = BigDecimal.ZERO;
        BigDecimal totSod = BigDecimal.ZERO;
        BigDecimal totTod = BigDecimal.ZERO;
        BigDecimal totFtod = BigDecimal.ZERO;

        APAgeingDetailAdapter adapter = new APAgeingDetailAdapter();

        for(Payable verification : getPayables())
        {
            long milis = end.getTime() - verification.getDueDate().getTime();
            int days = DateHelper.getDays(milis);

            BigDecimal unpaidAmount = verification.getMoney().getAmount();

            for (PaymentApplication application : verification.getApplications())
                if (application.getPayment().getDate().compareTo(end) <= 0)
                    unpaidAmount = unpaidAmount.subtract(application.getPaidAmount().subtract(application.getWriteOff()));

            // Not Implemented yet
//            for (DebitMemo memo : verification.getDebitMemos())
//                if (memo.getDate().compareTo(end) <= 0)
//                    unpaidAmount = unpaidAmount.subtract(memo.getAmount());

            if (unpaidAmount.abs().compareTo(BigDecimal.ONE) > 0)
            {
                if(adapter.getSupplier() == null)
                {
                    adapter.setSupplier(verification.getSupplier());

                    list.add(adapter);
                }
                else if(adapter.getSupplier() != null && adapter.getSupplier().getId().compareTo(verification.getSupplier().getId()) != 0)
                {
                    adapter = new APAgeingDetailAdapter();
                    adapter.setSupplier(verification.getSupplier());

                    list.add(adapter);
                }

                adapter.setBalance(adapter.getBalance().add(unpaidAmount));

                if (days <= 0)
                {
                    adapter.setNotYetDue(adapter.getNotYetDue().add(unpaidAmount));
                } else if (days <= 30)
                {
                    adapter.setFstOverDue(adapter.getFstOverDue().add(unpaidAmount));
                } else if (days <= 60)
                {
                    adapter.setSndOverDue(adapter.getSndOverDue().add(unpaidAmount));
                } else if (days <= 90)
                {
                    adapter.setThdOverDue(adapter.getThdOverDue().add(unpaidAmount));
                } else
                {
                    adapter.setFthOverDue(adapter.getFthOverDue().add(unpaidAmount));
                }
            }

            totBalance = totBalance.add(unpaidAmount);

            if (days <= 0)
                totNyd = totNyd.add(unpaidAmount);
            else if (days <= 30)
                totFsod = totFsod.add(unpaidAmount);
            else if (days <= 60)
                totSod = totSod.add(unpaidAmount);
            else if (days <= 90)
                totTod = totTod.add(unpaidAmount);
            else
                totFtod = totFtod.add(unpaidAmount);
        }

        if (!list.isEmpty())
        {
            mapList.put("list", list);
            mapList.put("totBalance", totBalance);
            mapList.put("totNyd", totNyd);
            mapList.put("totFsod", totFsod);
            mapList.put("totSod", totSod);
            mapList.put("totTod", totTod);
            mapList.put("totFtod", totFtod);

            BigDecimal divider = BigDecimal.ONE;
            if (totBalance.compareTo(BigDecimal.ZERO) != 0)
                divider = totBalance;

            if (totNyd.compareTo(BigDecimal.ZERO) > 0)
                mapList.put("notYetDuePercent", totNyd.divide(divider, 6, RoundingMode.HALF_EVEN).multiply(new BigDecimal(100)));
            else
                mapList.put("notYetDuePercent", BigDecimal.ZERO);

            if (totFsod.compareTo(BigDecimal.ZERO) > 0)
                mapList.put("fstMonthPercent", totFsod.divide(divider, 6, RoundingMode.HALF_EVEN).multiply(new BigDecimal(100)));
            else
                mapList.put("fstMonthPercent", BigDecimal.ZERO);

            if (totSod.compareTo(BigDecimal.ZERO) > 0)
                mapList.put("sndMonthPercent", totSod.divide(divider, 6, RoundingMode.HALF_EVEN).multiply(new BigDecimal(100)));
            else
                mapList.put("sndMonthPercent", BigDecimal.ZERO);

            if (totTod.compareTo(BigDecimal.ZERO) > 0)
                mapList.put("thdMonthPercent", totTod.divide(divider, 6, RoundingMode.HALF_EVEN).multiply(new BigDecimal(100)));
            else
                mapList.put("thdMonthPercent", BigDecimal.ZERO);

            if (totFtod.compareTo(BigDecimal.ZERO) > 0)
                mapList.put("fthMonthPercent", totFtod.divide(divider, 6, RoundingMode.HALF_EVEN).multiply(new BigDecimal(100)));
            else
                mapList.put("fthMonthPercent", BigDecimal.ZERO);
        }

        return mapList;
    }

    private List<Payable> getPayables()
    {
        APAgeingFilterCriteria criteria = (APAgeingFilterCriteria)getFilterCriteria();

        StringBuilder builder = new StringBuilder();
        builder.append("FROM Payable ver ");
        builder.append("WHERE ver.date <= :date ");

        if(SiriusValidator.validateLongParam(criteria.getSupplier()))
            builder.append("AND ver.supplier.id =:supplier ");

        if (SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()))
            builder.append("AND ver.organization.id =:org ");

        builder.append("ORDER BY ver.supplier.id, ver.date, ver.code ");

        Query query = getSession().createQuery(builder.toString());
        query.setReadOnly(true);
        query.setCacheable(true);
        query.setParameter("date", criteria.getDate());

        if(SiriusValidator.validateLongParam(criteria.getSupplier()))
            query.setParameter("supplier", criteria.getSupplier());

        if (SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()))
            query.setParameter("org", criteria.getOrganization());

        return query.list();
    }
}


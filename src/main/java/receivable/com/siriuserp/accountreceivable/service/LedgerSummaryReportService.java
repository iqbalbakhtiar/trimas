package com.siriuserp.accountreceivable.service;

import com.siriuserp.accountreceivable.criteria.LedgerSummaryReportFilterCriteria;
import com.siriuserp.accountreceivable.query.LedgerSummaryReportQuery;
import com.siriuserp.sdk.annotation.InjectParty;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.filter.AbstractReportFilterCriteria;
import com.siriuserp.sdk.utility.DateHelper;
import javolution.util.FastMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

@Component
@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
public class LedgerSummaryReportService {
    @Autowired
    private GenericDao genericDao;

    @InjectParty
    public Map<String,Object> pre()
    {
        FastMap<String, Object> map = new FastMap<String, Object>();
        map.put("ledgerCriteria", new LedgerSummaryReportFilterCriteria());

        return map;
    }

    public Map<String, Object> view(AbstractReportFilterCriteria filter)
    {
        LedgerSummaryReportFilterCriteria criteria = (LedgerSummaryReportFilterCriteria)filter;

        LedgerSummaryReportQuery query = new LedgerSummaryReportQuery();
        query.setFilterCriteria(criteria);

        Map<String, Object> map = new FastMap<String, Object>();
        map.put("report", genericDao.generate(query));
        map.put("criteria", criteria);
        map.put("organization", genericDao.load(Party.class, criteria.getOrganization()));

        return map;
    }

    public LedgerSummaryReportFilterCriteria createMonth(LedgerSummaryReportFilterCriteria criteria)
    {
        Date now = criteria.getDate();

        criteria.setNext(DateHelper.plusOneMonth(now));
        criteria.setPrev(DateHelper.minusOneMonth(now));

        return criteria;
    }
}

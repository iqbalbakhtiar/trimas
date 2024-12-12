package com.siriuserp.accounting.service;

import com.siriuserp.accounting.criteria.LedgerSummaryReportFilterCriteria;
import com.siriuserp.accounting.query.LedgerDetailReportQuery;
import com.siriuserp.sdk.annotation.InjectParty;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.filter.AbstractReportFilterCriteria;
import com.siriuserp.sdk.utility.DateHelper;
import com.siriuserp.sdk.utility.SiriusValidator;
import javolution.util.FastMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
public class LedgerDetailReportService {
    @Autowired
    private GenericDao genericDao;

    @InjectParty
    public Map<String, Object> pre()
    {
        FastMap<String, Object> map = new FastMap<String, Object>();
        map.put("ledgerCriteria", new LedgerSummaryReportFilterCriteria());

        return map;
    }

    public LedgerSummaryReportFilterCriteria createMonth(LedgerSummaryReportFilterCriteria criteria)
    {
        Date now = criteria.getDate();

        criteria.setNext(DateHelper.plusOneMonth(now));
        criteria.setPrev(DateHelper.minusOneMonth(now));

        return criteria;
    }

    public Map<String, Object> view(AbstractReportFilterCriteria filter)
    {
        LedgerSummaryReportFilterCriteria criteria = (LedgerSummaryReportFilterCriteria) filter;

        LedgerDetailReportQuery query = new LedgerDetailReportQuery();
        query.setFilterCriteria(criteria);

        Map<String, Object> map = new HashMap<String, Object>();

        if (SiriusValidator.validateLongParam(criteria.getCustomer()))
            map.put("customer", genericDao.load(Party.class, criteria.getCustomer()));

        map.put("criteria", criteria);
        map.put("organization", genericDao.load(Party.class, criteria.getOrganization()));
        map.put("report", genericDao.generate(query));

        return map;
    }
}

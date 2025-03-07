package com.siriuserp.accountpayable.service;

import com.siriuserp.accountpayable.criteria.APAgeingFilterCriteria;
import com.siriuserp.accountpayable.query.APAgeingDetailQuery;
import com.siriuserp.accountpayable.query.APAgeingSummaryQuery;
import com.siriuserp.sdk.annotation.InjectParty;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.utility.DateHelper;
import javolution.util.FastMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * @author Agung Dodi Perdana
 * @author Rama Almer Felix
 * Sirius Indonesia, PT
 * www.siriuserp.com
 * Version 1.5
 */

@Component
@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
public class APAgeingService {
    @Autowired
    private GenericDao genericDao;

    @InjectParty
    public Map<String, Object> pre()
    {
        FastMap<String, Object> map = new FastMap<String, Object>();
        map.put("criteria", new APAgeingFilterCriteria());

        return map;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> view(APAgeingFilterCriteria criteria)
    {
        APAgeingSummaryQuery query = new APAgeingSummaryQuery();
        query.setFilterCriteria(criteria);

        Map<String, Object> map = (FastMap<String, Object>) genericDao.generate(query);
        map.put("organization", genericDao.load(Party.class, criteria.getOrganization()));
        map.put("criteria", criteria);

        return map;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> viewdetail(APAgeingFilterCriteria criteria)
    {
        APAgeingDetailQuery query = new APAgeingDetailQuery();
        query.setFilterCriteria(criteria);

        Map<String, Object> map = (FastMap<String, Object>) genericDao.generate(query);
        map.put("organization", genericDao.load(Party.class, criteria.getOrganization()));
        map.put("criteria", criteria);

        return map;
    }

    public APAgeingFilterCriteria createMonth(APAgeingFilterCriteria criteria)
    {
        Date now = criteria.getDate();

        criteria.setNext(DateHelper.plusOneMonth(now));
        criteria.setPrev(DateHelper.minusOneMonth(now));

        return criteria;
    }
}

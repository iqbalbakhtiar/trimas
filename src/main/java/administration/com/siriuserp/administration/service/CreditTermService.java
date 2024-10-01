package com.siriuserp.administration.service;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.base.Service;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.dm.CreditTerm;
import com.siriuserp.sdk.dm.PartyRelationship;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.DateHelper;
import com.siriuserp.sdk.utility.QueryFactory;

import javolution.util.FastMap;

@Component
@Transactional(rollbackFor = Exception.class)
public class CreditTermService extends Service {
	
	@Autowired
	private GenericDao genericDao;
	
//	@Autowired
//	private CreditTermDao creditTermDao;
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws Exception {
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("filterCriteria", filterCriteria);
		map.put("terms", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));

		return map;
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preadd(Long partyRelationshipId)
	{
		CreditTerm creditTerm = new CreditTerm();
		creditTerm.setPartyRelationship(genericDao.load(PartyRelationship.class, partyRelationshipId));
		
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("creditTerm_add", creditTerm);

		return map;
	}
	
	@AuditTrails(className = CreditTerm.class, actionType = AuditTrailsActionType.CREATE)
    public void add(CreditTerm creditTerm) throws ServiceException {
        // Load the PartyRelationship to ensure it's attached to the session
        PartyRelationship partyRelationship = genericDao.load(PartyRelationship.class, creditTerm.getPartyRelationship().getId());
        creditTerm.setPartyRelationship(partyRelationship);

        Date validFromNewPlafon = creditTerm.getValidFrom();

        Iterator<CreditTerm> prevs = partyRelationship.getCreditTerms().iterator();

        while (prevs.hasNext())
		{
			CreditTerm prev = prevs.next();

			if (prev.getValidFrom().compareTo(validFromNewPlafon) > 0)
				creditTerm.setValidTo(DateHelper.minusOneDay(prev.getValidFrom()));

			if (prev.getValidFrom().compareTo(validFromNewPlafon) == 0)
			{
				prev.setValidTo(validFromNewPlafon);
				genericDao.update(prev);

				break;
			}

			if (prev.getValidFrom().compareTo(validFromNewPlafon) < 0)
			{
				prev.setValidTo(DateHelper.minusOneDay(validFromNewPlafon));
				genericDao.update(prev);

				break;
			}
		}

        creditTerm.setCreatedDate(DateHelper.now());
        creditTerm.setCreatedBy(getPerson());

        genericDao.add(creditTerm);
    }
}

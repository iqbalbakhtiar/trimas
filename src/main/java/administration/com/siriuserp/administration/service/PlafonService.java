package com.siriuserp.administration.service;

import java.util.Date;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.base.Service;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.dm.PartyRelationship;
import com.siriuserp.sdk.dm.Plafon;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.DateHelper;
import com.siriuserp.sdk.utility.QueryFactory;

import javolution.util.FastMap;

@Component
@Transactional(rollbackFor = Exception.class)
public class PlafonService extends Service {
	
	@Autowired
	private GenericDao genericDao;
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends AbstractGridViewQuery> queryclass) throws Exception {
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("plafons", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));
		map.put("filterCriteria", filterCriteria);
		
		return map;
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preadd(Long partyRelationshipId)
	{
		Plafon plafon = new Plafon();
		plafon.setPartyRelationship(genericDao.load(PartyRelationship.class, partyRelationshipId));
		
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("plafon_add", plafon);

		return map;
	}
	
	@AuditTrails(className = Plafon.class, actionType = AuditTrailsActionType.CREATE)
    public void add(Plafon plafon) throws ServiceException {
        // Load the PartyRelationship to ensure it's attached to the session
        PartyRelationship partyRelationship = genericDao.load(PartyRelationship.class, plafon.getPartyRelationship().getId());
        plafon.setPartyRelationship(partyRelationship);

        // Get the validFrom date from the new plafon
        Date validFromNewPlafon = plafon.getValidFrom();

        Iterator<Plafon> prevs = partyRelationship.getPlafons().iterator();

        while (prevs.hasNext())
		{
			Plafon prev = prevs.next();

			if (prev.getValidFrom().compareTo(validFromNewPlafon) > 0)
				plafon.setValidTo(DateHelper.minusOneDay(prev.getValidFrom()));

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

        // Set audit details
        plafon.setCreatedDate(DateHelper.now());
        plafon.setCreatedBy(getPerson());

        // Add the new plafon to the database
        genericDao.add(plafon);
    }

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(Long id)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("plafon_edit", genericDao.load(Plafon.class, id));

		return map;
	}
	
	@AuditTrails(className = Plafon.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(Plafon plafon) throws ServiceException
	{
		plafon.setUpdatedBy(getPerson());
		plafon.setUpdatedDate(DateHelper.now());
		genericDao.update(plafon);
	}
	
	@AuditTrails(className = Plafon.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(Long id) throws ServiceException
	{
		genericDao.delete(genericDao.load(Plafon.class, id));
	}
}

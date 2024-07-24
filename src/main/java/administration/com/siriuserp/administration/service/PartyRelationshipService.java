/**
 * Oct 30, 2008 9:49:15 AM
 * com.siriuserp.administration.service
 * PartyRelationshipService.java
 */
package com.siriuserp.administration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.base.Service;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.PartyRelationship;
import com.siriuserp.sdk.dm.PartyRelationshipType;
import com.siriuserp.sdk.dm.PartyRoleType;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.DateHelper;
import com.siriuserp.sdk.utility.QueryFactory;

import javolution.util.FastMap;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class PartyRelationshipService extends Service
{
	@Autowired
	private GenericDao genericDao;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("relationships", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));
		map.put("filterCriteria", filterCriteria);

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preadd(Long party)
	{
		PartyRelationship relationship = new PartyRelationship();
		relationship.setPartyFrom(genericDao.load(Party.class, party));

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("partyRelationship", relationship);
		map.put("roleTypes", genericDao.loadAll(PartyRoleType.class));
		map.put("relationshipTypes", genericDao.loadAll(PartyRelationshipType.class));

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public PartyRelationship load(Long id)
	{
		return genericDao.load(PartyRelationship.class, id);
	}

	@AuditTrails(className = PartyRelationship.class, actionType = AuditTrailsActionType.CREATE)
	public void add(PartyRelationship partyRelationship) throws ServiceException
	{
		partyRelationship.setCreatedBy(getPerson());
		partyRelationship.setCreatedDate(DateHelper.now());
		partyRelationship.setFromDate(DateHelper.today());

		genericDao.add(partyRelationship);
	}

	@AuditTrails(className = PartyRelationship.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(PartyRelationship partyRelationship) throws ServiceException
	{
		genericDao.delete(partyRelationship);
	}
}
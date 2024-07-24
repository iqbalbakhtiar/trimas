/**
 * Oct 31, 2008 9:30:40 AM
 * com.siriuserp.administration.service
 * ContactMechanismService.java
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
import com.siriuserp.sdk.dao.PartyDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.dm.ContactMechanism;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
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
public class ContactMechanismService extends Service
{
	@Autowired
	private PartyDao partyDao;

	@Autowired
	private GenericDao genericDao;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("filterCriteria", filterCriteria);
		map.put("contacts", genericDao.filter(QueryFactory.create(filterCriteria, queryclass)));

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preadd(String party)
	{
		ContactMechanism contactMechanism = new ContactMechanism();
		contactMechanism.setActive(true);
		contactMechanism.setParty(genericDao.load(Party.class, Long.valueOf(party)));

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("contactMechanism_add", contactMechanism);

		return map;
	}

	@AuditTrails(className = Party.class, actionType = AuditTrailsActionType.CREATE)
	public void add(ContactMechanism contactMechanism) throws ServiceException
	{
		Party party = genericDao.load(Party.class, contactMechanism.getParty().getId());
		party.getContactMechanisms().add(contactMechanism);
		party.setUpdatedBy(getPerson());
		party.setUpdatedDate(DateHelper.now());

		partyDao.update(party);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(Long id)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("contactMechanism_edit", load(id));

		return map;
	}

	@AuditTrails(className = ContactMechanism.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(ContactMechanism contactMechanism) throws ServiceException
	{
		Party party = contactMechanism.getParty();
		party.setUpdatedBy(getPerson());
		party.setUpdatedDate(DateHelper.now());

		partyDao.update(party);

		genericDao.update(contactMechanism);
	}

	@AuditTrails(className = ContactMechanism.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(ContactMechanism contactMechanism) throws ServiceException
	{
		genericDao.delete(contactMechanism);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public ContactMechanism load(Long id)
	{
		return genericDao.load(ContactMechanism.class, id);
	}
}

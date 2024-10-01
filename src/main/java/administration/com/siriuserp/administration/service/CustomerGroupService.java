package com.siriuserp.administration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.annotation.InjectParty;
import com.siriuserp.sdk.base.Service;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.PartyRelationship;
import com.siriuserp.sdk.dm.PartyRelationshipType;
import com.siriuserp.sdk.dm.PartyRoleType;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.QueryFactory;

import javolution.util.FastMap;

@Component
@Transactional(rollbackFor = Exception.class)
public class CustomerGroupService extends Service {
	
	@Autowired
	private GenericDao genericDao;

	@Autowired
	private CodeSequenceDao codeSequenceDao;
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends AbstractGridViewQuery> queryclass) throws Exception {
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("customerGroups", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));
		map.put("filterCriteria", filterCriteria);

		return map;
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	@InjectParty(keyName = "customer_group_add")
	public FastMap<String, Object> preadd()
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("customer_group_add", new Party());

		return map;
	}
	
	@AuditTrails(className = Party.class, actionType = AuditTrailsActionType.CREATE)
	public FastMap<String, Object> add(Party customerGroup) throws Exception {
		System.out.println("Customer Service Add");
		customerGroup.setCode(GeneratorHelper.instance().generate(TableType.CUSTOMER_GROUP, codeSequenceDao));
		customerGroup.setBase(true); // True, Karena Group
		
		genericDao.add(customerGroup);
		
		PartyRelationship relationship = new PartyRelationship();
		
		relationship.setActive(customerGroup.isActive());
		//Party
		relationship.setPartyFrom(customerGroup);
		relationship.setPartyTo(customerGroup.getOrganization()); // Company (Party) 
		
		//Relation Type
		relationship.setRelationshipType(genericDao.load(PartyRelationshipType.class, PartyRelationshipType.CUSTOMER_RELATIONSHIP));
		
		//Party Role
		relationship.setPartyRoleTypeFrom(genericDao.load(PartyRoleType.class, PartyRoleType.CUSTOMER));
		relationship.setPartyRoleTypeTo(genericDao.load(PartyRoleType.class, PartyRoleType.SUPPLIER));
		
		genericDao.add(relationship);
		
		
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("customer_group", customerGroup);
		map.put("id", relationship.getId());
		
		return map;
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(Long id) throws Exception {
		PartyRelationship partyRelationship = genericDao.load(PartyRelationship.class, id);
		
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("customer_group_edit", genericDao.load(Party.class, partyRelationship.getPartyFrom().getId()));
		map.put("relationship", partyRelationship);
		
		return map;
	}
	
	@AuditTrails(className = Party.class, actionType = AuditTrailsActionType.UPDATE)
	public FastMap<String, Object> edit(Party customerGroup, Long relationshipId) throws Exception {
		genericDao.update(customerGroup);
		
		PartyRelationship partyRelationship = genericDao.load(PartyRelationship.class, relationshipId);
		partyRelationship.setActive(customerGroup.isActive());
		
		genericDao.update(partyRelationship);
		
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("relationshipId", relationshipId);
		
		return map;
	}
	
	@AuditTrails(className = Party.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(Long id) throws Exception {
		PartyRelationship partyRelationship = genericDao.load(PartyRelationship.class, id);
		partyRelationship.setActive(false);
		
		genericDao.update(partyRelationship);

		Party customerGroup = genericDao.load(Party.class, partyRelationship.getPartyFrom().getId());
		customerGroup.setActive(false);

		genericDao.update(customerGroup);
	}
}

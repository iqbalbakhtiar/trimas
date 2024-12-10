package com.siriuserp.administration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.administration.form.PartyForm;
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
import com.siriuserp.sdk.utility.DateHelper;
import com.siriuserp.sdk.utility.FormHelper;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.QueryFactory;

import javolution.util.FastMap;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional(rollbackFor = Exception.class)
public class ApproverService extends Service {
	@Autowired
	private GenericDao genericDao;
	
	@Autowired
	private CodeSequenceDao codeSequenceDao;
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends AbstractGridViewQuery> queryclass) throws Exception {
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("approvers", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));
		map.put("filterCriteria", filterCriteria);

		return map;
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	@InjectParty(keyName = "approver_add")
	public FastMap<String, Object> preadd()
	{
		// Filter data hanya untuk SALES_APPROVER dan PURCHASE_APPROVER
		List<PartyRoleType> filteredPartyRoleTypes = genericDao.loadAll(PartyRoleType.class).stream()
				.filter(partyRoleType ->
						PartyRoleType.SALES_APPROVER.equals(partyRoleType.getId()) ||
						PartyRoleType.PURCHASE_APPROVER.equals(partyRoleType.getId())
				)
				.collect(Collectors.toList());

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("approver_add", new PartyForm());
		map.put("approver_types", filteredPartyRoleTypes);

		return map;
	}
	
	@AuditTrails(className = Party.class, actionType = AuditTrailsActionType.CREATE)
	public FastMap<String, Object> add(PartyForm partyForm) throws Exception {
		PartyRelationship relationship = new PartyRelationship();

		if (partyForm.getParty() != null) {
			relationship.setPartyFrom(partyForm.getParty());
			relationship.setPartyTo(partyForm.getOrganization());
			relationship.setPartyRoleTypeFrom(genericDao.load(PartyRoleType.class, partyForm.getPartyRoleTypeFrom()));
			relationship.setPartyRoleTypeTo(genericDao.load(PartyRoleType.class, PartyRoleType.COMPANY));
			relationship.setRelationshipType(genericDao.load(PartyRelationshipType.class, PartyRelationshipType.EMPLOYMENT_RELATIONSHIP));
			relationship.setFromDate(DateHelper.now());

			genericDao.add(relationship);
		} else {
			Party approver = FormHelper.create(Party.class, partyForm);

			approver.setCode(GeneratorHelper.instance().generate(TableType.PERSON_APPROVER, codeSequenceDao));
			approver.setBase(false); // false, Karena bukan Group
			approver.setActive(approver.isActive());
			approver.setCreatedBy(getPerson());
			approver.setCreatedDate(DateHelper.now());

			genericDao.add(approver);

			//Add Party Relationship
			//Party
			relationship.setPartyFrom(approver);
			relationship.setPartyTo(approver.getOrganization()); // Company (Party)
			//Relation Type
			relationship.setRelationshipType(genericDao.load(PartyRelationshipType.class, PartyRelationshipType.EMPLOYMENT_RELATIONSHIP));
			//Party Role
			relationship.setPartyRoleTypeFrom(genericDao.load(PartyRoleType.class, partyForm.getPartyRoleTypeFrom()));
			relationship.setPartyRoleTypeTo(genericDao.load(PartyRoleType.class, PartyRoleType.COMPANY));
			relationship.setCreatedBy(getPerson());
			relationship.setCreatedDate(DateHelper.now());
			relationship.setActive(approver.isActive());
			genericDao.add(relationship);
		}

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("id", relationship.getId());

        return map;
    }
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(Long id) throws Exception {
		PartyRelationship partyRelationship = genericDao.load(PartyRelationship.class, id);
	    Party approver = genericDao.load(Party.class, partyRelationship.getPartyFrom().getId());
	    
	    PartyForm partyForm = FormHelper.bind(PartyForm.class, partyRelationship);
	    
	    partyForm.setApprover(approver);
		
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("approver_edit", partyForm);
		map.put("partyFrom", approver);
		
		return map;
	}
	
	@AuditTrails(className = Party.class, actionType = AuditTrailsActionType.UPDATE)
	public FastMap<String, Object> edit(PartyRelationship approver, Long relationshipId) throws Exception {
		genericDao.update(approver);
		
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("relationshipId", relationshipId);
		
		return map;
	}
	
	@AuditTrails(className = Party.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(Long id) throws Exception {
		PartyRelationship partyRelationship = genericDao.load(PartyRelationship.class, id);
		partyRelationship.setActive(false);
		
		genericDao.update(partyRelationship);

		Party approver = genericDao.load(Party.class, partyRelationship.getPartyFrom().getId());
		approver.setActive(false);

		genericDao.update(approver);
	}
}

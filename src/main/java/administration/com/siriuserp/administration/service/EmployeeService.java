/**
 * File Name  : EmployeeService.java
 * Created On : Jan 31, 2019
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.administration.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.annotation.InjectParty;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.PartyRelationship;
import com.siriuserp.sdk.dm.PartyRelationshipType;
import com.siriuserp.sdk.dm.PartyRoleType;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.DateHelper;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.PathHelper;
import com.siriuserp.sdk.utility.QueryFactory;
import com.siriuserp.sdk.utility.SiriusValidator;
import com.siriuserp.sdk.utility.StringHelper;

import javolution.util.FastMap;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class EmployeeService
{
	@Autowired
	private GenericDao genericDao;

	@Autowired
	private CodeSequenceDao codeSequenceDao;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws Exception
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("filterCriteria", filterCriteria);
		map.put("employees", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	@InjectParty(keyName = "employee_add")
	public FastMap<String, Object> preadd()
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("employee_add", new Party());

		return map;
	}

	@AuditTrails(className = Party.class, actionType = AuditTrailsActionType.CREATE)
	public FastMap<String, Object> add(Party person, Long employeeId, MultipartFile file) throws Exception
	{
		Long relationshipId = null;

		if (SiriusValidator.validateParamWithZeroPosibility(employeeId))
		{
			person = load(employeeId);

			PartyRelationship relationship = new PartyRelationship();
			relationship.setFromDate(DateHelper.now());
			relationship.setRelationshipType(genericDao.load(PartyRelationshipType.class, PartyRelationshipType.EMPLOYMENT_RELATIONSHIP));
			relationship.setPartyFrom(person.getOrganization());
			relationship.setPartyRoleTypeFrom(genericDao.load(PartyRoleType.class, PartyRoleType.COMPANY));
			relationship.setPartyTo(person);
			relationship.setPartyRoleTypeTo(genericDao.load(PartyRoleType.class, PartyRoleType.EMPLOYEE));

			genericDao.add(relationship);
			genericDao.update(person);
			relationshipId = relationship.getId();
		} else
		{
			person.setCode(GeneratorHelper.instance().generate(TableType.PARTY, codeSequenceDao));

			PartyRelationship relationship = new PartyRelationship();
			relationship.setFromDate(DateHelper.now());
			relationship.setRelationshipType(genericDao.load(PartyRelationshipType.class, PartyRelationshipType.EMPLOYMENT_RELATIONSHIP));
			relationship.setPartyFrom(person);
			relationship.setPartyRoleTypeFrom(genericDao.load(PartyRoleType.class, PartyRoleType.EMPLOYEE));
			relationship.setPartyTo(person.getOrganization());
			relationship.setPartyRoleTypeTo(genericDao.load(PartyRoleType.class, PartyRoleType.COMPANY));

			if (file != null)
			{
				String[] _name = file.getOriginalFilename().split("[.]");
				if (!file.getOriginalFilename().equals(""))
				{
					String fileName = StringHelper.generateFileName(person.getCode(), ".", _name[_name.length - 1]);
					file.transferTo(new File(PathHelper.SERVLET_PATH + "//static/party//" + fileName));
					person.setPicture("party/" + fileName);
				}
			}

			genericDao.add(relationship);
			genericDao.add(person);
			relationshipId = relationship.getId();
			employeeId = person.getId();
		}

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("id", relationshipId);

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(Long id) throws Exception
	{
		PartyRelationship partyRelationship = genericDao.load(PartyRelationship.class, id);

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("employee_edit", load(partyRelationship.getPartyFrom().getId()));
		map.put("relationship", partyRelationship);

		return map;
	}

	@AuditTrails(className = Party.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(Party person, Long relationshipId, Boolean active, String note, MultipartFile file) throws Exception
	{
		if (file != null)
		{
			String[] _name = file.getOriginalFilename().split("[.]");
			if (!file.getOriginalFilename().equals(""))
			{
				String fileName = StringHelper.generateFileName(person.getCode(), ".", _name[_name.length - 1]);
				file.transferTo(new File(PathHelper.SERVLET_PATH + "//static/party//" + fileName));
				person.setPicture("party/" + fileName);
			}
		}

		if (active)
			person.setActive(true);

		genericDao.update(person);

		PartyRelationship rel = genericDao.load(PartyRelationship.class, relationshipId);
		if (note != null)
			rel.setNote(note);

		if (active != null)
			rel.setActive(active);

		genericDao.update(rel);
	}

	@AuditTrails(className = Party.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(Long id) throws Exception
	{
		PartyRelationship partyRelationship = genericDao.load(PartyRelationship.class, id);
		partyRelationship.setActive(false);

		genericDao.update(partyRelationship);

		Party person = load(partyRelationship.getPartyFrom().getId());
		person.setActive(false);

		genericDao.update(person);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Party load(Long id)
	{
		return genericDao.load(Party.class, id);
	}
}

/**
 * Apr 24, 2009 4:42:46 PM
 * com.siriuserp.administration.service
 * FacilityService.java
 */
package com.siriuserp.administration.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.administration.form.AdministrationForm;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.dm.AddressType;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.FacilityType;
import com.siriuserp.sdk.dm.Item;
import com.siriuserp.sdk.dm.PostalAddress;
import com.siriuserp.sdk.dm.PostalAddressType;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.FormHelper;
import com.siriuserp.sdk.utility.QueryFactory;

import javolution.util.FastMap;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class FacilityService
{
	@Autowired
	private GenericDao genericDao;

	@Autowired
	private GeographicsRemoteService geographicService;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("filterCriteria", filterCriteria);
		map.put("facilitys", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> preadd() throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("facility_add", new AdministrationForm());
		map.put("types", genericDao.loadAll(FacilityType.class));
		map.put("countries", geographicService.getCountryList());
		map.put("postals", AddressType.values());

		return map;
	}

	@AuditTrails(className = Facility.class, actionType = AuditTrailsActionType.CREATE)
	public void add(Facility facility) throws ServiceException
	{
		AdministrationForm form = (AdministrationForm) facility.getForm();

		PostalAddress postalAddress = form.getPostalAddress();
		postalAddress.setEnabled(true);

		for (Item item : form.getTypes())
		{
			PostalAddressType addressType = new PostalAddressType();
			addressType.setEnabled(item.isEnabled());
			addressType.setType(AddressType.valueOf(item.getLegend()));
			addressType.setPostalAddress(postalAddress);

			postalAddress.getAddressTypes().add(addressType);
		}

		genericDao.add(postalAddress);

		facility.setPostalAddress(postalAddress);

		genericDao.add(facility);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> preedit(Long id) throws Exception
	{
		AdministrationForm administrationForm = FormHelper.bind(AdministrationForm.class, load(id));

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("facility_form", administrationForm);
		map.put("facility_edit", administrationForm.getFacility());
		map.put("countries", geographicService.getCountryList());
		map.put("province", geographicService.getProvince(administrationForm.getFacility().getPostalAddress().getCity().getLastParent().getId()));
		map.put("city", geographicService.getCity(administrationForm.getFacility().getPostalAddress().getCity().getParent().getId()));

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Facility load(Long id)
	{
		return genericDao.load(Facility.class, id);
	}

	@AuditTrails(className = Facility.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(Facility facility) throws ServiceException
	{
		genericDao.update(facility);
		genericDao.update(facility.getPostalAddress());
	}

	@AuditTrails(className = Facility.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(Facility facility) throws ServiceException
	{
		genericDao.delete(facility);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Facility alias(String param, String value)
	{
		return genericDao.getUniqeField(Facility.class, param, value);
	}
}

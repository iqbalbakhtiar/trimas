/**
 * Oct 31, 2008 11:04:35 AM
 * com.siriuserp.administration.service
 * PostalAddressService.java
 */
package com.siriuserp.administration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.administration.form.PartyForm;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.base.Service;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dao.PartyDao;
import com.siriuserp.sdk.dao.PostalAddressDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.dm.AddressType;
import com.siriuserp.sdk.dm.Item;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.PostalAddress;
import com.siriuserp.sdk.dm.PostalAddressType;
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
public class PostalAddressService extends Service
{
	@Autowired
	private GenericDao genericDao;

	@Autowired
	private PartyDao partyDao;

	@Autowired
	private PostalAddressDao postalAddressDao;

	@Autowired
	private GeographicsRemoteService geographicService;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("filterCriteria", filterCriteria);
		map.put("addresses", FilterAndPaging.filter(postalAddressDao, QueryFactory.create(filterCriteria, queryclass)));

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preadd(Long party)
	{
		PartyForm form = new PartyForm();
		form.setEnabled(true);
		form.setParty(genericDao.load(Party.class, party));

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("postalAddress_add", form);
		map.put("countries", geographicService.getCountryList());
		map.put("types", AddressType.values());

		return map;
	}

	@AuditTrails(className = PostalAddress.class, actionType = AuditTrailsActionType.CREATE)
	public void add(PostalAddress postalAddress) throws Exception
	{
		if (postalAddress.getForm().getItems() != null)
		{
			for (Item item : postalAddress.getForm().getItems())
			{
				PostalAddressType postal = new PostalAddressType();
				postal.setEnabled(item.isActived());
				postal.setPostalAddress(postalAddress);
				postal.setType(item.getPostalType());

				postalAddress.getAddressTypes().add(postal);
			}
		}

		if (!postalAddress.isEnabled()) // Set Default ( Selected ) menjai 'false' apabila pada form apabila statusnya (enabled) 'inactive'
			postalAddress.setSelected(false);

		postalAddressDao.add(postalAddress);

		Party party = postalAddress.getParty();
		party.setUpdatedBy(getPerson());
		party.setUpdatedDate(DateHelper.now());

		partyDao.update(party); // Update status terakhir kali party diupdate

		for (PostalAddress address : party.getPostalAddresses()) // Update default address ke alamat yang baru, apabila alamat baru menjadi default
		{
			if (postalAddress.isSelected())
			{
				if (!address.getId().equals(postalAddress.getId()))
				{
					address.setSelected(false);
					postalAddressDao.update(address);
				}
			}
		}
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(Long id)
	{
		PostalAddress postalAddress = load(id);

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("countries", geographicService.getCountryList());
		map.put("province", geographicService.getProvince(postalAddress.getCity().getLastParent().getId()));
		map.put("city", geographicService.getCity(postalAddress.getCity().getParent().getId()));
		map.put("postalAddress_edit", postalAddress);

		return map;
	}

	@AuditTrails(className = Party.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(PostalAddress postalAddress, AddressType[] types) throws ServiceException
	{
		if (!postalAddress.isEnabled())
			postalAddress.setSelected(false);

		if (types != null)
		{
			for (int idx = 0; idx < types.length; idx++)
			{
				PostalAddressType address = new PostalAddressType();
				address.setPostalAddress(postalAddress);
				address.setType(types[idx]);

				postalAddress.getAddressTypes().add(address);
			}
		}

		postalAddressDao.update(postalAddress);

		Party party = postalAddress.getParty();
		party.setUpdatedBy(getPerson());
		party.setUpdatedDate(DateHelper.now());

		genericDao.update(party);

		for (PostalAddress address : party.getPostalAddresses())
		{
			if (postalAddress.isSelected())
			{
				if (!address.getId().equals(postalAddress.getId()))
				{
					address.setSelected(false);
					postalAddressDao.update(address);
				}
			}
		}
	}

	@AuditTrails(className = PostalAddress.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(PostalAddress postalAddress) throws ServiceException
	{
		postalAddressDao.delete(postalAddress);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public PostalAddress load(Long id)
	{
		return genericDao.load(PostalAddress.class, id);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Long checkByDefault(String selected, Long party)
	{
		PostalAddress address = postalAddressDao.loadByDefault(selected, party);

		if (address != null)
			return address.getId();

		return null;
	}
}

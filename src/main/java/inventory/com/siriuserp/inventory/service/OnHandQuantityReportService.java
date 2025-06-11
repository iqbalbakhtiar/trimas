package com.siriuserp.inventory.service;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.inventory.criteria.InventoryLedgerFilterCriteria;
import com.siriuserp.inventory.dm.ProductCategory;
import com.siriuserp.inventory.query.OnHandQuantityByDateReportQuery;
import com.siriuserp.sdk.annotation.InjectParty;
import com.siriuserp.sdk.base.Service;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastMap;

/**
 * @author ferdinand
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class OnHandQuantityReportService extends Service
{
	@InjectParty
	public Map<String, Object> pre() throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("criteria", new InventoryLedgerFilterCriteria());

		return map;
	}

	public Map<String, Object> view(InventoryLedgerFilterCriteria criteria)
	{
		OnHandQuantityByDateReportQuery query = new OnHandQuantityByDateReportQuery();
		query.setFilterCriteria(criteria);

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("criteria", criteria);
		map.put("reports", genericDao.generate(query));
		map.put("organization", genericDao.load(Party.class, criteria.getOrganization()));
		map.put("facility", SiriusValidator.validateLongParam(criteria.getFacility()) ? genericDao.load(Facility.class, criteria.getFacility()) : null);
		map.put("productCategory", SiriusValidator.validateLongParam(criteria.getProductCategory()) ? genericDao.load(ProductCategory.class, criteria.getProductCategory()) : null);

		return map;
	}
}

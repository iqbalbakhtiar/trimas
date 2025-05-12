/**
 * Jun 30, 2011
 * PurchaseReportService.java
 */
package com.siriuserp.procurement.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.procurement.criteria.PurchaseReportFilterCriteria;
import com.siriuserp.procurement.dm.PurchaseDocumentType;
import com.siriuserp.procurement.query.PurchaseReportQuery;
import com.siriuserp.sdk.annotation.InjectParty;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.Tax;
import com.siriuserp.sdk.exceptions.ServiceException;

import javolution.util.FastMap;

/**
 * @author Iqbal Bakhtiar
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
public class PurchaseReportService
{
	@Autowired
	private GenericDao genericDao;

	@InjectParty
	public Map<String, Object> pre() throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("criteria", new PurchaseReportFilterCriteria());
		map.put("taxs", genericDao.loadAll(Tax.class));
		map.put("documentTypes", PurchaseDocumentType.values());

		return map;
	}

	public Map<String, Object> view(PurchaseReportFilterCriteria criteria)
	{
		Party organization = genericDao.load(Party.class, criteria.getOrganization());

		PurchaseReportQuery query = new PurchaseReportQuery();
		query.setFilterCriteria(criteria);

		Map<String, Object> map = new FastMap<String, Object>();
		map.put("organization", organization);
		map.put("supplier", criteria.getSupplier() != null ? genericDao.load(Party.class, criteria.getSupplier()) : null);
		map.put("criteria", criteria);
		map.put("reports", genericDao.generateReport(query));

		return map;
	}
}

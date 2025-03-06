/**
 * Jun 30, 2011
 * PurchaseOrderReportService.java
 */
package com.siriuserp.procurement.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.procurement.criteria.PurchaseOrderReportFilterCriteria;
import com.siriuserp.procurement.query.PurchaseOrderReportQuery;
import com.siriuserp.sdk.annotation.InjectParty;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.Tax;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastMap;

/**
 * @author Iqbal
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
public class PurchaseOrderReportService
{
	@Autowired
	private GenericDao genericDao;

	@InjectParty
	public Map<String, Object> preadd() throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("criteria", new PurchaseOrderReportFilterCriteria());
		map.put("taxs", genericDao.loadAll(Tax.class));

		return map;
	}

	public Map<String, Object> genReport(PurchaseOrderReportFilterCriteria criteria)
	{
		PurchaseOrderReportQuery query = new PurchaseOrderReportQuery();
		query.setFilterCriteria(criteria);

		Map<String, Object> map = new FastMap<String, Object>();
		map.put("organization", SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()) ? genericDao.load(Party.class, criteria.getOrganization()) : null);
		map.put("supplier", SiriusValidator.validateParamWithZeroPosibility(criteria.getSupplier()) ? genericDao.load(Party.class, criteria.getSupplier()) : null);
		map.put("criteria", criteria);
		map.put("reports", genericDao.generateReport(query));

		return map;
	}
}

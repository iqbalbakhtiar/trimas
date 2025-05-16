/**
 * File Name  : SalesReportService.java
 * Created On : Apr 30, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.sales.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.sales.criteria.SalesReportFilterCriteria;
import com.siriuserp.sales.query.DeliveryReportViewQuery;
import com.siriuserp.sdk.annotation.InjectParty;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dm.Party;

import javolution.util.FastMap;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Component
@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
public class DeliveryReportService
{
	@Autowired
	private GenericDao dao;

	@InjectParty
	public FastMap<String, Object> pre()
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("reportCriteria", new SalesReportFilterCriteria());

		return map;
	}

	public Map<String, Object> view(SalesReportFilterCriteria criteria)
	{
		Party organization = dao.load(Party.class, criteria.getOrganization());

		Map<String, Object> map = new FastMap<String, Object>();
		map.put("organization", organization);
		map.put("customer", criteria.getCustomer() != null ? dao.load(Party.class, criteria.getCustomer()) : null);
		map.put("dateFrom", criteria.getDateFrom());
		map.put("dateTo", criteria.getDateTo());

		DeliveryReportViewQuery query = new DeliveryReportViewQuery();
		query.setFilterCriteria(criteria);

		map.put("reports", dao.generateReport(query));

		return map;
	}
}

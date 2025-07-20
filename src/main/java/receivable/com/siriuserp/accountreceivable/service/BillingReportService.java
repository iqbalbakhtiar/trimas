/**
 * File Name  : BillingReportService.java
 * Created On : Jul 19, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.accountreceivable.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.accountreceivable.criteria.BillingReportFilterCriteria;
import com.siriuserp.accountreceivable.query.BillingReportViewQuery;
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
public class BillingReportService
{
	@Autowired
	private GenericDao genericDao;

	@InjectParty
	public FastMap<String, Object> pre()
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("reportCriteria", new BillingReportFilterCriteria());

		return map;
	}

	public Map<String, Object> view(BillingReportFilterCriteria criteria)
	{
		Party organization = genericDao.load(Party.class, criteria.getOrganization());
		BillingReportViewQuery query = new BillingReportViewQuery();
		query.setFilterCriteria(criteria);

		Map<String, Object> map = new FastMap<String, Object>();
		map.put("organization", organization);
		map.put("customer", criteria.getCustomer() != null ? genericDao.load(Party.class, criteria.getCustomer()) : null);
		map.put("dateFrom", criteria.getDateFrom());
		map.put("dateTo", criteria.getDateTo());
		map.put("reports", genericDao.generateReport(query));

		return map;
	}
}

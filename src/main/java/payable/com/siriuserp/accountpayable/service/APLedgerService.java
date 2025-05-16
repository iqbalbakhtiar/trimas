/**
 * Mar 10, 2010 9:53:50 AM
 * com.siriuserp.accountpayable.service
 * APRegisterService.java
 */
package com.siriuserp.accountpayable.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.accountpayable.criteria.APLedgerFilterCriteria;
import com.siriuserp.accountpayable.query.APLedgerDetailQuery;
import com.siriuserp.accountpayable.query.APLedgerSummaryQuery;
import com.siriuserp.sdk.annotation.InjectParty;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dao.PartyDao;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastMap;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 * Version 1.5
 */

@Component
@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
public class APLedgerService
{
	@Autowired
	private GenericDao genericDao;

	@Autowired
	private PartyDao organizationDao;

	@InjectParty
	public Map<String, Object> pre() throws Exception
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("reportCriteria", new APLedgerFilterCriteria());

		return map;
	}

	public Map<String, Object> view(APLedgerFilterCriteria reportCriteria)
	{
		APLedgerSummaryQuery query = new APLedgerSummaryQuery();
		query.setFilterCriteria(reportCriteria);

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("organization", SiriusValidator.validateParamWithZeroPosibility(reportCriteria.getOrganization()) ? organizationDao.load(reportCriteria.getOrganization()) : null);
		map.put("reportCriteria", reportCriteria);
		map.put("reports", genericDao.generate(query));

		return map;
	}

	public Map<String, Object> viewdetail(APLedgerFilterCriteria reportCriteria)
	{
		APLedgerDetailQuery query = new APLedgerDetailQuery();
		query.setFilterCriteria(reportCriteria);

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("organization", SiriusValidator.validateParamWithZeroPosibility(reportCriteria.getOrganization()) ? organizationDao.load(reportCriteria.getOrganization()) : null);
		map.put("reportCriteria", reportCriteria);
		map.put("reports", genericDao.generate(query));

		return map;
	}
}

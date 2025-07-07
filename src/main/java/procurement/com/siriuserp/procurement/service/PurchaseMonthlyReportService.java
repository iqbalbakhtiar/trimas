package com.siriuserp.procurement.service;

import com.siriuserp.procurement.criteria.PurchaseReportFilterCriteria;
import com.siriuserp.procurement.dm.PurchaseDocumentType;
import com.siriuserp.procurement.query.PurchaseMonthlyReportQuery;
import com.siriuserp.sdk.annotation.InjectParty;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dm.Month;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.Tax;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.utility.DateHelper;
import com.siriuserp.sdk.utility.SiriusValidator;
import javolution.util.FastMap;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * @author Rama Almer Felix
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
public class PurchaseMonthlyReportService {
	@Autowired
	private GenericDao genericDao;

	@InjectParty
	public Map<String, Object> pre() throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("criteria", new PurchaseReportFilterCriteria());
		map.put("years", DateHelper.toYear(DateHelper.today()));
		map.put("months", Month.values());

		return map;
	}

	public Map<String, Object> view(PurchaseReportFilterCriteria criteria) {
		FastMap<String, Object> map = new FastMap<String, Object>();

		if (criteria.getDateFrom() == null)
			criteria.setDateFrom(new DateTime(criteria.getYear(), DateHelper.toIntMonth(criteria.getMonth()), 1, 0, 0, 0, 0).toDate());

		criteria.setDateTo(DateHelper.toEndDate(criteria.getDateFrom()));

		PurchaseMonthlyReportQuery query = new PurchaseMonthlyReportQuery();
		query.setFilterCriteria(criteria);

		map.put("criteria", criteria);
		map.put("period", DateHelper.toMonthEnum(criteria.getDateFrom()) + " " + DateHelper.toYear(criteria.getDateFrom()));
		map.put("reports", genericDao.generate(query));

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()))
			map.put("organization", genericDao.load(Party.class, criteria.getOrganization()));

		return map;
	}
	
	public PurchaseReportFilterCriteria createMonth(PurchaseReportFilterCriteria criteria)
	{
		if (SiriusValidator.validateDate(criteria.getDateFrom()))
		{
			criteria.setYear(DateHelper.getYear(criteria.getDateFrom()));
			criteria.setMonth(DateHelper.toMonthEnum(criteria.getDateFrom()));
		}

		Date now = new DateTime(criteria.getYear(), DateHelper.toIntMonth(criteria.getMonth()), 1, 0, 0, 0, 0).toDate();
		if (criteria.getDateFrom() != null)
			now = criteria.getDateFrom();

		criteria.setNext(DateHelper.plusOneMonth(now));
		criteria.setPrev(DateHelper.minusOneMonth(now));

		return criteria;
	}
}

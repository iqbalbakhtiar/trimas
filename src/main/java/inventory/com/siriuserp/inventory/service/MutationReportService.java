package com.siriuserp.inventory.service;

import com.siriuserp.inventory.criteria.InventoryLedgerFilterCriteria;
import com.siriuserp.inventory.query.MutationReportQuery;
import com.siriuserp.sdk.annotation.InjectParty;
import com.siriuserp.sdk.base.Service;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.utility.DateHelper;
import com.siriuserp.sdk.utility.SiriusValidator;
import javolution.util.FastMap;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

@Component
@Transactional(rollbackFor = Exception.class)
public class MutationReportService extends Service
{
	@Autowired
	private GenericDao genericDao;

	@InjectParty
	public Map<String, Object> pre() throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("mutationCriteria", new InventoryLedgerFilterCriteria());
		map.put("containers", genericDao.loadAll(Container.class));
		map.put("years", DateHelper.toYear(DateHelper.today()));

		return map;
	}

	public Map<String, Object> view(InventoryLedgerFilterCriteria criteria)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();

		if (criteria.getDateFrom() == null)
			criteria.setDateFrom(new DateTime(criteria.getYear(), DateHelper.toIntMonth(criteria.getMonth()), 1, 0, 0, 0, 0).toDate());

		criteria.setDateFrom(DateHelper.toStartDate(criteria.getDateFrom()));
		criteria.setDateTo(DateHelper.toEndDate(criteria.getDateFrom()));

		MutationReportQuery query = new MutationReportQuery();
		query.setFilterCriteria(criteria);

		if (SiriusValidator.validateLongParam(criteria.getOrganization()))
			map.put("organization", genericDao.load(Party.class, criteria.getOrganization()));

		if (SiriusValidator.validateLongParam(criteria.getContainer()))
			map.put("container", genericDao.load(Container.class, criteria.getContainer()));

		map.put("criteria", criteria);
		map.put("period", DateHelper.toMonthEnum(criteria.getDateFrom()) + " " + DateHelper.toYear(criteria.getDateFrom()));
		map.put("reports", genericDao.generate(query));

		return map;
	}

	public InventoryLedgerFilterCriteria createMonth(InventoryLedgerFilterCriteria criteria)
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

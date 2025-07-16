package com.siriuserp.inventory.service;

import com.siriuserp.inventory.criteria.InventoryLedgerFilterCriteria;
import com.siriuserp.inventory.query.GoodsReceiptReportQuery;
import com.siriuserp.procurement.dm.PurchaseDocumentType;
import com.siriuserp.sdk.annotation.InjectParty;
import com.siriuserp.sdk.base.Service;
import com.siriuserp.sdk.dao.GenericDao;
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

/**
 * @author Rama Almer Felix
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class GoodsReceiptReportService extends Service {
	@Autowired
	private GenericDao genericDao;

	@InjectParty
	public Map<String, Object> pre() throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("criteria", new InventoryLedgerFilterCriteria());
		map.put("documentTypes", PurchaseDocumentType.values());

		return map;
	}

	public Map<String, Object> detailview(InventoryLedgerFilterCriteria criteria)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();

		if (criteria.getDateFrom() == null)
			criteria.setDateFrom(new DateTime(criteria.getYear(), DateHelper.toIntMonth(criteria.getMonth()), 1, 0, 0, 0, 0).toDate());

		if (criteria.getDateTo() == null)
			criteria.setDateTo(DateHelper.toEndDate(criteria.getDateFrom()));

		GoodsReceiptReportQuery query = new GoodsReceiptReportQuery();
		query.setFilterCriteria(criteria);

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()))
			map.put("organization", genericDao.load(Party.class, criteria.getOrganization()));

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getSupplier()))
			map.put("supplier", genericDao.load(Party.class, criteria.getSupplier()));

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

/**
 * Dec 4, 2008 3:47:00 PM
 * com.siriuserp.reporting.accounting.service
 * BalanceSheetService.java
 */
package com.siriuserp.reporting.accounting.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.accounting.dao.AccountingPeriodDao;
import com.siriuserp.accounting.dm.AccountingPeriod;
import com.siriuserp.reporting.accounting.adapter.ComplexAccountingReportAdapter;
import com.siriuserp.reporting.accounting.adapter.ComplexConsecutiveReportAdapter;
import com.siriuserp.reporting.accounting.adapter.GLAccountProfitLossAdapter;
import com.siriuserp.reporting.accounting.adapter.ProfitLossReportAdapter;
import com.siriuserp.reporting.accounting.criteria.ProfitLossFilterCriteria;
import com.siriuserp.reporting.accounting.query.BalanceSheetConsecutiveReportQuery;
import com.siriuserp.reporting.accounting.query.BalanceSheetReportQuery;
import com.siriuserp.reporting.accounting.util.AccountingReportUtil;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dm.Month;

import javolution.util.FastList;
import javolution.util.FastMap;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
@Transactional(readOnly = true)
public class BalanceSheetService
{
	@Autowired
	private AccountingPeriodDao accountingPeriodDao;

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private AccountingReportUtil util;

	public FastMap<String, Object> pre()
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("filterCriteria", new ProfitLossFilterCriteria());
		map.put("years", accountingPeriodDao.loadAllAsYear());

		return map;
	}

	@SuppressWarnings("unchecked")
	public FastMap<String, Object> view(ProfitLossFilterCriteria filterCriteria)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();

		for (Long id : filterCriteria.getAccountingPeriodIds())
		{
			filterCriteria.getPrevAccountingPeriodIds().add(id);
			filterCriteria.getPrevAccountingPeriodIds().addAll(accountingPeriodDao.loadAllIDPrevInYear(genericDao.load(AccountingPeriod.class, id)));
		}

		ComplexAccountingReportAdapter reportAdapter = util.complex(new ComplexAccountingReportAdapter(), filterCriteria);

		BalanceSheetReportQuery query = new BalanceSheetReportQuery();
		query.setReportAdapter(reportAdapter);

		List<ProfitLossReportAdapter> data = genericDao.generateReport(query);

		for (ProfitLossReportAdapter adapter : data)
		{
			if (adapter.getAccount().getAccountType().getId().equals(Long.valueOf(1)))
				reportAdapter.getAssets().add(adapter);
			else if (adapter.getAccount().getAccountType().getId().equals(Long.valueOf(2)))
				reportAdapter.getLiabilities().add(adapter);
			else if (adapter.getAccount().getAccountType().getId().equals(Long.valueOf(3)))
				reportAdapter.getEquitys().add(adapter);
		}

		map.put("adapter", reportAdapter);
		map.put("criteria", filterCriteria);

		return map;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> genBalanceSheetConsecutive(ProfitLossFilterCriteria filterCriteria)
	{
		Map<String, Object> map = new FastMap<String, Object>();

		if (filterCriteria.getOrg() != null)
		{
			FastList.recycle(filterCriteria.getOrganizationIds());
			filterCriteria.getOrganizationIds().add(Long.valueOf(filterCriteria.getOrg()));
		}

		ComplexConsecutiveReportAdapter reportAdapter = (ComplexConsecutiveReportAdapter) util.complex(new ComplexConsecutiveReportAdapter(), filterCriteria);
		if (filterCriteria.getYear() != null)
		{
			FastList.recycle(reportAdapter.getPeriods());
			reportAdapter.getPeriods().addAll(accountingPeriodDao.getAllIdByYearAndMonth(filterCriteria.getYear(), null, filterCriteria.getOrg()));

			FastList.recycle(reportAdapter.getJanPeriods());
			reportAdapter.getJanPeriods().addAll(accountingPeriodDao.getAllIdByYearAndMonth(filterCriteria.getYear(), Month.JANUARY, filterCriteria.getOrg()));

			FastList.recycle(reportAdapter.getFebPeriods());
			reportAdapter.getFebPeriods().addAll(accountingPeriodDao.getAllIdByYearAndMonth(filterCriteria.getYear(), Month.FEBRUARY, filterCriteria.getOrg()));

			FastList.recycle(reportAdapter.getMarPeriods());
			reportAdapter.getMarPeriods().addAll(accountingPeriodDao.getAllIdByYearAndMonth(filterCriteria.getYear(), Month.MARCH, filterCriteria.getOrg()));

			FastList.recycle(reportAdapter.getAprPeriods());
			reportAdapter.getAprPeriods().addAll(accountingPeriodDao.getAllIdByYearAndMonth(filterCriteria.getYear(), Month.APRIL, filterCriteria.getOrg()));

			FastList.recycle(reportAdapter.getMayPeriods());
			reportAdapter.getMayPeriods().addAll(accountingPeriodDao.getAllIdByYearAndMonth(filterCriteria.getYear(), Month.MAY, filterCriteria.getOrg()));

			FastList.recycle(reportAdapter.getJunPeriods());
			reportAdapter.getJunPeriods().addAll(accountingPeriodDao.getAllIdByYearAndMonth(filterCriteria.getYear(), Month.JUNE, filterCriteria.getOrg()));

			FastList.recycle(reportAdapter.getJulPeriods());
			reportAdapter.getJulPeriods().addAll(accountingPeriodDao.getAllIdByYearAndMonth(filterCriteria.getYear(), Month.JULY, filterCriteria.getOrg()));

			FastList.recycle(reportAdapter.getAugPeriods());
			reportAdapter.getAugPeriods().addAll(accountingPeriodDao.getAllIdByYearAndMonth(filterCriteria.getYear(), Month.AUGUST, filterCriteria.getOrg()));

			FastList.recycle(reportAdapter.getSepPeriods());
			reportAdapter.getSepPeriods().addAll(accountingPeriodDao.getAllIdByYearAndMonth(filterCriteria.getYear(), Month.SEPTEMBER, filterCriteria.getOrg()));

			FastList.recycle(reportAdapter.getOctPeriods());
			reportAdapter.getOctPeriods().addAll(accountingPeriodDao.getAllIdByYearAndMonth(filterCriteria.getYear(), Month.OCTOBER, filterCriteria.getOrg()));

			FastList.recycle(reportAdapter.getNovPeriods());
			reportAdapter.getNovPeriods().addAll(accountingPeriodDao.getAllIdByYearAndMonth(filterCriteria.getYear(), Month.NOVEMBER, filterCriteria.getOrg()));

			FastList.recycle(reportAdapter.getDecPeriods());
			reportAdapter.getDecPeriods().addAll(accountingPeriodDao.getAllIdByYearAndMonth(filterCriteria.getYear(), Month.DECEMBER, filterCriteria.getOrg()));
		}

		BalanceSheetConsecutiveReportQuery query = new BalanceSheetConsecutiveReportQuery();
		query.setReportAdapter(reportAdapter);

		List<GLAccountProfitLossAdapter> data = genericDao.generateReport(query);

		for (ProfitLossReportAdapter adapter : data)
		{
			if (adapter.getAccount().getAccountType().getId().equals(Long.valueOf(1)))
				reportAdapter.getAssets().add(adapter);
			else if (adapter.getAccount().getAccountType().getId().equals(Long.valueOf(2)))
				reportAdapter.getLiabilities().add(adapter);
			else if (adapter.getAccount().getAccountType().getId().equals(Long.valueOf(3)))
				reportAdapter.getEquitys().add(adapter);
		}

		map.put("adapter", reportAdapter);
		map.put("criteria", filterCriteria);
		map.put("months", Month.values());

		return map;
	}
}

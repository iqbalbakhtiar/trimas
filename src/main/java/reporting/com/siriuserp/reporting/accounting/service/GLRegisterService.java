/**
 * Dec 11, 2008 2:30:05 PM
 * com.siriuserp.reporting.accounting.service
 * GLRegisterService.java
 */
package com.siriuserp.reporting.accounting.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.accounting.adapter.SimpleAccountingReportAdapter;
import com.siriuserp.accounting.criteria.GLRegisterFilterCriteria;
import com.siriuserp.accounting.dao.GLAccountBalanceDao;
import com.siriuserp.accounting.dao.GLAccountDao;
import com.siriuserp.accounting.dao.JournalEntryDetailDao;
import com.siriuserp.accounting.dm.GLAccount;
import com.siriuserp.accounting.dm.JournalEntryDetail;
import com.siriuserp.reporting.accounting.adapter.GLRegisterReportAdapter;
import com.siriuserp.reporting.accounting.query.GLRegisterReportAlterQuery;
import com.siriuserp.reporting.accounting.query.GLRegisterReportQuery;
import com.siriuserp.reporting.accounting.util.AccountingReportUtil;
import com.siriuserp.sdk.dao.CompanyStructureDao;
import com.siriuserp.sdk.dao.CurrencyDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dm.Currency;

import javolution.util.FastList;
import javolution.util.FastMap;

/**
 * @author Muhammad Rizal
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@SuppressWarnings("unchecked")
@Component
@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
public class GLRegisterService
{
	@Autowired
	private GenericDao genericDao;

	@Autowired
	private GLAccountBalanceDao accountBalanceDao;

	@Autowired
	private GLAccountDao accountDao;

	@Autowired
	private CurrencyDao currencyDao;

	@Autowired
	private AccountingReportUtil util;

	@Autowired
	private JournalEntryDetailDao entryDetailDao;

	@Autowired
	private CompanyStructureDao companyStructureDao;

	public FastMap<String, Object> pre()
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("glRegisterFilterCriteria", new GLRegisterFilterCriteria());
		map.put("accounts", accountDao.loadAllAccount());
		map.put("currencys", genericDao.loadAll(Currency.class));
		map.put("filterCriteria", new GLRegisterFilterCriteria());

		return map;
	}

	public FastMap<String, Object> view(GLRegisterFilterCriteria filterCriteria)
	{
		SimpleAccountingReportAdapter adapter = util.createAdapter(filterCriteria);
		adapter.setDateFrom(filterCriteria.getDateFrom());
		adapter.setDateTo(filterCriteria.getDateTo());
		adapter.setCurrency(filterCriteria.getCurrency());
		adapter.setDefaultCurrency(currencyDao.loadDefaultCurrency());

		if (filterCriteria.getAccounts().isEmpty())
			adapter.getAccounts().addAll(entryDetailDao.loadIDAccount(adapter));
		else
			adapter.getAccounts().addAll(filterCriteria.getAccounts());

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("adapter", adapter);

		if (filterCriteria.getCurrency() != null)
		{
			adapter.setCurrency(filterCriteria.getCurrency());
			map.put("currency", genericDao.load(Currency.class, filterCriteria.getCurrency()));
		} else
		{
			adapter.setCurrency(currencyDao.loadDefaultCurrency().getId());
			map.put("currency", currencyDao.loadDefaultCurrency());
		}

		map.put("criteria", filterCriteria);

		return map;
	}

	public FastMap<String, Object> detail(GLRegisterFilterCriteria filterCriteria) throws SecurityException {
	    SimpleAccountingReportAdapter adapter = util.createAdapter(filterCriteria);
	    adapter.setDateFrom(filterCriteria.getDateFrom());
	    adapter.setDateTo(filterCriteria.getDateTo());
	    adapter.setCurrency(filterCriteria.getCurrency());
	    adapter.setDefaultCurrency(currencyDao.loadDefaultCurrency());

	    FastMap<String, Object> response = new FastMap<String, Object>();
	    FastList<JournalEntryDetail> details = new FastList<JournalEntryDetail>();
	    details.addAll(entryDetailDao.loadRegister(adapter, filterCriteria.getAccount()));

	    GLAccount glAccount = genericDao.load(GLAccount.class, filterCriteria.getAccount());
	    adapter.setAccount(glAccount);

	    GLRegisterReportAdapter gAdapter = (GLRegisterReportAdapter) accountBalanceDao.generate(new GLRegisterReportQuery(adapter));
	    if (gAdapter == null) {
	        gAdapter = new GLRegisterReportAdapter();
	    }

	    response.put("id", filterCriteria.getAccount());
	    response.put("account", glAccount.getCode() + " - " + glAccount.getName());
	    response.put("balance", gAdapter.getOpeningdebet().subtract(gAdapter.getOpeningcredit()));
	    response.put("debet", gAdapter.getOpeningdebet());
	    response.put("credit", gAdapter.getOpeningcredit());

	    response.put("details", details.isEmpty() ? new FastList<JournalEntryDetail>() : details);

	    return response;
	}

	public FastMap<String, Object> beginpaging(GLRegisterFilterCriteria filterCriteria)
	{
		SimpleAccountingReportAdapter adapter = util.createAdapter(filterCriteria);

		List<GLRegisterReportAdapter> data = new FastList<GLRegisterReportAdapter>();

		SimpleAccountingReportAdapter filterAdapter = util.createAdapter(filterCriteria);
		filterAdapter.setCurrency(filterCriteria.getCurrency());
		filterAdapter.setDefaultCurrency(currencyDao.loadDefaultCurrency());

		adapter.getOrganizations().addAll(filterCriteria.getOrganizationIds());
		adapter.getPeriods().addAll(filterCriteria.getAccountingPeriodIds());
		adapter.setAccountId(filterCriteria.getAccount());

		adapter.setCurrency(filterCriteria.getCurrency());

		adapter.setDefaultCurrency(currencyDao.loadDefaultCurrency());

		//Duplication filter for saving last index
		for (GLRegisterReportAdapter acc : (List<GLRegisterReportAdapter>) accountBalanceDao.generateReport(new GLRegisterReportAlterQuery(adapter)))
		{
			if (!data.contains(acc))
				data.add(acc);
		}

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("adapter", adapter);
		map.put("currency", genericDao.load(Currency.class, filterCriteria.getCurrency()));
		map.put("reports", data);
		map.put("criteria", filterCriteria);
		map.put("filterCriteria", filterCriteria);

		return map;
	}

	public FastMap<String, Object> filterpaging(GLRegisterFilterCriteria filterCriteria)
	{
		SimpleAccountingReportAdapter adapter = util.createAdapter(filterCriteria);
		adapter.setCurrency(filterCriteria.getCurrency());

		List<GLRegisterReportAdapter> data = new FastList<GLRegisterReportAdapter>();

		SimpleAccountingReportAdapter filterAdapter = util.createAdapter(filterCriteria);
		filterAdapter.setCurrency(filterCriteria.getCurrency());
		filterAdapter.setDefaultCurrency(currencyDao.loadDefaultCurrency());

		adapter.getOrganizations().addAll(filterCriteria.getOrganizationIds());
		adapter.getPeriods().addAll(filterCriteria.getAccountingPeriodIds());

		adapter.setDefaultCurrency(currencyDao.loadDefaultCurrency());
		adapter.getAccounts().addAll(filterCriteria.getAccounts());
		adapter.setAccountId(filterCriteria.getAccount());

		//Duplication filter for saving last index
		for (GLRegisterReportAdapter acc : (List<GLRegisterReportAdapter>) accountBalanceDao.generateReport(new GLRegisterReportAlterQuery(adapter)))
		{
			if (!data.contains(acc))
				data.add(acc);
		}

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("adapter", adapter);
		map.put("currency", genericDao.load(Currency.class, filterCriteria.getCurrency()));
		map.put("reports", data);
		map.put("criteria", filterCriteria);

		if (adapter.getAccounts().size() == 0)
			map.put("selected", null);
		else
			map.put("selected", genericDao.load(GLAccount.class, adapter.getAccounts().get(0)));

		map.put("filterCriteria", filterCriteria);

		return map;
	}

	public FastMap<String, Object> report(GLRegisterFilterCriteria filterCriteria)
	{
		SimpleAccountingReportAdapter adapter = util.createAdapter(filterCriteria);
		adapter.setCurrency(filterCriteria.getCurrency());
		adapter.setDefaultCurrency(currencyDao.loadDefaultCurrency());

		if (filterCriteria.getAccounts().isEmpty())
			adapter.getAccounts().addAll(accountDao.loadAllIDAccount());
		else
			adapter.getAccounts().addAll(filterCriteria.getAccounts());

		for (Long organization : filterCriteria.getOrganizationIds())
		{
			adapter.getOrganizations().add(organization);
			adapter.getOrganizations().addAll(companyStructureDao.loadIDVerticalDown(organization));
		}

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("adapter", adapter);
		map.put("reports", accountBalanceDao.generateReport(new GLRegisterReportAlterQuery(adapter)));
		map.put("criteria", filterCriteria);

		return map;
	}
}

package com.siriuserp.accounting.service;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.accounting.criteria.AccountingPeriodFilterCriteria;
import com.siriuserp.accounting.dao.AccountingPeriodDao;
import com.siriuserp.accounting.dao.InterJournalDao;
import com.siriuserp.accounting.dao.JournalEntryDao;
import com.siriuserp.accounting.dm.AccountingPeriod;
import com.siriuserp.accounting.dm.PeriodStatus;
import com.siriuserp.accounting.posting.PostingFacade;
import com.siriuserp.accounting.query.AccountingPeriodGridViewQuery;
import com.siriuserp.reporting.accounting.query.AccountingPeriod4ReportPopupGridViewQuery;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.annotation.InjectParty;
import com.siriuserp.sdk.dao.CompanyStructureDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dm.Level;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.DateHelper;
import com.siriuserp.sdk.utility.QueryFactory;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastMap;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class AccountingPeriodService
{
	@Autowired
	private PostingFacade postingFacade;

	@Autowired
	private AccountingPeriodDao accountingPeriodDao;

	@Autowired
	private CompanyStructureDao companyStructureDao;

	@Autowired
	private InterJournalDao interJournalDao;

	@Autowired
	private JournalEntryDao journalEntryDao;

	@Autowired
	private GenericDao genericDao;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view()
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("accountingPeriods", genericDao.loadAll(AccountingPeriod.class));

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria) throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("periods", FilterAndPaging.filter(accountingPeriodDao, QueryFactory.create(filterCriteria, AccountingPeriodGridViewQuery.class)));
		map.put("filterCriteria", filterCriteria);

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	@InjectParty(keyName = "accountingPeriod")
	public FastMap<String, Object> preadd(String id)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();

		AccountingPeriod accountingPeriod = new AccountingPeriod();

		if (SiriusValidator.validateParamWithZeroPosibility(id))
		{
			AccountingPeriod period = genericDao.load(AccountingPeriod.class, Long.valueOf(id));
			accountingPeriod.setParent(period);
		}

		map.put("accountingPeriod", accountingPeriod);

		return map;
	}

	@AuditTrails(className = AccountingPeriod.class, actionType = AuditTrailsActionType.CREATE)
	public void add(AccountingPeriod accountingPeriod) throws ServiceException
	{
		if (accountingPeriod.getLevel().equals(Level.GROUP))
			accountingPeriod.setSequence(Long.valueOf(0));
		else
			accountingPeriod.setSequence(Long.valueOf(DateHelper.toMonth(accountingPeriod.getStartDate())));

		accountingPeriod.setYear(DateHelper.toYear(accountingPeriod.getStartDate()));
		accountingPeriod.setMonth(DateHelper.toMonthEnum(accountingPeriod.getStartDate()));
		accountingPeriodDao.add(accountingPeriod);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(String id)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("accountingPeriod", load(id));

		return map;
	}

	@AuditTrails(className = AccountingPeriod.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(AccountingPeriod accountingPeriod) throws ServiceException
	{
		accountingPeriodDao.update(accountingPeriod);
	}

	@AuditTrails(className = AccountingPeriod.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(AccountingPeriod accountingPeriod) throws ServiceException
	{
		if (accountingPeriod.getStatus().equals(PeriodStatus.CLOSED) || accountingPeriod.getStatus().equals(PeriodStatus.PRECLOSE))
			throw new ServiceException("Accounting Period Already closed!");
		int journals = accountingPeriodDao.loadJournals(accountingPeriod.getId()).size();
		if (journals <= 0)
			accountingPeriodDao.delete(accountingPeriod);
		else
			throw new ServiceException("Journal for this period has been exist");
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public AccountingPeriod load(String id)
	{
		return genericDao.load(AccountingPeriod.class, Long.valueOf(id));
	}

	public void preclose(String id) throws Exception
	{
		postingFacade.preclose(id);
	}

	public void close(String id) throws ServiceException
	{
		postingFacade.close(id);
	}

	public void open(String id) throws ServiceException
	{
		postingFacade.open(id);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public AccountingPeriod load(Long organization, Date date)
	{
		return loadWithStatus(organization, date, PeriodStatus.OPEN);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public AccountingPeriod loadWithStatus(Long organization, Date date, PeriodStatus status)
	{
		AccountingPeriod period = accountingPeriodDao.load(organization, date, status);
		if (period == null)
		{
			Party parent = companyStructureDao.loadParent(organization);
			if (parent != null)
				period = accountingPeriodDao.load(parent.getId(), date, status);
		}

		return period;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> closeable(Long id)
	{
		Map<String, Object> map = new FastMap<String, Object>();
		map.put("closeable", true);

		if (SiriusValidator.validateParamWithZeroPosibility(id) && interJournalDao.loadUnfinishInterJournal(id).compareTo(Long.valueOf(0)) != 0)
		{
			map.put("closeable", false);
			map.put("message", "One of Inter To/Inter From Journal Not finish, please finish it first!");
		}

		if (SiriusValidator.validateParamWithZeroPosibility(id) && journalEntryDao.getBatchedCount(Long.valueOf(id)).compareTo(Long.valueOf(0)) > 0)
		{
			map.put("closeable", false);
			map.put("message", "One of Batched Journal Not yet posted(" + journalEntryDao.getBatchedCount(Long.valueOf(id)) + "), please finish it first!");
		}

		return map;
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> report(GridViewFilterCriteria filterCriteria)
	{
		AccountingPeriodFilterCriteria criteria = (AccountingPeriodFilterCriteria) filterCriteria;
		criteria.setParent(companyStructureDao.loadParent(criteria.getOrganization()));

		AccountingPeriod4ReportPopupGridViewQuery query = new AccountingPeriod4ReportPopupGridViewQuery();
		query.setFilterCriteria(criteria);

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("periods", FilterAndPaging.filter(accountingPeriodDao, query));
		map.put("filterCriteria", filterCriteria);

		return map;
	}

}

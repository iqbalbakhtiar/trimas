package com.siriuserp.accounting.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.accounting.adapter.JournalEntryIndexUIAdapter;
import com.siriuserp.accounting.adapter.JournalEntryUIAdapter;
import com.siriuserp.accounting.criteria.JournalEntryFilterCriteria;
import com.siriuserp.accounting.dao.AccountingPeriodDao;
import com.siriuserp.accounting.dao.AccountingSchemaDao;
import com.siriuserp.accounting.dao.GLAccountBalanceDao;
import com.siriuserp.accounting.dao.JournalEntryDao;
import com.siriuserp.accounting.dao.JournalEntryDetailDao;
import com.siriuserp.accounting.dao.RecurringJournalDao;
import com.siriuserp.accounting.dm.AccountingPeriod;
import com.siriuserp.accounting.dm.AccountingSchema;
import com.siriuserp.accounting.dm.EntrySourceType;
import com.siriuserp.accounting.dm.GLAccount;
import com.siriuserp.accounting.dm.GLAccountBalance;
import com.siriuserp.accounting.dm.GLPostingType;
import com.siriuserp.accounting.dm.IndexType;
import com.siriuserp.accounting.dm.JournalEntry;
import com.siriuserp.accounting.dm.JournalEntryConfiguration;
import com.siriuserp.accounting.dm.JournalEntryDetail;
import com.siriuserp.accounting.dm.JournalEntryIndex;
import com.siriuserp.accounting.dm.JournalEntryStatus;
import com.siriuserp.accounting.dm.JournalEntryType;
import com.siriuserp.accounting.dm.JournalSchema;
import com.siriuserp.accounting.dm.PeriodStatus;
import com.siriuserp.accounting.dm.RecurringJournal;
import com.siriuserp.accounting.dm.RecurringJournalDetail;
import com.siriuserp.accounting.dm.RecurringJournalIndex;
import com.siriuserp.accounting.posting.ReverseProccess;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.base.Service;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.CompanyStructureDao;
import com.siriuserp.sdk.dao.CurrencyDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.dm.User;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.DateHelper;
import com.siriuserp.sdk.utility.DecimalHelper;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.QueryFactory;
import com.siriuserp.sdk.utility.SiriusValidator;
import com.siriuserp.sdk.utility.StringHelper;
import com.siriuserp.sdk.utility.UserHelper;

import javolution.util.FastMap;
import javolution.util.FastSet;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class JournalEntryService extends Service
{
	@Autowired
	private CurrencyDao currencyDao;

	@Autowired
	private ReverseProccess reverseProccess;

	@Autowired
	private CodeSequenceDao codeSequenceDao;

	@Autowired
	private JournalEntryDao journalEntryDao;

	@Autowired
	private GLAccountBalanceDao accountBalanceDao;

	@Autowired
	private AccountingPeriodDao accountingPeriodDao;

	@Autowired
	private RecurringJournalDao recurringJournalDao;

	@Autowired
	private CompanyStructureDao companyStructureDao;

	@Autowired
	private AccountingSchemaDao accountingSchemaDao;

	@Autowired
	private JournalEntryDetailDao journalEntryDetailDao;

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private AccountingPeriodService service;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		JournalEntryFilterCriteria criteria = (JournalEntryFilterCriteria) filterCriteria;

		if (criteria.getOrganization() == null)
		{
			User user = genericDao.load(User.class, UserHelper.activeUser().getId());

			criteria.setOrganization(user.getProfile().getOrganization().getId());
		}

		map.put("journalEntrys", FilterAndPaging.filter(journalEntryDao, QueryFactory.create(criteria, queryclass)));
		map.put("filterCriteria", criteria);

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> viewAll(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();

		map.put("journalEntrys", FilterAndPaging.filter(journalEntryDao, QueryFactory.create(filterCriteria, queryclass)));
		map.put("filterCriteria", filterCriteria);

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preadd() throws ServiceException
	{
		JournalEntryUIAdapter adapter = new JournalEntryUIAdapter(new JournalEntry());

		for (IndexType type : genericDao.loadAll(IndexType.class))
		{
			JournalEntryIndexUIAdapter index = new JournalEntryIndexUIAdapter();
			index.setType(type);
			index.setId(type.getId());

			adapter.getIndexes().add(index);
		}

		FastMap<String, Object> map = new FastMap<String, Object>();

		map.put("journalEntry_add", adapter.getJournalEntry());
		map.put("currencys", genericDao.loadAll(Currency.class));
		map.put("defaultCurrency", currencyDao.loadDefaultCurrency());
		map.put("adapter", adapter);
		map.put("now", new Date());

		if (getProfile() != null && getProfile().getOrganization() != null)
		{
			AccountingSchema schema = accountingSchemaDao.load(getProfile().getOrganization());
			if (schema != null && schema.getChartOfAccount() != null)
				map.put("accountingSchema", schema.getChartOfAccount());

			map.put("organization", getProfile().getOrganization());

		}

		return map;
	}

	@SuppressWarnings("rawtypes")
	public JournalEntry schematic(JournalEntryUIAdapter adapter, String[] accounts, String[] amounts, String[] postingTypes, String[] notes, JournalSchema journalSchema) throws ServiceException
	{
		FastSet.recycle((FastSet) adapter.getJournalEntry().getDetails());

		for (JournalEntryIndexUIAdapter index : adapter.getIndexes())
		{
			if (index.getText() != null && !index.getText().isEmpty())
			{
				JournalEntryIndex journalEntryIndex = new JournalEntryIndex();
				journalEntryIndex.setContent(index.getText());
				journalEntryIndex.setIndexType(genericDao.load(IndexType.class, index.getId()));
				journalEntryIndex.setJournalEntry(adapter.getJournalEntry());

				adapter.getJournalEntry().getIndexes().add(journalEntryIndex);
			}
		}

		if (accounts.length == amounts.length && accounts.length == postingTypes.length)
		{
			for (int idx = 0; idx < accounts.length; idx++)
			{
				JournalEntryDetail detail = new JournalEntryDetail();
				detail.setAccount(GLAccount.newInstance(accounts[idx]));
				detail.setJournalEntry(adapter.getJournalEntry());
				detail.setPostingType(GLPostingType.valueOf(postingTypes[idx]));
				detail.setNote(StringHelper.get(notes, idx));
				detail.setTransactionDate(adapter.getJournalEntry().getEntryDate());
				detail.setAmount(DecimalHelper.toSaveDecimal(amounts[idx]));

				adapter.getJournalEntry().getDetails().add(detail);
			}
		}

		adapter.getJournalEntry().setEntrySourceType(EntrySourceType.SCHEMATIC);
		adapter.getJournalEntry().setJournalSchema(journalSchema.getName());
		adapter.getJournalEntry().setSchemaId(journalSchema.getId());

		return adapter.getJournalEntry();
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> precopy(String id) throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("indexTypes", genericDao.loadAll(IndexType.class));
		map.put("currencys", genericDao.loadAll(Currency.class));
		map.put("now", new Date());

		JournalEntry journalEntry = new JournalEntry();

		JournalEntry copy = load(Long.valueOf(id));
		if (copy != null)
		{
			BigDecimal debet = BigDecimal.ZERO;
			BigDecimal credit = BigDecimal.ZERO;

			for (JournalEntryDetail detail : copy.getDetails())
			{
				JournalEntryDetail entryDetail = new JournalEntryDetail();
				entryDetail.setAccount(detail.getAccount());
				entryDetail.setAmount(detail.getAmount());
				entryDetail.setJournalEntry(journalEntry);
				entryDetail.setNote(detail.getNote());
				entryDetail.setPostingType(detail.getPostingType());
				entryDetail.setTransactionDate(new Date());

				journalEntry.getDetails().add(entryDetail);

				switch (detail.getPostingType())
				{
				case DEBET:
					debet = debet.add(detail.getAmount());
					break;
				case CREDIT:
					credit = credit.add(detail.getAmount());
					break;
				}
			}

			map.put("debet", debet);
			map.put("credit", credit);
		}

		map.put("journalEntry_add", journalEntry);
		map.put("currencys", genericDao.loadAll(Currency.class));
		map.put("defaultCurrency", currencyDao.loadDefaultCurrency());
		map.put("schema", accountingSchemaDao.load(copy.getOrganization()));

		if (getProfile() != null && getProfile().getOrganization() != null)
		{
			AccountingSchema schema = accountingSchemaDao.load(getProfile().getOrganization());
			if (schema != null && schema.getChartOfAccount() != null)
				map.put("accountingSchema", schema.getChartOfAccount());

			map.put("organization", getProfile().getOrganization());

		}

		return map;
	}

	public AccountingPeriod loadIgnorePeriode(Long organization, Date date)
	{
		return service.loadWithStatus(organization, date, null);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> prereverse(String id) throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("indexTypes", genericDao.loadAll(IndexType.class));
		map.put("currencys", genericDao.loadAll(Currency.class));
		map.put("defaultCurrency", currencyDao.loadDefaultCurrency());
		map.put("now", new Date());

		JournalEntry journalEntry = new JournalEntry();

		JournalEntry reverse = load(Long.valueOf(id));

		if (reverse != null)
		{

			for (JournalEntryDetail detail : reverse.getDetails())
			{
				JournalEntryDetail entryDetail = new JournalEntryDetail();
				entryDetail.setAccount(detail.getAccount());
				entryDetail.setAmount(detail.getAmount());
				entryDetail.setJournalEntry(journalEntry);
				entryDetail.setNote(detail.getNote());
				entryDetail.setTransactionDate(new Date());

				switch (detail.getPostingType())
				{
				case DEBET:
					entryDetail.setPostingType(GLPostingType.CREDIT);
					break;
				case CREDIT:
					entryDetail.setPostingType(GLPostingType.DEBET);
					break;
				}

				journalEntry.getDetails().add(entryDetail);
			}

			journalEntry.setOrganization(reverse.getOrganization());
			journalEntry.setAccountingPeriod(reverse.getAccountingPeriod());
			journalEntry.setEntrySourceType(reverse.getEntrySourceType());

		}

		map.put("journalEntry_add", journalEntry);
		map.put("schema", accountingSchemaDao.load(reverse.getOrganization()));

		if (getProfile() != null && getProfile().getOrganization() != null)
		{
			AccountingSchema schema = accountingSchemaDao.load(getProfile().getOrganization());
			if (schema != null && schema.getChartOfAccount() != null)
				map.put("accountingSchema", schema.getChartOfAccount());

			map.put("organization", getProfile().getOrganization());

		}

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(String id)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();

		JournalEntry journalEntry = load(Long.valueOf(id));

		map.put("currencys", genericDao.loadAll(Currency.class));
		map.put("defaultCurrency", currencyDao.loadDefaultCurrency());
		map.put("journalEntry_edit", journalEntry);
		map.put("display", new JournalEntryUIAdapter(journalEntry));

		return map;
	}

	@AuditTrails(className = JournalEntry.class, actionType = AuditTrailsActionType.CREATE)
	public void add(JournalEntry journalEntry) throws ServiceException
	{
		if (journalEntry.getDebet().compareTo(journalEntry.getCredit()) != 0)
			throw new ServiceException("Journal Entry amount doesnot balance!");

		if (journalEntry.getDetails().isEmpty())
			throw new ServiceException("Journal entry detail does not exist,please provide it first!");

		if (journalEntry.getAccountingPeriod() == null)
		{
			AccountingPeriod period = service.load(journalEntry.getOrganization().getId(), journalEntry.getEntryDate());
			if (period == null)
				throw new ServiceException("Accounting period does not exist or already closed!");

			journalEntry.setAccountingPeriod(period);
		}

		if (journalEntry.getCode() == null)
			journalEntry.setCode(GeneratorHelper.instance().generate(TableType.JOURNAL_ENTRY, codeSequenceDao, journalEntry.getOrganization(), journalEntry.getEntryDate()));

		for (JournalEntryIndex journalEntryIndex : journalEntry.getIndexes())
		{
			journalEntryIndex.setCreatedBy(getPerson(journalEntryIndex.getCreatedBy()));
			journalEntryIndex.setCreatedDate(DateHelper.now());
			journalEntryIndex.setIndexType(genericDao.load(IndexType.class, journalEntryIndex.getIndexType().getId()));
		}

		for (JournalEntryDetail detail : journalEntry.getDetails())
		{
			if (detail.getAccount() == null)
				throw new ServiceException("One of your journal entry detail account does not exist: " + detail.getNote());

			detail.setAccount(genericDao.load(GLAccount.class, Long.valueOf(detail.getAccount().getId())));
			detail.setCreatedBy(getPerson(detail.getCreatedBy()));
			detail.setCreatedDate(DateHelper.now());
		}

		JournalEntryConfiguration configuration = genericDao.load(JournalEntryConfiguration.class, Long.valueOf(1));
		if (configuration != null && isUserTransaction(journalEntry))
		{
			switch (configuration.getBatchingType())
			{
			case ALL:
				journalEntry.setEntryStatus(JournalEntryStatus.BATCHED);
				break;
			case CUSTOM:
				//check if current source is registered to be batched,doBatch,else doPost
				//doPost(journalEntry);
				break;
			default:
				//doPost(journalEntry);
			}
		} else
			doPost(journalEntry);

		journalEntryDao.add(journalEntry);
	}

	private boolean isUserTransaction(JournalEntry journalEntry)
	{
		return (!journalEntry.getEntrySourceType().equals(EntrySourceType.CLOSING) && !journalEntry.getEntrySourceType().equals(EntrySourceType.AUTOAJUSTMENT) && !journalEntry.getEntrySourceType().equals(EntrySourceType.OPENING));
	}

	private void doPost(JournalEntry journalEntry) throws ServiceException
	{
		for (JournalEntryDetail detail : journalEntry.getDetails())
			processBalance(journalEntry, detail);
	}

	@AuditTrails(className = JournalEntry.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(JournalEntry journalEntry) throws ServiceException
	{
		if (journalEntry.getAccountingPeriod() == null)
			throw new ServiceException("Accounting Period doesnot exist.");

		if (journalEntry.getEntryStatus().equals(JournalEntryStatus.POSTED))
			throw new ServiceException("Journal Already posted!");

		if (journalEntry.getAccountingPeriod().getStatus().equals(PeriodStatus.CLOSED))
			throw new ServiceException("Accounting Period Already closed!");

		AccountingPeriod period = service.load(journalEntry.getOrganization().getId(), journalEntry.getEntryDate());
		if (period == null)
			throw new ServiceException("Accounting period does not exist or already closed!");

		journalEntryDao.update(journalEntry);
	}

	@AuditTrails(className = JournalEntry.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(JournalEntry journalEntry, GLAccount[] accounts, BigDecimal[] amounts, GLPostingType[] types, String[] notes) throws ServiceException
	{
		if (journalEntry.getEntryStatus().equals(JournalEntryStatus.POSTED))
			throw new ServiceException("Journal Already posted!");

		if (journalEntry.getAccountingPeriod().getStatus().equals(PeriodStatus.CLOSED))
			throw new ServiceException("Accounting Period Already closed!");

		if (journalEntry.getDetails().isEmpty())
			throw new ServiceException("Journal Entry Detail does not exist,please provide it first!");

		AccountingPeriod period = service.load(journalEntry.getOrganization().getId(), journalEntry.getEntryDate());
		if (period == null)
			throw new ServiceException("Accounting period does not exist or already closed!");

		journalEntry.setAccountingPeriod(period);

		for (JournalEntryDetail detail : journalEntry.getDetails())
			journalEntryDetailDao.delete(detail);

		journalEntry.getDetails().clear();

		journalEntryDao.update(journalEntry);

		if (journalEntry.getDetails().isEmpty())
		{
			for (int idx = 0; idx < accounts.length; idx++)
			{
				JournalEntryDetail detail = new JournalEntryDetail();
				detail.setAccount(accounts[idx]);
				detail.setAmount(amounts[idx]);
				detail.setCreatedBy(journalEntry.getCreatedBy());
				detail.setCreatedDate(DateHelper.now());
				detail.setJournalEntry(journalEntry);
				detail.setNote(notes[idx]);
				detail.setPostingType(types[idx]);
				detail.setTransactionDate(journalEntry.getEntryDate());

				journalEntry.getDetails().add(detail);
			}

			journalEntryDao.update(journalEntry);
		}
	}

	@AuditTrails(className = JournalEntry.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(JournalEntry journalEntry) throws ServiceException
	{
		journalEntryDao.delete(journalEntry);
	}

	@AuditTrails(className = JournalEntry.class, actionType = AuditTrailsActionType.UPDATE)
	public void post(String[] ids) throws ServiceException
	{
		for (String id : ids)
		{
			JournalEntry journalEntry = load(Long.valueOf(id));
			if (journalEntry != null)
			{
				journalEntry.setEntryStatus(JournalEntryStatus.POSTED);
				journalEntry.setPostedBy(UserHelper.activePerson());

				journalEntryDao.update(journalEntry);

				for (JournalEntryDetail detail : journalEntry.getDetails())
					processBalance(journalEntry, detail);
			}
		}
	}

	private void processBalance(JournalEntry journalEntry, JournalEntryDetail detail) throws ServiceException
	{
		GLAccountBalance accountBalance = accountBalanceDao.load(detail.getAccount(), journalEntry.getCurrency(), journalEntry.getOrganization(), journalEntry.getAccountingPeriod());

		if (accountBalance != null)
		{
			doUserTransactions(journalEntry, detail, accountBalance);
			doSystemTransactions(journalEntry, detail, accountBalance);

			accountBalanceDao.update(accountBalance);
		} else
		{
			GLAccountBalance balance = new GLAccountBalance();
			balance.setAccount(detail.getAccount());
			balance.setAccountingPeriod(journalEntry.getAccountingPeriod());
			balance.setCreatedBy(getPerson());
			balance.setCreatedDate(DateHelper.now());
			balance.setCurrency(journalEntry.getCurrency());
			balance.setOrganization(journalEntry.getOrganization());
			balance.setCode(balance.getAccount().getCode() + balance.getCurrency().getSymbol() + balance.getOrganization().getCode() + balance.getAccountingPeriod().getCode());

			doUserTransactions(journalEntry, detail, balance);
			doSystemTransactions(journalEntry, detail, balance);

			accountBalanceDao.add(balance);
		}
	}

	private void doUserTransactions(JournalEntry journalEntry, JournalEntryDetail detail, GLAccountBalance balance)
	{
		switch (detail.getPostingType())
		{
		case CREDIT:
			balance.getUserTransaction().setCredit(DecimalHelper.safe(balance.getUserTransaction().getCredit()).add(detail.getAmount()));
			balance.getUserTransaction().setDefaultcredit(DecimalHelper.safe(balance.getUserTransaction().getDefaultcredit()).add(detail.getAmount().multiply(journalEntry.getRate())));
			break;
		default:
			balance.getUserTransaction().setDebet(DecimalHelper.safe(balance.getUserTransaction().getDebet()).add(detail.getAmount()));
			balance.getUserTransaction().setDefaultdebet(DecimalHelper.safe(balance.getUserTransaction().getDefaultdebet()).add(detail.getAmount().multiply(journalEntry.getRate())));
			break;
		}

		if (!journalEntry.getEntrySourceType().equals(EntrySourceType.CLOSING))
			doEntryBalanceDetail(journalEntry, detail, balance);
	}

	private void doEntryBalanceDetail(JournalEntry journalEntry, JournalEntryDetail detail, GLAccountBalance balance)
	{
		switch (journalEntry.getEntryType())
		{
		case ENTRY:
			switch (detail.getPostingType())
			{
			case CREDIT:
				balance.getUserTransaction().setEntrycredit(DecimalHelper.safe(balance.getUserTransaction().getEntrycredit()).add(detail.getAmount().multiply(journalEntry.getRate())));
				break;
			case DEBET:
				balance.getUserTransaction().setEntrydebet(DecimalHelper.safe(balance.getUserTransaction().getEntrydebet()).add(detail.getAmount().multiply(journalEntry.getRate())));
				break;
			}
			break;
		case CORRECTION:
			switch (detail.getPostingType())
			{
			case CREDIT:
				balance.getUserTransaction().setCorrectioncredit(DecimalHelper.safe(balance.getUserTransaction().getCorrectioncredit()).add(detail.getAmount().multiply(journalEntry.getRate())));
				break;
			case DEBET:
				balance.getUserTransaction().setCorrectiondebet(DecimalHelper.safe(balance.getUserTransaction().getCorrectiondebet()).add(detail.getAmount().multiply(journalEntry.getRate())));
				break;
			}
			break;
		case ADJUSTMENT:
			switch (detail.getPostingType())
			{
			case CREDIT:
				balance.getUserTransaction().setAdjustmentcredit(DecimalHelper.safe(balance.getUserTransaction().getAdjustmentcredit()).add(detail.getAmount().multiply(journalEntry.getRate())));
				break;
			case DEBET:
				balance.getUserTransaction().setAdjustmentdebet(DecimalHelper.safe(balance.getUserTransaction().getAdjustmentdebet()).add(detail.getAmount().multiply(journalEntry.getRate())));
				break;
			}
			break;
		}
	}

	private void doSystemTransactions(JournalEntry journalEntry, JournalEntryDetail detail, GLAccountBalance accountBalance)
	{
		switch (journalEntry.getEntrySourceType())
		{
		case AUTOAJUSTMENT:
			doAutoAdjustment(detail, accountBalance);
			break;

		case CLOSING:
			doClosing(detail, accountBalance);
			break;

		case OPENING:
			doOpening(detail, accountBalance);
			break;

		default:
			break;
		}
	}

	private void doAutoAdjustment(JournalEntryDetail detail, GLAccountBalance accountBalance)
	{
		switch (detail.getPostingType())
		{
		case CREDIT:
			accountBalance.getSystemTransaction().setAdjustmentcredit(DecimalHelper.safe(accountBalance.getSystemTransaction().getAdjustmentcredit()).add(detail.getAmount().multiply(detail.getJournalEntry().getRate())));
			break;
		default:
			accountBalance.getSystemTransaction().setAdjustmentdebet(DecimalHelper.safe(accountBalance.getSystemTransaction().getAdjustmentdebet()).add(detail.getAmount().multiply(detail.getJournalEntry().getRate())));
			break;
		}
	}

	private void doClosing(JournalEntryDetail detail, GLAccountBalance accountBalance)
	{
		switch (detail.getPostingType())
		{
		case CREDIT:
			accountBalance.getSystemTransaction()
					.setClosingcredit(DecimalHelper.safe(accountBalance.getSystemTransaction().getClosingcredit()).add(DecimalHelper.safe(detail.getAmount()).multiply(DecimalHelper.safe(detail.getJournalEntry().getRate()))));
			break;
		default:
			accountBalance.getSystemTransaction()
					.setClosingdebet(DecimalHelper.safe(accountBalance.getSystemTransaction().getClosingdebet()).add(DecimalHelper.safe(detail.getAmount()).multiply(DecimalHelper.safe(detail.getJournalEntry().getRate()))));
			break;
		}
	}

	private void doOpening(JournalEntryDetail detail, GLAccountBalance accountBalance)
	{
		switch (detail.getPostingType())
		{
		case CREDIT:
			accountBalance.getSystemTransaction().setOpeningcredit(DecimalHelper.safe(accountBalance.getSystemTransaction().getOpeningcredit()).add(detail.getAmount().multiply(detail.getJournalEntry().getRate())));
			break;
		default:
			accountBalance.getSystemTransaction().setOpeningdebet(DecimalHelper.safe(accountBalance.getSystemTransaction().getOpeningdebet()).add(detail.getAmount().multiply(detail.getJournalEntry().getRate())));
			break;
		}
	}

	public void cronjob() throws ServiceException
	{
		List<RecurringJournal> recurrings = recurringJournalDao.loadToday(DateHelper.getDay());
		for (RecurringJournal out : recurrings)
		{
			AccountingPeriod accountingPeriod = accountingPeriodDao.loadToday(out.getOrganization().getId());

			//if accounting period does not exist,try to load using parent organization
			if (accountingPeriod == null)
			{
				Party parent = companyStructureDao.loadParent(out.getOrganization().getId());
				if (parent != null)
					accountingPeriod = accountingPeriodDao.loadToday(parent.getId());
			}

			//still does not exist,throw exception
			if (accountingPeriod == null)
				throw new ServiceException("JournalEntryService.cronjob()");

			JournalEntry entry = new JournalEntry();
			entry.setCurrency(out.getCurrency());
			entry.setExchangeType(out.getExchangeType());
			entry.setEntryDate(new Date());
			entry.setEntrySourceType(EntrySourceType.RECURRING);
			entry.setEntryType(JournalEntryType.ENTRY);
			entry.setName(out.getName());
			entry.setNote("RECURRING ENTRY");
			entry.setOrganization(out.getOrganization());
			entry.setAccountingPeriod(accountingPeriod);
			entry.setCreatedBy(out.getCreatedBy());

			for (RecurringJournalIndex index : out.getIndexes())
			{
				JournalEntryIndex journalEntryIndex = new JournalEntryIndex();
				journalEntryIndex.setContent(index.getIndex());
				journalEntryIndex.setIndexType(index.getIndexType());
				journalEntryIndex.setJournalEntry(entry);
				journalEntryIndex.setCreatedBy(entry.getCreatedBy());

				entry.getIndexes().add(journalEntryIndex);
			}

			for (RecurringJournalDetail detail : out.getDetails())
			{
				JournalEntryDetail journalEntryDetail = new JournalEntryDetail();
				journalEntryDetail.setAccount(detail.getAccount());
				journalEntryDetail.setAmount(detail.getAmount());
				journalEntryDetail.setJournalEntry(entry);
				journalEntryDetail.setNote(detail.getNote());
				journalEntryDetail.setPostingType(detail.getPostingType());
				journalEntryDetail.setTransactionDate(new Date());
				journalEntryDetail.setCreatedBy(entry.getCreatedBy());

				entry.getDetails().add(journalEntryDetail);
			}

			add(entry);
		}
	}

	public void pending(JournalEntry journalEntry) throws ServiceException
	{
		journalEntry.setCode(GeneratorHelper.instance().generate(TableType.JOURNAL_ENTRY, codeSequenceDao, journalEntry.getOrganization()));

		for (JournalEntryIndex journalEntryIndex : journalEntry.getIndexes())
		{
			journalEntryIndex.setCreatedBy(getPerson(journalEntryIndex.getCreatedBy()));
			journalEntryIndex.setCreatedDate(DateHelper.now());
			journalEntryIndex.setIndexType(genericDao.load(IndexType.class, journalEntryIndex.getIndexType().getId()));
			journalEntryIndex.setJournalEntry(journalEntry);
		}

		for (JournalEntryDetail detail : journalEntry.getDetails())
		{
			detail.setAccount(genericDao.load(GLAccount.class, Long.valueOf(detail.getAccount().getId())));
			detail.setCreatedBy(getPerson(detail.getCreatedBy()));
			detail.setCreatedDate(DateHelper.now());
			detail.setJournalEntry(journalEntry);
		}

		if (journalEntry.getEntrySourceType().equals(EntrySourceType.SCHEMATIC) || journalEntry.getEntrySourceType().equals(EntrySourceType.INTER))
		{
			if (SiriusValidator.validateParamWithZeroPosibility(journalEntry.getJournalSchema()))
			{
				JournalSchema journalSchema = genericDao.load(JournalSchema.class, Long.valueOf(journalEntry.getJournalSchema()));
				if (journalSchema != null)
					journalEntry.setJournalSchema(journalSchema.getCode() + " " + journalSchema.getName());
			}
		}

		journalEntry.setEntryStatus(JournalEntryStatus.PENDING);
		journalEntryDao.add(journalEntry);
	}

	public JournalEntry load(Long id)
	{
		return genericDao.load(JournalEntry.class, id);
	}

	public void deleteAll(AccountingPeriod accountingPeriod, Party organization, AccountingPeriod next) throws ServiceException
	{
		journalEntryDao.deleteAll(accountingPeriod, organization, next);
	}

	public void unpost(Long journal) throws ServiceException
	{
		JournalEntry journalEntry = load(journal);
		if (journalEntry != null && journalEntry.getEntryStatus().equals(JournalEntryStatus.POSTED))
		{
			if (journalEntry.getAccountingPeriod().getStatus().equals(PeriodStatus.CLOSED))
				throw new ServiceException("Accounting Period Already Closed!");

			for (JournalEntryDetail detail : journalEntry.getDetails())
				reverseProccess.doReverseBalance(detail, journalEntry.getAccountingPeriod(), journalEntry.getOrganization());

			journalEntry.setEntryStatus(JournalEntryStatus.BATCHED);

			journalEntryDao.update(journalEntry);
		}
	}

	@AuditTrails(className = JournalEntry.class, actionType = AuditTrailsActionType.CREATE)
	public void recode(GridViewFilterCriteria filterCriteria) throws Exception
	{
		List<JournalEntry> journalEntries = genericDao.getUniqeFields(JournalEntry.class, new String[]
		{}, new Object[]
		{}, new String[]
		{ "entryDate", "id" }, new String[]
		{ "ASC", "ASC" });

		for (JournalEntry journalEntry : journalEntries)
			journalEntry.setCode(GeneratorHelper.instance().generate(TableType.JOURNAL_ENTRY, codeSequenceDao, journalEntry.getOrganization(), journalEntry.getEntryDate()));
	}
}

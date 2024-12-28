/**
 * May 20, 2009 8:42:16 AM
 * com.siriuserp.sdk.base
 * PostingRole.java
 */
package com.siriuserp.accounting.posting;

import org.springframework.beans.factory.annotation.Autowired;

import com.siriuserp.accounting.dao.AccountingPeriodDao;
import com.siriuserp.accounting.dao.AccountingSchemaDao;
import com.siriuserp.accounting.dao.ClosingAccountDao;
import com.siriuserp.accounting.dao.FixedAssetGroupDao;
import com.siriuserp.accounting.dao.ProductCategoryAccountingSchemaDao;
import com.siriuserp.accounting.dao.ProductCategoryClosingAccountDao;
import com.siriuserp.accounting.dm.AccountingPeriod;
import com.siriuserp.accounting.dm.AccountingSchema;
import com.siriuserp.accounting.dm.EntrySourceType;
import com.siriuserp.accounting.dm.JournalEntry;
import com.siriuserp.accounting.dm.JournalEntryType;
import com.siriuserp.accounting.dm.PeriodStatus;
import com.siriuserp.accounting.dm.Postingable;
import com.siriuserp.accounting.service.AccountingPeriodService;
import com.siriuserp.accounting.service.JournalEntryService;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.dao.CompanyStructureDao;
import com.siriuserp.sdk.dao.CurrencyDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dm.ActivityHistory;
import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.utility.DateHelper;
import com.siriuserp.sdk.utility.UserHelper;
import com.siriuserp.tools.service.AuditTrailService;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@SuppressWarnings("unchecked")
public abstract class AbstractPostingRole implements PostingRole
{
	protected Postingable postingable;

	protected AutomaticPosting automaticPosting;

	protected AccountingSchema accountingSchema;

	@Autowired
	protected GenericDao genericDao;

	@Autowired
	protected CurrencyDao currencyDao;

	@Autowired
	protected ClosingAccountDao closingAccountDao;

	@Autowired
	protected AuditTrailService auditTrailService;

	@Autowired
	protected AccountingSchemaDao accountingSchemaDao;

	@Autowired
	protected CompanyStructureDao companyStructureDao;

	@Autowired
	protected JournalEntryService journalEntryService;

	@Autowired
	protected AccountingPeriodDao accountingPeriodDao;

	/*@Autowired
	protected MasterCostSchemaDao masterCostSchemaDao;*/

	/*@Autowired
	protected ProductInOutAveragePriceDao productInOutAveragePriceDao;*/

	@Autowired
	protected ProductCategoryAccountingSchemaDao categoryAccountingSchemaDao;

	@Autowired
	protected ProductCategoryClosingAccountDao productCategoryClosingAccountDao;

	@Autowired
	protected ProductCategoryAccountingSchemaDao productCategoryAccountingSchemaDao;

	@Autowired
	protected FixedAssetGroupDao fixedAssetGroupDao;

	@Autowired
	protected AccountingPeriodService accountingPeriodService;

	/*@Autowired
	protected StockControllService stockControll;*/

	public AccountingSchema getAccountingSchema()
	{
		return accountingSchema;
	}

	public void setAccountingSchema(AccountingSchema accountingSchema)
	{
		this.accountingSchema = accountingSchema;
	}

	public AutomaticPosting getAutomaticPosting()
	{
		return automaticPosting;
	}

	public void setAutomaticPosting(AutomaticPosting automaticPosting)
	{
		this.automaticPosting = automaticPosting;
	}

	public <T extends Postingable> T getPostingable()
	{
		return (T) postingable;
	}

	public void setPostingable(Postingable postingable)
	{
		this.postingable = postingable;
	}

	protected JournalEntry newInstance() throws ServiceException
	{
		AccountingPeriod accountingPeriod = null;

		accountingPeriod = accountingPeriodDao.load(getPostingable().getOrganization().getId(), getPostingable().getDate(), PeriodStatus.OPEN);
		if (accountingPeriod == null)
		{
			Party parent = companyStructureDao.loadParent(getPostingable().getOrganization().getId());
			if (parent != null)
				accountingPeriod = accountingPeriodDao.load(parent.getId(), getPostingable().getDate(), PeriodStatus.OPEN);
		}

		if (accountingPeriod == null)
			throw new ServiceException("Accounting Period for " + DateHelper.format(getPostingable().getDate()) + " does not exist, " + "please go to Financial Accounting > Accounting Setting > Accounting Period,to set it up.");

		JournalEntry journalEntry = new JournalEntry();
		journalEntry.setAccountingPeriod(accountingPeriod);
		journalEntry.setEntryType(JournalEntryType.ENTRY);
		journalEntry.setEntrySourceType(EntrySourceType.STANDARD);
		journalEntry.setOrganization(genericDao.load(Party.class, getPostingable().getOrganization().getId()));

		return journalEntry;
	}

	@Override
	public void inisialize() throws ServiceException
	{
		AccountingSchema accountingSchema = accountingSchemaDao.load(postingable.getOrganization());
		if (accountingSchema != null)
			this.accountingSchema = accountingSchema;
	}

	@Override
	public void commit() throws ServiceException
	{
		if (automaticPosting.verify())
		{
			if (postingable.getJournalEntry() == null)
				throw new ServiceException("Auto posting journal does not exist.");
			genericDao.update(postingable);
		}
	}

	public void audit() throws ServiceException
	{
		ActivityHistory activityHistory = new ActivityHistory();
		activityHistory.setAccessedModule(this.getClass().getSimpleName());
		activityHistory.setActionDate(DateHelper.now());
		activityHistory.setAccessedModuleId(((Model) postingable).getAuditCode());
		activityHistory.setActionType(AuditTrailsActionType.CREATE);
		activityHistory.setActivePerson(UserHelper.activePerson());

		genericDao.add(activityHistory);
	}
}

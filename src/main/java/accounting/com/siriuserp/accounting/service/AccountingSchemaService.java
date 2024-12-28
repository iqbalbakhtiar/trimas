package com.siriuserp.accounting.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.accounting.dao.AccountingSchemaDao;
import com.siriuserp.accounting.dao.ClosingAccountDao;
import com.siriuserp.accounting.dao.ClosingAccountTypeDao;
import com.siriuserp.accounting.dao.ClosingDistributionDestinationDao;
import com.siriuserp.accounting.dao.JournalDistributionSourceDao;
import com.siriuserp.accounting.dm.AccountingPeriod;
import com.siriuserp.accounting.dm.AccountingSchema;
import com.siriuserp.accounting.dm.ChartOfAccount;
import com.siriuserp.accounting.dm.ClosingAccount;
import com.siriuserp.accounting.dm.ClosingAccountType;
import com.siriuserp.accounting.dm.ClosingDistribution;
import com.siriuserp.accounting.dm.ClosingDistributionDestination;
import com.siriuserp.accounting.dm.JournalDistributionSource;
import com.siriuserp.accounting.dm.JournalEntryConfiguration;
import com.siriuserp.accounting.query.AccountingSchemaGridViewQuery;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.annotation.InjectParty;
import com.siriuserp.sdk.dao.CompanyStructureDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.exceptions.DataAdditionException;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;

import javolution.util.FastMap;

@Component
@Transactional(rollbackFor = Exception.class)
public class AccountingSchemaService
{
	@Autowired
	private ClosingAccountDao closingAccountDao;

	@Autowired
	private GenericDao genericDao;

	/*@Autowired
	private SalesMasterCostDao salesMasterCostDao;*/

	@Autowired
	private AccountingSchemaDao accountingSchemaDao;

	@Autowired
	private CompanyStructureDao companyStructureDao;

	@Autowired
	private ClosingAccountTypeDao closingAccountTypeDao;

	/*@Autowired
	private ProductConsumptionDao consumptionDao;
	
	@Autowired
	private ProcurementMasterCostDao procurementMasterCostDao;*/

	@Autowired
	private JournalDistributionSourceDao journalDistributionSourceDao;

	@Autowired
	private ClosingDistributionDestinationDao closingDistributionDestinationDao;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria)
	{
		AccountingSchemaGridViewQuery query = new AccountingSchemaGridViewQuery();
		query.setFilterCriteria(filterCriteria);

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("filterCriteria", filterCriteria);
		map.put("accountingSchemas", FilterAndPaging.filter(genericDao, query));
		map.put("companyfacilitys", genericDao.loadAll(Party.class));

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	@InjectParty(keyName = "accountingSchema")
	public FastMap<String, Object> preadd()
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("periods", genericDao.loadAll(AccountingPeriod.class));
		map.put("coas", genericDao.loadAll(ChartOfAccount.class));

		AccountingSchema accountingSchema = new AccountingSchema();

		for (ClosingAccountType accountType : closingAccountTypeDao.loadAllNonAsset())
		{
			ClosingAccount closingAccount = new ClosingAccount();
			closingAccount.setAccountingSchema(accountingSchema);
			closingAccount.setClosingAccountType(accountType);

			accountingSchema.getClosingAccounts().add(closingAccount);
		}

		/*for (ProcurementMasterCost cost : procurementMasterCostDao.loadAllActive())
		{
			ProcurementMasterCostSchema schema = new ProcurementMasterCostSchema();
			schema.setAccountingSchema(accountingSchema);
			schema.setCost(cost);
		
			accountingSchema.getProcurementSchemas().add(schema);
		}*/

		map.put("accountingSchema_add", accountingSchema);

		return map;
	}

	@AuditTrails(className = AccountingSchema.class, actionType = AuditTrailsActionType.CREATE)
	public void add(AccountingSchema accountingSchema) throws ServiceException
	{
		accountingSchemaDao.add(accountingSchema);
	}

	public FastMap<String, Object> preedit(String id) throws DataAdditionException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();

		AccountingSchema accountingSchema = genericDao.load(AccountingSchema.class, Long.valueOf(id));

		for (ClosingAccountType accountType : closingAccountTypeDao.loadAllNonAsset())
		{
			ClosingAccount dbClosingAccount = closingAccountDao.loadByParentAndType(accountingSchema.getId(), accountType.getId());
			if (dbClosingAccount == null)
			{
				ClosingAccount closingAccount = new ClosingAccount();
				closingAccount.setAccountingSchema(accountingSchema);
				closingAccount.setClosingAccountType(accountType);

				accountingSchema.getClosingAccounts().add(closingAccount);
			}
		}

		map.put("accountingSchema_edit", accountingSchema);

		JournalEntryConfiguration configuration = genericDao.load(JournalEntryConfiguration.class, Long.valueOf(1));
		if (configuration != null && configuration.isJournalDistribution())
		{
			JournalDistributionSource distributionSource = journalDistributionSourceDao.load(accountingSchema.getOrganization());
			if (distributionSource != null)
			{
				map.put("distributable", true);

				ClosingDistribution closingDistribution = accountingSchema.getClosingDistribution();
				if (closingDistribution == null)
				{
					closingDistribution = new ClosingDistribution();

					Party parent = companyStructureDao.loadParent(accountingSchema.getOrganization().getId());
					if (parent != null)
					{
						for (Party organization : companyStructureDao.loadAllVerticalDown(parent))
						{
							ClosingDistributionDestination destination = new ClosingDistributionDestination();
							destination.setOrganization(organization);
							destination.setClosingDistribution(closingDistribution);

							closingDistribution.getDestinations().add(destination);
						}
					}

					accountingSchema.setClosingDistribution(closingDistribution);
				} else
				{
					Party parent = companyStructureDao.loadParent(accountingSchema.getOrganization().getId());
					if (parent != null)
					{
						List<Party> childs = companyStructureDao.loadAllVerticalDown(parent);
						for (Party organization : childs)
						{
							ClosingDistributionDestination destination = closingDistributionDestinationDao.loadByParentAndOrg(accountingSchema.getClosingDistribution().getId(), organization.getId());
							if (destination == null)
							{
								ClosingDistributionDestination in = new ClosingDistributionDestination();
								in.setOrganization(organization);
								in.setClosingDistribution(accountingSchema.getClosingDistribution());

								accountingSchema.getClosingDistribution().getDestinations().add(in);
							}
						}
					}
				}
			}
		}

		return map;
	}

	@AuditTrails(className = AccountingSchema.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(AccountingSchema accountingSchema) throws ServiceException
	{
		accountingSchemaDao.update(accountingSchema);
	}

	@AuditTrails(className = AccountingSchema.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(AccountingSchema accountingSchema) throws ServiceException
	{
		accountingSchemaDao.delete(accountingSchema);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public AccountingSchema load(String id)
	{
		return genericDao.load(AccountingSchema.class, Long.valueOf(id));
	}
}
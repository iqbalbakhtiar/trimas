package com.siriuserp.accounting.posting;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siriuserp.accounting.adapter.GLAccountBalanceAdapter;
import com.siriuserp.accounting.dao.AccountingSchemaDao;
import com.siriuserp.accounting.dm.AccountingSchema;
import com.siriuserp.accounting.dm.ClosingDistributionDestination;
import com.siriuserp.accounting.dm.EntrySourceType;
import com.siriuserp.accounting.dm.JournalEntry;
import com.siriuserp.accounting.dm.JournalEntryDetail;
import com.siriuserp.accounting.dm.JournalEntryStatus;
import com.siriuserp.accounting.dm.JournalEntryType;
import com.siriuserp.sdk.dm.ReportType;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.utility.DecimalHelper;

@Component
public class DistributionProcess extends PostingProcess
{
	@Autowired
	private AccountingSchemaDao accountingSchemaDao;

	@Override
	public void execute() throws ServiceException
	{
		if (configuration.isJournalDistribution())
		{
			AccountingSchema schema = accountingSchemaDao.loadDistributed(organization);
			if (schema != null)
			{
				BigDecimal total = BigDecimal.ZERO;

				List<GLAccountBalanceAdapter> adapters = accountBalanceDao.loadAll(accountingPeriod, organization, ReportType.INCOMESTATEMENT);
				if (!adapters.isEmpty())
				{
					JournalEntry journalEntry = new JournalEntry();
					journalEntry.setAccountingPeriod(accountingPeriod);
					journalEntry.setCurrency(defaultCurrency);
					journalEntry.setEntryDate(accountingPeriod.getEndDate());
					journalEntry.setEntrySourceType(EntrySourceType.AUTOAJUSTMENT);
					journalEntry.setEntryStatus(JournalEntryStatus.POSTED);
					journalEntry.setEntryType(JournalEntryType.ADJUSTMENT);
					journalEntry.setExchangeType(defaultExchangeType);
					journalEntry.setName("ACE/DISTRIBUTION");
					journalEntry.setNote("ACE/DISTRIBUTION");
					journalEntry.setOrganization(organization);

					for (GLAccountBalanceAdapter accountBalance : adapters)
					{
						BigDecimal amount = DecimalHelper.safe(accountBalance.getDebet()).subtract(DecimalHelper.safe(accountBalance.getCredit()));

						JournalEntryDetail from = new JournalEntryDetail();
						from.setAccount(accountBalance.getAccount());
						from.setAmount(DecimalHelper.positive(amount));
						from.setJournalEntry(journalEntry);
						from.setNote("ACE/DISTRIBUTION");
						from.setPostingType(reverse(amount));
						from.setTransactionDate(accountingPeriod.getEndDate());

						journalEntry.getDetails().add(from);

						total = total.add(amount);
					}

					if (!journalEntry.getDetails().isEmpty())
					{
						JournalEntryDetail to = new JournalEntryDetail();
						to.setAccount(schema.getClosingDistribution().getAccount());
						to.setAmount(DecimalHelper.positive(total));
						to.setJournalEntry(journalEntry);
						to.setNote("ACE/DISTRIBUTION");
						to.setPostingType(toPostingType(total));
						to.setTransactionDate(accountingPeriod.getEndDate());

						journalEntry.getDetails().add(to);
					}

					journalEntryService.add(journalEntry);

					for (ClosingDistributionDestination destination : schema.getClosingDistribution().getDestinations())
					{
						if (destination.getRate().compareTo(BigDecimal.ZERO) > 0)
						{
							JournalEntry distributed = new JournalEntry();
							distributed.setAccountingPeriod(accountingPeriod);
							distributed.setCurrency(defaultCurrency);
							distributed.setEntryDate(accountingPeriod.getEndDate());
							distributed.setEntrySourceType(EntrySourceType.AUTOAJUSTMENT);
							distributed.setEntryStatus(JournalEntryStatus.POSTED);
							distributed.setEntryType(JournalEntryType.ADJUSTMENT);
							distributed.setExchangeType(defaultExchangeType);
							distributed.setName("ACE/DISTRIBUTION");
							distributed.setNote("ACE/DISTRIBUTION");
							distributed.setOrganization(destination.getOrganization());

							BigDecimal distAmount = BigDecimal.ZERO;

							for (GLAccountBalanceAdapter accountBalance : adapters)
							{
								BigDecimal amount = DecimalHelper.safe(accountBalance.getDebet()).subtract(DecimalHelper.safe(accountBalance.getCredit()));

								amount = amount.multiply(destination.getRate().divide(DecimalHelper.HUNDRED));

								JournalEntryDetail from = new JournalEntryDetail();
								from.setAccount(accountBalance.getAccount());
								from.setAmount(DecimalHelper.positive(amount));
								from.setJournalEntry(distributed);
								from.setNote("ACE/DISTRIBUTION");
								from.setPostingType(toPostingType(amount));
								from.setTransactionDate(accountingPeriod.getEndDate());

								distributed.getDetails().add(from);

								distAmount = distAmount.add(amount);
							}

							if (!journalEntry.getDetails().isEmpty())
							{
								JournalEntryDetail to = new JournalEntryDetail();
								to.setAccount(schema.getClosingDistribution().getAccount());
								to.setAmount(DecimalHelper.positive(distAmount));
								to.setJournalEntry(distributed);
								to.setNote("ACE/DISTRIBUTION");
								to.setPostingType(reverse(distAmount));
								to.setTransactionDate(accountingPeriod.getEndDate());

								distributed.getDetails().add(to);
							}

							journalEntryService.add(distributed);
						}
					}
				}
			}
		}
	}
}

/**
 * Jan 28, 2008 4:41:58 PM
 * com.siriuserp.accounting.service.posting
 * OpeningProcess.java
 */
package com.siriuserp.accounting.posting;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.siriuserp.accounting.adapter.GLAccountBalanceAdapter;
import com.siriuserp.accounting.dm.AccountingPeriod;
import com.siriuserp.accounting.dm.ClosingAccount;
import com.siriuserp.accounting.dm.ClosingAccountType;
import com.siriuserp.accounting.dm.EntrySourceType;
import com.siriuserp.accounting.dm.GLPostingType;
import com.siriuserp.accounting.dm.JournalEntry;
import com.siriuserp.accounting.dm.JournalEntryDetail;
import com.siriuserp.accounting.dm.JournalEntryStatus;
import com.siriuserp.accounting.dm.JournalEntryType;
import com.siriuserp.sdk.dm.ReportType;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.utility.DecimalHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
public class OpeningProcess extends PostingProcess
{
	private AccountingPeriod nextPeriod;

	@Override
	public void execute() throws ServiceException
	{
		//known issue:if closed accounting period sequence is 12,next period sould be first period of next year(--- unresolved ---)
		if (nextPeriod == null)
			throw new ServiceException("Next Accounting Period does not exist,please go to Accounting Period Page for setting it!");

		ClosingAccount openingAccount = closingAccountDao.loadByOrganizationAndType(organization.getId(), ClosingAccountType.CLOSING_ACCOUNT);
		if (openingAccount == null || openingAccount.getAccount() == null)
			throw new ServiceException("Opening Account does not exist,please go to accounting schema for setting it!");

		JournalEntry journalEntry = new JournalEntry();
		journalEntry.setAccountingPeriod(nextPeriod);
		journalEntry.setCurrency(defaultCurrency);
		journalEntry.setExchangeType(defaultExchangeType);
		journalEntry.setEntryDate(nextPeriod.getStartDate());
		journalEntry.setEntrySourceType(EntrySourceType.OPENING);
		journalEntry.setEntryStatus(JournalEntryStatus.POSTED);
		journalEntry.setEntryType(JournalEntryType.ENTRY);
		journalEntry.setName("ACE/OPENING");
		journalEntry.setNote("ACE/OPENING");
		journalEntry.setOrganization(organization);

		BigDecimal total = BigDecimal.ZERO;

		for (GLAccountBalanceAdapter adapter : accountBalanceDao.loadAll(accountingPeriod, organization, ReportType.BALANCESHEET))
		{
			GLPostingType type = GLPostingType.DEBET;

			BigDecimal amount = DecimalHelper.safe(adapter.getDebet()).subtract(DecimalHelper.safe(adapter.getCredit()));
			total = total.add(amount);

			if (amount.compareTo(BigDecimal.ZERO) < 0)
			{
				type = GLPostingType.CREDIT;
				amount = amount.multiply(BigDecimal.valueOf(-1));
			}

			if (amount.compareTo(BigDecimal.ZERO) != 0)
			{
				JournalEntryDetail balance = new JournalEntryDetail();
				balance.setAccount(adapter.getAccount());
				balance.setAmount(amount);
				balance.setJournalEntry(journalEntry);
				balance.setNote("ACE/OPENING");
				balance.setPostingType(toPostingType(adapter.getAccount().getPostingType(), type));
				balance.setTransactionDate(journalEntry.getEntryDate());

				journalEntry.getDetails().add(balance);
			}
		}

		if (!journalEntry.getDetails().isEmpty())
		{
			JournalEntryDetail summary = new JournalEntryDetail();
			summary.setAccount(openingAccount.getAccount());
			summary.setAmount(DecimalHelper.positive(total));
			summary.setJournalEntry(journalEntry);
			summary.setNote("ACE/OPENING");
			summary.setPostingType(toPostingType(openingAccount.getAccount().getPostingType(), total));
			summary.setTransactionDate(journalEntry.getEntryDate());

			journalEntry.getDetails().add(summary);

			journalEntryService.add(journalEntry);
		}
	}

	public AccountingPeriod getNextPeriod()
	{
		return nextPeriod;
	}

	public void setNextPeriod(AccountingPeriod nextPeriod)
	{
		this.nextPeriod = nextPeriod;
	}
}

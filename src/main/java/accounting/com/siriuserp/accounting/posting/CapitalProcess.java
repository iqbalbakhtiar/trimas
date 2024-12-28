/**
 * Jan 28, 2008 4:34:47 PM
 * com.siriuserp.accounting.service.posting
 * CapitalProcess.java
 */
package com.siriuserp.accounting.posting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siriuserp.accounting.dao.IncomeSummaryBalanceDao;
import com.siriuserp.accounting.dm.ClosingAccount;
import com.siriuserp.accounting.dm.ClosingAccountType;
import com.siriuserp.accounting.dm.EntrySourceType;
import com.siriuserp.accounting.dm.IncomeSummaryBalance;
import com.siriuserp.accounting.dm.JournalEntry;
import com.siriuserp.accounting.dm.JournalEntryDetail;
import com.siriuserp.accounting.dm.JournalEntryStatus;
import com.siriuserp.accounting.dm.JournalEntryType;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.utility.DecimalHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
public class CapitalProcess extends PostingProcess
{
	@Autowired
	private IncomeSummaryBalanceDao incomeSummaryBalanceDao;

	@Override
	public void execute() throws ServiceException
	{
		IncomeSummaryBalance incomeSummaryBalance = incomeSummaryBalanceDao.load(accountingPeriod.getId(), organization.getId());
		if (incomeSummaryBalance != null)
		{
			ClosingAccount summaryAccount = closingAccountDao.loadByOrganizationAndType(organization.getId(), ClosingAccountType.INCOME_SUMMARY);
			ClosingAccount capitalAccount = closingAccountDao.loadByOrganizationAndType(organization.getId(), ClosingAccountType.CAPITAL_ACCOUNT);

			if (summaryAccount == null || summaryAccount.getAccount() == null || capitalAccount == null || capitalAccount.getAccount() == null)
				throw new ServiceException("either Summary Account or Capital Account does not exist,please go to Accounting Schema to setting it!");

			JournalEntry journalEntry = new JournalEntry();
			journalEntry.setAccountingPeriod(accountingPeriod);
			journalEntry.setCurrency(defaultCurrency);
			journalEntry.setExchangeType(defaultExchangeType);
			journalEntry.setEntryDate(accountingPeriod.getEndDate());
			journalEntry.setEntrySourceType(EntrySourceType.CLOSING);
			journalEntry.setEntryStatus(JournalEntryStatus.POSTED);
			journalEntry.setEntryType(JournalEntryType.ADJUSTMENT);
			journalEntry.setName("ACE/CAPITAL");
			journalEntry.setNote("ACE/CAPITAL");
			journalEntry.setOrganization(organization);

			JournalEntryDetail incomeSummary = new JournalEntryDetail();
			incomeSummary.setAccount(summaryAccount.getAccount());
			incomeSummary.setAmount(DecimalHelper.positive(incomeSummaryBalance.getAmount()));
			incomeSummary.setJournalEntry(journalEntry);
			incomeSummary.setNote("ACE/CAPITAL");
			incomeSummary.setPostingType(reverse(toPostingType(summaryAccount.getAccount().getPostingType(), incomeSummaryBalance.getPostingType())));
			incomeSummary.setTransactionDate(accountingPeriod.getEndDate());

			JournalEntryDetail capital = new JournalEntryDetail();
			capital.setAccount(capitalAccount.getAccount());
			capital.setAmount(incomeSummaryBalance.getAmount());
			capital.setJournalEntry(journalEntry);
			capital.setNote("ACE/CAPITAL");
			capital.setPostingType(reverse(incomeSummary.getPostingType()));
			capital.setTransactionDate(accountingPeriod.getEndDate());

			journalEntry.getDetails().add(incomeSummary);
			journalEntry.getDetails().add(capital);

			journalEntryService.add(journalEntry);
		}
	}
}

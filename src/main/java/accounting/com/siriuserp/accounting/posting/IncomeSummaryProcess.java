/**
 * Jan 28, 2008 4:28:16 PM
 * com.siriuserp.accounting.service.posting
 * IncomeSummaryProcess.java
 */
package com.siriuserp.accounting.posting;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siriuserp.accounting.adapter.GLAccountBalanceAdapter;
import com.siriuserp.accounting.dao.IncomeSummaryBalanceDao;
import com.siriuserp.accounting.dm.ClosingAccount;
import com.siriuserp.accounting.dm.ClosingAccountType;
import com.siriuserp.accounting.dm.EntrySourceType;
import com.siriuserp.accounting.dm.GLPostingType;
import com.siriuserp.accounting.dm.IncomeSummaryBalance;
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
public class IncomeSummaryProcess extends PostingProcess
{
	@Autowired
	private IncomeSummaryBalanceDao incomeSummaryBalanceDao;

	public void execute() throws ServiceException
	{
		ClosingAccount summaryAccount = closingAccountDao.loadByOrganizationAndType(organization.getId(), ClosingAccountType.INCOME_SUMMARY);
		if (summaryAccount == null || summaryAccount.getAccount() == null)
			throw new ServiceException("Income Summary Account doesnot exist.");

		JournalEntry journalEntry = new JournalEntry();
		journalEntry.setAccountingPeriod(accountingPeriod);
		journalEntry.setCurrency(defaultCurrency);
		journalEntry.setExchangeType(defaultExchangeType);
		journalEntry.setEntryDate(accountingPeriod.getEndDate());
		journalEntry.setEntrySourceType(EntrySourceType.CLOSING);
		journalEntry.setEntryStatus(JournalEntryStatus.POSTED);
		journalEntry.setEntryType(JournalEntryType.ADJUSTMENT);
		journalEntry.setName("ACE/INCOME SUMMARY");
		journalEntry.setNote("ACE/INCOME SUMMARY");
		journalEntry.setOrganization(organization);

		BigDecimal totaldebet = BigDecimal.ZERO;
		BigDecimal totalcredit = BigDecimal.ZERO;

		for (GLAccountBalanceAdapter accountBalance : accountBalanceDao.loadAll(accountingPeriod, organization, ReportType.INCOMESTATEMENT))
		{
			GLPostingType type = GLPostingType.DEBET;

			JournalEntryDetail balance = new JournalEntryDetail();
			balance.setAccount(accountBalance.getAccount());
			balance.setJournalEntry(journalEntry);
			balance.setNote("ACE/INCOME SUMMARY");
			balance.setTransactionDate(accountingPeriod.getEndDate());

			if (accountBalance.getAccount().getPostingType().equals(GLPostingType.CREDIT))
			{
				BigDecimal amount = DecimalHelper.safe(accountBalance.getCredit()).subtract(DecimalHelper.safe(accountBalance.getDebet()));
				if (amount.compareTo(BigDecimal.ZERO) < 0)
				{

					type = GLPostingType.DEBET;
					amount = amount.multiply(BigDecimal.valueOf(-1));
					totaldebet = totaldebet.add(amount);
				} else
				{
					type = GLPostingType.CREDIT;
					totalcredit = totalcredit.add(amount);
				}

				balance.setAmount(amount);
			} else
			{
				BigDecimal amount = DecimalHelper.safe(accountBalance.getDebet()).subtract(DecimalHelper.safe(accountBalance.getCredit()));
				if (amount.compareTo(BigDecimal.ZERO) < 0)
				{
					type = GLPostingType.CREDIT;
					amount = amount.multiply(BigDecimal.valueOf(-1));
					totalcredit = totalcredit.add(amount);
				} else
					totaldebet = totaldebet.add(amount);

				balance.setAmount(amount);
			}

			balance.setPostingType(reverse(type));
			journalEntry.getDetails().add(balance);
		}

		if (!journalEntry.getDetails().isEmpty() && ((totaldebet.subtract(totalcredit)).compareTo(BigDecimal.ZERO) != 0))
		{
			//income summary
			JournalEntryDetail summary = new JournalEntryDetail();
			summary.setAccount(summaryAccount.getAccount());
			summary.setJournalEntry(journalEntry);
			summary.setNote("ACE/INCOME SUMMARY");
			summary.setTransactionDate(accountingPeriod.getEndDate());

			if (summaryAccount.getAccount().getPostingType().equals(GLPostingType.CREDIT))
			{
				if ((totalcredit.subtract(totaldebet)).compareTo(BigDecimal.ZERO) < 0)
				{
					summary.setAmount(totalcredit.subtract(totaldebet).multiply(BigDecimal.valueOf(-1)));
					summary.setPostingType(GLPostingType.DEBET);
				} else
				{
					summary.setAmount(totalcredit.subtract(totaldebet));
					summary.setPostingType(GLPostingType.CREDIT);
				}
			} else
			{
				if ((totaldebet.subtract(totalcredit)).compareTo(BigDecimal.ZERO) < 0)
				{
					summary.setAmount(totaldebet.subtract(totalcredit).multiply(BigDecimal.valueOf(-1)));
					summary.setPostingType(GLPostingType.CREDIT);
				} else
				{
					summary.setAmount(totaldebet.subtract(totalcredit));
					summary.setPostingType(GLPostingType.DEBET);
				}
			}

			journalEntry.getDetails().add(summary);

			journalEntryService.add(journalEntry);

			IncomeSummaryBalance out = incomeSummaryBalanceDao.load(accountingPeriod.getId(), organization.getId());
			if (out != null)
			{
				out.setAmount(summary.getAmount());
				out.setPostingType(summary.getPostingType());

				incomeSummaryBalanceDao.update(out);
			} else
			{
				IncomeSummaryBalance incomeSummaryBalance = new IncomeSummaryBalance();
				incomeSummaryBalance.setAccountingPeriod(accountingPeriod);
				incomeSummaryBalance.setOrganization(organization);
				incomeSummaryBalance.setAmount(summary.getAmount());
				incomeSummaryBalance.setPostingType(summary.getPostingType());

				incomeSummaryBalanceDao.add(incomeSummaryBalance);
			}
		}
	}
}

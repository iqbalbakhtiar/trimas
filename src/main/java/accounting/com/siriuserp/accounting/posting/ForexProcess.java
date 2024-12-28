package com.siriuserp.accounting.posting;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.siriuserp.accounting.dm.ClosingAccount;
import com.siriuserp.accounting.dm.ClosingAccountType;
import com.siriuserp.accounting.dm.EntrySourceType;
import com.siriuserp.accounting.dm.GLAccountBalance;
import com.siriuserp.accounting.dm.GLPostingType;
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
public class ForexProcess extends PostingProcess
{
	public void execute() throws ServiceException
	{
		ClosingAccount lossAccount = closingAccountDao.loadByOrganizationAndType(organization.getId(), ClosingAccountType.CURRENCY_LOSS);
		ClosingAccount gainAccount = closingAccountDao.loadByOrganizationAndType(organization.getId(), ClosingAccountType.CURRENCY_GAIN);

		if (lossAccount == null || lossAccount.getAccount() == null || gainAccount == null || gainAccount.getAccount() == null)
			throw new ServiceException("Closing Account for Type Currency Gain or Currency Loss doesnot exist!");

		JournalEntry journalEntry = new JournalEntry();
		journalEntry.setAccountingPeriod(accountingPeriod);
		journalEntry.setCurrency(defaultCurrency);
		journalEntry.setEntryDate(accountingPeriod.getEndDate());
		journalEntry.setEntrySourceType(EntrySourceType.AUTOAJUSTMENT);
		journalEntry.setEntryStatus(JournalEntryStatus.POSTED);
		journalEntry.setEntryType(JournalEntryType.ADJUSTMENT);
		journalEntry.setExchangeType(defaultExchangeType);
		journalEntry.setName("ACE/CGOL");
		journalEntry.setNote("ACE/CGOL");
		journalEntry.setOrganization(organization);

		BigDecimal totalProfitorLoss = BigDecimal.ZERO;

		for (GLAccountBalance accountBalance : accountBalanceDao.loadCurrNonDefault(accountingPeriod, organization))
		{
			BigDecimal amount = DecimalHelper.safe(accountBalance.getUserTransaction().getDebet()).subtract(DecimalHelper.safe(accountBalance.getUserTransaction().getCredit()));
			BigDecimal defamount = DecimalHelper.safe(accountBalance.getUserTransaction().getDefaultdebet()).subtract(DecimalHelper.safe(accountBalance.getUserTransaction().getDefaultcredit()));

			GLPostingType profitType = GLPostingType.DEBET;

			BigDecimal profit = (amount.multiply(journalEntry.getRate())).subtract(defamount);

			totalProfitorLoss = totalProfitorLoss.add(profit);

			if (profit.compareTo(BigDecimal.ZERO) < 0)
			{
				profitType = GLPostingType.CREDIT;
				profit = profit.multiply(BigDecimal.valueOf(-1));
			}

			//create JournalEntryDetail only if profit/loss amount not equeal 0
			if (profit.compareTo(BigDecimal.ZERO) != 0)
			{
				JournalEntryDetail source = new JournalEntryDetail();
				source.setAccount(accountBalance.getAccount());
				source.setAmount(profit);
				source.setNote("ACE/CGOL");
				source.setJournalEntry(journalEntry);
				source.setPostingType(toPostingType(accountBalance.getAccount().getPostingType(), profitType));
				source.setTransactionDate(accountingPeriod.getEndDate());

				journalEntry.getDetails().add(source);
			}
		}

		//Posting only if details not empty
		if (!journalEntry.getDetails().isEmpty())
		{
			JournalEntryDetail destination = new JournalEntryDetail();
			destination.setAmount(DecimalHelper.positive(totalProfitorLoss));
			destination.setNote("ACE/CGOL");
			destination.setJournalEntry(journalEntry);
			destination.setTransactionDate(accountingPeriod.getEndDate());
			destination.setPostingType(reverse(totalProfitorLoss));

			switch (destination.getPostingType())
			{
			case DEBET:
				destination.setAccount(lossAccount.getAccount());
				break;

			default:
				destination.setAccount(gainAccount.getAccount());
				break;
			}

			journalEntry.getDetails().add(destination);

			journalEntryService.add(journalEntry);
		}
	}
}
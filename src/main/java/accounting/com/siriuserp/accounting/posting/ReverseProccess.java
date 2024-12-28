/**
 * Jul 6, 2010 5:46:04 PM
 * com.siriuserp.autoposting.service
 * ReverseProccess.java
 */
package com.siriuserp.accounting.posting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.accounting.dao.GLAccountBalanceDao;
import com.siriuserp.accounting.dm.AccountingPeriod;
import com.siriuserp.accounting.dm.EntrySourceType;
import com.siriuserp.accounting.dm.GLAccountBalance;
import com.siriuserp.accounting.dm.JournalEntryDetail;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.exceptions.DataEditException;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 * Version 1.5
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class ReverseProccess
{
	@Autowired
	protected GLAccountBalanceDao accountBalanceDao;

	public void doReverseBalance(JournalEntryDetail detail, AccountingPeriod period, Party organization) throws DataEditException
	{
		GLAccountBalance balance = accountBalanceDao.load(detail.getAccount(), detail.getJournalEntry().getCurrency(), organization, period);
		if (balance != null)
		{
			if (detail.getJournalEntry().getEntrySourceType().equals(EntrySourceType.AUTOAJUSTMENT) || detail.getJournalEntry().getEntrySourceType().equals(EntrySourceType.CLOSING)
					|| detail.getJournalEntry().getEntrySourceType().equals(EntrySourceType.OPENING))
			{
				switch (detail.getPostingType())
				{
				case CREDIT:
					balance.getUserTransaction().setCredit(balance.getUserTransaction().getCredit().subtract(detail.getAmount()));
					balance.getUserTransaction().setDefaultcredit(balance.getUserTransaction().getDefaultcredit().subtract(detail.getAmount().multiply(detail.getJournalEntry().getRate())));
					switch (detail.getJournalEntry().getEntrySourceType())
					{
					case AUTOAJUSTMENT:
						doCredit(detail, balance);
						balance.getSystemTransaction().setAdjustmentcredit(balance.getSystemTransaction().getAdjustmentcredit().subtract(detail.getAmount()));
						break;
					case CLOSING:
						balance.getSystemTransaction().setClosingcredit(balance.getSystemTransaction().getClosingcredit().subtract(detail.getAmount()));
						break;
					case OPENING:
						doCredit(detail, balance);
						balance.getSystemTransaction().setOpeningcredit(balance.getSystemTransaction().getOpeningcredit().subtract(detail.getAmount()));
						break;
					default:
						break;
					}
					break;
				case DEBET:
					balance.getUserTransaction().setDebet(balance.getUserTransaction().getDebet().subtract(detail.getAmount()));
					balance.getUserTransaction().setDefaultdebet(balance.getUserTransaction().getDefaultdebet().subtract(detail.getAmount().multiply(detail.getJournalEntry().getRate())));
					switch (detail.getJournalEntry().getEntrySourceType())
					{
					case AUTOAJUSTMENT:
						doDebet(detail, balance);
						balance.getSystemTransaction().setAdjustmentdebet(balance.getSystemTransaction().getAdjustmentdebet().subtract(detail.getAmount()));
						break;
					case CLOSING:
						balance.getSystemTransaction().setClosingdebet(balance.getSystemTransaction().getClosingdebet().subtract(detail.getAmount()));
						break;
					case OPENING:
						doDebet(detail, balance);
						balance.getSystemTransaction().setOpeningdebet(balance.getSystemTransaction().getOpeningdebet().subtract(detail.getAmount()));
						break;
					default:
						break;
					}
					break;
				}
			} else
			{
				switch (detail.getPostingType())
				{
				case CREDIT:
					balance.getUserTransaction().setCredit(balance.getUserTransaction().getCredit().subtract(detail.getAmount()));
					balance.getUserTransaction().setDefaultcredit(balance.getUserTransaction().getDefaultcredit().subtract(detail.getAmount().multiply(detail.getJournalEntry().getRate())));
					doCredit(detail, balance);
					break;
				case DEBET:
					balance.getUserTransaction().setDebet(balance.getUserTransaction().getDebet().subtract(detail.getAmount()));
					balance.getUserTransaction().setDefaultdebet(balance.getUserTransaction().getDefaultdebet().subtract(detail.getAmount().multiply(detail.getJournalEntry().getRate())));
					doDebet(detail, balance);
					break;
				}
			}

			accountBalanceDao.update(balance);
		}
	}

	public void doCredit(JournalEntryDetail detail, GLAccountBalance balance)
	{
		switch (detail.getJournalEntry().getEntryType())
		{
		case ADJUSTMENT:
			balance.getUserTransaction().setAdjustmentcredit(balance.getUserTransaction().getAdjustmentcredit().subtract(detail.getAmount()));
			break;
		case CORRECTION:
			balance.getUserTransaction().setCorrectioncredit(balance.getUserTransaction().getCorrectioncredit().subtract(detail.getAmount()));
			break;
		case ENTRY:
			balance.getUserTransaction().setEntrycredit(balance.getUserTransaction().getEntrycredit().subtract(detail.getAmount()));
			break;
		}
	}

	public void doDebet(JournalEntryDetail detail, GLAccountBalance balance)
	{
		switch (detail.getJournalEntry().getEntryType())
		{
		case ENTRY:
			balance.getUserTransaction().setEntrydebet(balance.getUserTransaction().getEntrydebet().subtract(detail.getAmount()));
			break;
		case ADJUSTMENT:
			balance.getUserTransaction().setAdjustmentdebet(balance.getUserTransaction().getAdjustmentdebet().subtract(detail.getAmount()));
			break;
		case CORRECTION:
			balance.getUserTransaction().setCorrectiondebet(balance.getUserTransaction().getCorrectiondebet().subtract(detail.getAmount()));
			break;
		}
	}
}

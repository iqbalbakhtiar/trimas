/**
 * Dec 1, 2008 5:08:47 PM
 * com.siriuserp.accounting.service.posting
 * ReverseClosingProcess.java
 */
package com.siriuserp.accounting.posting;

import java.math.BigDecimal;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siriuserp.accounting.dao.FixedAssetDao;
import com.siriuserp.accounting.dao.IncomeSummaryBalanceDao;
import com.siriuserp.accounting.dao.JournalEntryDetailDao;
import com.siriuserp.accounting.dm.AccountingPeriod;
import com.siriuserp.accounting.dm.DecliningInformation;
import com.siriuserp.accounting.dm.DepreciationMethod;
import com.siriuserp.accounting.dm.FixedAsset;
import com.siriuserp.accounting.dm.IncomeSummaryBalance;
import com.siriuserp.accounting.dm.JournalEntryDetail;
import com.siriuserp.accounting.dm.PeriodStatus;
import com.siriuserp.accounting.dm.StraightLineDepreciationInformation;
import com.siriuserp.sdk.exceptions.DataEditException;
import com.siriuserp.sdk.exceptions.ServiceException;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
public class ReverseClosingProcess extends PostingProcess
{
	@Autowired
	private FixedAssetDao fixedAssetDao;

	@Autowired
	private JournalEntryDetailDao journalEntryDetailDao;

	@Autowired
	private IncomeSummaryBalanceDao incomeSummaryBalanceDao;

	@Autowired
	private ReverseProccess reverseProccess;

	private AccountingPeriod next;

	public AccountingPeriod getNext()
	{
		return next;
	}

	public void setNext(AccountingPeriod next)
	{
		this.next = next;
	}

	@Override
	public void execute() throws ServiceException
	{
		if (next != null)
		{
			for (JournalEntryDetail journalEntryDetail : journalEntryDetailDao.loadOpening(next, organization))
				reverseProccess.doReverseBalance(journalEntryDetail, next, organization);
		}

		for (JournalEntryDetail detail : journalEntryDetailDao.loadForOpen(accountingPeriod, organization))
			reverseProccess.doReverseBalance(detail, accountingPeriod, organization);

		IncomeSummaryBalance income = incomeSummaryBalanceDao.load(accountingPeriod.getId(), organization.getId());
		if (income != null)
			incomeSummaryBalanceDao.delete(income);

		journalEntryService.deleteAll(accountingPeriod, organization, next);

		if (accountingPeriod.getStatus().equals(PeriodStatus.PRECLOSE) || accountingPeriod.getStatus().equals(PeriodStatus.CLOSED))
			reverseFixesAsset();
	}

	private void reverseFixesAsset() throws DataEditException
	{
		List<FixedAsset> assets = fixedAssetDao.loadFixedAssetBefore(accountingPeriod, organization.getId());
		if (assets != null && !assets.isEmpty())
		{
			for (FixedAsset fixedAsset : assets)
			{
				if (fixedAsset.getUsefulLife().compareTo(fixedAsset.getMaxlife()) < 0)
				{
					if (fixedAsset.getFixedAssetGroup().getDepreciationMethod().equals(DepreciationMethod.DECLINING_BALANCE))
					{
						DecliningInformation information = fixedAsset.getDecliningInformation();
						if (information != null)
						{
							double amount = information.getBookValue().doubleValue() * fixedAsset.getDecliningBalanceRate().doubleValue() / 100;

							BigDecimal accumulatedAmount = information.getAccumulated().subtract(BigDecimal.valueOf(amount));

							if (accumulatedAmount.compareTo(BigDecimal.ZERO) <= 0)
								information.setAccumulated(BigDecimal.ZERO);
							else
								information.setAccumulated(accumulatedAmount);

							information.setBookValue(fixedAsset.getAcquisitionValue().subtract(information.getAccumulated()));
						}
					} else if (fixedAsset.getFixedAssetGroup().getDepreciationMethod().equals(DepreciationMethod.STRAIGHT_LINE))
					{
						StraightLineDepreciationInformation information = fixedAsset.getStraightLine();
						if (information != null)
						{
							BigDecimal accumulatedAmount = information.getAccumulated().subtract(information.getMonth());

							if (accumulatedAmount.compareTo(BigDecimal.ZERO) <= 0)
								information.setAccumulated(BigDecimal.ZERO);
							else
								information.setAccumulated(accumulatedAmount);

							information.setBookValue(fixedAsset.getAcquisitionValue().subtract(information.getAccumulated()));
						}
					}

					if (new DateTime(fixedAsset.getAcquisitionDate()).plusMonths(fixedAsset.getMaxlife().intValue()).compareTo(new DateTime(accountingPeriod.getStartDate())) >= 0)
						fixedAsset.setUsefulLife(fixedAsset.getUsefulLife().add(BigDecimal.valueOf(1)));
					fixedAssetDao.update(fixedAsset);
				}
			}
		}
	}
}

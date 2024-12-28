/**
 * Jan 28, 2008 2:52:52 PM
 * com.siriuserp.accounting.service.posting
 * FixAssetProcess.java
 */
package com.siriuserp.accounting.posting;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siriuserp.accounting.dao.FixedAssetClosingInformationDao;
import com.siriuserp.accounting.dao.FixedAssetDao;
import com.siriuserp.accounting.dm.ClosingAccountType;
import com.siriuserp.accounting.dm.DecliningInformation;
import com.siriuserp.accounting.dm.DepreciationMethod;
import com.siriuserp.accounting.dm.EntrySourceType;
import com.siriuserp.accounting.dm.FixedAsset;
import com.siriuserp.accounting.dm.FixedAssetClosingInformation;
import com.siriuserp.accounting.dm.FixedAssetDepreciationDetail;
import com.siriuserp.accounting.dm.GLPostingType;
import com.siriuserp.accounting.dm.JournalEntry;
import com.siriuserp.accounting.dm.JournalEntryDetail;
import com.siriuserp.accounting.dm.JournalEntryStatus;
import com.siriuserp.accounting.dm.JournalEntryType;
import com.siriuserp.accounting.dm.StraightLineDepreciationInformation;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.utility.DateHelper;
import com.siriuserp.sdk.utility.DecimalHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
public class FixedAssetProcess extends PostingProcess
{
	@Autowired
	private FixedAssetDao fixedAssetDao;

	@Autowired
	private FixedAssetClosingInformationDao fixedAssetClosingInformationDao;

	@Override
	public void execute() throws ServiceException
	{
		List<FixedAsset> assets = fixedAssetDao.loadFixedAssetBefore(accountingPeriod, organization.getId());
		if (assets != null && !assets.isEmpty())
		{
			for (FixedAsset fixedAsset : assets)
			{
				FixedAssetClosingInformation depreciationAccount = fixedAssetClosingInformationDao.loadByGroupAndType(fixedAsset.getFixedAssetGroup().getId(), ClosingAccountType.ASSET_DEPRECIATION);
				FixedAssetClosingInformation accumulatedAccount = fixedAssetClosingInformationDao.loadByGroupAndType(fixedAsset.getFixedAssetGroup().getId(), ClosingAccountType.ACCUMULATIVE_DEPRECIATION);

				if (depreciationAccount == null || depreciationAccount.getClosingAccount() == null || accumulatedAccount == null || accumulatedAccount.getClosingAccount() == null)
					throw new ServiceException("Depreciation Account or Accumulated Account for Organization " + organization.getFullName().toUpperCase() + " does not exist");

				JournalEntry journalEntry = new JournalEntry();

				journalEntry.setAccountingPeriod(accountingPeriod);
				journalEntry.setCurrency(defaultCurrency);
				journalEntry.setEntryDate(accountingPeriod.getEndDate());
				journalEntry.setEntrySourceType(EntrySourceType.AUTOAJUSTMENT);
				journalEntry.setEntryStatus(JournalEntryStatus.POSTED);
				journalEntry.setEntryType(JournalEntryType.ADJUSTMENT);
				journalEntry.setExchangeType(defaultExchangeType);
				journalEntry.setName("ACE/FIXED ASSET - " + fixedAsset.getName());
				journalEntry.setNote("ACE/FIXED ASSET - " + fixedAsset.getName());
				journalEntry.setOrganization(organization);

				JournalEntryDetail depreciation = new JournalEntryDetail();
				depreciation.setAccount(depreciationAccount.getClosingAccount().getAccount());
				depreciation.setJournalEntry(journalEntry);
				depreciation.setNote("ACE/FIXED ASSET - " + fixedAsset.getName());
				depreciation.setTransactionDate(new Date());
				depreciation.setPostingType(GLPostingType.DEBET);
				depreciation.setAmount(BigDecimal.ZERO);

				if (fixedAsset.getFixedAssetGroup().getDepreciationMethod().equals(DepreciationMethod.DECLINING_BALANCE))
				{
					DecliningInformation information = fixedAsset.getDecliningInformation();
					if (information != null)
						depreciation.setAmount(information.getBookValue().multiply(DecimalHelper.percent(fixedAsset.getDecliningBalanceRate())));
				} else if (fixedAsset.getFixedAssetGroup().getDepreciationMethod().equals(DepreciationMethod.STRAIGHT_LINE))
				{
					StraightLineDepreciationInformation information = fixedAsset.getStraightLine();
					if (information != null)
						depreciation.setAmount(information.getMonth());
				}

				JournalEntryDetail accumulated = new JournalEntryDetail();
				accumulated.setAccount(accumulatedAccount.getClosingAccount().getAccount());
				accumulated.setJournalEntry(journalEntry);
				accumulated.setNote("ACE/FIXED ASSET - " + fixedAsset.getName());
				accumulated.setTransactionDate(journalEntry.getEntryDate());
				accumulated.setPostingType(GLPostingType.CREDIT);
				accumulated.setAmount(BigDecimal.ZERO);

				if (fixedAsset.getFixedAssetGroup().getDepreciationMethod().equals(DepreciationMethod.DECLINING_BALANCE))
				{
					DecliningInformation information = fixedAsset.getDecliningInformation();
					if (information != null)
					{
						BigDecimal amount = information.getBookValue().multiply(DecimalHelper.percent(fixedAsset.getDecliningBalanceRate()));

						accumulated.setAmount(amount);

						information.setAccumulated(information.getAccumulated().add(amount));
						information.setBookValue(information.getBookValue().subtract(amount));

						fixedAsset.setUsefulLife(fixedAsset.getUsefulLife().subtract(BigDecimal.ONE));

						FixedAssetDepreciationDetail history = new FixedAssetDepreciationDetail();
						history.setAmount(accumulated.getAmount());
						history.setDate(accumulated.getTransactionDate());
						history.setMonth(DateHelper.toMonthEnum(history.getDate()));
						history.setCreatedDate(DateHelper.now());
						history.setCreatedBy(accumulated.getCreatedBy());
						history.setFixedAsset(fixedAsset);

						fixedAsset.getDepreciations().add(history);
						fixedAsset.setLastDeprectiation(getAccountingPeriod().getId());

						fixedAssetDao.update(fixedAsset);
					}
				} else if (fixedAsset.getFixedAssetGroup().getDepreciationMethod().equals(DepreciationMethod.STRAIGHT_LINE))
				{
					StraightLineDepreciationInformation information = fixedAsset.getStraightLine();
					if (information != null)
					{
						accumulated.setAmount(information.getMonth());

						information.setAccumulated(information.getAccumulated().add(information.getMonth()));
						information.setBookValue(information.getBookValue().subtract(information.getMonth()));

						fixedAsset.setUsefulLife(fixedAsset.getUsefulLife().subtract(BigDecimal.ONE));

						FixedAssetDepreciationDetail history = new FixedAssetDepreciationDetail();
						history.setDate(accumulated.getTransactionDate());
						history.setAmount(information.getMonth());
						history.setMonth(DateHelper.toMonthEnum(history.getDate()));
						history.setFixedAsset(fixedAsset);
						history.setCreatedDate(DateHelper.now());
						history.setCreatedBy(accumulated.getCreatedBy());

						fixedAsset.getDepreciations().add(history);

						fixedAssetDao.update(fixedAsset);
					}
				}

				journalEntry.getDetails().add(depreciation);
				journalEntry.getDetails().add(accumulated);

				journalEntryService.add(journalEntry);
			}
		}
	}
}

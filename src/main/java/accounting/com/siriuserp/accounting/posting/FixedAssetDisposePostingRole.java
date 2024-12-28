/**
 * Jun 22, 2009 5:36:20 PM
 * com.siriuserp.accounting.service.posting
 * FixedAssetDisposePostingRole.java
 */
package com.siriuserp.accounting.posting;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.siriuserp.accounting.dm.ClosingAccount;
import com.siriuserp.accounting.dm.ClosingAccountType;
import com.siriuserp.accounting.dm.DepreciationMethod;
import com.siriuserp.accounting.dm.FixedAsset;
import com.siriuserp.accounting.dm.GLPostingType;
import com.siriuserp.accounting.dm.JournalEntry;
import com.siriuserp.accounting.dm.JournalEntryDetail;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.utility.DecimalHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
public class FixedAssetDisposePostingRole extends AbstractPostingRole
{
	@Override
	public void execute() throws ServiceException
	{
		FixedAsset fixedAsset = (FixedAsset) getPostingable();

		if (fixedAsset.isDisposed() && !fixedAsset.getFixedAssetGroup().getDepreciationMethod().equals(DepreciationMethod.NO_DEPRECIATION) && fixedAsset.getDisposeJournal() == null)
		{
			JournalEntry journalEntry = newInstance();
			journalEntry.setCurrency(fixedAsset.getCurrency());
			journalEntry.setEntryDate(fixedAsset.getAcquisitionDate());
			journalEntry.setExchangeType(fixedAsset.getExchangeType());
			journalEntry.setName(fixedAsset.getCode());
			journalEntry.setNote("AUTO FIXED ASSET");

			BigDecimal bookvalue = BigDecimal.valueOf(0);

			if (fixedAsset.getFixedAssetGroup().getDepreciationMethod().equals(DepreciationMethod.DECLINING_BALANCE))
				bookvalue = fixedAsset.getDecliningInformation().getBookValue();
			else if (fixedAsset.getFixedAssetGroup().getDepreciationMethod().equals(DepreciationMethod.STRAIGHT_LINE))
				bookvalue = fixedAsset.getStraightLine().getBookValue();

			doCashbank(fixedAsset, journalEntry);
			doGainLoss(fixedAsset, journalEntry, bookvalue);
			doAsset(fixedAsset, journalEntry, bookvalue);

			journalEntryService.add(journalEntry);

			fixedAsset.setDisposeJournal(journalEntry);
		}
	}

	private void doCashbank(FixedAsset fixedAsset, JournalEntry journalEntry)
	{
		JournalEntryDetail bank = new JournalEntryDetail();
		bank.setAccount(fixedAsset.getBankAccount().getAccount());
		bank.setAmount(fixedAsset.getDisposeAmount());
		bank.setJournalEntry(journalEntry);
		bank.setNote("ASSET DISPOSED");
		bank.setPostingType(GLPostingType.DEBET);
		bank.setTransactionDate(new Date());

		journalEntry.getDetails().add(bank);
	}

	private void doAsset(FixedAsset fixedAsset, JournalEntry journalEntry, BigDecimal bookvalue) throws ServiceException
	{
		ClosingAccount assetAccount = closingAccountDao.load(fixedAsset.getFixedAssetGroup().getId(), ClosingAccountType.ASSET);
		if (assetAccount == null)
			throw new ServiceException("Asset Account does not exist,Please go to Accounting Schema to set it up!");

		JournalEntryDetail asset = new JournalEntryDetail();
		asset.setAccount(assetAccount.getAccount());
		asset.setAmount(bookvalue);
		asset.setJournalEntry(journalEntry);
		asset.setNote("ASSET BOOK VALUE");
		asset.setPostingType(GLPostingType.CREDIT);
		asset.setTransactionDate(fixedAsset.getAcquisitionDate());

		journalEntry.getDetails().add(asset);
	}

	private void doGainLoss(FixedAsset fixedAsset, JournalEntry journalEntry, BigDecimal bookvalue) throws ServiceException
	{
		ClosingAccount lossAccount = closingAccountDao.load(fixedAsset.getFixedAssetGroup().getId(), ClosingAccountType.GAIN_LOSS_AND_DISPOSAL);
		if (lossAccount == null)
			throw new ServiceException("Gain Loss Account does not exist,Please go to Accounting Schema to set it up!");

		JournalEntryDetail loss = new JournalEntryDetail();
		loss.setAccount(lossAccount.getAccount());
		loss.setAmount(DecimalHelper.positive(bookvalue.subtract(fixedAsset.getDisposeAmount())));
		loss.setJournalEntry(journalEntry);
		loss.setNote("ASSET GAIN OR LOSS");

		if (bookvalue.subtract(fixedAsset.getDisposeAmount()).compareTo(BigDecimal.valueOf(0)) > 0)
			loss.setPostingType(GLPostingType.DEBET);
		else
			loss.setPostingType(GLPostingType.CREDIT);

		loss.setTransactionDate(fixedAsset.getAcquisitionDate());

		journalEntry.getDetails().add(loss);
	}
}

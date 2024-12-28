/**
 * Jun 5, 2009 10:10:17 AM
 * com.siriuserp.accounting.service.posting
 * FixedAssetAqcuisitionPostingRole.java
 */
package com.siriuserp.accounting.posting;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.siriuserp.accounting.dm.ClosingAccount;
import com.siriuserp.accounting.dm.ClosingAccountType;
import com.siriuserp.accounting.dm.FixedAsset;
import com.siriuserp.accounting.dm.GLPostingType;
import com.siriuserp.accounting.dm.JournalEntry;
import com.siriuserp.accounting.dm.JournalEntryDetail;
import com.siriuserp.accounting.dm.ProductCategoryAccountingSchema;
import com.siriuserp.accounting.dm.ProductCategoryClosingAccount;
import com.siriuserp.sdk.exceptions.ServiceException;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
public class FixedAssetAqcuisitionPostingRole extends AbstractPostingRole
{
	@Override
	public void execute() throws ServiceException
	{
		FixedAsset fixedAsset = (FixedAsset) getPostingable();
		JournalEntry journalEntry = newInstance();
		journalEntry.setCurrency(fixedAsset.getCurrency());
		journalEntry.setEntryDate(fixedAsset.getAcquisitionDate());
		journalEntry.setExchangeType(fixedAsset.getExchangeType());
		journalEntry.setName(fixedAsset.getCode());
		journalEntry.setOrganization(fixedAsset.getOrganization());
		journalEntry.setNote("AUTO FIXED ASSET FROM PROCUREMENT");

		//DEBET & CREDIT
		doInventory(journalEntry, fixedAsset.getAcquisitionValue(), fixedAsset);
		doAsset(journalEntry, fixedAsset.getAcquisitionValue(), fixedAsset);

		journalEntryService.add(journalEntry);

		postingable.setJournalEntry(journalEntry);
	}

	private void doAsset(JournalEntry journalEntry, BigDecimal amt, FixedAsset fixedAsset) throws ServiceException
	{
		ClosingAccount assetAccount = fixedAssetGroupDao.loadByOrganizationAndType(fixedAsset.getFixedAssetGroup().getId(), journalEntry.getOrganization().getId());
		if (assetAccount == null)
			throw new ServiceException("Purchase Account does not exist,Please go to Accounting Schema to set it up!");

		JournalEntryDetail asset = new JournalEntryDetail();
		asset.setAccount(assetAccount.getAccount());
		asset.setAmount(fixedAsset.getAcquisitionValue());
		asset.setJournalEntry(journalEntry);
		asset.setNote("ASSET");
		asset.setPostingType(GLPostingType.DEBET);
		asset.setTransactionDate(fixedAsset.getAcquisitionDate());

		journalEntry.getDetails().add(asset);

	}

	private void doInventory(JournalEntry journalEntry, BigDecimal amt, FixedAsset fixedAsset) throws ServiceException
	{

		ProductCategoryAccountingSchema schema = categoryAccountingSchemaDao.load(accountingSchema.getId(), fixedAsset.getProduct().getProductCategory().getId());

		ProductCategoryClosingAccount closingAccount = productCategoryClosingAccountDao.loadBySchemaAndAccountType(schema.getId(), ClosingAccountType.INVENTORY);
		if (closingAccount == null || closingAccount.getClosingAccount() == null)
			throw new ServiceException("Schema for Inventory does not exist!");

		JournalEntryDetail inventory = new JournalEntryDetail();
		inventory.setAccount(closingAccount.getClosingAccount().getAccount());
		inventory.setAmount(amt);
		inventory.setJournalEntry(journalEntry);
		inventory.setNote("INVENTORY");
		inventory.setPostingType(GLPostingType.CREDIT);
		inventory.setTransactionDate(journalEntry.getEntryDate());

		journalEntry.getDetails().add(inventory);
	}

}

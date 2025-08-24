/**
 * File Name  : AutoGenerateBarcodeSiblingRole.java
 * Created On : Aug 23, 2023
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.inventory.sibling;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.inventory.dm.GoodsReceiptManual;
import com.siriuserp.inventory.service.BarcodeGroupService;
import com.siriuserp.inventory.service.BarcodeService;
import com.siriuserp.sdk.dm.AbstractSiblingRole;
import com.siriuserp.sdk.dm.Barcode;
import com.siriuserp.sdk.dm.BarcodeGroup;
import com.siriuserp.sdk.dm.BarcodeGroupType;
import com.siriuserp.sdk.dm.Item;
import com.siriuserp.sdk.utility.DecimalHelper;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class GoodsReceiptManualGenerateBarcodeSiblingRole extends AbstractSiblingRole
{
	@Autowired
	private BarcodeService barcodeService;

	@Autowired
	private BarcodeGroupService barcodeGroupService;

	@Override
	public void execute() throws Exception, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException
	{
		GoodsReceiptManual goodsReceiptManual = (GoodsReceiptManual) getSiblingable();
		List<Item> itemsWithEmptySerial = new ArrayList<>();
		List<Item> itemsWithSerial = new ArrayList<>();
		List<Item> itemsWithSerialExist = new ArrayList<>();
		BarcodeGroup barcodeGroup = null;

		if (goodsReceiptManual != null)
		{
			for (Item item : goodsReceiptManual.getForm().getItems())
			{
				if (item.getProduct() != null && item.getProduct().isSerial() && SiriusValidator.nz(item.getReceipted()))
				{
					String serial = item.getSerial();

					if (!SiriusValidator.validateParam(serial))
					{
						itemsWithEmptySerial.add(item);
					} else
					{
						Barcode barcode = barcodeService.loadByCode(serial);
						if (barcode != null)
							itemsWithSerialExist.add(item);
						else
							itemsWithSerial.add(item);
					}
				}
			}

			boolean needGenerate = !itemsWithEmptySerial.isEmpty() || !itemsWithSerial.isEmpty();

			if (goodsReceiptManual.getBarcodeGroup() == null && needGenerate)
			{
				barcodeGroup = new BarcodeGroup();
				BeanUtils.copyProperties(goodsReceiptManual, barcodeGroup, "form");
				barcodeGroup.setBarcodeGroupType(BarcodeGroupType.STOCK_ADJUSTMENT);
				barcodeGroup.setActive(true);
				barcodeGroup.setQuantity(goodsReceiptManual.getForm().getItems().stream().filter(item -> item.getProduct() != null && item.getProduct().isSerial()).map(Item::getQuantity).collect(DecimalHelper.sum()));
				goodsReceiptManual.setBarcodeGroup(barcodeGroup);
			}

			if (!itemsWithEmptySerial.isEmpty())
				prosesBarcode(itemsWithEmptySerial, barcodeGroup);

			if (!itemsWithSerial.isEmpty())
				prosesBarcode(itemsWithSerial, barcodeGroup);

			if (!itemsWithSerialExist.isEmpty())
				prosesBarcode(itemsWithSerialExist, null);
		}
	}

	@Transactional
	public void prosesBarcode(List<Item> items, BarcodeGroup barcodeGroup) throws Exception
	{
		if (barcodeGroup != null)
			barcodeGroupService.addFromGR(barcodeGroup, items);
	}
}

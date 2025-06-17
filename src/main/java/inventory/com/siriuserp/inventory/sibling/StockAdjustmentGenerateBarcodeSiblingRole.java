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

import com.siriuserp.inventory.dm.StockAdjustment;
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
public class StockAdjustmentGenerateBarcodeSiblingRole extends AbstractSiblingRole
{
	@Autowired
	private BarcodeService barcodeService;
	
	@Autowired
	private BarcodeGroupService barcodeGroupService;
	
	@Override
	public void execute() throws Exception, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException
	{
		StockAdjustment stockAdjustment = (StockAdjustment) getSiblingable();
		List<Item> itemsWithEmptySerial = new ArrayList<>();
		List<Item> itemsWithSerial = new ArrayList<>();
		List<Item> itemsWithSerialExist = new ArrayList<>();
		
		BarcodeGroup barcodeGroup = new BarcodeGroup();
		
		if (stockAdjustment != null) {

			for (Item item : stockAdjustment.getForm().getItems()) {
				
				if (item.getProduct() != null && SiriusValidator.nz(item.getQuantity())) {
				    String serial = item.getSerial();
				    
				    if (serial == null || serial.trim().isEmpty()) {
				        itemsWithEmptySerial.add(item);
				    } else {
				        Barcode barcode = barcodeService.loadByCode(serial);
				        if (barcode != null) {
				            itemsWithSerialExist.add(item); 
				        } else
				        	itemsWithSerial.add(item);
				        	
				    }
				}
			}
			
		 if (!itemsWithEmptySerial.isEmpty() || !itemsWithSerial.isEmpty()) {
			 
		        barcodeGroup = new BarcodeGroup();
		        BeanUtils.copyProperties(stockAdjustment, barcodeGroup, "form");

		        barcodeGroup.setBarcodeGroupType(BarcodeGroupType.STOCK_ADJUSTMENT);
		        barcodeGroup.setActive(true);
		        barcodeGroup.setQuantity(
		            stockAdjustment.getForm().getItems().stream()
		                .map(Item::getQuantity)
		                .collect(DecimalHelper.sum())
		        );

		        barcodeGroupService.add(barcodeGroup);
		    }

		    if (!itemsWithEmptySerial.isEmpty()) {
		        prosesBarcode(itemsWithEmptySerial, barcodeGroup);
		    }

		    if (!itemsWithSerial.isEmpty()) {
		        prosesBarcode(itemsWithSerial, barcodeGroup);
		    }

		    if (!itemsWithSerialExist.isEmpty()) {
		        prosesBarcode(itemsWithSerialExist, null);
		    }
			
	    }
		
	}
	
	@Transactional
	public void prosesBarcode(List<Item> items, BarcodeGroup barcodeGroup) throws Exception {
		
		if(barcodeGroup!=null ) {
			
		    for (Item item : items) {
		        if (item.getProduct() != null && item.getProduct().isSerial() && SiriusValidator.nz(item.getQuantity())) {
		        	
		        	boolean isBarcodeValid = false;
		        	
		        	if(SiriusValidator.validateParam(item.getSerial())) {
		        		Barcode barcode = barcodeService.loadByCode(item.getSerial());
		        		if(barcode == null)
		        			isBarcodeValid = true;
		        	}
		        	
		            Barcode code = barcodeService.generateBarcode(
		                item.getProduct().getId(),
		                item.getQuantity(), item.getSerial(),
		                barcodeGroup, isBarcodeValid
		            );
		            
		            item.setSerial(code.getCode());
		        
		        }
		              
		     }
		 }
	}
	
}

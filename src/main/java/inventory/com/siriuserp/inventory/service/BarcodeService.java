/**
 * 
 */
package com.siriuserp.inventory.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.inventory.dao.BarcodeDao;
import com.siriuserp.inventory.dm.Product;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.dm.Barcode;
import com.siriuserp.sdk.dm.BarcodeGroup;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.QueryFactory;

import javolution.util.FastMap;

/**
 * @author ferdinand
 *
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class BarcodeService
{
	@Autowired
	private GenericDao genericDao;
	
	@Autowired
	private BarcodeDao barcodeDao;

	@Autowired
	private CodeSequenceDao codeSequenceDao;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends AbstractGridViewQuery> queryclass) throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("filterCriteria", filterCriteria);
		map.put("barcodes", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Barcode load(Long id)
	{
		return genericDao.load(Barcode.class, id);
	}
	
	@Transactional(readOnly = true)
	public Barcode loadByCode(String code)
	{
		return barcodeDao.loadByCode(code);
	}


	@AuditTrails(className = Barcode.class, actionType = AuditTrailsActionType.CREATE)
	public Barcode generateBarcode(Long productId, BigDecimal quantity, String serial, BarcodeGroup barcodeGroup, boolean isBarcodeValid) throws ServiceException
	{
		
		Barcode barcode = new Barcode();
		
		if(!isBarcodeValid)
			barcode.setCode(GeneratorHelper.instance().generate(TableType.BARCODE_PRODUCT, codeSequenceDao, barcodeGroup.getDate()));
		else
			barcode.setCode(serial);
		
		barcode.setProduct(genericDao.load(Product.class, productId));
		barcode.setQuantity(quantity);
		barcode.setBarcodeGroup(barcodeGroup);

		genericDao.add(barcode);

		return barcode;
	}
}

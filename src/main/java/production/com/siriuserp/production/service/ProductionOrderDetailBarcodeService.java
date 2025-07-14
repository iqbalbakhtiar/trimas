package com.siriuserp.production.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.inventory.form.InventoryForm;
import com.siriuserp.inventory.service.BarcodeGroupService;
import com.siriuserp.production.dm.ProductionOrderDetail;
import com.siriuserp.production.dm.ProductionOrderDetailBarcode;
import com.siriuserp.production.form.ProductionForm;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.base.Service;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.dm.Barcode;
import com.siriuserp.sdk.dm.BarcodeGroup;
import com.siriuserp.sdk.dm.BarcodeGroupType;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.FormHelper;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.QueryFactory;

import javolution.util.FastList;
import javolution.util.FastMap;

/**
 * @author ferdinand
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class ProductionOrderDetailBarcodeService extends Service
{
	@Autowired
	private CodeSequenceDao codeSequenceDao;
	
	@Autowired
	private BarcodeGroupService barcodeGroupService;
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		
		map.put("filterCriteria", filterCriteria);
		map.put("barcodes", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));
		
		return map;
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> preadd(Long id)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		
		map.put("barcode_add", new ProductionForm());
		map.put("productionOrderDetail", id != null ? genericDao.load(ProductionOrderDetail.class, id) : null);
		
		return map;
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> preadd2(ProductionForm form)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		
		map.put("barcode_add", form);
		
		return map;
	}
	
	@AuditTrails(className = ProductionOrderDetailBarcode.class, actionType = AuditTrailsActionType.CREATE)
	public void add(ProductionOrderDetailBarcode productionOrderDetailBarcode) throws Exception
	{
		productionOrderDetailBarcode.setCode(GeneratorHelper.instance().generate(TableType.PRODUCTION_ORDER_DETAIL_BARCODE, codeSequenceDao));
		
		InventoryForm inventoryForm = new InventoryForm();
		inventoryForm.setOrganization(productionOrderDetailBarcode.getProductionOrderDetail().getProductionOrder().getOrganization());
		inventoryForm.setDate(productionOrderDetailBarcode.getDate());
		inventoryForm.setBarcodeGroupType(BarcodeGroupType.PRODUCTION);
		inventoryForm.getItems().addAll(productionOrderDetailBarcode.getForm().getItems());
		
		productionOrderDetailBarcode.setBarcodeGroup(barcodeGroupService.add(FormHelper.create(BarcodeGroup.class, inventoryForm)));
		
		genericDao.add(productionOrderDetailBarcode);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> preedit(Long id) throws Exception
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		
		map.put("barcode_edit", genericDao.load(ProductionOrderDetailBarcode.class, id));
		
		return map;
	}
	
	@AuditTrails(className = ProductionOrderDetailBarcode.class, actionType = AuditTrailsActionType.UPDATE)
	public void update(ProductionOrderDetailBarcode productionOrderDetailBarcode) throws Exception
	{
		genericDao.update(productionOrderDetailBarcode);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Map<String, Object> print(Long id, String type)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		FastList<Barcode> barcodes = new FastList<Barcode>();
		
		map.put("id", id);
		
		if (type.equals("GROUP"))
			barcodes.addAll(genericDao.load(ProductionOrderDetailBarcode.class, id).getBarcodeGroup().getBarcodes());
		else
		{
			barcodes.add(genericDao.load(Barcode.class, id));
			map.put("id", barcodes.getFirst().getBarcodeGroup().getProductionOrderDetailBarcode().getId());
		}
		
		map.put("barcodes", barcodes);
		map.put("type", "4");
		
		return map;
	}
}
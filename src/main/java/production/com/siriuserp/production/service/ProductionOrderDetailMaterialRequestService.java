package com.siriuserp.production.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.siriuserp.inventory.dm.WarehouseTransactionType;
import com.siriuserp.inventory.service.AutoWarehouseService;
import com.siriuserp.production.dm.MaterialSource;
import com.siriuserp.production.dm.MaterialType;
import com.siriuserp.production.dm.ProductionOrderDetail;
import com.siriuserp.production.dm.ProductionOrderDetailItem;
import com.siriuserp.production.dm.ProductionOrderDetailMaterialRequest;
import com.siriuserp.production.dm.ProductionOrderDetailMaterialRequestItem;
import com.siriuserp.production.form.ProductionForm;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.base.Service;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dm.Item;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.ReferenceItemHelper;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastMap;

/**
 * @author ferdinand
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class ProductionOrderDetailMaterialRequestService extends Service 
{
	@Autowired
    private CodeSequenceDao codeSequenceDao;
	
	@Autowired
    private AutoWarehouseService warehouseService;
	
	public Map<String, Object> preadd(Long id)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		
		map.put("detail", genericDao.load(ProductionOrderDetail.class, id));
		map.put("request_add", new ProductionForm());
		
		return map;
	}
	
	@AuditTrails(className = ProductionOrderDetailMaterialRequest.class, actionType = AuditTrailsActionType.CREATE)
	public void add(ProductionOrderDetailMaterialRequest materialRequest) throws Exception
	{
		Assert.notEmpty(materialRequest.getForm().getItems(), "Empty item transaction, please recheck !");

		materialRequest.setCode(GeneratorHelper.instance().generate(TableType.PRODUCTION_MATERIAL_REQUEST, codeSequenceDao));
		
		for (Item item : materialRequest.getForm().getItems())
			if (SiriusValidator.gz(item.getQuantity()))
			{
				ProductionOrderDetailMaterialRequestItem materialItem = new ProductionOrderDetailMaterialRequestItem();
				materialItem.setProductionOrderDetailMaterialRequest(materialRequest);
				materialItem.setProduct(item.getProduct());
				materialItem.setQuantity(item.getQuantity());
				materialItem.setFacilitySource(materialRequest.getSource());
				materialItem.setFacilityDestination(materialRequest.getDestination());
				materialItem.setTransactionItem(ReferenceItemHelper.init(genericDao, item.getQuantity(), WarehouseTransactionType.INTERNAL, materialItem));
				
				materialItem.setSourceGrid(item.getSource().getGrid());
				materialItem.setSourceContainer(item.getSource());
				
				materialItem.setDestinationGrid(materialRequest.getContainer().getGrid());
				materialItem.setDestinationContainer(materialRequest.getContainer());
				
				materialRequest.getItems().add(materialItem);
			}
		
		genericDao.add(materialRequest);

        //Auto Issue
        warehouseService.autoIssue(materialRequest);
        
        //Auto Receipt
        warehouseService.autoReceipt(materialRequest);
        
        //Create Input Material
        ProductionOrderDetail detail = genericDao.load(ProductionOrderDetail.class, materialRequest.getProductionOrderDetail().getId());
        for (ProductionOrderDetailMaterialRequestItem item : materialRequest.getItems())
        {
        	ProductionOrderDetailItem detailItem = new ProductionOrderDetailItem();
        	detailItem.setProduct(item.getProduct());
			detailItem.setProductionOrderDetail(detail);
			detailItem.setQuantity(item.getQuantity());
			detailItem.setMaterialSource(MaterialSource.MATERIAL_REQUEST);
			detailItem.setMaterialType(MaterialType.INPUT);
        	
        	detail.getItems().add(detailItem);
        }
        
        genericDao.update(detail);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(Long id) throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		
		map.put("request_edit", genericDao.load(ProductionOrderDetailMaterialRequest.class, id));
		
		return map;
	}
	
	@AuditTrails(className = ProductionOrderDetailMaterialRequest.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(ProductionOrderDetailMaterialRequest materialRequest) throws ServiceException
	{
		genericDao.update(materialRequest);
	}
}

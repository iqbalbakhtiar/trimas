package com.siriuserp.production.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.production.dm.ProductionCostCenterGroup;
import com.siriuserp.production.dm.ProductionOrder;
import com.siriuserp.production.dm.ProductionOrderItem;
import com.siriuserp.production.dm.ProductionOrderStatus;
import com.siriuserp.production.form.ProductionForm;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.annotation.InjectParty;
import com.siriuserp.sdk.base.Service;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.dm.Item;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.QueryFactory;

import javolution.util.FastMap;

/**
 * @author ferdinand
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class ProductionOrderService extends Service 
{
	@Autowired
	private CodeSequenceDao codeSequenceDao;
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		
		map.put("filterCriteria", filterCriteria);
		map.put("statuses", ProductionOrderStatus.values());
		map.put("orders", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));

		return map;
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	@InjectParty(keyName = "order_add")
	public Map<String, Object> preadd()
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		
		map.put("order_add", new ProductionForm());
		
		return map;
	}
	
	@AuditTrails(className = ProductionOrder.class, actionType = AuditTrailsActionType.CREATE)
	public void add(ProductionForm form) throws ServiceException
	{
		ProductionOrder productionOrder = form.getProductionOrder();
		productionOrder.setCode(GeneratorHelper.instance().generate(TableType.PRODUCTION_ORDER, codeSequenceDao));
		
		for (ProductionCostCenterGroup costGroup : form.getCostCenterGroupProductions())
			if (costGroup.getCostCenterGroup() != null)
			{
				costGroup.setProductionOrder(productionOrder);
				productionOrder.getProductionCostCenterGroups().add(costGroup);
			}
		
		for (Item item : form.getItems())
			if (item.getProduct() != null)
			{
				ProductionOrderItem productionItem = new ProductionOrderItem();
				productionItem.setProduct(item.getProduct());
				productionItem.setQuantity(item.getQuantity());
				productionItem.setCogsWeight(item.getWeight());
				productionItem.setMaterialType(item.getMaterialType());
				productionItem.setProductionOrder(productionOrder);
				productionItem.setReferenceCode(productionOrder.getCode());
				
				productionOrder.getItems().add(productionItem);
			}
		
		genericDao.add(productionOrder);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(Long id) throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		
		map.put("order_edit", load(id));
		
		return map;
	}
	
	@AuditTrails(className = ProductionOrder.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(ProductionOrder productionOrder) throws ServiceException
	{
		genericDao.update(productionOrder);
	}
	
	@AuditTrails(className = ProductionOrder.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(ProductionOrder productionOrder) throws ServiceException
	{
		genericDao.delete(productionOrder);
	}

	@Transactional(readOnly = false)
	public ProductionOrder load(Long id)
	{
		return genericDao.load(ProductionOrder.class, id);
	}
}

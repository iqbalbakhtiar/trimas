package com.siriuserp.production.service;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.production.dm.MaterialSource;
import com.siriuserp.production.dm.MaterialType;
import com.siriuserp.production.dm.ProductionCostCenterGroup;
import com.siriuserp.production.dm.ProductionDetailCostCenterGroup;
import com.siriuserp.production.dm.ProductionOrder;
import com.siriuserp.production.dm.ProductionOrderDetail;
import com.siriuserp.production.dm.ProductionOrderDetailItem;
import com.siriuserp.production.dm.ProductionOrderItem;
import com.siriuserp.production.dm.ProductionOrderStatus;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.base.Service;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.db.GridViewQuery;
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
public class ProductionOrderDetailService extends Service
{
	@Autowired
	private CodeSequenceDao codeSequenceDao;
	
	@Autowired
	private ProductionOrderService productionOrderService;
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		
		map.put("filterCriteria", filterCriteria);
		map.put("details", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));

		return map;
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> preadd(Long id)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		
		ProductionOrderDetail detail = new ProductionOrderDetail();
		detail.setProductionOrder(productionOrderService.load(id));
		
		map.put("detail_add", detail);
		
		return map;
	}
	
	@AuditTrails(className = ProductionOrderDetail.class, actionType = AuditTrailsActionType.CREATE)
	public void add(ProductionOrderDetail productionOrderDetail) throws ServiceException
	{
		productionOrderDetail.setCode(GeneratorHelper.instance().generate(TableType.PRODUCTION_ORDER_DETAIL, codeSequenceDao));
		productionOrderDetail.setStatus(ProductionOrderStatus.ON_GOING);
		
		ProductionOrder productionOrder = productionOrderService.load(productionOrderDetail.getProductionOrder().getId());
		
		for (ProductionCostCenterGroup costCenterGroup : productionOrder.getProductionCostCenterGroups())
		{
			ProductionDetailCostCenterGroup costGroup = new ProductionDetailCostCenterGroup();
			costGroup.setCostCenterGroup(costCenterGroup.getCostCenterGroup());
			costGroup.setProductionOrderDetail(productionOrderDetail);
			
			productionOrderDetail.getProductionDetailCostCenterGroups().add(costGroup);
		}
		
		for (ProductionOrderItem item : productionOrder.getItems())
		{
			ProductionOrderDetailItem detailItem = new ProductionOrderDetailItem();
			detailItem.setProduct(item.getProduct());
			detailItem.setProductionOrderDetail(productionOrderDetail);
			
			if (item.getMaterialType().equals(MaterialType.WIP))
			{
				detailItem.setQuantity(item.getQuantity());
				detailItem.setMaterialSource(MaterialSource.WIP);
				detailItem.setMaterialType(MaterialType.INPUT);
			}
			
			if (item.getMaterialType().equals(MaterialType.OUTPUT))
			{
				detailItem.setQuantity(BigDecimal.ZERO);
				detailItem.setMaterialSource(MaterialSource.OUTPUT);
				detailItem.setMaterialType(MaterialType.OUTPUT);
			}
			
			productionOrderDetail.getItems().add(detailItem);
		}
		
		genericDao.add(productionOrderDetail);
		
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(Long id) throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		
		map.put("detail_edit", load(id));
		
		return map;
	}
	
	@AuditTrails(className = ProductionOrderDetail.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(ProductionOrderDetail productionOrderDetail) throws ServiceException
	{
		genericDao.update(productionOrderDetail);
	}
	
	
	@AuditTrails(className = ProductionOrderDetail.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(ProductionOrderDetail productionOrderDetail) throws ServiceException
	{
		genericDao.delete(productionOrderDetail);
	}

	@Transactional(readOnly = false)
	public ProductionOrderDetail load(Long id)
	{
		return genericDao.load(ProductionOrderDetail.class, id);
	}
}

/**
 * File Name  : WorkOrderService.java
 * Created On : Jul 30, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.production.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.inventory.dm.ConversionType;
import com.siriuserp.inventory.dm.WarehouseTransactionType;
import com.siriuserp.production.dm.ProductionStatus;
import com.siriuserp.production.dm.WorkOrder;
import com.siriuserp.production.dm.WorkOrderApprovableBridge;
import com.siriuserp.production.dm.WorkOrderItem;
import com.siriuserp.production.form.ProductionForm;
import com.siriuserp.sales.dm.ApprovableType;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.annotation.AutomaticSibling;
import com.siriuserp.sdk.annotation.InjectParty;
import com.siriuserp.sdk.base.Service;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.dm.ApprovalDecisionStatus;
import com.siriuserp.sdk.dm.Item;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.ApprovableBridgeHelper;
import com.siriuserp.sdk.utility.FormHelper;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.QueryFactory;
import com.siriuserp.sdk.utility.ReferenceItemHelper;

import javolution.util.FastMap;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class WorkOrderService extends Service
{
	@Autowired
	private GenericDao genericDao;

	@Autowired
	private CodeSequenceDao codeSequenceDao;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends AbstractGridViewQuery> queryclass) throws Exception
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("workOrders", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));
		map.put("filterCriteria", filterCriteria);

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	@InjectParty(keyName = "workOrder_form")
	public FastMap<String, Object> preadd()
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("workOrder_form", new ProductionForm());

		return map;
	}

	@AuditTrails(className = WorkOrder.class, actionType = AuditTrailsActionType.CREATE)
	public void add(WorkOrder workOrder) throws Exception
	{
		ProductionForm form = (ProductionForm) workOrder.getForm();
		workOrder.setCode(GeneratorHelper.instance().generate(TableType.WORK_ORDER, codeSequenceDao));
		workOrder.setProductionStatus(ProductionStatus.OPEN);

		if (form.getApprover() != null)
		{
			WorkOrderApprovableBridge approvableBridge = ApprovableBridgeHelper.create(WorkOrderApprovableBridge.class, workOrder);
			approvableBridge.setApprovableType(ApprovableType.WORK_ORDER);
			approvableBridge.setUri("workorderpreedit.htm");

			workOrder.setApprovable(approvableBridge);
		}

		for (Item item : form.getItems())
		{
			if (item.getProduct() != null && item.getQuantity().compareTo(BigDecimal.ZERO) > 0)
			{
				WarehouseTransactionType transactionType = WarehouseTransactionType.OUT;
				if (item.getConversionType().equals(ConversionType.RESULT))
					transactionType = WarehouseTransactionType.IN;

				WorkOrderItem workOrderItem = new WorkOrderItem();
				workOrderItem.setWorkOrder(workOrder);
				workOrderItem.setConversionType(item.getConversionType());
				workOrderItem.setContainer(item.getContainer());
				workOrderItem.setProduct(item.getProduct());
				workOrderItem.setQuantity(item.getQuantity());
				workOrderItem.getLot().setSerial(item.getSerial());
				workOrderItem.setNote(item.getNote());

				if (workOrderItem.getConversionType().equals(ConversionType.CONVERT))
				{
					workOrderItem.setSourceContainer(item.getContainer());
					workOrderItem.setSourceGrid(workOrderItem.getSourceContainer().getGrid());
					workOrderItem.setFacilitySource(workOrderItem.getSourceGrid().getFacility());
				} else
				{
					workOrderItem.setDestinationContainer(item.getContainer());
					workOrderItem.setDestinationGrid(workOrderItem.getDestinationContainer().getGrid());
					workOrderItem.setFacilityDestination(workOrderItem.getDestinationGrid().getFacility());
				}

				workOrderItem.setTransactionItem(ReferenceItemHelper.init(genericDao, workOrderItem.getQuantity(), transactionType, workOrderItem));

				workOrder.getItems().add(workOrderItem);
			}
		}

		genericDao.add(workOrder);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(Long id) throws Exception
	{
		ProductionForm productionForm = FormHelper.bind(ProductionForm.class, load(id));
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("workOrder_form", productionForm);
		map.put("workOrder_edit", productionForm.getWorkOrder());
		map.put("approvalDecisionStatuses", ApprovalDecisionStatus.values());
		map.put("approvalDecision", productionForm.getWorkOrder().getApprovable() != null ? productionForm.getWorkOrder().getApprovable().getApprovalDecision() : null);

		return map;
	}

	@AuditTrails(className = WorkOrder.class, actionType = AuditTrailsActionType.UPDATE)
	@AutomaticSibling(roles = "ApprovableSiblingRole")
	public void edit(WorkOrder workOrder) throws Exception
	{
		genericDao.update(workOrder);
	}

	@AuditTrails(className = WorkOrder.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(WorkOrder workOrder) throws Exception
	{
		if (workOrder.getProductionStatus().equals(ProductionStatus.OPEN))
			genericDao.delete(workOrder);
	}

	@AuditTrails(className = WorkOrder.class, actionType = AuditTrailsActionType.UPDATE)
	public void changeStatus(Long id, String productionStatus) throws ServiceException
	{
		WorkOrder workOrder = load(id);
		workOrder.setProductionStatus(ProductionStatus.valueOf(productionStatus));

		genericDao.update(workOrder);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public WorkOrder load(Long id)
	{
		return genericDao.load(WorkOrder.class, id);
	}
}

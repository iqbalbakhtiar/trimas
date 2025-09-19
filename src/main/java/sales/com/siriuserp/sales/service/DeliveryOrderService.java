/**
 * File Name  : DeliveryOrderService.java
 * Created On : Mar 18, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.sales.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.inventory.dao.InventoryItemDao;
import com.siriuserp.inventory.dm.InventoryItem;
import com.siriuserp.sales.dm.DeliveryOrder;
import com.siriuserp.sales.dm.DeliveryOrderItem;
import com.siriuserp.sales.dm.DeliveryOrderItemType;
import com.siriuserp.sales.dm.DeliveryOrderReferenceItem;
import com.siriuserp.sales.dm.SOStatus;
import com.siriuserp.sales.dm.SalesOrder;
import com.siriuserp.sales.dm.SalesOrderItem;
import com.siriuserp.sales.form.SalesForm;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.base.Service;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.dm.Item;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.DateHelper;
import com.siriuserp.sdk.utility.FormHelper;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.QueryFactory;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastList;
import javolution.util.FastMap;
import javolution.util.FastSet;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class DeliveryOrderService extends Service
{
	@Autowired
	private GenericDao genericDao;

	@Autowired
	private CodeSequenceDao codeSequenceDao;

	@Autowired
	private InventoryItemDao inventoryItemDao;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends AbstractGridViewQuery> queryclass) throws Exception
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("deliveryOrders", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));
		map.put("filterCriteria", filterCriteria);

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preadd1(GridViewFilterCriteria filterCriteria, Class<? extends AbstractGridViewQuery> queryclass) throws Exception
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("deliveryReferences", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));
		map.put("filterCriteria", filterCriteria);
		map.put("deliveryOrder_form", new SalesForm());

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preadd2(SalesForm form) throws ServiceException
	{
		for (Item item : form.getItems())
		{
			if (item.getDeliveryReferenceItem() != null)
			{
				DeliveryOrderReferenceItem referenceItem = item.getDeliveryReferenceItem();
				form.setCode(referenceItem.getCode());
				form.setOrganization(referenceItem.getOrganization());
				form.setCustomer(referenceItem.getCustomer());
				form.setShippingAddress(referenceItem.getShippingAddress());
				form.setFacility(referenceItem.getFacility());
			} else
				form.getItems().remove(item);
		}

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("deliveryOrder_form", form);

		return map;
	}

	@AuditTrails(className = DeliveryOrder.class, actionType = AuditTrailsActionType.CREATE)
	public void add(DeliveryOrder deliveryOrder) throws Exception
	{
		SalesForm form = (SalesForm) deliveryOrder.getForm();
		deliveryOrder.setCode(GeneratorHelper.instance().generate(TableType.DELIVERY_ORDER, codeSequenceDao, deliveryOrder.getDate()));

		FastSet<Long> salesIds = new FastSet<Long>();
		List<Item> baseItems = new FastList<Item>();
		List<Item> serialItems = new FastList<Item>();

		for (Item item : form.getItems())
		{
			if (item.getDeliveryItemType() != null && SiriusValidator.gz(item.getQuantity()))
			{
				if (item.getDeliveryItemType().equals(DeliveryOrderItemType.BASE))
					baseItems.add(item);
				else
					serialItems.add(item);
			}
		}

		for (Item item : baseItems)
		{
			DeliveryOrderItem baseItem = new DeliveryOrderItem();
			baseItem.setDeliveryReferenceItem(item.getDeliveryReferenceItem());
			baseItem.setDeliveryOrder(deliveryOrder);
			baseItem.setContainer(item.getContainer());
			baseItem.setQuantity(item.getQuantity());
			baseItem.setDeliveryItemType(item.getDeliveryItemType());
			baseItem.setNote(item.getNote());

			DeliveryOrderReferenceItem deliveryReference = item.getDeliveryReferenceItem();
			deliveryReference.setDeliverable(false);

			genericDao.update(deliveryReference);

			SalesOrderItem salesItem = genericDao.load(SalesOrderItem.class, deliveryReference.getSalesOrderItem().getId());
			BigDecimal deliveredQty = salesItem.getDelivered();
			deliveredQty = deliveredQty.add(item.getQuantity());
			salesItem.setDelivered(deliveredQty);

			genericDao.update(salesItem);

			salesIds.add(salesItem.getSalesOrder().getId());

			deliveryOrder.getItems().add(baseItem);

			for (Item sItem : serialItems)
			{
				if (sItem.getDeliveryReferenceItem().getId().equals(item.getDeliveryReferenceItem().getId()))
				{
					DeliveryOrderItem serialItem = new DeliveryOrderItem();
					serialItem.setDeliveryOrder(deliveryOrder);
					serialItem.setContainer(sItem.getContainer());
					serialItem.setQuantity(sItem.getQuantity());
					serialItem.setDeliveryItemType(sItem.getDeliveryItemType());
					serialItem.setNote(sItem.getNote());
					serialItem.getLot().setSerial(sItem.getSerial());
					serialItem.getLot().setCode(sItem.getLotCode());
					serialItem.setItemParent(baseItem);

					InventoryItem inventoryItem = inventoryItemDao.getItemBySerial(serialItem.getLot().getSerial(), true);
					inventoryItem.setReserved(inventoryItem.getOnHand());

					genericDao.update(inventoryItem);

					deliveryOrder.getItems().add(serialItem);
				}
			}
		}

		genericDao.add(deliveryOrder);

		for (Long salesId : salesIds)
		{
			SalesOrder salesOrder = genericDao.load(SalesOrder.class, salesId);
			salesOrder.setSoStatus(SOStatus.DELIVERED);

			genericDao.update(salesOrder);
		}
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(Long id) throws Exception
	{
		SalesForm form = FormHelper.bind(SalesForm.class, load(id));

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("deliveryOrder_form", form);
		map.put("deliveryOrder_edit", form.getDeliveryOrder());

		return map;
	}

	@AuditTrails(className = DeliveryOrder.class, actionType = AuditTrailsActionType.UPDATE)
	public FastMap<String, Object> edit(DeliveryOrder deliveryOrder) throws Exception
	{
		deliveryOrder.setUpdatedBy(getPerson());
		deliveryOrder.setUpdatedDate(DateHelper.now());

		genericDao.update(deliveryOrder);

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("id", deliveryOrder.getId());

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public DeliveryOrder load(Long id)
	{
		return genericDao.load(DeliveryOrder.class, id);
	}

	@AuditTrails(className = DeliveryOrder.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(DeliveryOrder deliveryOrder) throws Exception
	{
		for (DeliveryOrderItem item : deliveryOrder.getItems())
		{
			if (item.getDeliveryItemType().equals(DeliveryOrderItemType.BASE))
			{
				DeliveryOrderReferenceItem deliveryReference = item.getDeliveryReferenceItem();
				deliveryReference.setDeliverable(true);

				genericDao.update(deliveryReference);

				SalesOrderItem salesItem = genericDao.load(SalesOrderItem.class, deliveryReference.getSalesOrderItem().getId());
				BigDecimal deliveredQty = salesItem.getDelivered();
				deliveredQty = deliveredQty.subtract(item.getQuantity());
				salesItem.setDelivered(deliveredQty);

				genericDao.update(salesItem);
			} else
			{
				InventoryItem inventoryItem = inventoryItemDao.getItemBySerial(item.getLot().getSerial(), true);
				inventoryItem.setReserved(BigDecimal.ZERO);

				genericDao.update(inventoryItem);
			}
		}

		genericDao.delete(deliveryOrder);
	}

	@AuditTrails(className = DeliveryOrder.class, actionType = AuditTrailsActionType.UPDATE)
	public void updateStatus(Long id, SOStatus status) throws Exception
	{
		DeliveryOrder deliveryOrder = genericDao.load(DeliveryOrder.class, id);
		deliveryOrder.setStatus(status);

		if (status.equals(SOStatus.CANCELED))
		{
			for (DeliveryOrderItem item : deliveryOrder.getItems())
			{
				if (item.getDeliveryItemType().equals(DeliveryOrderItemType.BASE))
				{
					SalesOrderItem salesItem = genericDao.load(SalesOrderItem.class, item.getDeliveryReferenceItem().getSalesOrderItem().getId());
					BigDecimal deliveredQty = salesItem.getDelivered();
					deliveredQty = deliveredQty.subtract(item.getQuantity());
					salesItem.setDelivered(deliveredQty);

					BigDecimal assignedQty = salesItem.getAssigned();
					assignedQty = assignedQty.subtract(item.getQuantity());
					salesItem.setAssigned(assignedQty);

					genericDao.update(salesItem);
				} else
				{
					InventoryItem inventoryItem = inventoryItemDao.getItemBySerial(item.getLot().getSerial(), true);
					inventoryItem.setReserved(BigDecimal.ZERO);

					genericDao.update(inventoryItem);
				}
			}
		}

		genericDao.update(deliveryOrder);
	}
}

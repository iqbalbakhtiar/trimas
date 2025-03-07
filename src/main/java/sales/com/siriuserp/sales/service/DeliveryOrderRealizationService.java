package com.siriuserp.sales.service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.inventory.dm.InventoryItem;
import com.siriuserp.inventory.dm.WarehouseTransactionType;
import com.siriuserp.inventory.util.InventoryItemTagUtil;
import com.siriuserp.sales.dm.DeliveryOrder;
import com.siriuserp.sales.dm.DeliveryOrderItem;
import com.siriuserp.sales.dm.DeliveryOrderRealization;
import com.siriuserp.sales.dm.DeliveryOrderRealizationItem;
import com.siriuserp.sales.dm.DeliveryOrderRealizationReserveBridge;
import com.siriuserp.sales.dm.SOStatus;
import com.siriuserp.sales.dm.SalesOrderReferenceItem;
import com.siriuserp.sales.form.DeliveryOrderForm;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.annotation.AutomaticSibling;
import com.siriuserp.sdk.base.Service;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.dm.Item;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.dm.Tag;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.DateHelper;
import com.siriuserp.sdk.utility.FormHelper;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.QueryFactory;
import com.siriuserp.sdk.utility.ReferenceItemHelper;

import javolution.util.FastList;
import javolution.util.FastMap;

/**
 * @author Rama Almer Felix
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class DeliveryOrderRealizationService extends Service
{

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private CodeSequenceDao codeSequenceDao;

	@Autowired
	private InventoryItemTagUtil inventoryItemUtil;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends AbstractGridViewQuery> queryclass) throws Exception
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("dors", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));
		map.put("filterCriteria", filterCriteria);

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preadd2(Long id)
	{
		DeliveryOrder deliveryOrder = genericDao.load(DeliveryOrder.class, id);
		Hibernate.initialize(deliveryOrder);
		DeliveryOrderForm form = new DeliveryOrderForm();

		for (DeliveryOrderItem doItem : deliveryOrder.getItems())
		{
			Item item = new Item();
			item.setSalesReferenceItem(doItem.getSalesReference());

			form.getItems().add(item);
		}

		form.setDeliveryOrder(deliveryOrder);

		// Get Currency and Tax from SalesReferenceItems
		form.setCurrency(form.getDeliveryOrder().getItems().iterator().next().getSalesReference().getSalesOrderItem().getMoney().getCurrency());
		form.setTax(form.getDeliveryOrder().getItems().iterator().next().getSalesReference().getTax());

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("dor_form", form);

		return map;
	}

	@AuditTrails(className = DeliveryOrderRealization.class, actionType = AuditTrailsActionType.CREATE)
	@AutomaticSibling(roles =
	{ "DelInventorySiblingRole", "AddBillingSiblingRole" })
	public void add(DeliveryOrderRealization dor) throws Exception
	{
		DeliveryOrderForm form = (DeliveryOrderForm) dor.getForm();

		//load DO
		DeliveryOrder delivery = genericDao.load(DeliveryOrder.class, form.getDeliveryOrder().getId());

		// Set DOR Value
		dor.setCode(GeneratorHelper.instance().generate(TableType.DELIVERY_ORDER_REALIZATION, codeSequenceDao));
		dor.setOrganization(delivery.getOrganization());
		dor.setFacility(delivery.getFacility());
		dor.setCustomer(delivery.getCustomer());

		for (Item item : form.getItems())
		{
			//Load to avoid no session
			SalesOrderReferenceItem salesReferenceItem = genericDao.load(SalesOrderReferenceItem.class, item.getSalesReferenceItem().getId());
			DeliveryOrderItem deliveryOrderItem = genericDao.load(DeliveryOrderItem.class, salesReferenceItem.getDeliveryOrderItem().getId());

			// Set Accepted DOR Item
			if (item.getAccepted().compareTo(BigDecimal.ZERO) > 0)
			{
				DeliveryOrderRealizationItem dorItem = new DeliveryOrderRealizationItem();
				dorItem.setAccepted(item.getAccepted());
				dorItem.setReturned(item.getReturned());
				dorItem.setDeliveryOrderRealization(dor);
				dorItem.setDeliveryOrderItem(deliveryOrderItem);
				dorItem.setProduct(salesReferenceItem.getSalesOrderItem().getProduct());
				dorItem.setSourceContainer(deliveryOrderItem.getContainer());
				dorItem.setNote(item.getNote());

				dorItem.setTransactionItem(ReferenceItemHelper.init(genericDao, dorItem.getAccepted(), WarehouseTransactionType.OUT, dorItem));

				dor.getAccepteds().add(dorItem);
			}

			// Set Shrink DOR Item
			if (item.getShrinkage().compareTo(BigDecimal.ZERO) > 0)
			{
				DeliveryOrderRealizationItem shinkItem = new DeliveryOrderRealizationItem();
				shinkItem.setTag(Tag.shrink());
				shinkItem.setShrinkage(item.getShrinkage());
				shinkItem.setDeliveryOrderRealization(dor);
				shinkItem.setDeliveryOrderItem(deliveryOrderItem);
				shinkItem.setProduct(salesReferenceItem.getSalesOrderItem().getProduct());
				shinkItem.setSourceContainer(deliveryOrderItem.getContainer());

				shinkItem.setTransactionItem(ReferenceItemHelper.init(genericDao, shinkItem.getShrinkage(), WarehouseTransactionType.OUT, shinkItem));

				shinkItem.setReserveBridge(inventoryItemUtil.init(DeliveryOrderRealizationReserveBridge.class));
				shinkItem.getReserveBridge().setRealizationItem(shinkItem);

				inventoryItemUtil.reserve(InventoryItem.class, shinkItem);

				dor.getShrinks().add(shinkItem);
			}
		}

		genericDao.add(dor);

		// Update DO Status
		delivery.setStatus(SOStatus.DELIVERED);
		delivery.setUpdatedBy(getPerson());
		delivery.setUpdatedDate(DateHelper.now());

		genericDao.update(delivery);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(Long id) throws Exception
	{
		DeliveryOrderRealization deliveryOrderRealization = genericDao.load(DeliveryOrderRealization.class, id);
		DeliveryOrderForm form = FormHelper.bind(DeliveryOrderForm.class, deliveryOrderRealization);
		form.setDeliveryOrderRealization(deliveryOrderRealization);

		// Gabungkan DOR item yang ada shrinkage
		Map<Long, DeliveryOrderRealizationItem> aggregatedItems = deliveryOrderRealization.getItems().stream().collect(Collectors.toMap(item -> item.getDeliveryOrderItem().getId(), item -> {
			DeliveryOrderRealizationItem newItem = new DeliveryOrderRealizationItem();
			newItem.setDeliveryOrderItem(item.getDeliveryOrderItem());
			newItem.setAccepted(item.getAccepted());
			newItem.setReturned(item.getReturned());
			newItem.setShrinkage(item.getShrinkage());
			newItem.setNote(item.getNote());
			return newItem;
		}, (existing, incoming) -> {
			existing.setAccepted(existing.getAccepted().add(incoming.getAccepted()));
			existing.setReturned(existing.getReturned().add(incoming.getReturned()));
			existing.setShrinkage(existing.getShrinkage().add(incoming.getShrinkage()));
			return existing;
		}));

		FastMap<String, Object> map = new FastMap<>();
		map.put("dor_form", form);
		map.put("dorItems", new FastList<>(aggregatedItems.values()));

		return map;
	}

	@AuditTrails(className = DeliveryOrderRealization.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(DeliveryOrderForm form) throws Exception
	{
		form.getDeliveryOrderRealization().setUpdatedBy(getPerson());
		form.getDeliveryOrderRealization().setUpdatedDate(DateHelper.now());

		genericDao.update(form.getDeliveryOrderRealization()); // Update DOR Directly from JSP
	}
}

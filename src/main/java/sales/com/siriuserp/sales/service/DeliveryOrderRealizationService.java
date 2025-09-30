/**
 * File Name  : DeliveryOrderRealizationService.java
 * Created On : Mar 18, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.sales.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.accountreceivable.dao.BillingDao;
import com.siriuserp.accountreceivable.dm.BillingReferenceType;
import com.siriuserp.inventory.dao.InventoryItemDao;
import com.siriuserp.inventory.dm.InventoryItem;
import com.siriuserp.inventory.dm.WarehouseTransactionType;
import com.siriuserp.sales.dm.DeliveryOrder;
import com.siriuserp.sales.dm.DeliveryOrderItem;
import com.siriuserp.sales.dm.DeliveryOrderItemType;
import com.siriuserp.sales.dm.DeliveryOrderRealization;
import com.siriuserp.sales.dm.DeliveryOrderRealizationItem;
import com.siriuserp.sales.dm.SOStatus;
import com.siriuserp.sales.form.SalesForm;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.annotation.AutomaticSibling;
import com.siriuserp.sdk.base.Service;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.dm.Item;
import com.siriuserp.sdk.dm.Money;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.DateHelper;
import com.siriuserp.sdk.utility.FormHelper;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.QueryFactory;
import com.siriuserp.sdk.utility.ReferenceItemHelper;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastMap;

/**
 * @author Iqbal Bakhtiar
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
	private InventoryItemDao inventoryItemDao;

	@Autowired
	private BillingDao billingDao;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends AbstractGridViewQuery> queryclass) throws Exception
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("dors", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));
		map.put("filterCriteria", filterCriteria);

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preadd1(GridViewFilterCriteria filterCriteria, Class<? extends AbstractGridViewQuery> queryclass) throws Exception
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("deliveryOrders", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));
		map.put("filterCriteria", filterCriteria);

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preadd2(Long id)
	{
		DeliveryOrder deliveryOrder = genericDao.load(DeliveryOrder.class, id);
		SalesForm form = new SalesForm();
		form.setDeliveryOrder(deliveryOrder);
		form.setCurrency(form.getDeliveryOrder().getItems().iterator().next().getMoney().getCurrency());
		form.setTax(form.getDeliveryOrder().getItems().iterator().next().getTax());

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("realization_form", form);

		return map;
	}

	@AuditTrails(className = DeliveryOrderRealization.class, actionType = AuditTrailsActionType.CREATE)
	@AutomaticSibling(roles =
	{ "DelInventorySiblingRole", "AddBillingSiblingRole" })
	public void add(DeliveryOrderRealization realization) throws Exception
	{
		SalesForm form = (SalesForm) realization.getForm();
		DeliveryOrder delivery = genericDao.load(DeliveryOrder.class, form.getDeliveryOrder().getId());

		realization.setCode(GeneratorHelper.instance().generate(TableType.DELIVERY_ORDER_REALIZATION, codeSequenceDao));
		realization.setOrganization(delivery.getOrganization());
		realization.setFacility(delivery.getFacility());
		realization.setCustomer(delivery.getCustomer());

		for (Item item : form.getItems())
		{
			DeliveryOrderItem deliveryOrderItem = genericDao.load(DeliveryOrderItem.class, item.getReference());

			if (item.getAccepted().compareTo(BigDecimal.ZERO) > 0)
			{
				Money money = new Money();
				money.setAmount(deliveryOrderItem.getMoney().getAmount());
				money.setCurrency(deliveryOrderItem.getMoney().getCurrency());
				money.setExchangeType(deliveryOrderItem.getMoney().getExchangeType());
				money.setRate(deliveryOrderItem.getMoney().getRate());

				DeliveryOrderRealizationItem realizationItem = new DeliveryOrderRealizationItem();
				realizationItem.setAccepted(item.getAccepted());
				realizationItem.setReturned(item.getReturned());
				realizationItem.setDeliveryOrderRealization(realization);
				realizationItem.setDeliveryOrderItem(deliveryOrderItem);
				realizationItem.setProduct(deliveryOrderItem.getProduct());
				realizationItem.setSourceContainer(deliveryOrderItem.getContainer());
				realizationItem.setMoney(money);
				realizationItem.setNote(item.getNote());
				realizationItem.getLot().setSerial(item.getSerial());
				realizationItem.getLot().setCode(item.getLotCode());
				realizationItem.setTax(deliveryOrderItem.getTax());

				if (SiriusValidator.validateParam(realizationItem.getLot().getSerial()))
				{
					InventoryItem inventoryItem = inventoryItemDao.getItemBySerial(realizationItem.getLot().getSerial(), true);
					inventoryItem.setReserved(BigDecimal.ZERO);

					genericDao.update(inventoryItem);
				}

				if (!realizationItem.getProduct().isSerial() || deliveryOrderItem.getDeliveryItemType().equals(DeliveryOrderItemType.SERIAL))
					realizationItem.setTransactionItem(ReferenceItemHelper.init(genericDao, realizationItem.getAccepted(), WarehouseTransactionType.OUT, realizationItem));

				realization.getAccepteds().add(realizationItem);

				if (deliveryOrderItem.getDeliveryPlanningSequence().getDeliveryPlanning().getSalesOrder().isBillingable())
					realization.setBillingable(false);
			}
		}

		genericDao.add(realization);

		delivery.setStatus(SOStatus.DELIVERED);
		delivery.setUpdatedBy(getPerson());
		delivery.setUpdatedDate(DateHelper.now());

		genericDao.update(delivery);

	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(Long id) throws Exception
	{
		SalesForm form = FormHelper.bind(SalesForm.class, load(id));

		FastMap<String, Object> map = new FastMap<>();
		map.put("realization_form", form);
		map.put("realization_edit", form.getDeliveryOrderRealization());
		map.put("billing", billingDao.getBillingByReference(id, BillingReferenceType.DELIVERY_ORDER_REALIZATION));

		return map;
	}

	@AuditTrails(className = DeliveryOrderRealization.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(SalesForm form) throws Exception
	{
		form.getDeliveryOrderRealization().setUpdatedBy(getPerson());
		form.getDeliveryOrderRealization().setUpdatedDate(DateHelper.now());

		genericDao.update(form.getDeliveryOrderRealization());
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public DeliveryOrderRealization load(Long id)
	{
		return genericDao.load(DeliveryOrderRealization.class, id);
	}
}

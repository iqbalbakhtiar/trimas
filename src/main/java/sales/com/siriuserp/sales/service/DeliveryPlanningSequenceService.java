/**
 * File Name  : DeliveryPlanningSequenceService.java
 * Created On : Mar 6, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.sales.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.sales.dm.DeliveryOrderReferenceItem;
import com.siriuserp.sales.dm.DeliveryPlanning;
import com.siriuserp.sales.dm.DeliveryPlanningSequence;
import com.siriuserp.sales.dm.DeliveryPlanningSequenceItem;
import com.siriuserp.sales.dm.SOStatus;
import com.siriuserp.sales.dm.SalesOrder;
import com.siriuserp.sales.dm.SalesOrderItem;
import com.siriuserp.sales.form.SalesForm;
import com.siriuserp.sales.util.DeliveryOrderReferenceUtil;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dm.Item;
import com.siriuserp.sdk.dm.Money;
import com.siriuserp.sdk.utility.DateHelper;
import com.siriuserp.sdk.utility.FormHelper;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastList;
import javolution.util.FastMap;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class DeliveryPlanningSequenceService
{
	@Autowired
	private GenericDao genericDao;

	public Map<String, Object> preadd(Long id) throws Exception
	{
		SalesForm form = new SalesForm();
		DeliveryPlanning deliveryPlanning = genericDao.load(DeliveryPlanning.class, id);
		form.setDeliveryPlanning(deliveryPlanning);

		List<Item> items = new FastList<Item>();
		SalesOrder salesOrder = genericDao.load(SalesOrder.class, deliveryPlanning.getSalesOrder().getId());
		form.setDate(DateHelper.today());
		form.setNo(Long.valueOf(deliveryPlanning.getSequenceCounter() + 1));
		form.setPostalAddress(salesOrder.getShippingAddress());
		form.setFacility(salesOrder.getFacility());
		form.setTax(salesOrder.getTax());

		for (SalesOrderItem salesItem : salesOrder.getItems())
		{
			if (salesItem.getQuantity().subtract(salesItem.getAssigned()).compareTo(BigDecimal.valueOf(0)) > 0 && salesItem.getLockStatus().equals(SOStatus.OPEN))
			{
				Item item = new Item();
				item.setProduct(salesItem.getProduct());
				item.setQuantity(salesItem.getQuantity());
				item.setAssigned(salesItem.getAssigned());
				item.setDelivered(salesItem.getDelivered());
				item.setReference(salesItem.getId());

				items.add(item);
			}
		}

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("salesItems", deliveryPlanning.getSalesOrder().getItems());
		map.put("items", items);
		map.put("planning_form", form);

		return map;
	}

	@AuditTrails(className = DeliveryPlanningSequence.class, actionType = AuditTrailsActionType.CREATE)
	public void add(DeliveryPlanningSequence deliveryPlanningSequence) throws Exception
	{
		for (Item item : deliveryPlanningSequence.getForm().getItems())
		{
			if (SiriusValidator.gz(item.getQuantity()) && SiriusValidator.validateParam(item.getReference()))
			{
				SalesOrderItem salesItem = genericDao.load(SalesOrderItem.class, item.getReference());
				Money money = new Money();
				money.setAmount(salesItem.getMoney().getAmount());
				money.setRate(salesItem.getMoney().getRate());
				money.setCurrency(salesItem.getMoney().getCurrency());
				money.setExchangeType(salesItem.getMoney().getExchangeType());

				DeliveryPlanningSequenceItem sequenceItem = new DeliveryPlanningSequenceItem();
				sequenceItem.setSalesOrderItem(salesItem);
				sequenceItem.setProduct(salesItem.getProduct());
				sequenceItem.setQuantity(item.getQuantity());
				sequenceItem.setDiscount(salesItem.getDiscount());
				sequenceItem.setMoney(money);
				sequenceItem.setDeliveryPlanningSequence(deliveryPlanningSequence);

				BigDecimal assignedQty = salesItem.getAssigned();
				assignedQty = assignedQty.add(sequenceItem.getQuantity());
				salesItem.setAssigned(assignedQty);

				genericDao.update(salesItem);

				deliveryPlanningSequence.getSequenceItems().add(sequenceItem);
			}
		}

		genericDao.add(deliveryPlanningSequence);

		for (DeliveryPlanningSequenceItem sequenceItem : deliveryPlanningSequence.getSequenceItems())
		{
			DeliveryOrderReferenceItem referenceItem = DeliveryOrderReferenceUtil.initItem(sequenceItem);
			genericDao.add(referenceItem);
		}

		DeliveryPlanning deliveryPlanning = genericDao.load(DeliveryPlanning.class, deliveryPlanningSequence.getDeliveryPlanning().getId());
		deliveryPlanning.setSequenceCounter(deliveryPlanning.getSequenceCounter() + 1);

		genericDao.update(deliveryPlanning);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> preedit(Long id) throws Exception
	{
		SalesForm form = FormHelper.bind(SalesForm.class, load(id));

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("planning_form", form);
		map.put("plan_edit", form.getDeliveryPlanningSequence());

		return map;
	}

	@AuditTrails(className = DeliveryPlanningSequence.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(DeliveryPlanningSequence deliveryPlanningSequence) throws Exception
	{
		genericDao.update(deliveryPlanningSequence);
	}

	@AuditTrails(className = DeliveryPlanningSequence.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(DeliveryPlanningSequence sequence) throws Exception
	{
		for (DeliveryPlanningSequenceItem item : sequence.getSequenceItems())
		{
			SalesOrderItem salesItem = genericDao.load(SalesOrderItem.class, item.getSalesOrderItem().getId());
			BigDecimal unassignedQty = item.getQuantity();
			salesItem.setAssigned(salesItem.getAssigned().subtract(unassignedQty));
			salesItem.setLockStatus(SOStatus.OPEN);

			genericDao.update(salesItem);
		}

		genericDao.delete(sequence);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public DeliveryPlanningSequence load(Long id)
	{
		return genericDao.load(DeliveryPlanningSequence.class, id);
	}
}

/**
 * File Name  : DeliveryOrderService.java
 * Created On : Mar 18, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.sales.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.sales.dm.DeliveryOrder;
import com.siriuserp.sales.dm.DeliveryOrderItem;
import com.siriuserp.sales.dm.DeliveryOrderReferenceItem;
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
import com.siriuserp.tools.service.ProfileService;

import javolution.util.FastMap;

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
	private ProfileService profileService;

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
				form.setFacility(profileService.loadProfile().getFacility());
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
		deliveryOrder.setCode(GeneratorHelper.instance().generate(TableType.DELIVERY_ORDER, codeSequenceDao));

		for (Item item : form.getItems())
		{
			if (SiriusValidator.gz(item.getQuantity()))
			{
				DeliveryOrderItem deliveryOrderItem = new DeliveryOrderItem();
				deliveryOrderItem.setDeliveryReferenceItem(item.getDeliveryReferenceItem());
				deliveryOrderItem.setDeliveryOrder(deliveryOrder);
				deliveryOrderItem.setContainer(item.getContainer());
				deliveryOrderItem.setQuantity(item.getQuantity());
				deliveryOrderItem.setNote(item.getNote());

				deliveryOrder.getItems().add(deliveryOrderItem);

				DeliveryOrderReferenceItem deliveryReference = item.getDeliveryReferenceItem();
				deliveryReference.setDeliverable(false);

				genericDao.update(deliveryReference);

				SalesOrderItem salesItem = genericDao.load(SalesOrderItem.class, deliveryReference.getSalesOrderItem().getId());
				BigDecimal deliveredQty = salesItem.getDelivered();
				deliveredQty = deliveredQty.add(item.getQuantity());
				salesItem.setDelivered(deliveredQty);

				genericDao.update(salesItem);
			}
		}

		genericDao.add(deliveryOrder);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(Long id) throws Exception
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		DeliveryOrder deliveryOrder = genericDao.load(DeliveryOrder.class, id);
		SalesForm form = FormHelper.bind(SalesForm.class, deliveryOrder);

		form.setDeliveryOrder(deliveryOrder);

		// Set Reference Code from one of the Sales Reference
		for (DeliveryOrderItem item : deliveryOrder.getItems())
		{
			/*if (item.getDeliveryReferenceItem().getReferenceId() != null)
			{
				SalesOrder salesOrder = genericDao.load(SalesOrder.class, item.getDeliveryReferenceItem().getReferenceId());
				map.put("referenceCode", salesOrder.getCode());
				map.put("poCode", salesOrder.getPoCode());
				break;
			}*/
		}
		map.put("deliveryOrder_form", form);

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
			DeliveryOrderReferenceItem deliveryReference = item.getDeliveryReferenceItem();
			deliveryReference.setDeliverable(true);

			genericDao.update(deliveryReference);

			SalesOrderItem salesItem = genericDao.load(SalesOrderItem.class, deliveryReference.getSalesOrderItem().getId());
			BigDecimal deliveredQty = salesItem.getDelivered();
			deliveredQty = deliveredQty.subtract(item.getQuantity());
			salesItem.setDelivered(deliveredQty);

			genericDao.update(salesItem);
		}

		genericDao.delete(deliveryOrder);
	}
}

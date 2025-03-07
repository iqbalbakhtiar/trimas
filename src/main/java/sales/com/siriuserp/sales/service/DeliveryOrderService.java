package com.siriuserp.sales.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.sales.dm.DeliveryOrder;
import com.siriuserp.sales.dm.DeliveryOrderItem;
import com.siriuserp.sales.dm.SOStatus;
import com.siriuserp.sales.dm.SalesOrder;
import com.siriuserp.sales.dm.SalesOrderReferenceItem;
import com.siriuserp.sales.form.DeliveryOrderForm;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.base.Service;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.dm.Item;
import com.siriuserp.sdk.dm.Profile;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.exceptions.DataEditException;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.DateHelper;
import com.siriuserp.sdk.utility.FormHelper;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.QueryFactory;
import com.siriuserp.tools.service.ProfileService;

import javolution.util.FastMap;

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
	public FastMap<String, Object> preadd2(Long id) throws ServiceException
	{
		SalesOrder salesOrder = genericDao.load(SalesOrder.class, id);
		DeliveryOrderForm form = new DeliveryOrderForm();

		Profile profile = profileService.loadProfile();

		/*for (SalesOrderReferenceItem salesReference: salesOrder.getItems()) {
			Item item = new Item();
			item.setSalesReferenceItem(salesReference);
		
			form.getItems().add(item);
		}*/

		form.setSalesOrder(salesOrder);

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("deliveryOrder_form", form);
		map.put("facility", profile.getFacility());

		return map;
	}

	@AuditTrails(className = DeliveryOrder.class, actionType = AuditTrailsActionType.CREATE)
	public void add(DeliveryOrder deliveryOrder) throws Exception
	{
		DeliveryOrderForm form = (DeliveryOrderForm) deliveryOrder.getForm();

		// Set DO value
		deliveryOrder.setCode(GeneratorHelper.instance().generate(TableType.DELIVERY_ORDER, codeSequenceDao));
		deliveryOrder.setRit(BigDecimal.ONE);
		deliveryOrder.setRealization(false);
		deliveryOrder.setStatus(SOStatus.OPEN);
		deliveryOrder.setOrganization(form.getSalesOrder().getOrganization());
		deliveryOrder.setCustomer(form.getSalesOrder().getCustomer());
		deliveryOrder.setShippingAddress(form.getSalesOrder().getShippingAddress());

		for (Item item : form.getItems())
		{
			DeliveryOrderItem deliveryOrderItem = new DeliveryOrderItem();
			deliveryOrderItem.setSalesReference(item.getSalesReferenceItem());
			deliveryOrderItem.setNote(item.getNote());
			deliveryOrderItem.setDeliveryOrder(deliveryOrder);
			deliveryOrderItem.setCreatedBy(getPerson());
			deliveryOrderItem.setCreatedDate(DateHelper.now());
			deliveryOrderItem.setContainer(item.getContainer());

			deliveryOrder.getItems().add(deliveryOrderItem);

			// Set deliverable menjadi false pada SalesReference terkait
			// Set delivered sebagai DO Quantity
			SalesOrderReferenceItem salesReference = item.getSalesReferenceItem();
			salesReference.setDeliverable(false);
			//salesReference.setDelivered(item.getDelivered());
			salesReference.setUpdatedBy(getPerson());
			salesReference.setUpdatedDate(DateHelper.now());

			genericDao.update(salesReference);
		}

		genericDao.add(deliveryOrder);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(Long id) throws Exception
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		DeliveryOrder deliveryOrder = genericDao.load(DeliveryOrder.class, id);
		DeliveryOrderForm form = FormHelper.bind(DeliveryOrderForm.class, deliveryOrder);

		form.setDeliveryOrder(deliveryOrder);

		// Set Reference Code from one of the Sales Reference
		for (DeliveryOrderItem item : deliveryOrder.getItems())
		{
			/*if (item.getSalesReferenceItem().getReferenceId() != null)
			{
				SalesOrder salesOrder = genericDao.load(SalesOrder.class, item.getSalesReferenceItem().getReferenceId());
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

	@AuditTrails(className = DeliveryOrder.class, actionType = AuditTrailsActionType.UPDATE)
	public DeliveryOrder sent(Long id) throws DataEditException
	{
		DeliveryOrder deliveryOrder = genericDao.load(DeliveryOrder.class, id);
		deliveryOrder.setStatus(SOStatus.SENT);

		genericDao.update(deliveryOrder);

		return deliveryOrder;
	}
}

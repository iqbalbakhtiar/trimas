package com.siriuserp.sales.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.sales.adapter.SalesOrderAdapter;
import com.siriuserp.sales.dm.ApprovableType;
import com.siriuserp.sales.dm.DeliveryPlanning;
import com.siriuserp.sales.dm.SOStatus;
import com.siriuserp.sales.dm.SalesOrder;
import com.siriuserp.sales.dm.SalesOrderApprovableBridge;
import com.siriuserp.sales.dm.SalesOrderItem;
import com.siriuserp.sales.dm.DeliveryOrderReferenceItem;
import com.siriuserp.sales.dm.SalesType;
import com.siriuserp.sales.form.SalesForm;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.annotation.AutomaticSibling;
import com.siriuserp.sdk.annotation.InjectParty;
import com.siriuserp.sdk.base.Service;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dao.SalesReferenceItemDao;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.dm.ApprovalDecisionStatus;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.Item;
import com.siriuserp.sdk.dm.Money;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.dm.Tax;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.ApprovableBridgeHelper;
import com.siriuserp.sdk.utility.DateHelper;
import com.siriuserp.sdk.utility.FormHelper;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.QueryFactory;

import javolution.util.FastMap;

@Component
@Transactional(rollbackFor = Exception.class)
public class SalesOrderService extends Service
{
	@Autowired
	private GenericDao genericDao;

	@Autowired
	private SalesReferenceItemDao salesReferenceItemDao;

	@Autowired
	private CodeSequenceDao codeSequenceDao;

	@Autowired
	private DeliveryPlanningService planningService;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends AbstractGridViewQuery> queryclass) throws Exception
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("salesOrders", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));
		map.put("filterCriteria", filterCriteria);

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	@InjectParty(keyName = "salesOrder_form")
	public FastMap<String, Object> preadd()
	{
		FastMap<String, Object> map = new FastMap<String, Object>();

		map.put("salesOrder_form", new SalesForm());
		map.put("taxes", genericDao.loadAll(Tax.class));
		map.put("twoMonth", DateHelper.plusDays(DateHelper.now(), 60));

		return map;
	}

	@AuditTrails(className = SalesOrder.class, actionType = AuditTrailsActionType.CREATE)
	public void add(SalesOrder salesOrder) throws Exception
	{
		SalesForm form = (SalesForm) salesOrder.getForm();

		if (salesOrder.getShippingAddress() == null)
			throw new ServiceException("Customer doesn't have Shipping Address, please set it first on customer page.");

		Money money = new Money();
		Currency currency = genericDao.load(Currency.class, 1L);
		money.setCurrency(currency);
		money.setAmount(form.getAmount());

		salesOrder.setCode(GeneratorHelper.instance().generate(TableType.SALES_ORDER, codeSequenceDao));
		salesOrder.setMoney(money);
		salesOrder.setSalesType(SalesType.STANDARD);

		if (form.getApprover() != null)
		{
			SalesOrderApprovableBridge approvableBridge = ApprovableBridgeHelper.create(SalesOrderApprovableBridge.class, salesOrder);
			approvableBridge.setApprovableType(ApprovableType.SALES_ORDER);
			approvableBridge.setUri("salesorderpreedit.htm");
			salesOrder.setApprovable(approvableBridge);
		}

		for (Item item : form.getItems())
		{
			if (item.getProduct() != null)
			{
				Money itemMoney = new Money();
				itemMoney.setAmount(item.getAmount());
				itemMoney.setCurrency(salesOrder.getMoney().getCurrency());

				SalesOrderItem salesOrderItem = new SalesOrderItem();
				salesOrderItem.setMoney(itemMoney);

				salesOrderItem.setProduct(item.getProduct());
				salesOrderItem.setQuantity(item.getQuantity());
				salesOrderItem.setDiscount(item.getDiscount());
				salesOrderItem.setNote(item.getNote());
				salesOrderItem.setSalesOrder(salesOrder);

				salesOrder.getItems().add(salesOrderItem);
			}
		}

		genericDao.add(salesOrder);

		if (form.getApprover() == null)
		{
			DeliveryPlanning deliveryPlanning = new DeliveryPlanning();
			deliveryPlanning.setDate(DateHelper.today());
			deliveryPlanning.setSalesOrder(salesOrder);

			planningService.add(deliveryPlanning);
		}
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(Long id) throws Exception
	{
		SalesForm salesForm = FormHelper.bind(SalesForm.class, genericDao.load(SalesOrder.class, id));
		SalesOrderAdapter adapter = new SalesOrderAdapter(salesForm.getSalesOrder());

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("salesOrder_form", salesForm);
		map.put("salesOrder_edit", salesForm.getSalesOrder());
		map.put("approvalDecisionStatuses", ApprovalDecisionStatus.values());
		map.put("approvalDecision", salesForm.getSalesOrder().getApprovable() != null ? salesForm.getSalesOrder().getApprovable().getApprovalDecision() : null);
		map.put("adapter", adapter);

		return map;
	}

	@AuditTrails(className = SalesOrder.class, actionType = AuditTrailsActionType.UPDATE)
	@AutomaticSibling(roles = "ApprovableSiblingRole")
	public void edit(SalesOrder salesOrder) throws Exception
	{
		genericDao.update(salesOrder);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public DeliveryOrderReferenceItem load(Long productId)
	{
		return salesReferenceItemDao.loadByProduct(productId);
	}

	@AuditTrails(className = SalesOrder.class, actionType = AuditTrailsActionType.UPDATE)
	public void close(Long id) throws ServiceException
	{
		SalesOrder salesOrder = genericDao.load(SalesOrder.class, id);
		salesOrder.setSoStatus(SOStatus.CLOSE);

		genericDao.update(salesOrder);
	}
}

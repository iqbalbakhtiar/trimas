package com.siriuserp.sales.service;

import com.siriuserp.sdk.utility.DateHelper;
import com.siriuserp.sdk.utility.FormHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.sales.dm.SalesOrder;
import com.siriuserp.sales.dm.SalesOrderItem;
import com.siriuserp.sales.dm.SalesType;
import com.siriuserp.sales.form.SalesForm;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.annotation.InjectParty;
import com.siriuserp.sdk.base.Service;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.Item;
import com.siriuserp.sdk.dm.Money;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.dm.Tax;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.QueryFactory;

import javolution.util.FastMap;

@Component
@Transactional(rollbackFor = Exception.class)
public class SalesOrderService extends Service {
	
	@Autowired
	private GenericDao genericDao;
	
	@Autowired
	private CodeSequenceDao codeSequenceDao;
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends AbstractGridViewQuery> queryclass) throws Exception {
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

		return map;
	}
	
	@AuditTrails(className = SalesOrder.class, actionType = AuditTrailsActionType.CREATE)
	public FastMap<String, Object> add(SalesOrder salesOrder) throws Exception {
		SalesForm form = (SalesForm) salesOrder.getForm();

		Money moneySalesOrder = new Money();
		Currency currencyIdr = genericDao.load(Currency.class, 1L);
		moneySalesOrder.setCurrency(currencyIdr);
		moneySalesOrder.setAmount(form.getAmount());
		
		salesOrder.setCode(GeneratorHelper.instance().generate(TableType.SALES_ORDER, codeSequenceDao));
		salesOrder.setMoney(moneySalesOrder);
		salesOrder.setSalesType(SalesType.STANDARD);
		salesOrder.setCreatedBy(getPerson());
		
		for (Item item : form.getItems()) {
			SalesOrderItem salesOrderItem = new SalesOrderItem();
			Money money = new Money();
			
	        money.setAmount(item.getAmount());
	        money.setCurrency(genericDao.load(Currency.class, 1L));
			
			salesOrderItem.setProduct(item.getProduct());
			salesOrderItem.setQuantity(item.getQuantity());
			salesOrderItem.setMoney(money);
			salesOrderItem.setDiscount(item.getDiscount());
			salesOrderItem.setNote(item.getNote());
			salesOrderItem.setSalesType(SalesType.STANDARD);
			salesOrderItem.setOrganization(salesOrder.getOrganization());
			salesOrderItem.setCustomer(salesOrder.getCustomer());
			salesOrderItem.setApprover(salesOrder.getApprover());
			salesOrderItem.setFacility(salesOrder.getFacility());
			salesOrderItem.setShippingAddress(salesOrder.getShippingAddress());
			salesOrderItem.setTax(salesOrder.getTax());
			salesOrderItem.setCreatedBy(getPerson());
			salesOrderItem.setSalesOrder(salesOrder);
			
			salesOrder.getItems().add(salesOrderItem);
		}
		
		genericDao.add(salesOrder);
		
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("id", salesOrder.getId());
		
		return map;
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(Long id) throws Exception {
		SalesOrder salesOrder = genericDao.load(SalesOrder.class, id);
		SalesForm salesForm = FormHelper.bind(SalesForm.class, salesOrder);

		salesForm.setSalesOrder(salesOrder);
		
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("salesOrder_form", salesForm);
		
		return map;
	}

	@AuditTrails(className = SalesOrder.class, actionType = AuditTrailsActionType.UPDATE)
	public FastMap<String, Object> edit(SalesOrder salesOrder) throws Exception {
		salesOrder.setUpdatedBy(getPerson());
		salesOrder.setUpdatedDate(DateHelper.now());

		genericDao.update(salesOrder);

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("id", salesOrder.getId());

		return map;
	}
}

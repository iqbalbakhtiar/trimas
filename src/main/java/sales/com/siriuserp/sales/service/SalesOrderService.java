package com.siriuserp.sales.service;

import com.siriuserp.sales.dm.*;
import com.siriuserp.sdk.annotation.AutomaticSibling;
import com.siriuserp.sdk.dao.CreditTermDao;
import com.siriuserp.sdk.dao.PartyRelationshipDao;
import com.siriuserp.sdk.dm.*;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.utility.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.sales.form.SalesForm;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.annotation.InjectParty;
import com.siriuserp.sdk.base.Service;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;

import javolution.util.FastMap;

@Component
@Transactional(rollbackFor = Exception.class)
public class SalesOrderService extends Service {
	
	@Autowired
	private GenericDao genericDao;
	
	@Autowired
	private CodeSequenceDao codeSequenceDao;

	@Autowired
	private PartyRelationshipDao partyRelationshipDao;

	@Autowired
	private CreditTermDao creditTermDao;
	
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

		// Credit Term harus didapatkan dari party relation ship
		PartyRelationship relationship = partyRelationshipDao.load(salesOrder.getCustomer().getId(), salesOrder.getOrganization().getId(), PartyRelationshipType.CUSTOMER_RELATIONSHIP);
		CreditTerm creditTerm = creditTermDao.loadByRelationship(relationship.getId(), true, salesOrder.getDate());
		if (creditTerm == null)
			throw new ServiceException("Customer doesn't have active Credit Term, please set it first on customer page.");

		salesOrder.setCreditTerm(creditTerm);

		//Add ApprovableBridge using Helper
		SalesOrderApprovableBridge approvableBridge = ApprovableBridgeHelper.create(SalesOrderApprovableBridge.class, salesOrder);
		approvableBridge.setApprovableType(ApprovableType.SALES_ORDER);
		approvableBridge.setUri("salesorderpreedit.htm");
		salesOrder.setApprovable(approvableBridge);

		genericDao.add(salesOrder);

		// Add every Sales Order Item
		for (Item item : form.getItems()) {
			if (item.getProduct() != null) { // Untuk menghindari bug line item kosong
				SalesOrderItem salesOrderItem = new SalesOrderItem();
				Money money = new Money();

				money.setAmount(item.getAmount());
				money.setCurrency(genericDao.load(Currency.class, 1L));

				salesOrderItem.setDate(salesOrder.getDate());
				salesOrderItem.setReferenceId(salesOrder.getId());
				salesOrderItem.setReferenceCode(salesOrder.getCode());
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
				salesOrderItem.setTerm(salesOrder.getCreditTerm().getTerm());

				genericDao.add(salesOrderItem);
			}
		}

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
		map.put("approvalDecisionStatuses", ApprovalDecisionStatus.values());
		map.put("approvalDecision", salesForm.getSalesOrder().getApprovable().getApprovalDecision());

		return map;
	}

	@AutomaticSibling(roles = "ApprovableSiblingRole")
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

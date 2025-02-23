/**
 * File Name  : StandardPurchaseOrderService.java
 * Created On : Feb 22, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.procurement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.inventory.dm.WarehouseTransactionItem;
import com.siriuserp.inventory.dm.WarehouseTransactionType;
import com.siriuserp.procurement.dm.POStatus;
import com.siriuserp.procurement.dm.PurchaseOrder;
import com.siriuserp.procurement.dm.PurchaseOrderApprovableBridge;
import com.siriuserp.procurement.dm.PurchaseOrderItem;
import com.siriuserp.procurement.dm.PurchaseRequisitionItem;
import com.siriuserp.procurement.dm.PurchaseType;
import com.siriuserp.procurement.form.PurchaseForm;
import com.siriuserp.sales.dm.ApprovableType;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.annotation.InjectParty;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.CurrencyDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.Item;
import com.siriuserp.sdk.dm.Money;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.dm.Tax;
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
public class StandardPurchaseOrderService
{
	@Autowired
	private GenericDao genericDao;

	@Autowired
	private CodeSequenceDao codeSequenceDao;

	@Autowired
	private CurrencyDao currencyDao;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends AbstractGridViewQuery> queryclass) throws Exception
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("requisitions", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));
		map.put("filterCriteria", filterCriteria);

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	@InjectParty(keyName = "purchase_form")
	public FastMap<String, Object> preadd() throws Exception
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("purchase_form", new PurchaseForm());
		map.put("taxes", genericDao.loadAll(Tax.class));
		map.put("facilities", genericDao.loadAll(Facility.class));

		return map;
	}

	@AuditTrails(className = PurchaseOrder.class, actionType = AuditTrailsActionType.CREATE)
	public void add(PurchaseOrder purchaseOrder) throws Exception
	{
		PurchaseForm form = (PurchaseForm) purchaseOrder.getForm();
		purchaseOrder.setMoney(new Money());
		purchaseOrder.getMoney().setAmount(form.getAmount());
		purchaseOrder.getMoney().setCurrency(currencyDao.loadDefaultCurrency());
		purchaseOrder.setCode(GeneratorHelper.instance().generate(TableType.STANDARD_PURCHASE_ORDER, codeSequenceDao));
		purchaseOrder.setShippingDate(form.getDeliveryDate());
		purchaseOrder.setPurchaseType(PurchaseType.STANDARD);
		purchaseOrder.setStatus(POStatus.OPEN);

		//Add ApprovableBridge using Helper when needed approval
		if (purchaseOrder.getApprover() != null)
		{
			PurchaseOrderApprovableBridge approvableBridge = ApprovableBridgeHelper.create(PurchaseOrderApprovableBridge.class, purchaseOrder);
			approvableBridge.setApprovableType(ApprovableType.PURCHASE_ORDER);
			approvableBridge.setUri("standardpurchaseorderpreedit.htm");
			purchaseOrder.setApprovable(approvableBridge);
		}

		for (Item item : form.getItems())
		{
			if (item.getProduct() != null)
			{
				PurchaseRequisitionItem requisitionItem = genericDao.load(PurchaseRequisitionItem.class, item.getReference());
				requisitionItem.setAvailable(false);
				genericDao.update(requisitionItem);

				PurchaseOrderItem purchaseItem = new PurchaseOrderItem();
				purchaseItem.setRequisitionItem(requisitionItem);
				purchaseItem.setProduct(item.getProduct());
				purchaseItem.setQuantity(item.getQuantity());
				purchaseItem.getMoney().setAmount(item.getAmount());
				purchaseItem.getMoney().setCurrency(currencyDao.loadDefaultCurrency());
				purchaseItem.setNote(item.getNote());

				// Set Transaction Item using helper & set locked to true
				WarehouseTransactionItem warehouseTransactionItem = ReferenceItemHelper.init(genericDao, item.getQuantity(), WarehouseTransactionType.IN, purchaseItem);
				warehouseTransactionItem.setLocked(true);
				purchaseItem.setTransactionItem(warehouseTransactionItem);

				purchaseOrder.getItems().add(purchaseItem);
			}
		}

		genericDao.add(purchaseOrder);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(Long id) throws Exception
	{
		PurchaseForm purchaseForm = FormHelper.bind(PurchaseForm.class, load(id));

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("purchase_form", purchaseForm);
		map.put("purchase_edit", purchaseForm.getPurchaseOrder());

		return map;
	}

	@AuditTrails(className = PurchaseOrder.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(PurchaseOrder purchaseOrder) throws Exception
	{
		genericDao.update(purchaseOrder);
	}

	@AuditTrails(className = PurchaseOrder.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(PurchaseOrder purchaseOrder) throws ServiceException
	{
		genericDao.delete(purchaseOrder);
	}

	@Transactional(readOnly = false)
	public PurchaseOrder load(Long id)
	{
		return genericDao.load(PurchaseOrder.class, id);
	}
}

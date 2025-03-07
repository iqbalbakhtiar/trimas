/**
 * File Name  : StandardPurchaseOrderService.java
 * Created On : Feb 22, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.procurement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.accountpayable.dm.InvoiceVerification;
import com.siriuserp.accountpayable.dm.InvoiceVerificationItem;
import com.siriuserp.accountpayable.dm.InvoiceVerificationReferenceItem;
import com.siriuserp.accountpayable.dm.InvoiceVerificationReferenceHelper;
import com.siriuserp.accountpayable.dm.InvoiceVerificationReferenceType;
import com.siriuserp.accountpayable.service.InvoiceVerificationService;
import com.siriuserp.inventory.dm.WarehouseTransactionItem;
import com.siriuserp.inventory.dm.WarehouseTransactionSource;
import com.siriuserp.inventory.dm.WarehouseTransactionType;
import com.siriuserp.inventory.form.InventoryForm;
import com.siriuserp.procurement.adapter.PurchaseOrderAdapter;
import com.siriuserp.procurement.dm.POStatus;
import com.siriuserp.procurement.dm.PurchaseOrder;
import com.siriuserp.procurement.dm.PurchaseOrderApprovableBridge;
import com.siriuserp.procurement.dm.PurchaseOrderItem;
import com.siriuserp.procurement.dm.PurchaseOrderItemType;
import com.siriuserp.procurement.dm.PurchaseRequisitionItem;
import com.siriuserp.procurement.dm.PurchaseType;
import com.siriuserp.procurement.form.PurchaseForm;
import com.siriuserp.sales.dm.ApprovableType;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.annotation.AutomaticSibling;
import com.siriuserp.sdk.annotation.InjectParty;
import com.siriuserp.sdk.base.Service;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.CurrencyDao;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.dm.ApprovalDecisionStatus;
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
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastMap;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class StandardPurchaseOrderService extends Service
{
	@Autowired
	private CodeSequenceDao codeSequenceDao;

	@Autowired
	private CurrencyDao currencyDao;

	@Autowired
	private InvoiceVerificationService invoiceVerificationService;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends AbstractGridViewQuery> queryclass) throws Exception
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("purchaseOrders", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));
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
		purchaseOrder.setInvoiceBeforeReceipt(true);

		//Add ApprovableBridge using Helper when needed approval
		if (purchaseOrder.getApprover() != null)
		{
			PurchaseOrderApprovableBridge approvableBridge = ApprovableBridgeHelper.create(PurchaseOrderApprovableBridge.class, purchaseOrder);
			approvableBridge.setApprovableType(ApprovableType.PURCHASE_ORDER);
			approvableBridge.setUri("standardpurchaseorderpreedit.htm");
			purchaseOrder.setApprovable(approvableBridge);
		}

		boolean barcode = false;
		for (Item item : form.getItems())
		{
			if (item.getReference() != null)
			{
				PurchaseRequisitionItem requisitionItem = genericDao.load(PurchaseRequisitionItem.class, item.getReference());
				requisitionItem.setAvailable(false);
				genericDao.update(requisitionItem);

				PurchaseOrderItem purchaseItem = new PurchaseOrderItem();
				purchaseItem.setRequisitionItem(requisitionItem);
				purchaseItem.setProduct(requisitionItem.getProduct());
				purchaseItem.setQuantity(item.getQuantity());
				purchaseItem.setDiscount(item.getDiscount());
				purchaseItem.getMoney().setAmount(item.getAmount());
				purchaseItem.getMoney().setCurrency(currencyDao.loadDefaultCurrency());
				purchaseItem.setNote(item.getNote());
				purchaseItem.setFacilityDestination(purchaseOrder.getShipTo());
				purchaseItem.setPurchaseOrder(purchaseOrder);
				purchaseItem.setTransactionSource(WarehouseTransactionSource.STANDARD_PURCHASE_ORDER);

				if (!purchaseItem.getProduct().isSerial())
				{
					WarehouseTransactionItem warehouseTransactionItem = ReferenceItemHelper.init(genericDao, item.getQuantity(), WarehouseTransactionType.IN, purchaseItem);
					if (purchaseOrder.getApprover() != null)
						warehouseTransactionItem.setLocked(true);
					else
						warehouseTransactionItem.setLocked(false);

					purchaseItem.setTransactionItem(warehouseTransactionItem);
				}

				purchaseOrder.getItems().add(purchaseItem);

				if (purchaseItem.getProduct().isSerial())
					barcode = true;
			}
		}

		if (barcode)
			purchaseOrder.setStatus(POStatus.BARCODE);

		genericDao.add(purchaseOrder);

		if (purchaseOrder.getApprovable() == null && purchaseOrder.isInvoiceBeforeReceipt())
			createInvoice(purchaseOrder);
	}

	@AuditTrails(className = PurchaseOrderItem.class, actionType = AuditTrailsActionType.CREATE)
	public void addItem(Long purchaseOrderId, List<Item> items) throws Exception
	{
		PurchaseOrder purchaseOrder = load(purchaseOrderId);

		for (Item item : items)
		{
			if (item.getProduct() != null && SiriusValidator.validateParam(item.getReference()))
			{
				PurchaseOrderItem purchaseItem = new PurchaseOrderItem();
				purchaseItem.setItemParent(genericDao.load(PurchaseOrderItem.class, item.getReference()));
				purchaseItem.setPurchaseItemType(PurchaseOrderItemType.SERIAL);
				purchaseItem.getLot().setSerial(item.getSerial());
				purchaseItem.setProduct(item.getProduct());
				purchaseItem.setQuantity(item.getQuantityReal());
				purchaseItem.getMoney().setAmount(purchaseItem.getItemParent().getMoney().getAmount());
				purchaseItem.getMoney().setCurrency(purchaseItem.getItemParent().getMoney().getCurrency());
				purchaseItem.setFacilityDestination(purchaseOrder.getShipTo());
				purchaseItem.setPurchaseOrder(purchaseOrder);

				if (purchaseOrder.getPurchaseType().equals(PurchaseType.STANDARD))
					purchaseItem.setTransactionSource(WarehouseTransactionSource.STANDARD_PURCHASE_ORDER);
				else
					purchaseItem.setTransactionSource(WarehouseTransactionSource.DIRECT_PURCHASE_ORDER);

				WarehouseTransactionItem warehouseTransactionItem = ReferenceItemHelper.init(genericDao, purchaseItem.getQuantity(), WarehouseTransactionType.IN, purchaseItem);
				warehouseTransactionItem.setLocked(false);

				purchaseItem.setTransactionItem(warehouseTransactionItem);

				purchaseOrder.getItems().add(purchaseItem);
			}
		}

		purchaseOrder.setStatus(POStatus.OPEN);
		genericDao.update(purchaseOrder);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(Long id) throws Exception
	{
		PurchaseForm purchaseForm = FormHelper.bind(PurchaseForm.class, load(id));

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("purchase_form", purchaseForm);
		map.put("purchase_edit", purchaseForm.getPurchaseOrder());
		map.put("adapter", new PurchaseOrderAdapter(purchaseForm.getPurchaseOrder()));
		map.put("approvalDecisionStatuses", ApprovalDecisionStatus.values());
		map.put("approvalDecision", purchaseForm.getPurchaseOrder().getApprovable() != null ? purchaseForm.getPurchaseOrder().getApprovable().getApprovalDecision() : null);

		return map;
	}

	@AutomaticSibling(roles = "ApprovableSiblingRole")
	@AuditTrails(className = PurchaseOrder.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(PurchaseOrder purchaseOrder) throws Exception
	{
		genericDao.update(purchaseOrder);
	}

	@AuditTrails(className = PurchaseOrder.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(PurchaseOrder purchaseOrder) throws ServiceException
	{
		for (PurchaseOrderItem item : purchaseOrder.getItems())
		{
			PurchaseRequisitionItem requisitionItem = genericDao.load(PurchaseRequisitionItem.class, item.getRequisitionItem().getId());
			requisitionItem.setAvailable(true);
			genericDao.update(requisitionItem);
		}

		genericDao.delete(purchaseOrder);
	}

	@Transactional(readOnly = false)
	public PurchaseOrder load(Long id)
	{
		return genericDao.load(PurchaseOrder.class, id);
	}

	@AuditTrails(className = InvoiceVerification.class, actionType = AuditTrailsActionType.CREATE)
	public void createInvoice(PurchaseOrder purchaseOrder) throws Exception
	{
		InventoryForm form = new InventoryForm();
		InvoiceVerification invoiceVerification = new InvoiceVerification();
		invoiceVerification.setForm(form);
		invoiceVerification.setDate(purchaseOrder.getDate());
		invoiceVerification.setOrganization(purchaseOrder.getOrganization());
		invoiceVerification.setMoney(new Money());
		invoiceVerification.setSupplier(purchaseOrder.getSupplier());
		invoiceVerification.setTax(purchaseOrder.getTax());
		invoiceVerification.getMoney().setCurrency(purchaseOrder.getMoney().getCurrency());

		for (PurchaseOrderItem purchaseItem : purchaseOrder.getItems())
		{
			InvoiceVerificationReferenceItem invoiceReference = new InvoiceVerificationReferenceItem();
			invoiceReference.setCode(purchaseOrder.getCode());
			invoiceReference.setDate(purchaseOrder.getDate());
			invoiceReference.setOrganization(purchaseOrder.getOrganization());
			invoiceReference.setFacility(purchaseOrder.getFacility());
			invoiceReference.setSupplier(purchaseOrder.getSupplier());
			invoiceReference.setPurchaseOrderItem(purchaseItem);
			invoiceReference.setVerificated(true);
			invoiceReference.setReferenceType(InvoiceVerificationReferenceType.PURCHASE_ORDER);
			genericDao.add(invoiceReference);

			InvoiceVerificationItem invoiceItem = InvoiceVerificationReferenceHelper.initItem(invoiceReference);
			invoiceItem.setInvoiceVerification(invoiceVerification);

			Item item = new Item();
			item.setInvoiceVerificationItem(invoiceItem);
			form.getItems().add(item);

			invoiceVerification.getItems().add(invoiceItem);
		}

		invoiceVerification.setUnpaid(purchaseOrder.getMoney().getAmount());
		invoiceVerification.getMoney().setAmount(invoiceVerification.getUnpaid());

		form.setInvoiceVerification(invoiceVerification);
		form.setPurchaseOrder(purchaseOrder);

		invoiceVerificationService.add(form.getInvoiceVerification());
	}
}

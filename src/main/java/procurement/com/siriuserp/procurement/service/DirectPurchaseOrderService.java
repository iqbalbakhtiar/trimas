package com.siriuserp.procurement.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.accountpayable.dm.InvoiceVerification;
import com.siriuserp.accountpayable.dm.InvoiceVerificationItem;
import com.siriuserp.accountpayable.dm.InvoiceVerificationReferenceItem;
import com.siriuserp.accountpayable.dm.InvoiceVerificationReferenceType;
import com.siriuserp.accountpayable.service.InvoiceVerificationService;
import com.siriuserp.accountpayable.util.InvoiceVerificationReferenceUtil;
import com.siriuserp.inventory.dm.WarehouseTransactionItem;
import com.siriuserp.inventory.dm.WarehouseTransactionSource;
import com.siriuserp.inventory.dm.WarehouseTransactionType;
import com.siriuserp.inventory.form.InventoryForm;
import com.siriuserp.procurement.adapter.PurchaseOrderAdapter;
import com.siriuserp.procurement.dm.POStatus;
import com.siriuserp.procurement.dm.PurchaseOrder;
import com.siriuserp.procurement.dm.PurchaseOrderApprovableBridge;
import com.siriuserp.procurement.dm.PurchaseOrderItem;
import com.siriuserp.procurement.dm.PurchaseType;
import com.siriuserp.procurement.form.PurchaseForm;
import com.siriuserp.procurement.interceptor.PurchaseOrderApprovableInterceptor;
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
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.ApprovableBridgeHelper;
import com.siriuserp.sdk.utility.DateHelper;
import com.siriuserp.sdk.utility.FormHelper;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.QueryFactory;
import com.siriuserp.sdk.utility.ReferenceItemHelper;
import com.siriuserp.tools.sibling.ApprovableSiblingRole;

import javolution.util.FastMap;

@Component
@Transactional(rollbackFor = Exception.class)
public class DirectPurchaseOrderService extends Service
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
		map.put("dpos", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));
		map.put("filterCriteria", filterCriteria);
		map.put("approvableDecisionStat", ApprovalDecisionStatus.values());

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	@InjectParty(keyName = "dpo_form")
	public FastMap<String, Object> preadd()
	{
		List<Facility> facilities = genericDao.loadAll(Facility.class);

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("dpo_form", new PurchaseForm());
		map.put("taxes", genericDao.loadAll(Tax.class));
		map.put("facilities", facilities);

		return map;
	}

	@AuditTrails(className = PurchaseOrder.class, actionType = AuditTrailsActionType.CREATE)
	public void add(PurchaseOrder purchaseOrder) throws Exception
	{
		PurchaseForm form = (PurchaseForm) purchaseOrder.getForm();

		purchaseOrder.setMoney(new Money());
		purchaseOrder.getMoney().setAmount(form.getAmount());
		purchaseOrder.getMoney().setCurrency(currencyDao.loadDefaultCurrency());
		purchaseOrder.setCode(GeneratorHelper.instance().generate(TableType.DIRECT_PURCHASE_ORDER, codeSequenceDao));
		purchaseOrder.setShippingDate(form.getDeliveryDate());
		purchaseOrder.setPurchaseType(PurchaseType.DIRECT);
		purchaseOrder.setStatus(POStatus.OPEN);
		purchaseOrder.setInvoiceBeforeReceipt(true);
		purchaseOrder.setUri("directpurchaseorderpreedit.htm");

		//Add ApprovableBridge using Helper when needed approval
		if (purchaseOrder.getApprover() != null)
		{
			PurchaseOrderApprovableBridge approvableBridge = ApprovableBridgeHelper.create(PurchaseOrderApprovableBridge.class, purchaseOrder);
			approvableBridge.setApprovableType(ApprovableType.PURCHASE_ORDER);
			approvableBridge.setUri("directpurchaseorderpreedit.htm");
			purchaseOrder.setApprovable(approvableBridge);
		}

		boolean barcode = false;
		for (Item item : form.getItems())
		{
			if (item.getProduct() != null)
			{
				PurchaseOrderItem purchaseItem = new PurchaseOrderItem();
				purchaseItem.setProduct(item.getProduct());
				purchaseItem.setQuantity(item.getQuantity());
				purchaseItem.setDiscount(item.getDiscount());
				purchaseItem.getMoney().setAmount(item.getAmount());
				purchaseItem.getMoney().setCurrency(currencyDao.loadDefaultCurrency());
				purchaseItem.setNote(item.getNote());
				purchaseItem.setFacilityDestination(purchaseOrder.getShipTo());
				purchaseItem.setPurchaseOrder(purchaseOrder);
				purchaseItem.setTransactionSource(WarehouseTransactionSource.DIRECT_PURCHASE_ORDER);

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

		System.out.println(purchaseOrder.getSupplierPhone().getContact());
		
		genericDao.add(purchaseOrder);

		if (purchaseOrder.getApprovable() == null && purchaseOrder.isInvoiceBeforeReceipt())
			createInvoice(purchaseOrder);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(Long id) throws Exception
	{
		PurchaseOrder purchaseOrder = genericDao.load(PurchaseOrder.class, id);
		PurchaseForm purchaseForm = FormHelper.bind(PurchaseForm.class, purchaseOrder);
		PurchaseOrderAdapter adapter = new PurchaseOrderAdapter(purchaseOrder);

		purchaseForm.setPurchaseOrder(purchaseOrder);

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("dpo_form", purchaseForm);
		map.put("dpo_edit", purchaseForm.getPurchaseOrder());
		map.put("approvalDecisionStatuses", ApprovalDecisionStatus.values());
		map.put("approvalDecision", purchaseForm.getPurchaseOrder().getApprovable() != null ? purchaseForm.getPurchaseOrder().getApprovable().getApprovalDecision() : null);
		map.put("adapter", adapter);

		return map;
	}

	/**
	 * Fungsi ini bisa sebagai update data PO atau bisa sebagai approve PO
	 * Setelah fungsi ini selesai akan execute kelas {@link ApprovableSiblingRole}
	 * Kemudian eksekusi {@link PurchaseOrderApprovableInterceptor}
	 *
	 * @param purchaseOrder dari halaman preedit
	 * @return id PO
	 */
	@AutomaticSibling(roles = "ApprovableSiblingRole")
	@AuditTrails(className = PurchaseOrder.class, actionType = AuditTrailsActionType.UPDATE)
	public FastMap<String, Object> edit(PurchaseOrder purchaseOrder) throws Exception
	{
		purchaseOrder.setUpdatedBy(getPerson());
		purchaseOrder.setUpdatedDate(DateHelper.now());

		genericDao.update(purchaseOrder);

		for (Item item : purchaseOrder.getForm().getItems()) {
			if (item.getReference() != null) {
				PurchaseOrderItem poi = genericDao.load(PurchaseOrderItem.class, item.getReference());
				purchaseOrder.getItems().remove(poi);
				genericDao.delete(poi);
			}
		}

		if (purchaseOrder.getStatus() == POStatus.CLOSED) {
			// Ambil invoice verifications yang terkait dari PurchaseOrder
			Set<InvoiceVerification> invoiceVerifs = purchaseOrder.getInvoiceVerifications();
			for (InvoiceVerification inv : invoiceVerifs) {
				// Hapus data invoice verification
				genericDao.delete(inv);
			}
		}
		
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("id", purchaseOrder.getId());

		return map;
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

			InvoiceVerificationItem invoiceItem = InvoiceVerificationReferenceUtil.initItem(invoiceReference);
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

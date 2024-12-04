package com.siriuserp.procurement.service;

import com.siriuserp.inventory.dm.WarehouseTransactionItem;
import com.siriuserp.inventory.dm.WarehouseTransactionType;
import com.siriuserp.procurement.adapter.PurchaseOrderAdapter;
import com.siriuserp.procurement.dm.*;
import com.siriuserp.procurement.form.PurchaseForm;
import com.siriuserp.procurement.interceptor.PurchaseOrderApprovableInterceptor;
import com.siriuserp.sales.dm.ApprovableType;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.annotation.AutomaticSibling;
import com.siriuserp.sdk.annotation.InjectParty;
import com.siriuserp.sdk.base.Service;
import com.siriuserp.sdk.dao.*;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.dm.*;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.*;
import com.siriuserp.tools.sibling.ApprovableSiblingRole;
import javolution.util.FastMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional(rollbackFor = Exception.class)
public class DirectPurchaseOrderService extends Service {

    @Autowired
    private GenericDao genericDao;

    @Autowired
    private CodeSequenceDao codeSequenceDao;

    @Autowired
    private PartyRelationshipDao partyRelationshipDao;

    @Autowired
    private CreditTermDao creditTermDao;

    @Autowired
    private CurrencyDao currencyDao;

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends AbstractGridViewQuery> queryclass) throws Exception {
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
		FastMap<String, Object> map = new FastMap<String, Object>();

        List<Facility> facilities = genericDao.loadAll(Facility.class);

		map.put("dpo_form", new PurchaseForm());
        map.put("taxes", genericDao.loadAll(Tax.class));
        map.put("facilities", facilities);

		return map;
	}

    @AuditTrails(className = PurchaseOrder.class, actionType = AuditTrailsActionType.CREATE)
    public FastMap<String, Object> add(PurchaseOrder purchaseOrder) throws Exception {
        PurchaseForm form = (PurchaseForm) purchaseOrder.getForm();

        purchaseOrder.setMoney(new Money());
        purchaseOrder.getMoney().setAmount(form.getAmount());
        purchaseOrder.getMoney().setCurrency(currencyDao.loadDefaultCurrency());

        purchaseOrder.setCode(GeneratorHelper.instance().generate(TableType.DIRECT_PURCHASE_ORDER, codeSequenceDao));
        purchaseOrder.setShippingDate(form.getDeliveryDate());
        purchaseOrder.setPurchaseType(PurchaseType.DIRECT);
        purchaseOrder.setStatus(POStatus.OPEN);

        // Credit Term harus didapatkan dari party relationship
		PartyRelationship relationship = partyRelationshipDao.load(purchaseOrder.getSupplier().getId(), purchaseOrder.getOrganization().getId(), PartyRelationshipType.SUPPLIER_RELATIONSHIP);
		CreditTerm creditTerm = creditTermDao.loadByRelationship(relationship.getId(), true, purchaseOrder.getDate());
		if (creditTerm == null)
			throw new ServiceException("Supplier doesn't have active Credit Term, please set it first on supplier page.");
        purchaseOrder.setCreditTerm(creditTerm);

        //Add ApprovableBridge using Helper
        PurchaseOrderApprovableBridge approvableBridge = ApprovableBridgeHelper.create(PurchaseOrderApprovableBridge.class, purchaseOrder);
        approvableBridge.setApprovableType(ApprovableType.PURCHASE_ORDER);
        approvableBridge.setUri("directpurchaseorderpreedit.htm");
        purchaseOrder.setApprovable(approvableBridge);

        genericDao.add(purchaseOrder);

        for (Item item: form.getItems()) {
            if (item.getProduct() != null) {
                PurchaseOrderItem orderItem = new PurchaseOrderItem();

                // Set Puchase Order Item
                orderItem.setDeliveryDate(purchaseOrder.getShippingDate());
                orderItem.setQuantity(item.getQuantity());
                orderItem.setDiscount(item.getDiscount());
                orderItem.setPurchaseOrder(purchaseOrder);
                orderItem.setLocked(true);

                // Set Warehouse Reference Item
                orderItem.setDate(purchaseOrder.getDate());
                orderItem.setReferenceId(purchaseOrder.getId());
                orderItem.setReferenceCode(purchaseOrder.getCode());
                orderItem.setNote(item.getNote());
                orderItem.setReferenceFrom(purchaseOrder.getSupplier().getFullName());
                orderItem.setReferenceTo(purchaseOrder.getShipTo().getName());
                orderItem.setMoney(new Money());
                orderItem.getMoney().setAmount(item.getAmount());
                orderItem.getMoney().setCurrency(currencyDao.loadDefaultCurrency());
                orderItem.setOrganization(purchaseOrder.getOrganization());
                orderItem.setFacilityDestination(purchaseOrder.getShipTo());
                orderItem.setProduct(item.getProduct());
                orderItem.setTax(purchaseOrder.getTax());
                orderItem.setCreatedBy(getPerson());
                orderItem.setCreatedDate(DateHelper.now());
                orderItem.setParty(purchaseOrder.getSupplier());
                // Set Transaction Item using helper & set locked to true
                WarehouseTransactionItem warehouseTransactionItem = ReferenceItemHelper.init(genericDao, item.getQuantity(), WarehouseTransactionType.IN, orderItem);
                warehouseTransactionItem.setLocked(true);
                orderItem.setTransactionItem(warehouseTransactionItem);

                genericDao.add(orderItem);
            }
        }

        FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("id", purchaseOrder.getId());

		return map;
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(Long id) throws Exception {
		PurchaseOrder purchaseOrder = genericDao.load(PurchaseOrder.class, id);
		PurchaseForm purchaseForm = FormHelper.bind(PurchaseForm.class, purchaseOrder);
        PurchaseOrderAdapter adapter = new PurchaseOrderAdapter(purchaseOrder);

		purchaseForm.setPurchaseOrder(purchaseOrder);

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("dpo_form", purchaseForm);
		map.put("approvalDecisionStatuses", ApprovalDecisionStatus.values());
		map.put("approvalDecision", purchaseForm.getPurchaseOrder().getApprovable().getApprovalDecision());
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
	public FastMap<String, Object> edit(PurchaseOrder purchaseOrder) throws Exception {
		purchaseOrder.setUpdatedBy(getPerson());
		purchaseOrder.setUpdatedDate(DateHelper.now());

		genericDao.update(purchaseOrder);

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("id", purchaseOrder.getId());

		return map;
	}
}

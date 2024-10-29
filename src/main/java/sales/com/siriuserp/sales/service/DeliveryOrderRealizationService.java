package com.siriuserp.sales.service;

import com.siriuserp.accounting.dm.*;
import com.siriuserp.accounting.form.AccountingForm;
import com.siriuserp.accounting.service.BillingService;
import com.siriuserp.sales.dm.*;
import com.siriuserp.sales.form.DeliveryOrderForm;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.base.Service;
import com.siriuserp.sdk.dao.*;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.dm.*;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.*;
import javolution.util.FastMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @author Rama Almer Felix
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class DeliveryOrderRealizationService extends Service {

    @Autowired
    private GenericDao genericDao;

    @Autowired
    private CurrencyDao currencyDao;

    @Autowired
    private BillingService billingService;

    @Autowired
    private PostalAddressDao postalAddressDao;

    @Autowired
    private CodeSequenceDao codeSequenceDao;

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends AbstractGridViewQuery> queryclass) throws Exception {
        FastMap<String, Object> map = new FastMap<String, Object>();
        map.put("dors", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));
        map.put("filterCriteria", filterCriteria);

        return map;
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preadd2(Long id) {
		DeliveryOrder deliveryOrder = genericDao.load(DeliveryOrder.class, id);
		DeliveryOrderForm form = new DeliveryOrderForm();

		for (DeliveryOrderItem doItem: deliveryOrder.getItems()) {
            Item item = new Item();
            item.setSalesReferenceItem(doItem.getSalesReferenceItem());

			form.getItems().add(item);
		}

		form.setDeliveryOrder(deliveryOrder);

        FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("dor_form", form);

		return map;
	}

    @AuditTrails(className = DeliveryOrderRealization.class, actionType = AuditTrailsActionType.CREATE)
    public FastMap<String, Object> add(DeliveryOrderRealization dor) throws Exception {
        DeliveryOrderForm form = (DeliveryOrderForm) dor.getForm();

        // Set DOR Value
        dor.setCode(GeneratorHelper.instance().generate(TableType.DELIVERY_ORDER_REALIZATION, codeSequenceDao));
        dor.setOrganization(form.getDeliveryOrder().getOrganization());
        dor.setFacility(form.getDeliveryOrder().getFacility());
        dor.setCustomer(form.getDeliveryOrder().getCustomer());

        genericDao.add(dor);

        for (Item item: form.getItems()) {
            // Set DOR Item
            DeliveryOrderRealizationItem dorItem = new DeliveryOrderRealizationItem();
            dorItem.setDeliveryOrderRealization(dor);
            dorItem.setDeliveryOrderItem(item.getSalesReferenceItem().getDeliveryOrderItem());
            dorItem.setAccepted(item.getAccepted());
            dorItem.setShrinkage(item.getShrinkage());

            // Set Warehouse Reference Value
            dorItem.setReferenceId(dor.getId());
            dorItem.setReferenceCode(dor.getCode());
            dorItem.setDate(dor.getDate());
            dorItem.setReferenceFrom(dor.getFacility().getName());
            dorItem.setReferenceTo(dor.getCustomer().getFullName());
            dorItem.setOrganization(dor.getOrganization());
            dorItem.setParty(dor.getCustomer());
            dorItem.setFacilitySource(dor.getFacility());
            dorItem.setProduct(item.getSalesReferenceItem().getProduct());
            dorItem.setTax(item.getSalesReferenceItem().getTax());
            dorItem.setCurrency(item.getSalesReferenceItem().getMoney().getCurrency());
            dorItem.setNote(item.getNote());

            genericDao.add(dorItem);

            dor.getItems().add(dorItem);
        }

        // Update DO Status
        form.getDeliveryOrder().setStatus(SOStatus.DELIVERED);
        form.getDeliveryOrder().setUpdatedBy(getPerson());
        form.getDeliveryOrder().setUpdatedDate(DateHelper.now());
        genericDao.update(form.getDeliveryOrder());

        // Auto Generate Billing after create DOR
        createBilling(dor);

        FastMap<String, Object> map = new FastMap<String, Object>();
        map.put("id", dor.getId());

        return map;
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public FastMap<String,Object> preedit(Long id) throws Exception {
        FastMap<String, Object> map = new FastMap<String, Object>();
        DeliveryOrderRealization deliveryOrderRealization = genericDao.load(DeliveryOrderRealization.class, id);
        DeliveryOrderForm form = FormHelper.bind(DeliveryOrderForm.class, deliveryOrderRealization);

        form.setDeliveryOrderRealization(deliveryOrderRealization);
        // Get Shipping Address from one of DO Item's
        for (DeliveryOrderRealizationItem item: deliveryOrderRealization.getItems()){
            if (item.getDeliveryOrderItem().getSalesReferenceItem().getShippingAddress() != null) {
                form.setShippingAddress(item.getDeliveryOrderItem().getSalesReferenceItem().getShippingAddress());
                break;
            }
        }
        // Get Delivery Order from one of DOR Items
        for (DeliveryOrderRealizationItem dorItem: deliveryOrderRealization.getItems()) {
            if (dorItem.getDeliveryOrderItem().getDeliveryOrder() != null) {
                form.setDeliveryOrder(dorItem.getDeliveryOrderItem().getDeliveryOrder());
                break;
            }
        }

        ObjectPrinter.printJson(deliveryOrderRealization);

        map.put("dor_form", form);

        return map;
    }

    @AuditTrails(className = DeliveryOrderRealization.class, actionType = AuditTrailsActionType.UPDATE)
    public void edit(DeliveryOrderForm form) throws Exception {
        form.getDeliveryOrderRealization().setUpdatedBy(getPerson());
        form.getDeliveryOrderRealization().setUpdatedDate(DateHelper.now());

        genericDao.update(form.getDeliveryOrderRealization()); // Update DOR Directly from JSP
    }

    @AuditTrails(className = BillingReferenceItem.class, actionType = AuditTrailsActionType.CREATE)
    public void createBilling(DeliveryOrderRealization dor) throws Exception {
        AccountingForm form = new AccountingForm();
        Billing billing = new Billing();
        billing.setMoney(new Money());
        billing.setForm(form);
        billing.setDate(dor.getDate());
        billing.setOrganization(dor.getOrganization());
        billing.setFacility(dor.getFacility()); // Note: Untuk sekarang Akan selalu Null Karena di DOR belum diset Facilitynya
        billing.setBillingType(genericDao.load(BillingType.class, BillingType.DELIVERY_ORDER_REALIZATION));
        billing.setNote("AUTO BILLING FROM DOR [" + dor.getCode() + "]");
        billing.getMoney().setCurrency(currencyDao.loadDefaultCurrency());
        Party customer = genericDao.load(Party.class, dor.getCustomer().getId()); // To Avoid LazyLoadingExecption no session
        billing.setCustomer(customer);
        // Set Billing Shipping Address
        for (DeliveryOrderRealizationItem item :dor.getItems()) {
            if (item.getDeliveryOrderItem().getDeliveryOrder().getShippingAddress() != null) {
                billing.setShippingAddress(item.getDeliveryOrderItem().getDeliveryOrder().getShippingAddress());
                break;
            } else {
                throw new ServiceException("Shipping Address on Delivery Order is not Found!");
            }
        }
        // Set Billing - Billing Address
        PostalAddress billingAddress = postalAddressDao.loadAddressByPartyAndType(dor.getCustomer().getId(), AddressType.OFFICE);
        if (billingAddress == null) {
            throw new ServiceException("Customer doesn't have address type OFFICE, please set it first on customer page.");
        } else {
            billing.setBillingAddress(billingAddress);
        }

        /* Initialize totalAmount for billing.amount */
        BigDecimal totalAmount = BigDecimal.ZERO;
        /* Looping salesReference */
        for (DeliveryOrderRealizationItem dorItem : dor.getItems()) {
            BillingReferenceItem referenceItem = new BillingReferenceItem();
            referenceItem.setMoney(new Money());
            referenceItem.setReferenceId(dor.getId());
            referenceItem.setReferenceCode(dor.getCode());
            referenceItem.setReferenceDate(dor.getDate());
            referenceItem.setReferenceName(SiriusValidator.getEnumName(dor.getClass())); // Jadi DELIVERY_ORDER_REALIZATION
            referenceItem.setQuantity(
                    /* Quantity Billing Ref dari (Accepted - Shrinkage) DOR Item */
                    dorItem.getAccepted().subtract(dorItem.getShrinkage())
            );
            referenceItem.getMoney().setAmount(dorItem.getDeliveryOrderItem().getSalesReferenceItem().getMoney().getAmount());
            referenceItem.getMoney().setRate(dorItem.getDeliveryOrderItem().getSalesReferenceItem().getMoney().getRate());
            referenceItem.getMoney().setCurrency(dorItem.getDeliveryOrderItem().getSalesReferenceItem().getMoney().getCurrency());
            referenceItem.setDiscount(dorItem.getDeliveryOrderItem().getSalesReferenceItem().getDiscount());
            referenceItem.setReferenceUom(dorItem.getProduct().getUnitOfMeasure().getMeasureId());
            referenceItem.setOrganization(dor.getOrganization());
            referenceItem.setFacility(dor.getFacility());
            referenceItem.setCustomer(dor.getCustomer());
            referenceItem.setReferenceType(BillingReferenceType.DELIVERY_ORDER_REALIZATION);
            referenceItem.setCreatedBy(dor.getCreatedBy());
            referenceItem.setCreatedDate(dor.getCreatedDate());
            referenceItem.setTax(dorItem.getDeliveryOrderItem().getSalesReferenceItem().getTax());
            referenceItem.setProduct(dorItem.getProduct());

            /*
             Data yang belum diset pada billing reference:
             referenceIdExt (null)
             referenceCodeExt (null)
             referenceUri (null)
            */

            genericDao.add(referenceItem);

            Item item = new Item();
            item.setBillingReferenceItem(referenceItem);

            form.getItems().add(item);

            /*
             Kalkulasi totalAmount dengan perhitungan quantity, diskon, dan pajak
             sebagai nilai total billing amount dan unpaidnya
             tatotalAmount += (Price * Qty) - Discount
            */
            totalAmount = totalAmount.add(
                    // Kalikan harga dengan quantity
                    referenceItem.getMoney().getAmount().multiply(referenceItem.getQuantity())
                            // Kurangi diskon
                            .subtract(
                                    referenceItem.getMoney().getAmount().multiply(referenceItem.getQuantity())
                                            .multiply(referenceItem.getDiscount()).divide(BigDecimal.valueOf(100))
                            )
            );

            // Set Billing term,rate & tax
            billing.setTerm(dorItem.getDeliveryOrderItem().getSalesReferenceItem().getTerm());
            billing.getMoney().setRate(dorItem.getDeliveryOrderItem().getSalesReferenceItem().getMoney().getRate());
            billing.setTax(dorItem.getTax());
        }

        // Check address type Tax (Selected Priorty) if tax rate 0 taxAddress can be NULL
        PostalAddress taxAddress = postalAddressDao.loadAddressByPartyAndType(dor.getCustomer().getId(), AddressType.TAX);
        assert billing.getTax() != null;
        if (billing.getTax().getTaxRate().compareTo(BigDecimal.ZERO) > 0 && taxAddress == null) {
            throw new ServiceException("Customer doesn't have address type TAX, please set it first on customer page.");
        } else {
            billing.setTaxAddress(taxAddress);
        }

        // Calculate Tax Amount after get Total Line Item Price / Amount and Add it to totalAmount
        BigDecimal taxAmount = totalAmount.multiply(billing.getTax().getTaxRate()).divide(BigDecimal.valueOf(100));
        totalAmount = totalAmount.add(taxAmount);

        billing.getMoney().setAmount(totalAmount);
        billing.setUnpaid(billing.getMoney().getAmount());
        billing.setDueDate(DateHelper.plusDays(billing.getDate(), billing.getTerm())); // Billing.date + Term

        form.setBilling(billing);
        billingService.add(form.getBilling());
    }
}

package com.siriuserp.accounting.sibling;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siriuserp.accounting.dm.Billing;
import com.siriuserp.accounting.dm.BillingReferenceItem;
import com.siriuserp.accounting.dm.BillingReferenceType;
import com.siriuserp.accounting.dm.BillingType;
import com.siriuserp.accounting.form.AccountingForm;
import com.siriuserp.accounting.service.BillingService;
import com.siriuserp.inventory.dm.Issueable;
import com.siriuserp.sales.dm.DeliveryOrderRealization;
import com.siriuserp.sales.dm.DeliveryOrderRealizationItem;
import com.siriuserp.sdk.dao.PostalAddressDao;
import com.siriuserp.sdk.dm.AbstractSiblingRole;
import com.siriuserp.sdk.dm.AddressType;
import com.siriuserp.sdk.dm.Item;
import com.siriuserp.sdk.dm.Money;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.PostalAddress;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.utility.DateHelper;
import com.siriuserp.sdk.utility.FormHelper;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Betsu Brahmana Restu 
 * PT Sirius Indonesia 
 * betsu@siriuserp.com
 */

@Component
public class AddBillingSiblingRole extends AbstractSiblingRole {

    @Autowired
    private PostalAddressDao postalAddressDao;

	@Autowired
	private BillingService billingService;
	
	@Override
	public void execute() throws Exception {
		Object object = (Object) getSiblingable();
		 
		Issueable warehouse = (Issueable) object;
		
		DeliveryOrderRealization dor = genericDao.load(DeliveryOrderRealization.class, warehouse.getId());
		 
		Billing billing = FormHelper.create(Billing.class, new AccountingForm());
        billing.setMoney(new Money());
        billing.setDate(dor.getDate());
        billing.setOrganization(dor.getOrganization());
        billing.setFacility(dor.getFacility()); // Note: Untuk sekarang Akan selalu Null Karena di DOR belum diset Facilitynya
        billing.setBillingType(genericDao.load(BillingType.class, BillingType.DELIVERY_ORDER_REALIZATION));
        billing.setNote("AUTO BILLING FROM DOR [" + dor.getCode() + "]");
        billing.getMoney().setCurrency(dor.getCurrency());
	        
        Party customer = genericDao.load(Party.class, dor.getCustomer().getId()); // To Avoid LazyLoadingExecption no session
        
        PostalAddress billingAddress = postalAddressDao.loadAddressByPartyAndType(dor.getCustomer().getId(), AddressType.OFFICE);
        if (billingAddress == null) {
            throw new ServiceException("Customer doesn't have address type OFFICE, please set it first on customer page.");
        } else {
            billing.setBillingAddress(billingAddress);
        }
        
        billing.setCustomer(customer);
        billing.setShippingAddress(dor.getShippingAddress());

        /* Initialize totalAmount for billing.amount */
        BigDecimal totalAmount = BigDecimal.ZERO;
        /* Looping salesReference */
        for (DeliveryOrderRealizationItem dorItem : dor.getItems()) {
            // Buat BillingReferenceItem
            BillingReferenceItem referenceItem = new BillingReferenceItem();
            referenceItem.setMoney(new Money());
            referenceItem.setReferenceId(dor.getId());
            referenceItem.setReferenceCode(dor.getCode());
            referenceItem.setReferenceDate(dor.getDate());
            referenceItem.setReferenceName(SiriusValidator.getEnumName(dor.getClass())); // Jadi DELIVERY_ORDER_REALIZATION
            referenceItem.setQuantity(dorItem.getAccepted());
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

            billing.getForm().getItems().add(item);

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

        billingService.add(billing);
	}
}

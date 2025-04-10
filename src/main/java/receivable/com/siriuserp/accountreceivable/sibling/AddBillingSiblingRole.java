package com.siriuserp.accountreceivable.sibling;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siriuserp.accounting.form.AccountingForm;
import com.siriuserp.accountreceivable.dm.Billing;
import com.siriuserp.accountreceivable.dm.BillingReferenceItem;
import com.siriuserp.accountreceivable.dm.BillingReferenceType;
import com.siriuserp.accountreceivable.dm.BillingType;
import com.siriuserp.accountreceivable.service.BillingService;
import com.siriuserp.inventory.dm.Issueable;
import com.siriuserp.sales.dm.DeliveryOrderItemType;
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
public class AddBillingSiblingRole extends AbstractSiblingRole
{
	@Autowired
	private PostalAddressDao postalAddressDao;

	@Autowired
	private BillingService billingService;

	@Override
	public void execute() throws Exception
	{
		Object object = (Object) getSiblingable();

		Issueable warehouse = (Issueable) object;

		DeliveryOrderRealization realization = genericDao.load(DeliveryOrderRealization.class, warehouse.getId());

		Billing billing = FormHelper.create(Billing.class, new AccountingForm());
		billing.setMoney(new Money());
		billing.setDate(realization.getDate());
		billing.setOrganization(realization.getOrganization());
		billing.setFacility(realization.getFacility()); // Note: Untuk sekarang Akan selalu Null Karena di DOR belum diset Facilitynya
		billing.setBillingType(genericDao.load(BillingType.class, BillingType.DELIVERY_ORDER_REALIZATION));
		billing.setNote("AUTO BILLING FROM DOR [" + realization.getCode() + "]");
		billing.getMoney().setCurrency(realization.getCurrency());

		Party customer = genericDao.load(Party.class, realization.getCustomer().getId());

		PostalAddress billingAddress = postalAddressDao.loadAddressByPartyAndType(realization.getCustomer().getId(), AddressType.OFFICE);
		if (billingAddress == null)
			throw new ServiceException("Customer doesn't have address type OFFICE, please set it first on customer page.");
		else
			billing.setBillingAddress(billingAddress);

		billing.setCustomer(customer);
		billing.setShippingAddress(realization.getShippingAddress());

		BigDecimal totalAmount = BigDecimal.ZERO;
		for (DeliveryOrderRealizationItem realizationItem : realization.getItems())
		{
			if (realizationItem.getDeliveryOrderItem().getDeliveryItemType().equals(DeliveryOrderItemType.BASE))
			{
				// Buat BillingReferenceItem
				BillingReferenceItem referenceItem = new BillingReferenceItem();
				referenceItem.setMoney(new Money());
				referenceItem.setReferenceId(realization.getId());
				referenceItem.setReferenceCode(realization.getCode());
				referenceItem.setReferenceDate(realization.getDate());
				referenceItem.setReferenceName(SiriusValidator.getEnumName(realization.getClass())); // Jadi DELIVERY_ORDER_REALIZATION
				referenceItem.setQuantity(realizationItem.getAccepted());
				referenceItem.getMoney().setAmount(realizationItem.getMoney().getAmount());
				referenceItem.getMoney().setRate(realizationItem.getMoney().getRate());
				referenceItem.getMoney().setCurrency(realizationItem.getMoney().getCurrency());
				referenceItem.setDiscount(realizationItem.getDeliveryOrderItem().getDiscount());
				referenceItem.setReferenceUom(realizationItem.getProduct().getUnitOfMeasure().getMeasureId());
				referenceItem.setOrganization(realization.getOrganization());
				referenceItem.setFacility(realization.getFacility());
				referenceItem.setCustomer(realization.getCustomer());
				referenceItem.setReferenceType(BillingReferenceType.DELIVERY_ORDER_REALIZATION);
				referenceItem.setCreatedBy(realization.getCreatedBy());
				referenceItem.setCreatedDate(realization.getCreatedDate());
				referenceItem.setTax(realizationItem.getTax());
				referenceItem.setProduct(realizationItem.getProduct());

				genericDao.add(referenceItem);

				Item item = new Item();
				item.setBillingReferenceItem(referenceItem);

				billing.getForm().getItems().add(item);

				totalAmount = totalAmount.add(referenceItem.getMoney().getAmount().multiply(referenceItem.getQuantity())
						.subtract(referenceItem.getMoney().getAmount().multiply(referenceItem.getQuantity()).multiply(referenceItem.getDiscount()).divide(BigDecimal.valueOf(100))));

				// Set Billing term,rate & tax
				billing.setTerm(realizationItem.getDeliveryOrderItem().getDeliveryReferenceItem().getTerm());
				billing.getMoney().setRate(realizationItem.getMoney().getRate());
				billing.setTax(realizationItem.getTax());
			}
		}

		// Check address type Tax (Selected Priorty) if tax rate 0 taxAddress can be NULL
		PostalAddress taxAddress = postalAddressDao.loadAddressByPartyAndType(realization.getCustomer().getId(), AddressType.TAX);
		assert billing.getTax() != null;
		if (billing.getTax().getTaxRate().compareTo(BigDecimal.ZERO) > 0 && taxAddress == null)
			throw new ServiceException("Customer doesn't have address type TAX, please set it first on customer page.");
		else
			billing.setTaxAddress(taxAddress);

		// Calculate Tax Amount after get Total Line Item Price / Amount and Add it to totalAmount
		BigDecimal taxAmount = totalAmount.multiply(billing.getTax().getTaxRate()).divide(BigDecimal.valueOf(100));
		totalAmount = totalAmount.add(taxAmount);

		billing.getMoney().setAmount(totalAmount);
		billing.setUnpaid(billing.getMoney().getAmount());
		billing.setDueDate(DateHelper.plusDays(billing.getDate(), billing.getTerm())); // Billing.date + Term

		billingService.add(billing);
	}
}

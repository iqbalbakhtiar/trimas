/**
 * File Name  : AddBillingSiblingRoleNew.java
 * Created On : Aug 25, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.accountreceivable.sibling;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siriuserp.accounting.form.AccountingForm;
import com.siriuserp.accountreceivable.dm.Billing;
import com.siriuserp.accountreceivable.dm.BillingReferenceItem;
import com.siriuserp.accountreceivable.dm.BillingReferenceable;
import com.siriuserp.accountreceivable.dm.BillingType;
import com.siriuserp.accountreceivable.dm.Billingable;
import com.siriuserp.accountreceivable.dm.BillingableItemType;
import com.siriuserp.accountreceivable.service.BillingService;
import com.siriuserp.accountreceivable.util.BillingReferenceUtil;
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

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Component
public class AddBillingSiblingRole extends AbstractSiblingRole
{
	@Autowired
	private PostalAddressDao postalAddressDao;

	@Autowired
	private BillingService billingService;

	/*@Autowired
	private ReceiptService receiptService;*/

	@Override
	public void execute() throws Exception
	{
		Object object = (Object) getSiblingable();
		Billingable billingable = (Billingable) object;

		if (billingable != null && billingable.isBillingable())
		{
			StringBuilder note = new StringBuilder();
			note.append("AUTO BILLING FROM ");

			if (billingable.getBillingType().equals(BillingType.DELIVERY_ORDER_REALIZATION))
				note.append("DELIVERY REALIZATION ");
			else
				note.append("SALES ORDER ");

			note.append("[" + billingable.getCode() + "]");

			Billing billing = FormHelper.create(Billing.class, new AccountingForm());
			billing.setMoney(new Money());
			billing.setDate(billingable.getDate());
			billing.setOrganization(billingable.getOrganization());
			billing.setFacility(billingable.getFacility());
			billing.setBillingType(genericDao.load(BillingType.class, billingable.getBillingType()));
			billing.setNote(note.toString());
			billing.getMoney().setCurrency(billingable.getCurrency());

			Party customer = genericDao.load(Party.class, billingable.getCustomer().getId());

			PostalAddress billingAddress = postalAddressDao.loadAddressByPartyAndType(billingable.getCustomer().getId(), AddressType.OFFICE);
			if (billingAddress == null)
				throw new ServiceException("Customer doesn't have address type OFFICE, please set it first on customer page.");
			else
				billing.setBillingAddress(billingAddress);

			billing.setCustomer(customer);
			billing.setShippingAddress(billingable.getShippingAddress());

			BigDecimal totalAmount = BigDecimal.ZERO;

			for (BillingReferenceable referenceable : billingable.getBillingReferenceables())
			{
				if (referenceable.getBillingableItemType().equals(BillingableItemType.BASE))
				{
					BillingReferenceItem referenceItem = BillingReferenceUtil.initItem(referenceable);
					genericDao.add(referenceItem);

					Item item = new Item();
					item.setBillingReferenceItem(referenceItem);

					billing.getForm().getItems().add(item);

					totalAmount = totalAmount.add((referenceItem.getMoney().getAmount().subtract(referenceItem.getDiscount())).multiply(referenceItem.getQuantity()));

					billing.setTerm(referenceItem.getTerm());
					billing.getMoney().setRate(referenceItem.getMoney().getRate());
					billing.setTax(referenceItem.getTax());
				}
			}

			PostalAddress taxAddress = postalAddressDao.loadAddressByPartyAndType(billing.getCustomer().getId(), AddressType.TAX);
			assert billing.getTax() != null;
			if (billing.getTax().getTaxRate().compareTo(BigDecimal.ZERO) > 0 && taxAddress == null)
				throw new ServiceException("Customer doesn't have address type TAX, please set it first on customer page.");
			else
				billing.setTaxAddress(taxAddress);

			BigDecimal taxAmount = totalAmount.multiply(billing.getTax().getTaxRate()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
			totalAmount = totalAmount.add(taxAmount);

			billing.getMoney().setAmount(totalAmount);
			billing.setUnpaid(billing.getMoney().getAmount());
			billing.setDueDate(DateHelper.plusDays(billing.getDate(), billing.getTerm())); // Billing.date + Term

			billingService.add(billing);

			/*if (billingable.isAutoReceipt())
			{
				if (billingable.getBillingType().equals(BillingType.SALES_ORDER))
				{
					SalesOrder salesOrder = genericDao.load(SalesOrder.class, billingable.getReferenceId());
					for (SalesOrderPaymentProgramApplication application : salesOrder.getApplications())
					{
						if (application.getAmount().compareTo(BigDecimal.ZERO) > 0)
						{
							Receipt receipt = FormHelper.create(Receipt.class, new AccountingForm());
							receipt.setOrganization(billing.getOrganization());
							receipt.setDate(billing.getDate());
							receipt.setCustomer(billing.getCustomer());
							receipt.setCurrency(billing.getMoney().getCurrency());
			
							ReceiptInformation information = new ReceiptInformation();
							information.setPaymentMethodType(application.getPaymentMethodType());
							information.setAmount(application.getAmount());
			
							if (application.getPaymentMethodType().equals(PaymentMethodType.CASH))
								information.setBankAccount(billing.getOrganization().getBankAccountCash());
							else
								information.setBankAccount(billing.getOrganization().getBankAccountTransfer());
			
							receipt.setReceiptInformation(information);
			
							Item item = new Item();
							item.setReference(billing.getId());
							item.setPrice(application.getAmount());
							item.setWriteOffType(WriteOffType.ADJUSTMENT);
							item.setWriteOff(BigDecimal.ZERO);
			
							receipt.getForm().getItems().add(item);
			
							receiptService.add(receipt);
						}
					}
				}
			}*/
		}
	}
}

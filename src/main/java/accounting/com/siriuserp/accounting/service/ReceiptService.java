package com.siriuserp.accounting.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.accounting.adapter.ReceiptAdapter;
import com.siriuserp.accounting.dm.Billing;
import com.siriuserp.accounting.dm.BillingItem;
import com.siriuserp.accounting.dm.FinancialStatus;
import com.siriuserp.accounting.dm.Receipt;
import com.siriuserp.accounting.dm.ReceiptApplication;
import com.siriuserp.accounting.form.AccountingForm;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.annotation.InjectParty;
import com.siriuserp.sdk.base.Service;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.CurrencyDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.Item;
import com.siriuserp.sdk.dm.PaymentMethodType;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.DateHelper;
import com.siriuserp.sdk.utility.EnglishNumber;
import com.siriuserp.sdk.utility.FormHelper;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.ObjectPrinter;
import com.siriuserp.sdk.utility.QueryFactory;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastMap;

@Component
@Transactional(rollbackFor = Exception.class)
public class ReceiptService extends Service {

    @Autowired
    private GenericDao genericDao;

    @Autowired
    private CodeSequenceDao codeSequenceDao;

    @Autowired
	private CurrencyDao currencyDao;

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws Exception
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("filterCriteria", filterCriteria);
		map.put("receipts", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));

		return map;
	}

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	@InjectParty(keyName = "receipt_form")
	public FastMap<String, Object> preadd() throws Exception
	{
		Receipt receipt = new Receipt();
		AccountingForm form = new AccountingForm();
		form.setReceipt(receipt);

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("currencys", genericDao.loadAll(Currency.class));
		map.put("defaultCurrency", currencyDao.loadDefaultCurrency());
		map.put("types", PaymentMethodType.values());

		map.put("receipt_form", form);

		return map;
	}

	@AuditTrails(className = Receipt.class, actionType = AuditTrailsActionType.CREATE)
	public FastMap<String, Object> add(Receipt receipt) throws Exception
	{
		AccountingForm form = (AccountingForm) receipt.getForm();
		BigDecimal total = BigDecimal.ZERO;

		ObjectPrinter.printJson(form);

		for (Item item : form.getItems()) // Item bisa direpresentasikan 1 billing juga atau 1 billing yang mau dibayar
		{
			// getReference = Id Billing
			// Kondisi jika Id Billing tidak null dan "Paid" pada receipt application > 0
			if (SiriusValidator.validateLongParam(item.getReference()) && SiriusValidator.gz(item.getPrice()))
			{
				ReceiptApplication application = new ReceiptApplication();
				application.setBilling(genericDao.load(Billing.class, item.getReference()));
				application.setPaidAmount(item.getPrice().add(item.getWriteOff()));
				application.setReceipt(receipt);
				application.setWriteOff(item.getWriteOff());
				application.setWriteOffType(item.getWriteOffType());

                /* unpaid = unpaid - paidAmount */
				BigDecimal unpaid = application.getBilling().getUnpaid().subtract(application.getPaidAmount());
				total = total.add(application.getPaidAmount());

				// Kalau Bukan Clearing
				if (!receipt.getReceiptInformation().getPaymentMethodType().equals(PaymentMethodType.CLEARING))
				{
					// Kalau Billing sudah lunas alias = billing.unpaid = 0
					if (unpaid.compareTo(BigDecimal.ONE.negate()) >= 0 && unpaid.compareTo(BigDecimal.ONE) <= 0)
					{
						application.getBilling().setUnpaid(BigDecimal.ZERO);
						application.getBilling().setFinancialStatus(FinancialStatus.PAID);
						application.getBilling().setPaidDate(receipt.getDate());
					} else { // Kalau belum lunas, billing >= 0 set unpaid dari hasil unpaid - paidAmount
						application.getBilling().setUnpaid(unpaid);
					}
				} else { // Kalau Clearing
					// Untuk sekarang kalau tipe pembayarannya clearing sepertinya tidak melakukan apa-apa pada billing
					application.getBilling().setClearing(application.getBilling().getClearing());
				}

				// Kalau billingnya sudah LUNAS update billing reference item - paid menjadi true
				if (application.getBilling().getFinancialStatus().equals(FinancialStatus.PAID)) {
					for (BillingItem billingItem : application.getBilling().getItems()) {
						billingItem.getBillingReferenceItem().setPaid(true);
						genericDao.update(billingItem);
					}
				}

				genericDao.update(application.getBilling());

				receipt.getApplications().add(application);
			}
		}

		if (!receipt.getApplications().isEmpty())
		{
			receipt.getReceiptInformation().setAmount(total);

			receipt.setCode(GeneratorHelper.instance().generate(TableType.RECEIPT, codeSequenceDao));

			genericDao.add(receipt);
		}

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("id", receipt.getId());

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(Long id, String redirectUri) throws Exception
	{
		AccountingForm form = FormHelper.bind(AccountingForm.class, genericDao.load(Receipt.class, id));
		ReceiptAdapter adapter = new ReceiptAdapter(form.getReceipt());

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("receipt_form", form);
		map.put("receipt_edit", adapter.getReceipt());
		map.put("said", EnglishNumber.convertIdComma(adapter.getReceipt().getReceiptInformation().getAmount().setScale(5, RoundingMode.UP)));
		map.put("adapter", adapter);
		map.put("redirectUri", redirectUri);

		return map;
	}

	@AuditTrails(className = Receipt.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(AccountingForm form) throws Exception
	{
		form.getReceipt().setUpdatedBy(getPerson());
		form.getReceipt().setUpdatedDate(DateHelper.now());
		genericDao.update(form.getReceipt());
	}
}

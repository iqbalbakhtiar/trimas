package com.siriuserp.accountreceivable.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.accounting.form.AccountingForm;
import com.siriuserp.accountpayable.dm.PaymentMethodType;
import com.siriuserp.accountreceivable.adapter.ReceiptAdapter;
import com.siriuserp.accountreceivable.dm.Billing;
import com.siriuserp.accountreceivable.dm.BillingItem;
import com.siriuserp.accountreceivable.dm.FinancialStatus;
import com.siriuserp.accountreceivable.dm.Receipt;
import com.siriuserp.accountreceivable.dm.ReceiptApplication;
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
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.DateHelper;
import com.siriuserp.sdk.utility.EnglishNumber;
import com.siriuserp.sdk.utility.FormHelper;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.QueryFactory;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastMap;

@Component
@Transactional(rollbackFor = Exception.class)
public class ReceiptService extends Service
{

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
	public void add(Receipt receipt) throws Exception
	{
		AccountingForm form = (AccountingForm) receipt.getForm();
		BigDecimal total = BigDecimal.ZERO;

		for (Item item : form.getItems())
		{
			if (SiriusValidator.validateLongParam(item.getReference()) && (SiriusValidator.gz(item.getPrice()) || SiriusValidator.nz(item.getWriteOff())))
			{
				ReceiptApplication application = new ReceiptApplication();
				application.setBilling(genericDao.load(Billing.class, item.getReference()));
				application.setPaidAmount(item.getPrice());
				application.setWriteOff(item.getWriteOff());
				application.setWriteOffType(item.getWriteOffType());
				application.setReceipt(receipt);

				BigDecimal oriWriteOff = application.getPaidAmount().subtract(application.getWriteOff());
				BigDecimal unpaid = (application.getBilling().getUnpaid().add(application.getWriteOff())).subtract(application.getPaidAmount());

				if (!receipt.getReceiptInformation().getPaymentMethodType().equals(PaymentMethodType.CLEARING))
				{
					if (unpaid.compareTo(BigDecimal.ONE.negate()) >= 0 && unpaid.compareTo(BigDecimal.ONE) <= 0)
					{
						application.getBilling().setUnpaid(BigDecimal.ZERO);
						application.getBilling().setFinancialStatus(FinancialStatus.PAID);
						application.getBilling().setPaidDate(receipt.getDate());
					} else
						application.getBilling().setUnpaid(unpaid);
				} else
					application.getBilling().setClearing(application.getBilling().getClearing().add(oriWriteOff));

				if (application.getBilling().getFinancialStatus().equals(FinancialStatus.PAID))
				{
					for (BillingItem billingItem : application.getBilling().getItems())
					{
						billingItem.getBillingReferenceItem().setPaid(true);
						genericDao.update(billingItem);
					}
				}

				genericDao.update(application.getBilling());

				receipt.getApplications().add(application);
				
				total = total.add(application.getPaidAmount()).add(application.getWriteOff());
			}
		}

		if (!receipt.getApplications().isEmpty())
		{
			receipt.getReceiptInformation().setAmount(total);

			receipt.setCode(GeneratorHelper.instance().generate(TableType.RECEIPT, codeSequenceDao));

			// Set Real Date from Date, for AR Ledger Report
			receipt.setRealDate(receipt.getDate());

			genericDao.add(receipt);
		}
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

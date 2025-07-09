package com.siriuserp.accountpayable.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import org.apache.commons.lang.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.accountpayable.adapter.PaymentUIAdapter;
import com.siriuserp.accountpayable.dm.Payable;
import com.siriuserp.accountpayable.dm.Payment;
import com.siriuserp.accountpayable.dm.PaymentApplication;
import com.siriuserp.accountpayable.dm.PaymentInformation;
import com.siriuserp.accountpayable.dm.PaymentMethodType;
import com.siriuserp.accountpayable.form.PayablesForm;
import com.siriuserp.accountreceivable.dm.FinancialStatus;
import com.siriuserp.accountreceivable.dm.WriteOffType;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.annotation.InjectParty;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.CurrencyDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.Item;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.DateHelper;
import com.siriuserp.sdk.utility.EnglishNumber;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.QueryFactory;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastMap;

@Component
@Transactional(rollbackFor = Exception.class)
public class PaymentService
{

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private CurrencyDao currencyDao;

	@Autowired
	private CodeSequenceDao codeSequenceDao;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws Exception
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("filterCriteria", filterCriteria);
		map.put("payments", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	@InjectParty(keyName = "payment_add")
	public Map<String, Object> preadd() throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		PaymentMethodType[] types = new PaymentMethodType[]
		{ PaymentMethodType.CASH, PaymentMethodType.TRANSFER };

		PayablesForm payment = new PayablesForm();
		payment.setDate(DateHelper.now());
		payment.setPaymentInformation(new PaymentInformation());
		payment.setCurrency(currencyDao.loadDefaultCurrency());

		map.put("payment_add", payment);
		map.put("currencys", genericDao.loadAll(Currency.class));
		map.put("defaultCurrency", currencyDao.loadDefaultCurrency());
		map.put("writes", WriteOffType.values());
		map.put("types", types);

		return map;
	}

	@AuditTrails(className = Payment.class, actionType = AuditTrailsActionType.CREATE)
	public void add(Payment payment) throws ServiceException
	{
		if (payment.getForm().getItems().isEmpty())
			throw new ServiceException("Empty item transaction, please recheck !");

		Facility facility = null;

		for (Item item : payment.getForm().getItems())
		{
			if (item.getInvoiceVerification() != null && (SiriusValidator.gz(item.getPaidAmount()) || SiriusValidator.nz(item.getWriteOff())))
			{
				PaymentApplication application = new PaymentApplication();
				application.setPayable(item.getInvoiceVerification());
				application.setPaidAmount(item.getPaidAmount());
				application.setWriteOff(item.getWriteOff());
				application.setWriteoffType(item.getWriteOffType());
				application.setPayment(payment);

				facility = application.getPayable().getFacility();

				BigDecimal oriWriteOff = application.getPaidAmount().subtract(application.getWriteOff());
				BigDecimal unpaid = (application.getPayable().getUnpaid().add(application.getWriteOff())).subtract(application.getPaidAmount());

				if (!payment.getPaymentInformation().getPaymentMethodType().equals(PaymentMethodType.CLEARING))
				{
					if (unpaid.compareTo(BigDecimal.ONE.negate()) >= 0 && unpaid.compareTo(BigDecimal.ONE) <= 0)
					{
						application.getPayable().setUnpaid(BigDecimal.ZERO);
						application.getPayable().setStatus(FinancialStatus.PAID);
						application.getPayable().setPaidDate(payment.getDate());
					} else
						application.getPayable().setUnpaid(unpaid);
				}
				//				else if (payment.getPaymentInformation().getPaymentMethodType().equals(PaymentMethodType.CLEARING) && payment.getClearPayment() != null)
				//				{
				//					BigDecimal cleared = (application.getPaidAmount().subtract(DecimalHelper.safe(application.getWriteOff())));
				//
				//					application.getPayable().setUnpaid(application.getPayable().getUnpaid().subtract(cleared));
				//					if (application.getPayable().getUnpaid().compareTo(BigDecimal.ZERO) <= 0)
				//					{
				//						application.getPayable().setUnpaid(BigDecimal.ZERO);
				//						application.getPayable().setStatus(FinancialStatus.PAID);
				//					}
				//
				//					application.getPayable().setClearing(application.getPayable().getClearing().subtract(cleared));
				//
				//					if (application.getPayable().getClearing().compareTo(BigDecimal.ZERO) <= 0)
				//						application.getPayable().setClearing(BigDecimal.ZERO);
				//
				//					payment.setCleared(true);
				//
				//					ClearPayment clearPayment = genericDao.load(ClearPayment.class, payment.getClearPayment().getId());
				//					clearPayment.setPayment(payment);
				//					genericDao.update(clearPayment);
				//				}
				else
					application.getPayable().setClearing(application.getPayable().getClearing().add(oriWriteOff));

				genericDao.update(application.getPayable());

				payment.getApplications().add(application);

				//				if (application.getPayable().getExchange().getRate().compareTo(payment.getExchange().getRate()) != 0)
				//					types.add(PostingType.GAIN_LOSS);
			}
		}

		//		for (PostingType type : types)
		//		{
		//			PaymentPostingBridge bridge = new PaymentPostingBridge();
		//			bridge.setPayment(payment);
		//			bridge.setPostingType(type);
		//
		//			payment.getPostings().add(bridge);
		//		}

		if (facility != null)
		{
			payment.setCode(GeneratorHelper.instance().generate(TableType.PAYMENT, codeSequenceDao, facility.getCode()));
			payment.setFacility(facility);
		} else
			payment.setCode(GeneratorHelper.instance().generate(TableType.PAYMENT, codeSequenceDao, payment.getOrganization()));

		genericDao.add(payment);

		//		for (PaymentPostingBridge bridge : payment.getPostings())
		//			bridgeService.posting(bridge);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> preedit(Long id) throws ServiceException
	{
		PaymentUIAdapter adapter = new PaymentUIAdapter();
		adapter.setPayment(load(id));
		String saidId = EnglishNumber.convertIdComma(adapter.getPayment().getPaymentInformation().getAmount().setScale(2, RoundingMode.HALF_UP));

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("payment_edit", adapter.getPayment());
		map.put("said", EnglishNumber.convertIdComma(adapter.getPayment().getPaymentInformation().getAmount().add((adapter.getPayment().getPaymentInformation().getBankCharges()).setScale(5, RoundingMode.UP))));
		map.put("adapter", adapter);
		map.put("saidId", WordUtils.capitalizeFully(saidId));

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Payment load(Long id)
	{
		return genericDao.load(Payment.class, Long.valueOf(id));
	}

	@AuditTrails(className = Payment.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(Payment payment) throws ServiceException
	{
		genericDao.update(payment);
	}

	@AuditTrails(className = Payment.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(Payment payment) throws Exception
	{
		for (PaymentApplication application : payment.getApplications())
		{
			BigDecimal unpaid = BigDecimal.ZERO;
			unpaid = unpaid.subtract(application.getWriteOff());
			unpaid = unpaid.add(application.getPaidAmount());

			Payable payable = genericDao.load(Payable.class, application.getPayable().getId());
			if (payable != null)
			{
				payable.setUnpaid(payable.getUnpaid().add(unpaid));
				payable.setPaidDate(null);

				if (payment.getPaymentInformation().getPaymentMethodType().equals(PaymentMethodType.CLEARING))
					payable.setClearing(payable.getClearing().subtract(unpaid));

				payable.setStatus(FinancialStatus.UNPAID);
				genericDao.update(payable);
			}
		}

		genericDao.delete(payment);
	}
}

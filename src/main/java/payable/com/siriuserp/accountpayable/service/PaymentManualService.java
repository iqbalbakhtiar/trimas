/**
 * File Name  : PaymentManualService.java
 * Created On : Oct 17, 2023
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.accountpayable.service;

import java.math.RoundingMode;
import java.util.Map;

import org.apache.commons.lang.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.accountpayable.dm.PaymentInformation;
import com.siriuserp.accountpayable.dm.PaymentManual;
import com.siriuserp.accountpayable.dm.PaymentManualReferenceType;
import com.siriuserp.accountpayable.dm.PaymentMethodType;
import com.siriuserp.accountpayable.form.PayablesForm;
import com.siriuserp.accountreceivable.dm.WriteOffType;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.annotation.InjectParty;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.CurrencyDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.Month;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.dm.UrlCache;
import com.siriuserp.sdk.dm.User;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.DateHelper;
import com.siriuserp.sdk.utility.EnglishNumber;
import com.siriuserp.sdk.utility.FormHelper;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.QueryFactory;
import com.siriuserp.sdk.utility.SiriusValidator;
import com.siriuserp.sdk.utility.UserHelper;

import javolution.util.FastMap;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class PaymentManualService
{
	@Autowired
	private GenericDao genericDao;

	@Autowired
	private CodeSequenceDao codeSequenceDao;

	@Autowired
	private CurrencyDao currencyDao;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws Exception
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		if (SiriusValidator.validateParamWithZeroPosibility(filterCriteria.getOrganization()))
			map.put("org", genericDao.load(Party.class, filterCriteria.getOrganization()));

		map.put("filterCriteria", filterCriteria);
		map.put("payments", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));

		User user = UserHelper.activeUser();
		UrlCache rejournal = user.getUrls().get("/page/paymentmanualrecalculatejournal.htm");

		map.put("rejournal", rejournal != null ? rejournal.getAccessType() : null);
		map.put("months", Month.values());
		map.put("years", DateHelper.toYear(DateHelper.today()));

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	@InjectParty(keyName = "paymentManual_form")
	public Map<String, Object> preadd() throws Exception
	{
		PaymentMethodType[] types = new PaymentMethodType[]
		{ PaymentMethodType.CASH, PaymentMethodType.TRANSFER };

		PayablesForm payment = new PayablesForm();
		payment.setDate(DateHelper.now());
		payment.setPaymentInformation(new PaymentInformation());

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("paymentManual_form", payment);
		map.put("currencys", genericDao.loadAll(Currency.class));
		map.put("defaultCurrency", currencyDao.loadDefaultCurrency());
		map.put("writes", WriteOffType.values());
		map.put("types", types);
		map.put("referenceTypes", PaymentManualReferenceType.values());

		return map;
	}

	@AuditTrails(className = PaymentManual.class, actionType = AuditTrailsActionType.CREATE)
	public void add(PaymentManual paymentManual) throws Exception
	{
		paymentManual.setCode(GeneratorHelper.instance().generate(TableType.PAYMENT_MANUAL, codeSequenceDao));

		genericDao.add(paymentManual);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(Long id) throws Exception
	{
		PaymentManual paymentManual = load(id);
		PayablesForm form = FormHelper.bind(PayablesForm.class, paymentManual);
		String saidId = EnglishNumber.convertIdComma(paymentManual.getPaymentInformation().getAmount().setScale(2, RoundingMode.HALF_UP));

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("paymentManual_form", form);
		map.put("paymentManual_edit", paymentManual);
		map.put("saidId", WordUtils.capitalizeFully(saidId));

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public PaymentManual load(Long id)
	{
		return genericDao.load(PaymentManual.class, id);
	}

	@AuditTrails(className = PaymentManual.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(PaymentManual paymentManual) throws Exception
	{
		genericDao.update(paymentManual);
	}

	@AuditTrails(className = PaymentManual.class, actionType = AuditTrailsActionType.CREATE)
	public void rejournal(PaymentManual paymentManual) throws Exception
	{
	}

	@AuditTrails(className = PaymentManual.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(PaymentManual paymentManual) throws Exception
	{
		genericDao.delete(paymentManual);
	}

	@AuditTrails(className = PaymentManual.class, actionType = AuditTrailsActionType.CREATE)
	public void recalculateJournal(GridViewFilterCriteria filterCriteria) throws Exception
	{
		/*PaymentFilterCriteria criteria = (PaymentFilterCriteria) filterCriteria;
		criteria.setDateFrom(DateHelper.toStartDate(DateHelper.toIntMonth(criteria.getMonth()), criteria.getYear()));
		criteria.setDateTo(DateHelper.toEndDate(criteria.getDateFrom()));
		
		List<PaymentManual> paymentManuals = genericDao.getUniqeFields(PaymentManual.class, new String[]
		{ "date >=", "date <=" }, new Object[]
		{ criteria.getDateFrom(), criteria.getDateTo() }, new String[]
		{ "id" }, new String[]
		{ "ASC" });*/

		/*for (PaymentManual paymentManual : salesOrders)
		{
			for (PaymentPostingBridge bridge : paymentManual.getPostings())
			{
				if (bridge.isRejournalable())
					bridgeService.posting(bridge);
			}
		}*/
	}
}

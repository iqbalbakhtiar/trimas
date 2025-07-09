/**
 * File Name  : ReceiptManualService.java
 * Created On : Dec 5, 2023
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.accountreceivable.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.accountpayable.dm.PaymentMethodType;
import com.siriuserp.accountreceivable.dm.ReceiptApplication;
import com.siriuserp.accountreceivable.dm.ReceiptInformation;
import com.siriuserp.accountreceivable.dm.ReceiptManual;
import com.siriuserp.accountreceivable.dm.ReceiptManualReferenceType;
import com.siriuserp.accountreceivable.dm.WriteOffType;
import com.siriuserp.accountreceivable.form.ReceivablesForm;
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
public class ReceiptManualService
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
		map.put("receipts", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));

		User user = UserHelper.activeUser();
		UrlCache rejournal = user.getUrls().get("/page/receiptmanualrecalculatejournal.htm");

		map.put("rejournal", rejournal != null ? rejournal.getAccessType() : null);
		map.put("months", Month.values());
		map.put("years", DateHelper.toYear(DateHelper.today()));

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	@InjectParty(keyName = "receiptManual_form")
	public Map<String, Object> preadd() throws Exception
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		PaymentMethodType[] types = new PaymentMethodType[]
		{ PaymentMethodType.CASH, PaymentMethodType.TRANSFER, PaymentMethodType.CLEARING };

		ReceivablesForm payment = new ReceivablesForm();
		payment.setDate(DateHelper.now());
		payment.setReceiptInformation(new ReceiptInformation());

		map.put("receiptManual_form", payment);
		map.put("currencys", genericDao.loadAll(Currency.class));
		map.put("defaultCurrency", currencyDao.loadDefaultCurrency());
		map.put("writes", WriteOffType.values());
		map.put("types", types);
		map.put("referenceTypes", ReceiptManualReferenceType.values());

		return map;
	}

	@AuditTrails(className = ReceiptManual.class, actionType = AuditTrailsActionType.CREATE)
	public void add(ReceiptManual receiptManual) throws Exception
	{
		receiptManual.setCode(GeneratorHelper.instance().generate(TableType.RECEIPT_MANUAL, codeSequenceDao));

		ReceiptApplication application = new ReceiptApplication();
		application.setPaidAmount(receiptManual.getReceiptInformation().getAmount().subtract(receiptManual.getReceiptInformation().getBankCharges()));
		application.setReceipt(receiptManual);

		receiptManual.getApplications().add(application);

		genericDao.add(receiptManual);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(Long id) throws Exception
	{
		ReceiptManual receiptManual = load(id);
		ReceivablesForm form = FormHelper.bind(ReceivablesForm.class, receiptManual);

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("receiptManual_form", form);
		map.put("receiptManual_edit", receiptManual);

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public ReceiptManual load(Long id)
	{
		return genericDao.load(ReceiptManual.class, id);
	}

	@AuditTrails(className = ReceiptManual.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(ReceiptManual receiptManual) throws Exception
	{
		genericDao.update(receiptManual);
	}

	@AuditTrails(className = ReceiptManual.class, actionType = AuditTrailsActionType.CREATE)
	public void rejournal(ReceiptManual receiptManual) throws Exception
	{
	}

	@AuditTrails(className = ReceiptManual.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(ReceiptManual receiptManual) throws Exception
	{
		genericDao.delete(receiptManual);
	}

	@AuditTrails(className = ReceiptManual.class, actionType = AuditTrailsActionType.CREATE)
	public void recalculateJournal(GridViewFilterCriteria filterCriteria) throws Exception
	{
		/*ReceiptFilterCriteria criteria = (ReceiptFilterCriteria) filterCriteria;
		criteria.setDateFrom(DateHelper.toStartDate(DateHelper.toIntMonth(criteria.getMonth()), criteria.getYear()));
		criteria.setDateTo(DateHelper.toEndDate(criteria.getDateFrom()));
		
		List<ReceiptManual> receiptManuals = genericDao.getUniqeFields(ReceiptManual.class, new String[]
		{ "date >=", "date <=" }, new Object[]
		{ criteria.getDateFrom(), criteria.getDateTo() }, new String[]
		{ "id" }, new String[]
		{ "ASC" });*/

		/*for (ReceiptManual receiptManual : receiptManuals)
		{
			for (ReceiptPostingBridge bridge : receiptManual.getPostings())
			{
				if (bridge.isRejournalable())
					bridgeService.posting(bridge);
			}
		}*/
	}
}

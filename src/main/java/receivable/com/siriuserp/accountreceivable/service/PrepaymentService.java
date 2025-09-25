package com.siriuserp.accountreceivable.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.accounting.form.AccountingForm;
import com.siriuserp.accountpayable.dm.PaymentMethodType;
import com.siriuserp.accountreceivable.dm.Prepayment;
import com.siriuserp.accountreceivable.dm.Receipt;
import com.siriuserp.accountreceivable.dm.ReceiptInformation;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.annotation.InjectParty;
import com.siriuserp.sdk.base.Service;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.CurrencyDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.DateHelper;
import com.siriuserp.sdk.utility.FormHelper;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.QueryFactory;
import com.siriuserp.sdk.utility.UserHelper;

import javolution.util.FastMap;

/**
 * @author ferdinand
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class PrepaymentService extends Service
{
	@Autowired
	private CurrencyDao currencyDao;
	
	@Autowired
	private CodeSequenceDao codeSequenceDao;
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws Exception
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		
		map.put("filterCriteria", filterCriteria);
		map.put("prepayments", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));

		return map;
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	@InjectParty(keyName = "prepayment_form")
	public FastMap<String, Object> preadd() throws Exception
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		
		AccountingForm form = new AccountingForm();
		
		form.setDate(DateHelper.now());
		form.setCurrency(currencyDao.loadDefaultCurrency());
		form.setCreatedBy(UserHelper.activePerson());
		
		map.put("prepayment_form", form);
		map.put("types", PaymentMethodType.values());
		
		return map;
	}
	
	@AuditTrails(className = Prepayment.class, actionType = AuditTrailsActionType.CREATE)
//	@AutomaticPosting(process = "Prepayment", roleClasses = PrePaymentPostingRole.class)
	public void add(Prepayment prepayment) throws Exception
	{
		prepayment.setCode(GeneratorHelper.instance().generate(TableType.PRE_PAYMENT, codeSequenceDao));
		
		genericDao.add(prepayment);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(Long id) throws Exception
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		AccountingForm form = FormHelper.bind(AccountingForm.class, load(id));
		
		form.getPrepayment().getReceiptInformation();
		
		map.put("prepayment_form", form);
		map.put("prepayment_edit", form.getPrepayment());

		return map;
	}

	@AuditTrails(className = Prepayment.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(Prepayment prepayment) throws Exception
	{
		genericDao.update(prepayment);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preapply(Long id) throws Exception
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		Prepayment prepayment = load(id);
		
		AccountingForm form = new AccountingForm();
		form.setReceipt(new Receipt());
		form.setReceiptInformation(new ReceiptInformation());
		
		BeanUtils.copyProperties(prepayment, form, "receiptInformation");
		BeanUtils.copyProperties(prepayment.getReceiptInformation(), form.getReceiptInformation(), "id", "amount");
		
		form.getReceiptInformation().setAmount(prepayment.getUnapplied());
		form.setPrepayment(prepayment);
		
		map.put("prepayment_form", form);
		map.put("defaultCurrency", currencyDao.loadDefaultCurrency());
		
		return map;
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Prepayment load(Long id)
	{
		return genericDao.load(Prepayment.class, id);
	}
}

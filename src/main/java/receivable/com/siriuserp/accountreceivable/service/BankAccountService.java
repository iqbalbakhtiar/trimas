package com.siriuserp.accountreceivable.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.accounting.dm.BankAccount;
import com.siriuserp.accountreceivable.form.AccountingForm;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.annotation.InjectParty;
import com.siriuserp.sdk.base.Service;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.PartyBankAccount;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.FormHelper;
import com.siriuserp.sdk.utility.QueryFactory;

import javolution.util.FastMap;

@Component
@Transactional(rollbackFor = Exception.class)
public class BankAccountService extends Service {
	
	@Autowired
	private GenericDao genericDao;
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("filterCriteria", filterCriteria);
		map.put("accounts", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));

		return map;
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	@InjectParty(keyName = "bankAccount_form")
	public FastMap<String, Object> preadd()
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("bankAccount_form", new AccountingForm());
		map.put("currencys", genericDao.loadAll(Currency.class));

		return map;
	}
	
	@AuditTrails(className = BankAccount.class, actionType = AuditTrailsActionType.CREATE)
	public void add(BankAccount bankAccount) throws ServiceException
	{
		genericDao.add(bankAccount);

		if (bankAccount.getHolder() != null)
		{
			bankAccount.setHolder(genericDao.load(Party.class, bankAccount.getHolder().getId()));
			genericDao.update(bankAccount);

			PartyBankAccount partyBankAccount = new PartyBankAccount();
			partyBankAccount.setParty(bankAccount.getHolder());
			partyBankAccount.setBankAccount(bankAccount);

			genericDao.add(partyBankAccount);
		}
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(Long id) throws Exception
	{
		AccountingForm form = FormHelper.bind(AccountingForm.class, load(id));

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("bankAccount_form", form);
		map.put("bankAccount_edit", form.getBankAccount());

		return map;
	}
	
	@AuditTrails(className = BankAccount.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(BankAccount account) throws ServiceException
	{
		genericDao.update(account);
	}
	
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public BankAccount load(Long id)
	{
		return genericDao.load(BankAccount.class, id);
	}
}

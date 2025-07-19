package com.siriuserp.administration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.base.Service;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.PartyBankAccount;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.utility.DateHelper;

import javolution.util.FastMap;

@Component
@Transactional(rollbackFor = Exception.class)
public class PartyBankAccountService extends Service
{
	@Autowired
	private GenericDao genericDao;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preadd(Long party)
	{
		PartyBankAccount partyBankAccount = new PartyBankAccount();
		partyBankAccount.setParty(genericDao.load(Party.class, party));

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("partyBankAccount_add", partyBankAccount);

		return map;
	}

	@AuditTrails(className = PartyBankAccount.class, actionType = AuditTrailsActionType.CREATE)
	public void add(PartyBankAccount partyBankAccount) throws ServiceException
	{
		partyBankAccount.setCreatedDate(DateHelper.now());
		partyBankAccount.setCreatedBy(getPerson());

		genericDao.add(partyBankAccount);

		Party party = partyBankAccount.getParty();
		party.setUpdatedBy(getPerson());
		party.setUpdatedDate(DateHelper.now());

		genericDao.update(party);

		for (PartyBankAccount bankAccount : party.getPartyBankAccounts())
		{
			if (bankAccount.isSelected())
			{
				if (!bankAccount.getId().equals(partyBankAccount.getId()))
				{
					bankAccount.setSelected(false);
					genericDao.update(bankAccount);
				}
			}
		}
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(Long id)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("partyBankAccount_edit", genericDao.load(PartyBankAccount.class, id));

		return map;
	}

	@AuditTrails(className = PartyBankAccount.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(PartyBankAccount partyBankAccount) throws ServiceException
	{
		if (!partyBankAccount.isEnabled())
			partyBankAccount.setSelected(false);

		partyBankAccount.setUpdatedBy(getPerson());
		partyBankAccount.setUpdatedDate(DateHelper.now());

		genericDao.update(partyBankAccount);

		Party party = partyBankAccount.getParty();
		party.setUpdatedBy(getPerson());
		party.setUpdatedDate(DateHelper.now());

		genericDao.update(party);

		for (PartyBankAccount bankAccount : party.getPartyBankAccounts())
		{
			if (bankAccount.isSelected())
			{
				if (!bankAccount.getId().equals(partyBankAccount.getId()))
				{
					bankAccount.setSelected(false);
					genericDao.update(bankAccount);
				}
			}
		}
	}

	@AuditTrails(className = PartyBankAccount.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(Long id) throws ServiceException
	{
		genericDao.delete(genericDao.load(PartyBankAccount.class, id));
	}
}

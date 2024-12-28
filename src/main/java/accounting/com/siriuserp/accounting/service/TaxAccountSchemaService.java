/**
 * Dec 21, 2009 2:58:42 PM
 * com.siriuserp.accounting.service
 * TaxAccountSchemaService.java
 */
package com.siriuserp.accounting.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.accounting.dao.ClosingAccountTypeDao;
import com.siriuserp.accounting.dm.AccountingSchema;
import com.siriuserp.accounting.dm.ClosingAccountType;
import com.siriuserp.accounting.dm.GroupType;
import com.siriuserp.accounting.dm.TaxAccountSchema;
import com.siriuserp.accounting.dm.TaxPostingAccount;
import com.siriuserp.accounting.query.Tax4SchemaQuery;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.exceptions.ServiceException;

import javolution.util.FastMap;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class TaxAccountSchemaService
{
	@Autowired
	private GenericDao genericDao;

	@Autowired
	private ClosingAccountTypeDao closingAccountTypeDao;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> preadd(Long schema)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("taxes", genericDao.filter(new Tax4SchemaQuery(schema)));

		TaxAccountSchema accountSchema = new TaxAccountSchema();
		accountSchema.setAccountingSchema(genericDao.load(AccountingSchema.class, schema));

		for (ClosingAccountType type : closingAccountTypeDao.loadAll(GroupType.TAX))
		{
			TaxPostingAccount postingAccount = new TaxPostingAccount();
			postingAccount.setAccountType(type);
			postingAccount.setTaxSchema(accountSchema);

			accountSchema.getPostingAccounts().add(postingAccount);
		}

		map.put("accountSchema", accountSchema);

		return map;

	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> preedit(Long schema)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();

		TaxAccountSchema accountSchema = load(schema);

		for (ClosingAccountType type : closingAccountTypeDao.loadAll(GroupType.TAX))
		{
			boolean exist = false;

			for (TaxPostingAccount account : accountSchema.getPostingAccounts())
			{
				if (account.getAccountType().getId().equals(type.getId()))
				{
					exist = true;
					break;
				}
			}

			if (!exist)
			{
				TaxPostingAccount postingAccount = new TaxPostingAccount();
				postingAccount.setAccountType(type);
				postingAccount.setTaxSchema(accountSchema);

				accountSchema.getPostingAccounts().add(postingAccount);
			}
		}

		map.put("accountSchema", accountSchema);

		return map;

	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public TaxAccountSchema load(Long id)
	{
		return genericDao.load(TaxAccountSchema.class, id);
	}

	@AuditTrails(className = TaxAccountSchema.class, actionType = AuditTrailsActionType.CREATE)
	public void add(TaxAccountSchema accountSchema) throws ServiceException
	{
		genericDao.add(accountSchema);
	}

	@AuditTrails(className = TaxAccountSchema.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(TaxAccountSchema accountSchema) throws ServiceException
	{
		genericDao.update(accountSchema);
	}

	@AuditTrails(className = TaxAccountSchema.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(TaxAccountSchema accountSchema) throws ServiceException
	{
		genericDao.delete(accountSchema);
	}
}

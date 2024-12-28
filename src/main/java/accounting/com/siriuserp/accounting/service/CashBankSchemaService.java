/**
 * 
 */
package com.siriuserp.accounting.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.accounting.dm.AccountingSchema;
import com.siriuserp.accounting.dm.CashBankSchema;
import com.siriuserp.accounting.dm.ClosingAccountType;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.exceptions.ServiceException;

import javolution.util.FastMap;

/**
 * @author
 * Betsu Brahmana Restu
 * PT. Sirius Indonesia
 * betsu@siriuserp.com
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class CashBankSchemaService
{
	@Autowired
	private GenericDao genericDao;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> preadd(Long parent)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();

		CashBankSchema schema = new CashBankSchema();
		schema.setAccountingSchema(genericDao.load(AccountingSchema.class, parent));
		//default prepaymnet only, comment it and using list
		schema.setClosingAccountType(genericDao.load(ClosingAccountType.class, ClosingAccountType.AR_PREPAYMENT));

		map.put("cashbank_schema_add", schema);
		//uncomment this if want dynamic closing account type
		//map.put("closings", closingAccountTypeDao.loadAllReceivables());

		return map;
	}

	@AuditTrails(className = CashBankSchema.class, actionType = AuditTrailsActionType.CREATE)
	public void add(CashBankSchema schema) throws ServiceException
	{
		genericDao.add(schema);
	}

	public Map<String, Object> preedit(Long id)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("cashbank_schema_edit", genericDao.load(CashBankSchema.class, id));

		return map;
	}

	@AuditTrails(className = CashBankSchema.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(CashBankSchema schema) throws ServiceException
	{
		genericDao.update(schema);
	}
}

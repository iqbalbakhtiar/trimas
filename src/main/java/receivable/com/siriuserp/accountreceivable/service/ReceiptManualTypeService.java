/**
 * File Name  : ReceiptManualTypeService.java
 * Created On : Dec 5, 2023
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.accountreceivable.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.accountreceivable.dm.ReceiptManualReferenceType;
import com.siriuserp.accountreceivable.dm.ReceiptManualType;
import com.siriuserp.accountreceivable.form.ReceivablesForm;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.FormHelper;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.QueryFactory;

import javolution.util.FastMap;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class ReceiptManualTypeService
{
	@Autowired
	private GenericDao genericDao;

	@Autowired
	private CodeSequenceDao codeSequenceDao;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("filterCriteria", filterCriteria);
		map.put("receiptTypes", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));
		map.put("referenceTypes", ReceiptManualReferenceType.values());

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preadd()
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("receiptType_form", new ReceivablesForm());
		map.put("referenceTypes", ReceiptManualReferenceType.values());

		return map;
	}

	@AuditTrails(className = ReceiptManualType.class, actionType = AuditTrailsActionType.CREATE)
	public void add(ReceiptManualType receiptType) throws ServiceException
	{
		receiptType.setCode(GeneratorHelper.instance().generate(TableType.RECEIPT_MANUAL_TYPE, codeSequenceDao));
		genericDao.add(receiptType);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(Long id) throws Exception
	{
		ReceiptManualType receiptType = load(id);
		ReceivablesForm form = FormHelper.bind(ReceivablesForm.class, receiptType);

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("receiptType_form", form);
		map.put("receiptType_edit", receiptType);

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public ReceiptManualType load(Long id)
	{
		return genericDao.load(ReceiptManualType.class, id);
	}

	@AuditTrails(className = ReceiptManualType.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(ReceiptManualType receiptType) throws ServiceException
	{
		genericDao.update(receiptType);
	}

	@AuditTrails(className = ReceiptManualType.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(ReceiptManualType receiptType) throws ServiceException
	{
		genericDao.delete(receiptType);
	}
}

/**
 * File Name  : PaymentManualTypeService.java
 * Created On : Oct 17, 2023
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.accountpayable.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.accountpayable.dm.PaymentManualReferenceType;
import com.siriuserp.accountpayable.dm.PaymentManualType;
import com.siriuserp.accountpayable.form.PayablesForm;
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
public class PaymentManualTypeService
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
		map.put("paymentTypes", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));
		map.put("referenceTypes", PaymentManualReferenceType.values());

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preadd()
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("paymentType_form", new PayablesForm());
		map.put("referenceTypes", PaymentManualReferenceType.values());

		return map;
	}

	@AuditTrails(className = PaymentManualType.class, actionType = AuditTrailsActionType.CREATE)
	public void add(PaymentManualType paymentType) throws ServiceException
	{
		paymentType.setCode(GeneratorHelper.instance().generate(TableType.PAYMENT_MANUAL_TYPE, codeSequenceDao));
		genericDao.add(paymentType);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(Long id) throws Exception
	{
		PaymentManualType paymentType = load(id);
		PayablesForm form = FormHelper.bind(PayablesForm.class, paymentType);

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("paymentType_form", form);
		map.put("paymentType_edit", paymentType);

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public PaymentManualType load(Long id)
	{
		return genericDao.load(PaymentManualType.class, id);
	}

	@AuditTrails(className = PaymentManualType.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(PaymentManualType paymentType) throws ServiceException
	{
		genericDao.update(paymentType);
	}

	@AuditTrails(className = PaymentManualType.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(PaymentManualType paymentType) throws ServiceException
	{
		genericDao.delete(paymentType);
	}
}

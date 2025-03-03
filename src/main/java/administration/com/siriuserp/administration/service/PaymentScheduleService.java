package com.siriuserp.administration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.accountpayable.dm.PaymentMethod;
import com.siriuserp.accountpayable.dm.PaymentSchedule;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.base.Service;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.utility.QueryFactory;

import javolution.util.FastMap;

/**
 * @author Rama Almer Felix
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class PaymentScheduleService extends Service {

	@Autowired
	private GenericDao genericDao;
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("filterCriteria", filterCriteria);
		map.put("paymentSchedule", genericDao.filter(QueryFactory.create(filterCriteria, queryclass)));

		return map;
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preadd(String paymentMethod)
	{
		PaymentSchedule paymentSchedule = new PaymentSchedule();
		paymentSchedule.setPaymentMethod(genericDao.load(PaymentMethod.class, Long.valueOf(paymentMethod)));
		
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("paymentSchedule_add", paymentSchedule);

		return map;
	}
	
	@AuditTrails(className = PaymentSchedule.class, actionType = AuditTrailsActionType.CREATE)
	public void add(PaymentSchedule paymentSchedule) throws ServiceException {
		genericDao.add(paymentSchedule);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(Long id)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("paymentSchedule_edit", genericDao.load(PaymentSchedule.class, id));

		return map;
	}
	
	@AuditTrails(className = PaymentSchedule.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(PaymentSchedule paymentSchedule) throws ServiceException
	{
		genericDao.update(paymentSchedule);
	}
	
	@AuditTrails(className = PaymentSchedule.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(Long id) throws ServiceException
	{
		genericDao.delete(genericDao.load(PaymentSchedule.class, id));
	}
}

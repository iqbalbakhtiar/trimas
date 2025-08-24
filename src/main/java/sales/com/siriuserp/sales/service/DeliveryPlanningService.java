/**
 * File Name  : DeliveryPlanningService.java
 * Created On : Mar 6, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.sales.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.sales.dm.DeliveryPlanning;
import com.siriuserp.sales.dm.SOStatus;
import com.siriuserp.sales.dm.SalesOrder;
import com.siriuserp.sales.form.SalesForm;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.dm.TableType;
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
public class DeliveryPlanningService
{
	@Autowired
	private CodeSequenceDao codeSequenceDao;

	@Autowired
	private GenericDao genericDao;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws Exception
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("filterCriteria", filterCriteria);
		map.put("plannings", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> preadd1() throws Exception
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("salesOrders", genericDao.loadAll(SalesOrder.class));

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> preadd2(Long id) throws Exception
	{
		DeliveryPlanning planning = new DeliveryPlanning();
		planning.setSalesOrder(genericDao.load(SalesOrder.class, id));

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("planning_add", planning);

		return map;
	}

	@AuditTrails(className = DeliveryPlanning.class, actionType = AuditTrailsActionType.CREATE)
	public void add(DeliveryPlanning planning) throws Exception
	{
		SalesOrder salesOrder = genericDao.load(SalesOrder.class, planning.getSalesOrder().getId());
		salesOrder.setSoStatus(SOStatus.PLANNING);
		genericDao.update(salesOrder);

		planning.setCode(GeneratorHelper.instance().generate(TableType.DELIVERY_PLANNING, codeSequenceDao, planning.getDate()));
		genericDao.add(planning);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> preedit(Long id) throws Exception
	{
		SalesForm form = FormHelper.bind(SalesForm.class, load(id));

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("planning_form", form);
		map.put("planning_edit", form.getDeliveryPlanning());
		map.put("salesOrder", genericDao.load(SalesOrder.class, form.getDeliveryPlanning().getSalesOrder().getId()));

		return map;
	}

	@AuditTrails(className = DeliveryPlanning.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(DeliveryPlanning planning) throws Exception
	{
		genericDao.update(planning);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public DeliveryPlanning load(Long id)
	{
		return genericDao.load(DeliveryPlanning.class, id);
	}

	@AuditTrails(className = DeliveryPlanning.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(DeliveryPlanning planning) throws Exception
	{
		genericDao.delete(planning);
	}
}

/**
 * File Name  : MachineService.java
 * Created On : Aug 7, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.production.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.production.dm.Machine;
import com.siriuserp.production.form.ProductionForm;
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
public class MachineService
{
	@Autowired
	private GenericDao genericDao;

	@Autowired
	private CodeSequenceDao codeSequenceDao;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws Exception
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("filterCriteria", filterCriteria);
		map.put("machines", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> preadd()
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("machine_form", new ProductionForm());

		return map;
	}

	@AuditTrails(className = Machine.class, actionType = AuditTrailsActionType.CREATE)
	public void add(Machine machine) throws Exception
	{
		machine.setCode(GeneratorHelper.instance().generate(TableType.MACHINE, codeSequenceDao));
		genericDao.add(machine);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> preedit(Long id) throws Exception
	{
		ProductionForm productionForm = FormHelper.bind(ProductionForm.class, load(id));
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("machine_form", productionForm);
		map.put("machine_edit", productionForm.getMachine());

		return map;
	}

	@AuditTrails(className = Machine.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(Machine machine) throws Exception
	{
		genericDao.update(machine);
	}

	@AuditTrails(className = Machine.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(Machine machine) throws Exception
	{
		genericDao.delete(machine);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Machine load(Long id) throws Exception
	{
		return genericDao.load(Machine.class, id);
	}
}

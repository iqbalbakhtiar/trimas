package com.siriuserp.production.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.production.dm.Machine;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.QueryFactory;

import javolution.util.FastMap;

/**
 * @author Muhammad Rizal
 * Sirius Indonesia, PT
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
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws ServiceException
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

		Machine machine = new Machine();
		map.put("machine_add", machine);

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> preedit(Long id)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("machine_edit", load(id));

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Machine load(Long id)
	{
		return genericDao.load(Machine.class, id);
	}

	@AuditTrails(className = Container.class, actionType = AuditTrailsActionType.CREATE)
	public void add(Machine machine) throws ServiceException
	{
		machine.setCode(GeneratorHelper.instance().generate(TableType.MACHINE, codeSequenceDao));
		genericDao.add(machine);
	}

	@AuditTrails(className = Machine.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(Machine machine) throws ServiceException
	{
		
		System.out.println(machine.getId());
		genericDao.update(machine);
	}

	@AuditTrails(className = Machine.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(Machine machine) throws ServiceException
	{
		genericDao.delete(machine);
	}
}

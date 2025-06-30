package com.siriuserp.production.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.production.dm.ProductionOrderDetail;
import com.siriuserp.production.dm.WorkIssue;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.base.Service;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.utility.GeneratorHelper;

import javolution.util.FastMap;

/**
 * @author ferdinand
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class WorkIssueService extends Service 
{
	@Autowired
	private CodeSequenceDao codeSequenceDao;
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> preadd(Long id)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		
		WorkIssue workIssue  = new WorkIssue();
		workIssue.setProductionOrderDetail(genericDao.load(ProductionOrderDetail.class, id));
		
		map.put("work_add", workIssue);
		
		return map;
	}
	
	@AuditTrails(className = WorkIssue.class, actionType = AuditTrailsActionType.CREATE)
	public void add(WorkIssue workIssue) throws ServiceException
	{
		workIssue.setCode(GeneratorHelper.instance().generate(TableType.WORK_ISSUE, codeSequenceDao));
		
		genericDao.add(workIssue);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(Long id) throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		
		map.put("work_edit", load(id));
		
		return map;
	}
	
	@AuditTrails(className = WorkIssue.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(WorkIssue workIssue) throws ServiceException
	{
		genericDao.update(workIssue);
	}
	
	
	@AuditTrails(className = WorkIssue.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(WorkIssue workIssue) throws ServiceException
	{
		genericDao.delete(workIssue);
	}

	@Transactional(readOnly = false)
	public WorkIssue load(Long id)
	{
		return genericDao.load(WorkIssue.class, id);
	}
}

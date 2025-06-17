package com.siriuserp.production.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.inventory.dm.UnitOfMeasure;
import com.siriuserp.production.dm.CostCenterGroup;
import com.siriuserp.production.dm.CostCenterGroupItem;
import com.siriuserp.production.dm.CostCenterType;
import com.siriuserp.production.form.ProductionForm;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.dm.Item;
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
public class CostCenterGroupService
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
		map.put("types", CostCenterType.values());
		map.put("costGroups", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> preadd()
	{
		FastMap<String, Object> map = new FastMap<String, Object>();

		map.put("costgroup_add", new ProductionForm());
		map.put("uoms", genericDao.loadAll(UnitOfMeasure.class));

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> preedit(Long id) throws Exception
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		CostCenterGroup costCenterGroup = genericDao.load(CostCenterGroup.class, id);
		
		map.put("costgroup_edit", costCenterGroup);

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public CostCenterGroup load(Long id)
	{
		return genericDao.load(CostCenterGroup.class, id);
	}

	@AuditTrails(className = CostCenterGroup.class, actionType = AuditTrailsActionType.CREATE)
	public void add(CostCenterGroup costGroup) throws ServiceException
	{
		costGroup.setCode(GeneratorHelper.instance().generate(TableType.COST_CENTER, codeSequenceDao));
		
		for (Item item : costGroup.getForm().getItems())
			if (item.getCostCenter() != null) 
			{
				CostCenterGroupItem costCenterGroupItem = new CostCenterGroupItem();

				costCenterGroupItem.setCostCenter(item.getCostCenter());
				costCenterGroupItem.setUnitCost(item.getUnitCost());
				costCenterGroupItem.setCostCenterGroup(costGroup);

				costGroup.getItems().add(costCenterGroupItem);
			}
		
		genericDao.add(costGroup);
	}

	@AuditTrails(className = CostCenterGroup.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(CostCenterGroup costGroup) throws ServiceException
	{
		genericDao.update(costGroup);
	}

	@AuditTrails(className = CostCenterGroup.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(CostCenterGroup costGroup) throws ServiceException
	{
		genericDao.delete(costGroup);
	}
}

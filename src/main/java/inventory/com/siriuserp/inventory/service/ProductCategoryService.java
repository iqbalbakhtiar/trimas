package com.siriuserp.inventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.inventory.dm.ProductCategory;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.base.Service;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.QueryFactory;

import javolution.util.FastMap;

@Component
@Transactional(rollbackFor = Exception.class)
public class ProductCategoryService extends Service {
	
	@Autowired
	private GenericDao genericDao;
	
	@Autowired
	private CodeSequenceDao codeSequenceDao;
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws Exception {
		FastMap<String, Object> map = new FastMap<String, Object>();
		
		map.put("filterCriteria", filterCriteria);
		map.put("categorys", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));
		
		return map;
	}
	
	@AuditTrails(className = ProductCategory.class, actionType = AuditTrailsActionType.CREATE)
	public void add(ProductCategory category) throws Exception {
		category.setCode(GeneratorHelper.instance().generate(TableType.PRODUCT_CATEGORY, codeSequenceDao));
		
		genericDao.add(category);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(Long id)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("category_edit", load(id));
		return map;
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public ProductCategory load(Long id)
	{
		return genericDao.load(ProductCategory.class, id);
	}
	
	@AuditTrails(className = ProductCategory.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(ProductCategory category) throws Exception
	{
		genericDao.update(category);
	}
	
	@AuditTrails(className = ProductCategory.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(ProductCategory category) throws Exception
	{
		genericDao.delete(category);
	}
}

package com.siriuserp.inventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.inventory.criteria.ProductFilterCriteria;
import com.siriuserp.inventory.dm.Product;
import com.siriuserp.inventory.dm.UnitOfMeasure;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.base.Service;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.QueryFactory;

import javolution.util.FastMap;

@Component
@Transactional(rollbackFor = Exception.class)
public class ProductService extends Service {
	
	@Autowired
	private GenericDao genericDao;
	
	@Autowired
	private CodeSequenceDao codeSequenceDao;
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends AbstractGridViewQuery> queryclass) throws Exception
	{
		ProductFilterCriteria criteria = (ProductFilterCriteria) filterCriteria;

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("filterCriteria", criteria);
		map.put("products", FilterAndPaging.filter(genericDao, QueryFactory.create(criteria, queryclass)));

		return map;
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preadd() throws Exception {
		FastMap<String, Object> map = new FastMap<String, Object>();
		
		map.put("product_form", new Product());
		map.put("uoms", genericDao.loadAll(UnitOfMeasure.class));
		
		return map;
	}
	
	@AuditTrails(className = Product.class, actionType = AuditTrailsActionType.CREATE)
	public void add(Product product) throws Exception {
		product.setCode(GeneratorHelper.instance().generate(TableType.PRODUCT, codeSequenceDao));
		
		genericDao.add(product);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(Long id) throws Exception {
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("product_form", load(id));
		
		return map;
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Product load(Long id) {
		return genericDao.load(Product.class, id);
	}
	
	@AuditTrails(className = Product.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(Product product) throws Exception
	{
		genericDao.update(product);
	}
	
	@AuditTrails(className = Product.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(Product product) throws Exception
	{
		genericDao.delete(product);
	}
}

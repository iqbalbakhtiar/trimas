package com.siriuserp.inventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.inventory.criteria.ProductFilterCriteria;
import com.siriuserp.inventory.dm.Brand;
import com.siriuserp.inventory.form.InventoryForm;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.base.Service;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.FormHelper;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.QueryFactory;

import javolution.util.FastMap;

/**
 * @author ferdinand
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class BrandService extends Service
{
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
		map.put("brands", FilterAndPaging.filter(genericDao, QueryFactory.create(criteria, queryclass)));

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preadd() throws Exception
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("brand_form", new InventoryForm());

		return map;
	}

	@AuditTrails(className = Brand.class, actionType = AuditTrailsActionType.CREATE)
	public void add(Brand brand) throws Exception
	{
		brand.setCode(GeneratorHelper.instance().generate(TableType.BRAND, codeSequenceDao));
		genericDao.add(brand);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(Long id) throws Exception
	{
		Brand brand = load(id);
		InventoryForm form = FormHelper.bind(InventoryForm.class, brand);

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("brand_form", form);
		map.put("brand_edit", genericDao.load(Brand.class, id));

		return map;
	}

	@AuditTrails(className = Brand.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(Brand brand) throws Exception
	{
		genericDao.update(brand);
	}

	@AuditTrails(className = Brand.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(Brand brand) throws Exception
	{
		genericDao.delete(brand);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Brand load(Long id)
	{
		return genericDao.load(Brand.class, id);
	}
}

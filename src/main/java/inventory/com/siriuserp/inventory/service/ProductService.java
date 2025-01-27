package com.siriuserp.inventory.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.inventory.criteria.ProductFilterCriteria;
import com.siriuserp.inventory.dao.ProductDao;
import com.siriuserp.inventory.dm.Product;
import com.siriuserp.inventory.dm.ProductType;
import com.siriuserp.inventory.dm.UnitOfMeasure;
import com.siriuserp.inventory.form.InventoryForm;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.base.Service;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.FormHelper;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.PathHelper;
import com.siriuserp.sdk.utility.QueryFactory;
import com.siriuserp.sdk.utility.StringHelper;

import javolution.util.FastMap;

@Component
@Transactional(rollbackFor = Exception.class)
public class ProductService extends Service
{
	@Autowired
	private GenericDao genericDao;

	@Autowired
	private CodeSequenceDao codeSequenceDao;

	@Autowired
	private ProductDao productDao;

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
	public FastMap<String, Object> preadd() throws Exception
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("product_form", new InventoryForm());
		map.put("uoms", genericDao.loadAll(UnitOfMeasure.class));
		map.put("types", ProductType.values());

		return map;
	}

	@AuditTrails(className = Product.class, actionType = AuditTrailsActionType.CREATE)
	public void add(Product product) throws Exception
	{
		InventoryForm form = (InventoryForm) product.getForm();
		product.setCode(GeneratorHelper.instance().generate(TableType.PRODUCT, codeSequenceDao));

		product.setName(product.getName().toUpperCase());
		if (check(product.getName()))
			throw new ServiceException("Product with name " + product.getName() + " already exist !!!");

		if (form.getFile() != null)
		{
			String[] _name = form.getFile().getOriginalFilename().split("[.]");
			if (_name.length > 1)
			{
				String itemName = StringHelper.generateFileName(product.getCode(), ".", _name[_name.length - 1]);
				form.getFile().transferTo(new File(PathHelper.SERVLET_PATH + "//static//product//" + itemName));
				product.setPicture("product/" + itemName);
			}
		}

		genericDao.add(product);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public boolean check(String productName)
	{
		Product product = productDao.loadByName(productName);

		return product != null ? true : false;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(Long id) throws Exception
	{
		Product product = load(id);
		InventoryForm form = FormHelper.bind(InventoryForm.class, product);

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("product_form", form);
		map.put("product_edit", product);

		return map;
	}

	@AuditTrails(className = Product.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(Product product) throws Exception
	{
		InventoryForm form = (InventoryForm) product.getForm();

		if (form.getFile() != null)
		{
			String[] _name = form.getFile().getOriginalFilename().split("[.]");
			if (_name.length > 1)
			{
				String itemName = StringHelper.generateFileName(product.getCode(), ".", _name[_name.length - 1]);
				form.getFile().transferTo(new File(PathHelper.SERVLET_PATH + "//static//product//" + itemName));
				product.setPicture("product/" + itemName);
			}
		}

		genericDao.update(product);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Product load(Long id)
	{
		return genericDao.load(Product.class, id);
	}

	@AuditTrails(className = Product.class, actionType = AuditTrailsActionType.UPDATE)
	public void delete(Product product) throws Exception
	{
		product.setEnabled(false);
		genericDao.update(product);
	}
}

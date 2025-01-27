/**
 * Mar 16, 2009 11:10:38 AM
 * com.siriuserp.inventory.dao.impl
 * ProductDaoImpl.java
 */
package com.siriuserp.inventory.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.siriuserp.inventory.dao.ProductDao;
import com.siriuserp.inventory.dm.Product;
import com.siriuserp.sdk.db.DaoHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
public class ProductDaoImpl extends DaoHelper<Product> implements ProductDao
{
	@Override
	public Product loadByCode(String code)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("FROM Product p ");
		builder.append("WHERE p.code =:code");

		Query query = getSession().createQuery(builder.toString());
		query.setParameter("code", code);

		return (Product) query.uniqueResult();
	}

	@Override
	public Product loadByName(String name)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("FROM Product p ");
		builder.append("WHERE p.name =:name");

		Query query = getSession().createQuery(builder.toString());
		query.setParameter("name", name);

		return (Product) query.uniqueResult();
	}

	@Override
	public Product loadByBarcodeId(String barcodeId)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("FROM Product p ");
		builder.append("WHERE p.barcodeId =:barcodeId");

		Query query = getSession().createQuery(builder.toString());
		query.setParameter("barcodeId", barcodeId);

		return (Product) query.uniqueResult();
	}
}

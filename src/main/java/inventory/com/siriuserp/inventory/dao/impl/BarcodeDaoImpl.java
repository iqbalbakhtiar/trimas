/**
 * Mar 16, 2009 11:10:38 AM
 * com.siriuserp.inventory.dao.impl
 * ProductDaoImpl.java
 */
package com.siriuserp.inventory.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.siriuserp.inventory.dao.BarcodeDao;
import com.siriuserp.sdk.db.DaoHelper;
import com.siriuserp.sdk.dm.Barcode;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
public class BarcodeDaoImpl extends DaoHelper<Barcode> implements BarcodeDao
{
	@Override
	public Barcode loadByCode(String code)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("FROM Barcode bar ");
		builder.append("WHERE bar.code =:code GROUP BY bar.code ");

		Query query = getSession().createQuery(builder.toString());
		query.setParameter("code", code);

		return (Barcode) query.uniqueResult();
	}

}

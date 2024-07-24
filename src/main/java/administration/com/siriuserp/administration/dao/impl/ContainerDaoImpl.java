/**
 * Sep 20, 2006 1:31:52 PM
 * net.konsep.sirius.administration.dao.hibernate
 * ContainerDaoImpl.java
 */
package com.siriuserp.administration.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.siriuserp.sdk.dao.ContainerDao;
import com.siriuserp.sdk.db.DaoHelper;
import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
public class ContainerDaoImpl extends DaoHelper<Container> implements ContainerDao
{
	@Override
	public Container loadByCode(String code)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("FROM Container con WHERE con.code =:code ORDER BY con.id DESC");
		Query query = getSession().createQuery(builder.toString());
		query.setParameter("code", code);
		query.setMaxResults(1);

		return (Container) query.uniqueResult();
	}

	@Override
	public Container loadByBarcode(String barcode)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("FROM Container con WHERE con.barcodeId =:code ORDER BY con.id DESC");

		Query query = getSession().createQuery(builder.toString());
		query.setParameter("code", barcode);
		query.setMaxResults(1);

		return (Container) query.uniqueResult();
	}

	@Override
	public Container loadDefaultContainer(Long facility) {
		return loadDefaultContainer(facility, null);
	}

	@Override
	public Container loadDefaultContainer(Long facility, Long container) {
		StringBuilder builder = new StringBuilder();
		builder.append("FROM Container con WHERE con.defaultContainer=:defc AND con.grid.facility.id=:facility ");
		
		if(SiriusValidator.validateParamWithZeroPosibility(container))
			builder.append("AND con.id != :container ");
		
		Query query=getSession().createQuery(builder.toString());
		query.setParameter("facility", facility);
		query.setParameter("defc", true);
		
		if(SiriusValidator.validateParamWithZeroPosibility(container))
			query.setParameter("container", container);
		
		query.setMaxResults(1);
		return (Container) query.uniqueResult();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Container> loadContainerByFacility(Long facility)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("FROM Container con WHERE con.grid.facility.id =:facility ORDER BY con.id ASC");
		Query query = getSession().createQuery(builder.toString());
		query.setParameter("facility", facility);

		return (List<Container>) query.list();
	}
}

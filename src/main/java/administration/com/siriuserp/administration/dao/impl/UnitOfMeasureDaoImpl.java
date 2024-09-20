/**
 * Apr 7, 2006
 * UnitOfMeadureDaoImpl.java
 */
package com.siriuserp.administration.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.siriuserp.inventory.dm.UnitOfMeasure;
import com.siriuserp.inventory.dm.UnitType;
import com.siriuserp.sdk.dao.UnitOfMeasureDao;
import com.siriuserp.sdk.db.DaoHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@SuppressWarnings("unchecked")
@Component("unitOfMeasureDao")
public class UnitOfMeasureDaoImpl extends DaoHelper<UnitOfMeasure> implements UnitOfMeasureDao
{
    @Override
    public List<UnitOfMeasure> loadAll(UnitType type)
    {
        Query query = getSession().createQuery("FROM UnitOfMeasure UOM WHERE UOM.type =:type ");
        query.setParameter("type",type);
        query.setCacheable(true);
        query.setReadOnly(true);
        
        return query.list();
    }
    
    @Override
    public UnitOfMeasure loadDefault()
    {
        Query query = getSession().createQuery("FROM UnitOfMeasure UOM WHERE UOM.base =:base ");
        query.setParameter("base",Boolean.TRUE);
        query.setMaxResults(1);
        query.setCacheable(true);
        query.setReadOnly(true);
        
        return (UnitOfMeasure)query.uniqueResult();
    }

	@Override
	public List<UnitOfMeasure> loadAll(boolean pack) {
		
		Query query = getSession().createQuery("FROM UnitOfMeasure UOM WHERE UOM.pack =:pack ");
        query.setParameter("pack",pack);
        query.setCacheable(true);
        query.setReadOnly(true);
        
        return query.list();
	}
}

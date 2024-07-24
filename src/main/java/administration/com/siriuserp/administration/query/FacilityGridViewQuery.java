/**
 * Apr 24, 2009 4:43:13 PM
 * com.siriuserp.administration.query
 * FacilityGridViewQuery.java
 */
package com.siriuserp.administration.query;

import org.hibernate.Query;

import com.siriuserp.administration.criteria.FacilityFilterCriteria;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastList;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public class FacilityGridViewQuery extends AbstractGridViewQuery
{

    @Override
    public Long count()
    {
        FacilityFilterCriteria criteria = (FacilityFilterCriteria)getFilterCriteria();
        
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT COUNT(facility.id) FROM Facility facility WHERE facility.id IS NOT NULL");
        
        Object object = createQuery(builder, criteria).uniqueResult();
        if(object != null)
            return (Long)object;
        
        return Long.valueOf(0);
    }

    @SuppressWarnings("unchecked")
    public Object execute()
    {
        FastList<Facility> list = new FastList<Facility>();
        
        FacilityFilterCriteria criteria = (FacilityFilterCriteria)getFilterCriteria();
        
        StringBuilder builder = new StringBuilder();
        builder.append("FROM Facility facility WHERE facility.id IS NOT NULL ");
        
        Query query = createQuery(builder, criteria);
		query.setFirstResult(criteria.start());
		query.setMaxResults(criteria.getMax());

		list.addAll(query.list());
        
        return list;
    }
    
    public Query createQuery(StringBuilder builder, FacilityFilterCriteria criteria)
	{
    	if(SiriusValidator.validateParam(criteria.getName()))
            builder.append(" AND facility.name like '%"+criteria.getName()+"%'");
        
        if(SiriusValidator.validateLongParam(criteria.getOrganization()))
        	builder.append(" AND facility.owner.id =:owner");
        
        Query query = getSession().createQuery(builder.toString());
        
        if(SiriusValidator.validateLongParam(criteria.getOrganization()))
        	query.setParameter("owner", criteria.getOrganization());
        
        query.setFirstResult(criteria.start());
        query.setMaxResults(criteria.getMax());
        
        return query;
	}

}

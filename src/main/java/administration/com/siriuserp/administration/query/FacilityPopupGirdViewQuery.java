/**
 * May 4, 2009 3:11:54 PM
 * com.siriuserp.popup.query
 * FacilityPopupGirdViewQuery.java
 */
package com.siriuserp.administration.query;

import org.hibernate.Query;

import com.siriuserp.administration.criteria.FacilityFilterCriteria;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.FacilityImplementation;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastList;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public class FacilityPopupGirdViewQuery extends AbstractGridViewQuery
{

    @Override
    public Long count()
    {
        FacilityFilterCriteria criteria =  (FacilityFilterCriteria)getFilterCriteria();

        StringBuilder builder = new StringBuilder();
        builder.append("SELECT COUNT(DISTINCT role.facility) FROM FacilityRole role WHERE role.id IS NOT NULL");

        if(SiriusValidator.validateLongParam(criteria.getOrganization()))
        	builder.append(" AND role.party.id =:org");
        
        if(SiriusValidator.validateParamWithZeroPosibility(criteria.getType()))
            builder.append(" AND role.facility.facilityType.id =:type");

        if(SiriusValidator.validateParam(criteria.getImplementation()))
            builder.append(" AND role.facility.implementation =:implementation");
        
        Query query = getSession().createQuery(builder.toString());
        
        if(SiriusValidator.validateLongParam(criteria.getOrganization()))
        	query.setParameter("org",criteria.getOrganization());

        if(SiriusValidator.validateParamWithZeroPosibility(criteria.getType()))
            query.setParameter("type",criteria.getType());

        if(SiriusValidator.validateParam(criteria.getImplementation()))
            query.setParameter("implementation", FacilityImplementation.valueOf(criteria.getImplementation()));
        
        Object object = query.uniqueResult();
        if(object != null)
            return (Long)object;
        
        return Long.valueOf(0);
    }

    @SuppressWarnings("unchecked")
    public Object execute()
    {
        FastList<Facility> list = new FastList<Facility>();

        FacilityFilterCriteria criteria =  (FacilityFilterCriteria)getFilterCriteria();

        StringBuilder builder = new StringBuilder();
        builder.append("SELECT DISTINCT(role.facility) FROM FacilityRole role WHERE role.id IS NOT NULL");
        
        if(SiriusValidator.validateLongParam(criteria.getOrganization()))
        	builder.append(" AND role.party.id =:org");
        
        if(SiriusValidator.validateParamWithZeroPosibility(criteria.getType()))
            builder.append(" AND role.facility.facilityType.id =:type");
        
        if(SiriusValidator.validateParam(criteria.getImplementation()))
            builder.append(" AND role.facility.implementation =:implementation");

        Query query = getSession().createQuery(builder.toString());
        
        if(SiriusValidator.validateLongParam(criteria.getOrganization()))
        	query.setParameter("org",criteria.getOrganization());

        if(SiriusValidator.validateParamWithZeroPosibility(criteria.getType()))
            query.setParameter("type",criteria.getType());
        
        if(SiriusValidator.validateParam(criteria.getImplementation()))
            query.setParameter("implementation", FacilityImplementation.valueOf(criteria.getImplementation()));
        
        list.addAll(query.list());

        return list;
    }

}

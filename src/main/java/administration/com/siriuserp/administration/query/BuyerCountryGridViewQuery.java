/**
 * Apr 14, 2009 3:23:56 PM
 * com.siriuserp.popup.controller
 * CompanyStructureStandardGridViewQuery.java
 */
package com.siriuserp.administration.query;

import org.hibernate.Query;

import com.siriuserp.administration.criteria.CoreTaxFilterCriteria;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public class BuyerCountryGridViewQuery extends AbstractGridViewQuery
{
   
    @Override
    public Query getQuery(ExecutorType type)
    {
    	CoreTaxFilterCriteria criteria = (CoreTaxFilterCriteria) getFilterCriteria();
    	StringBuilder builder = new StringBuilder();
    	
    	if(type.equals(ExecutorType.COUNT))
    		builder.append("SELECT COUNT(country) ");
    	
    	builder.append("FROM BuyerCountry country WHERE country.id IS NOT NULL ");
         
        if(SiriusValidator.validateParam(criteria.getCode()))
            builder.append("AND country.code LIKE :code ");
        
        if(SiriusValidator.validateParam(criteria.getDescription()))
            builder.append("AND country.description LIKE :description ");

        if(type.equals(ExecutorType.HQL))
        	builder.append("ORDER BY country.code ASC ");
         
        Query query = getSession().createQuery(builder.toString());
        query.setCacheable(true);
        
        if(SiriusValidator.validateParam(criteria.getCode()))
        	query.setParameter("code", "%" + criteria.getCode() + "%");
        
        if(SiriusValidator.validateParam(criteria.getDescription()))
        	query.setParameter("description", "%" + criteria.getDescription() + "%");
         
        return query;
    }
}

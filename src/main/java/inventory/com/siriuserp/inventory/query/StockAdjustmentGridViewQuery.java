/**
 * 
 */
package com.siriuserp.inventory.query;

import org.hibernate.Query;

import com.siriuserp.inventory.criteria.StockAdjustmentFilterCriteria;
import com.siriuserp.inventory.dm.StockAdjustment;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastList;

/**
 * @author ferdinand
 */

public class StockAdjustmentGridViewQuery extends AbstractGridViewQuery
{
	public Query getQuery(ExecutorType type) 
	{
		StockAdjustmentFilterCriteria criteria = (StockAdjustmentFilterCriteria)getFilterCriteria();
        StringBuilder builder = new StringBuilder();
        
        if(type.equals(ExecutorType.COUNT))
        	builder.append("SELECT COUNT(stock) ");
        
        builder.append("FROM StockAdjustment stock WHERE stock.id IS NOT NULL");
        
        if(SiriusValidator.validateParam(criteria.getCode()))
            builder.append(" AND stock.code like '%"+criteria.getCode()+"%'");
        
        if(SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()))
        	builder.append(" AND stock.organization.id = "+criteria.getOrganization());
        
        if(SiriusValidator.validateParamWithZeroPosibility(criteria.getFacility()))
            builder.append(" AND stock.facility.id = "+criteria.getFacility());
        
        if(SiriusValidator.validateDate(criteria.getDateFrom()))
        {
            if(SiriusValidator.validateDate(criteria.getDateTo()))
                builder.append(" AND stock.date BETWEEN :dateFrom AND :dateTo");
            else
                builder.append(" AND stock.date >= :dateFrom");
        }else if(SiriusValidator.validateDate(criteria.getDateTo()))
            builder.append(" AND stock.date <= :dateTo");

        if(type.equals(ExecutorType.HQL))
        	builder.append(" ORDER BY stock.date DESC, stock.id DESC");
        
        Query query = getSession().createQuery(builder.toString());
        query.setCacheable(true);
        query.setReadOnly(true);
//        query.setParameterList("orgs",getAccessibleOrganizations());
        
        if(SiriusValidator.validateDate(criteria.getDateFrom()))
            query.setParameter("dateFrom",criteria.getDateFrom());

        if(SiriusValidator.validateDate(criteria.getDateTo()))
            query.setParameter("dateTo",criteria.getDateTo());
        
        return query;
	}
	
    @Override
    public Long count()
    {
        if(!getAccessibleOrganizations().isEmpty())
        {
            Object object = getQuery(ExecutorType.COUNT).uniqueResult();
            if(object != null)
                return (Long)object;
        }
        
        return Long.valueOf(0);
    }

    @SuppressWarnings("unchecked")
    public Object execute()
    {
        FastList<StockAdjustment> list = new FastList<StockAdjustment>();
        
        if(!getAccessibleOrganizations().isEmpty())
        {
            Query query = getQuery(ExecutorType.HQL);
            query.setFirstResult(filterCriteria.start());
            query.setMaxResults(filterCriteria.getMax());
            
            list.addAll(query.list());
        }

        return list;
    }
}

package com.siriuserp.inventory.query;

import org.hibernate.Query;

import com.siriuserp.inventory.criteria.GoodsReceiptFilterCriteria;
import com.siriuserp.inventory.dm.GoodsReceipt;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastList;

/**
 * @author Andres Nodas
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public class GoodsReceiptManualGridViewQuery extends AbstractGridViewQuery {

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
        FastList<GoodsReceipt> list = new FastList<GoodsReceipt>();
        
        if(!getAccessibleOrganizations().isEmpty())
        {
            Query query = getQuery(ExecutorType.HQL);
            query.setFirstResult(filterCriteria.start());
            query.setMaxResults(filterCriteria.getMax());
            
            list.addAll(query.list());
        }
        
        return list;
    }

    public Query getQuery(ExecutorType type) 
    {
    	GoodsReceiptFilterCriteria criteria = (GoodsReceiptFilterCriteria)getFilterCriteria();
        StringBuilder builder = new StringBuilder();
        
        if(type.equals(ExecutorType.COUNT))
        	builder.append("SELECT COUNT(receipt.id) ");
        
        builder.append("FROM GoodsReceiptManual receipt ");
        builder.append("WHERE receipt.organization.id IS NOT NULL ");
        
        if(SiriusValidator.validateParam(criteria.getCode()))
            builder.append("AND receipt.code LIKE :code ");
        
        if(SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()))
        	builder.append("AND receipt.organization.id =:org ");
        
        if(SiriusValidator.validateParamWithZeroPosibility(criteria.getFacility()))
        	builder.append("AND receipt.facility.id =:facility ");
        
        if(SiriusValidator.validateDate(criteria.getDateFrom()))
        {
            if(SiriusValidator.validateDate(criteria.getDateTo()))
                builder.append("AND receipt.date BETWEEN :start AND :end ");
            else
                builder.append("AND receipt.date >= :start ");
        }else if(SiriusValidator.validateDate(criteria.getDateTo()))
            builder.append("AND receipt.date <= :end ");
        
        if(type.equals(ExecutorType.HQL))
        	builder.append("ORDER BY receipt.id DESC ");
        
        Query query = getSession().createQuery(builder.toString());
        query.setCacheable(true);
        query.setReadOnly(true);
        //query.setParameterList("orgs",getAccessibleOrganizations());
        
        if(SiriusValidator.validateParam(criteria.getCode()))
            query.setParameter("code","%"+criteria.getCode()+"%");
        
        if(SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()))
        	query.setParameter("org", criteria.getOrganization());
        
        if(SiriusValidator.validateParamWithZeroPosibility(criteria.getFacility()))
        	query.setParameter("facility", criteria.getFacility());
        
        if(SiriusValidator.validateDate(criteria.getDateFrom()))
            query.setParameter("start",criteria.getDateFrom());

        if(SiriusValidator.validateDate(criteria.getDateTo()))
            query.setParameter("end",criteria.getDateTo());
        
        return query;
    }

}

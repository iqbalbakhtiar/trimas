package com.siriuserp.production.query;

import org.hibernate.Query;

import com.siriuserp.inventory.criteria.ProductionOrderFilterCriteria;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author ferdinand
 */

public class ProductionOrderDetailGridViewQuery  extends AbstractGridViewQuery
{
	@Override
    public Query getQuery(ExecutorType executorType) 
	{
		ProductionOrderFilterCriteria criteria = (ProductionOrderFilterCriteria) getFilterCriteria();
		StringBuilder builder = new StringBuilder();
		
		if (executorType.equals(ExecutorType.COUNT))
			builder.append("SELECT COUNT(id) ");
		
		builder.append("FROM ProductionOrderDetail detail WHERE detail.id IS NOT NULL ");
		
		if (SiriusValidator.validateParam(criteria.getCode()))
            builder.append("AND detail.code LIKE :code ");
		
		if (SiriusValidator.validateDate(criteria.getDateFrom()))
        {
            if (SiriusValidator.validateDate(criteria.getDateTo()))
                builder.append("AND detail.date BETWEEN :startDate AND :endDate ");
            else
                builder.append("AND detail.date >= :startDate ");
        }
		else if (SiriusValidator.validateDate(criteria.getDateTo()))
            builder.append("AND detail.date <= :endDate ");
		
		builder.append("ORDER BY detail.id DESC, detail.date DESC ");
		
		Query query = getSession().createQuery(builder.toString());
        query.setReadOnly(true);
        
        if (SiriusValidator.validateParam(criteria.getCode()))
            query.setParameter("code", "%" + criteria.getCode() + "%");
        
        if (SiriusValidator.validateDate(criteria.getDateFrom()))
            query.setParameter("startDate", criteria.getDateFrom());

        if (SiriusValidator.validateDate(criteria.getDateTo()))
            query.setParameter("endDate", criteria.getDateTo());
        
        return query;
	}
}

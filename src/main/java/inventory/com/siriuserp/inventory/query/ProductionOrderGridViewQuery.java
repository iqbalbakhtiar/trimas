package com.siriuserp.inventory.query;

import org.hibernate.Query;

import com.siriuserp.inventory.criteria.ProductionOrderFilterCriteria;
import com.siriuserp.production.dm.ProductionOrderStatus;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author ferdinand
 */

public class ProductionOrderGridViewQuery  extends AbstractGridViewQuery
{
	@Override
    public Query getQuery(ExecutorType executorType) 
	{
		ProductionOrderFilterCriteria criteria = (ProductionOrderFilterCriteria) getFilterCriteria();
		StringBuilder builder = new StringBuilder();
		
		if (executorType.equals(ExecutorType.COUNT))
			builder.append("SELECT COUNT(id) ");
		
		builder.append("FROM ProductionOrder order WHERE order.id IS NOT NULL ");
		
		if (SiriusValidator.validateParam(criteria.getCode()))
            builder.append("AND order.code LIKE :code ");
		
		if (SiriusValidator.validateParam(criteria.getLotNumber()))
            builder.append("AND order.lotNumber LIKE :lotNumber ");
		
		if (SiriusValidator.validateParam(criteria.getStatus()))
			builder.append("AND order.status =:status ");
		
		if (SiriusValidator.validateDate(criteria.getDateFrom()))
        {
            if (SiriusValidator.validateDate(criteria.getDateTo()))
                builder.append("AND order.date BETWEEN :startDate AND :endDate ");
            else
                builder.append("AND order.date >= :startDate ");
        } else if (SiriusValidator.validateDate(criteria.getDateTo()))
            builder.append("AND order.date <= :endDate ");
		
		builder.append("ORDER BY order.id DESC, order.date DESC ");
		
		Query query = getSession().createQuery(builder.toString());
        query.setReadOnly(true);
        
        if (SiriusValidator.validateParam(criteria.getCode()))
            query.setParameter("code", "%" + criteria.getCode() + "%");
        
        if (SiriusValidator.validateParam(criteria.getLotNumber()))
            query.setParameter("lotNumber", "%" + criteria.getLotNumber() + "%");
        
        if (SiriusValidator.validateParam(criteria.getStatus()))
        	query.setParameter("status", ProductionOrderStatus.valueOf(criteria.getStatus()));
        
        if (SiriusValidator.validateDate(criteria.getDateFrom()))
            query.setParameter("startDate", criteria.getDateFrom());

        if (SiriusValidator.validateDate(criteria.getDateTo()))
            query.setParameter("endDate", criteria.getDateTo());
        
        return query;
	}
}

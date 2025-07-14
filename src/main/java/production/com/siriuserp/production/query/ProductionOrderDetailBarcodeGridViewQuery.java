package com.siriuserp.production.query;

import org.hibernate.Query;

import com.siriuserp.inventory.criteria.ProductionOrderDetailBarcodeFilterCriteria;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author ferdinand
 */

public class ProductionOrderDetailBarcodeGridViewQuery  extends AbstractGridViewQuery
{
	@Override
    public Query getQuery(ExecutorType executorType) 
	{
		ProductionOrderDetailBarcodeFilterCriteria criteria = (ProductionOrderDetailBarcodeFilterCriteria) getFilterCriteria();
		StringBuilder builder = new StringBuilder();
		
		if (executorType.equals(ExecutorType.COUNT))
			builder.append("SELECT COUNT(id) ");
		
		builder.append("FROM ProductionOrderDetailBarcode barcode WHERE barcode.id IS NOT NULL ");
		
		if (SiriusValidator.validateParam(criteria.getCode()))
            builder.append("AND barcode.code LIKE :code ");
		
		if (SiriusValidator.validateDate(criteria.getDateFrom()))
        {
            if (SiriusValidator.validateDate(criteria.getDateTo()))
                builder.append("AND barcode.date BETWEEN :startDate AND :endDate ");
            else
                builder.append("AND barcode.date >= :startDate ");
        } 
		else if (SiriusValidator.validateDate(criteria.getDateTo()))
            builder.append("AND barcode.date <= :endDate ");
		
		builder.append("ORDER BY barcode.id DESC, barcode.date DESC ");
		
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

/**
 * Jun 30, 2011
 * PurchaseOrderReportQuery.java
 */
package com.siriuserp.procurement.query;

import org.hibernate.Query;

import com.siriuserp.procurement.criteria.PurchaseOrderReportFilterCriteria;
import com.siriuserp.sdk.db.AbstractStandardReportQuery;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Iqbal
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public class PurchaseOrderReportQuery extends AbstractStandardReportQuery
{	
	@Override
	public Object execute() {
		
		PurchaseOrderReportFilterCriteria criteria = (PurchaseOrderReportFilterCriteria) getFilterCriteria();
		
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT new com.siriuserp.procurement.adapter.PurchaseOrderReportAdapter(po) ");
        builder.append("FROM PurchaseOrder po ");
        builder.append("WHERE po.organization.id =:org ");
        
        if(SiriusValidator.validateParamWithZeroPosibility(criteria.getSupplier()))
        	 builder.append("AND po.supplier.id = :supplier ");
        
        if (SiriusValidator.validateDate(criteria.getDateFrom()))
		{
			if (SiriusValidator.validateDate(criteria.getDateTo()))
				builder.append("AND po.date BETWEEN :from AND :to ");
			else
				builder.append("AND po.date >= :from ");
		} else if (SiriusValidator.validateDate(criteria.getDateTo()))
			builder.append("AND po.date <= :to ");
        
        if (SiriusValidator.validateParam(criteria.getSortBy()))
        	builder.append("ORDER BY po." + criteria.getSortBy() + " ASC, po.id ");
        else
        	builder.append("ORDER BY po.date ASC, po.id ");

        Query query = getSession().createQuery(builder.toString());
        query.setReadOnly(true);
        query.setParameter("org",criteria.getOrganization());
        
        if(SiriusValidator.validateParamWithZeroPosibility(criteria.getSupplier()))
       	 	query.setParameter("supplier", criteria.getSupplier());
        
        if(SiriusValidator.validateDate(criteria.getDateFrom()))
        	query.setParameter("from", criteria.getDateFrom());
        
        if(SiriusValidator.validateDate(criteria.getDateTo()))
        	query.setParameter("to",criteria.getDateTo());

        return query.list();
		
	}

}

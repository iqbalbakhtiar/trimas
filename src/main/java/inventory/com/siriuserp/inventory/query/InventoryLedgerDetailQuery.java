/**
 * Mar 5, 2010 9:17:43 AM
 * com.siriuserp.inventory.query
 * InventoryLedgerDetailQuery.java
 */
package com.siriuserp.inventory.query;

import java.math.BigDecimal;

import org.hibernate.Query;

import com.siriuserp.inventory.adapter.InventoryLedgerAdapter;
import com.siriuserp.inventory.criteria.InventoryLedgerFilterCriteria;
import com.siriuserp.sdk.db.AbstractStandardReportQuery;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastList;
import javolution.util.FastMap;

/**
 * @author Muhammad Rizal
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@SuppressWarnings("unchecked")
public class InventoryLedgerDetailQuery extends AbstractStandardReportQuery
{
    @Override
    public Object execute()
    {
        InventoryLedgerFilterCriteria criteria = (InventoryLedgerFilterCriteria)getFilterCriteria();

        FastList<FastMap<String,Object>> reports = new FastList<FastMap<String,Object>>();
       
        for(InventoryLedgerAdapter adapter : getOpening(criteria))
        {	
        	FastList<FastMap<String, Object>> transactions = new FastList<FastMap<String,Object>>();
            
        	BigDecimal in = BigDecimal.ZERO;
        	BigDecimal out = BigDecimal.ZERO;
        	BigDecimal sum = adapter.getIn().subtract(adapter.getOut());
        	
	        for(InventoryLedgerAdapter detail: getTransactionsLong(adapter, criteria))
		        if(detail.getIn().add(detail.getOut()).compareTo(BigDecimal.ZERO) != 0)
		        {
			        sum = sum.add(detail.getIn().subtract(detail.getOut()));
			        in = in.add(detail.getIn());
			        out = out.add(detail.getOut());
			            
			        FastMap<String,Object> tMap = new FastMap<String, Object>();
			        tMap.put("date", detail.getDate());
			        tMap.put("reference", detail.getReference());
			        tMap.put("warehouse", detail.getDescription());
			        tMap.put("refid", detail.getReferenceId());
			        tMap.put("warid", detail.getDescriptionId());
			        tMap.put("reftype", detail.getReferenceType());
			        tMap.put("wartype", detail.getDescriptionType());
			        tMap.put("refuri", detail.getReferenceUri());
			        tMap.put("waruri", detail.getDescriptionUri());
			        tMap.put("note", detail.getNote() == null ? " - " : detail.getNote());
			        tMap.put("in", detail.getIn());
			        tMap.put("out", detail.getOut());
			        tMap.put("sum", sum); 
			               
			        transactions.add(tMap);
		        }

	
	        if(sum.compareTo(BigDecimal.ZERO) > 0 || in.compareTo(BigDecimal.ZERO) > 0 || out.compareTo(BigDecimal.ZERO) > 0)
	        {
	        	FastMap<String,Object> pMap = new FastMap<String, Object>();
	            pMap.put("facility", adapter.getFacilityName());
	           	pMap.put("container", adapter.getContainerName());
	            pMap.put("code", adapter.getProductCode());
	            pMap.put("product", adapter.getProductName());
	            pMap.put("opening", adapter.getIn().subtract(adapter.getOut()));
	            pMap.put("in", in);
	            pMap.put("out", out);
	            pMap.put("sum", sum);
	            pMap.put("reserved",adapter.getReserved());
	            pMap.put("transactions", transactions);
 
	            reports.add(pMap);
	        }
        }

        return reports;
    }
    
    private FastList<InventoryLedgerAdapter> getOpening(InventoryLedgerFilterCriteria criteria)
    {
        FastList<InventoryLedgerAdapter> adapters = new FastList<InventoryLedgerAdapter>();
        
    	StringBuilder builder = new StringBuilder();
        builder.append("SELECT new com.siriuserp.inventory.adapter.InventoryLedgerAdapter("
        	+ "SUM(CASE WHEN balance.date < :date THEN balance.in ELSE 0 END ), "
        	+ "SUM(CASE WHEN balance.date < :date THEN balance.out ELSE 0 END ), "
        	+ "balance.facilityName, balance.containerName, balance.productCode, balance.productName,  "
        	+ "balance.facilityId, balance.containerId, balance.productId, "
        	+ "(SELECT(SUM(inv.reserved))FROM InventoryItem inv WHERE inv.product.id = balance.productId AND inv.container.id = balance.containerId), "
    		+ "(SELECT grid FROM Grid grid WHERE grid.id = balance.gridId)) ");
        builder.append("FROM DWInventoryItemBalanceDetail balance WHERE balance.id IS NOT NULL ");
     
        if(SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()))
            builder.append("AND balance.organizationId =:organization ");

	    if(SiriusValidator.validateParamWithZeroPosibility(criteria.getFacility()))
	    	builder.append("AND balance.facilityId =:facility ");
       
	    if(SiriusValidator.validateParamWithZeroPosibility(criteria.getProduct()))
	      	builder.append("AND balance.productId =:product ");
		
	    if(SiriusValidator.validateParamWithZeroPosibility(criteria.getContainer()))
	      	builder.append("AND balance.containerId =:container ");
        
	    builder.append("GROUP BY balance.containerId, balance.productId");
        
        Query query = getSession().createQuery(builder.toString());
        query.setReadOnly(true);
        query.setParameter("date",criteria.getDateFrom());

        if(SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()))
            query.setParameter("organization",criteria.getOrganization());

	    if(SiriusValidator.validateParamWithZeroPosibility(criteria.getFacility()))
	    	query.setParameter("facility", criteria.getFacility());
        
	    if(SiriusValidator.validateParamWithZeroPosibility(criteria.getProduct()))
	        query.setParameter("product", criteria.getProduct());
            
        adapters.addAll(query.list());

        return adapters;
    }
    
    private FastList<InventoryLedgerAdapter> getTransactionsLong(InventoryLedgerAdapter adapter, InventoryLedgerFilterCriteria criteria)
    {
        FastList<InventoryLedgerAdapter> list = new FastList<InventoryLedgerAdapter>();
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT new com.siriuserp.inventory.adapter.InventoryLedgerAdapter(SUM(balance.in), SUM(balance.out), balance.date, "
        	+ "balance.warehouseCode, balance.referenceCode, "
        	+ "balance.warehouseId, balance.referenceId, "
        	+ "balance.warehouseType, balance.referenceType, "
        	+ "balance.warehouseUri, balance.referenceUri, balance.note) FROM DWInventoryItemBalanceDetail balance ");
        
        builder.append("WHERE balance.productId =:productId ");
        builder.append("AND balance.containerId =:containerId ");
        builder.append("AND balance.date >= :start AND balance.date <= :end ");
     
        if(SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()))
            builder.append("AND balance.organizationId =:organization ");

	    if(SiriusValidator.validateParamWithZeroPosibility(criteria.getFacility()))
	    	builder.append("AND balance.facilityId =:facility ");
        
        builder.append("GROUP BY balance.referenceCode ORDER BY balance.date, balance.uom, balance.productName ASC, balance.referenceType DESC, balance.referenceCode ASC");
        
        Query query = getSession().createQuery(builder.toString());
        query.setReadOnly(true);
        query.setParameter("productId", adapter.getProductId());
        query.setParameter("containerId", adapter.getContainerId());
        query.setParameter("start", criteria.getDateFrom());
        query.setParameter("end", criteria.getDateTo());
        
        if(SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()))
            query.setParameter("organization", criteria.getOrganization());
               
	    if(SiriusValidator.validateParamWithZeroPosibility(criteria.getFacility()))
	    	query.setParameter("facility", criteria.getFacility());
        
        list.addAll(query.list());
        
        return list;
    }
}

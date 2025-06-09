package com.siriuserp.inventory.query;

import com.siriuserp.inventory.adapter.WasteReportAdapter;
import com.siriuserp.inventory.criteria.WasteReportFilterCriteria;
import com.siriuserp.sdk.db.AbstractStandardReportQuery;
import com.siriuserp.sdk.utility.SiriusValidator;
import javolution.util.FastList;
import org.hibernate.Query;

/**
 * @author Rama Almer Felix
 * Sirius Indonesia, PT
 * www.siriuserp.com
 * Version 1.5
 */
@SuppressWarnings("unchecked")
public class WasteReportQuery extends AbstractStandardReportQuery {
    @Override
    public Object execute() {
        WasteReportFilterCriteria criteria = (WasteReportFilterCriteria) getFilterCriteria();

		FastList<WasteReportAdapter> details = getReports(criteria);

		WasteReportAdapter root = new WasteReportAdapter();
		root.getAdapters().addAll(details);
		return root;
    }
    
    public FastList<WasteReportAdapter> getReports(WasteReportFilterCriteria criteria)
	{
		FastList<WasteReportAdapter> adapters = new FastList<WasteReportAdapter>();

		StringBuilder builder = new StringBuilder();
		builder.append("SELECT new com.siriuserp.inventory.adapter.WasteReportAdapter(");
		builder.append("SUM( CASE WHEN balance.date < :start THEN (balance.in-balance.out) ELSE 0 END ), ");
		builder.append("SUM( CASE WHEN balance.date BETWEEN :start AND :end THEN balance.in ELSE 0 END ), ");
		builder.append("SUM( CASE WHEN balance.date BETWEEN :start AND :end THEN balance.out ELSE 0 END ), ");
		builder.append("(");
		builder.append("    SUM(CASE WHEN balance.date < :start AND balance.in  > 0 AND balance.serial IS NOT NULL THEN 1 ELSE 0 END)");
		builder.append("   - SUM(CASE WHEN balance.date < :start AND balance.out > 0 AND balance.serial IS NOT NULL THEN 1 ELSE 0 END)");
		builder.append("), "); // openingSerial
		builder.append("SUM(CASE WHEN balance.date BETWEEN :start AND :end AND balance.in  > 0 AND balance.serial IS NOT NULL THEN 1 ELSE 0 END), "); // inSerial
		builder.append("SUM(CASE WHEN balance.date BETWEEN :start AND :end AND balance.out > 0 AND balance.serial IS NOT NULL THEN 1 ELSE 0 END), "); // outSerial
		builder.append("balance.productCode, balance.productName, balance.productId, balance.uom ");
		builder.append(") ");
		builder.append("FROM DWInventoryItemBalanceDetail balance ");
		builder.append("WHERE balance.date <=:end ");
		builder.append("AND balance.productCategoryName = 'WASTE' ");

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()))
			builder.append("AND balance.organizationId =:organization ");

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getFacility()))
			builder.append("AND balance.facilityId =:facility ");

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getContainer()))
			builder.append("AND balance.containerId =:container ");

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getProduct()))
			builder.append("AND balance.productId =:product ");

		builder.append("GROUP BY balance.productId ");
		builder.append("ORDER BY balance.productName ASC");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setReadOnly(true);
		query.setParameter("start", criteria.getDateFrom());
		query.setParameter("end", criteria.getDateTo());

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()))
			query.setParameter("organization", criteria.getOrganization());

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getFacility()))
			query.setParameter("facility", criteria.getFacility());

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getContainer()))
			query.setParameter("container", criteria.getContainer());

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getProduct()))
			query.setParameter("product", criteria.getProduct());

		adapters.addAll(query.list());

		return adapters;
	}
}

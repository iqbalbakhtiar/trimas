/**
 * Mar 3, 2010 5:25:15 PM
 * com.siriuserp.inventory.query
 * InventoryLedgerQuery.java
 */
package com.siriuserp.inventory.query;

import org.hibernate.Query;

import com.siriuserp.inventory.adapter.InventoryLedgerAdapter;
import com.siriuserp.inventory.criteria.InventoryLedgerFilterCriteria;
import com.siriuserp.sdk.db.AbstractStandardReportQuery;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastList;

/**
 * @author Muhammad Rizal
 * Sirius Indonesia, PT
 * www.siriuserp.com
 * Version 1.5
 */
@SuppressWarnings("unchecked")
public class InventoryLedgerSummaryReportQuery extends AbstractStandardReportQuery
{
	@Override
	public Object execute()
	{
		FastList<InventoryLedgerAdapter> list = new FastList<InventoryLedgerAdapter>();

		InventoryLedgerFilterCriteria criteria = (InventoryLedgerFilterCriteria) getFilterCriteria();

		InventoryLedgerAdapter adapter = null;

		for (InventoryLedgerAdapter detail : getAdapters(criteria))
		{
			if (adapter != null && adapter.getFacilityId().compareTo(detail.getFacilityId()) == 0)
				adapter.getAdapters().add(detail);
			else
			{
				adapter = new InventoryLedgerAdapter();
				adapter.setFacilityName(detail.getFacilityName());
				adapter.setFacilityId(detail.getFacilityId());
				adapter.getAdapters().add(detail);

				list.add(adapter);
			}
		}

		return list;
	}

	public FastList<InventoryLedgerAdapter> getAdapters(InventoryLedgerFilterCriteria criteria)
	{
		FastList<InventoryLedgerAdapter> adapters = new FastList<InventoryLedgerAdapter>();

		StringBuilder builder = new StringBuilder();
		builder.append("SELECT new com.siriuserp.inventory.adapter.InventoryLedgerAdapter(SUM(CASE WHEN balance.date < :start THEN (balance.in-balance.out) ELSE 0 END), ");
		builder.append("SUM(CASE WHEN balance.date BETWEEN :start AND :end THEN balance.in ELSE 0 END), ");
		builder.append("SUM(CASE WHEN balance.date BETWEEN :start AND :end THEN balance.out ELSE 0 END), ");
		builder.append("balance.facilityId, balance.facilityName, balance.gridName, balance.containerName, ");
		builder.append("balance.lotCode, balance.productId, balance.productCode, balance.productName, ");
		builder.append("balance.productCategoryId, balance.productCategoryName, balance.uom) ");
		builder.append("FROM DWInventoryItemBalanceDetail balance ");
		builder.append("WHERE balance.date <=:end ");

		if (SiriusValidator.validateLongParam(criteria.getOrganization()))
			builder.append("AND balance.organizationId =:organization ");

		if (SiriusValidator.validateLongParam(criteria.getFacility()))
			builder.append("AND balance.facilityId =:facility ");

		if (SiriusValidator.validateLongParam(criteria.getContainer()))
			builder.append("AND balance.containerId =:container ");

		if (SiriusValidator.validateLongParam(criteria.getProductCategory()))
			builder.append("AND balance.productCategoryId =:productCategory ");

		if (SiriusValidator.validateLongParam(criteria.getProduct()))
			builder.append("AND balance.productId =:product ");

		if (SiriusValidator.validateParam(criteria.getLotCode()))
			builder.append("AND balance.lotCode =:lotCode ");

		builder.append("GROUP BY balance.facilityId, balance.containerId, balance.lotCode, balance.productId ");
		builder.append("ORDER BY balance.containerName ASC, balance.productName ASC, balance.lotCode ASC, balance.productCategoryName ASC");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setReadOnly(true);
		query.setParameter("start", criteria.getDateFrom());
		query.setParameter("end", criteria.getDateTo());

		if (SiriusValidator.validateLongParam(criteria.getOrganization()))
			query.setParameter("organization", criteria.getOrganization());

		if (SiriusValidator.validateLongParam(criteria.getFacility()))
			query.setParameter("facility", criteria.getFacility());

		if (SiriusValidator.validateLongParam(criteria.getContainer()))
			query.setParameter("container", criteria.getContainer());

		if (SiriusValidator.validateLongParam(criteria.getProductCategory()))
			query.setParameter("productCategory", criteria.getProductCategory());

		if (SiriusValidator.validateLongParam(criteria.getProduct()))
			query.setParameter("product", criteria.getProduct());

		if (SiriusValidator.validateParam(criteria.getLotCode()))
			query.setParameter("lotCode", criteria.getLotCode());

		adapters.addAll(query.list());

		return adapters;
	}
}

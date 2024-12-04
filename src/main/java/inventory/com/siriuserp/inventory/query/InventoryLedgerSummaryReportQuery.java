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
				adapter.setGrid(detail.getGrid());
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
		builder.append("SELECT new com.siriuserp.inventory.adapter.InventoryLedgerAdapter(SUM( CASE WHEN balance.date < :start THEN (balance.in-balance.out) ELSE 0 END ), ");
		builder.append("SUM( CASE WHEN balance.date BETWEEN :start AND :end THEN balance.in ELSE 0 END ), ");
		builder.append("SUM( CASE WHEN balance.date BETWEEN :start AND :end THEN balance.out ELSE 0 END ), ");
		builder.append("balance.facilityName, balance.gridName, balance.containerName, balance.productCode, balance.productName, balance.uom, balance.facilityId, ");
		builder.append("grid) ");
		builder.append("FROM DWInventoryItemBalanceDetail balance, Grid grid ");
		builder.append("WHERE balance.gridId = grid.id AND balance.date <=:end ");

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()))
			builder.append("AND balance.organizationId =:organization ");

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getFacility()))
			builder.append("AND balance.facilityId =:facility ");

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getContainer()))
			builder.append("AND balance.containerId =:container ");

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getProduct()))
			builder.append("AND balance.productId =:product ");

		builder.append("GROUP BY balance.facilityId, balance.containerId, balance.productId ");
		builder.append("ORDER BY balance.containerName ASC, balance.productCode ASC, balance.productName ASC");

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

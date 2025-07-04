package com.siriuserp.inventory.query;

import com.siriuserp.inventory.adapter.InventoryLedgerAdapter;
import com.siriuserp.inventory.criteria.InventoryLedgerFilterCriteria;
import com.siriuserp.sdk.db.AbstractStandardReportQuery;
import com.siriuserp.sdk.utility.SiriusValidator;
import javolution.util.FastList;
import org.hibernate.Query;

/**
 * @author Rama Almer Felix
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@SuppressWarnings("unchecked")
public class MutationReportQuery extends AbstractStandardReportQuery
{
	@Override
	public Object execute()
	{
		InventoryLedgerFilterCriteria criteria = (InventoryLedgerFilterCriteria) getFilterCriteria();

		FastList<InventoryLedgerAdapter> reports = getProductSummary(criteria);

		return reports;
	}

	private FastList<InventoryLedgerAdapter> getProductSummary(InventoryLedgerFilterCriteria criteria)
	{
		FastList<InventoryLedgerAdapter> list = new FastList<>();

		StringBuilder builder = new StringBuilder();
		builder.append("SELECT new com.siriuserp.inventory.adapter.InventoryLedgerAdapter(");
		builder.append("SUM(balance.in), ");
		builder.append("SUM(balance.out), ");
		builder.append("SUM(balance.cogs), ");
		builder.append("balance.productCode, ");
		builder.append("balance.productName) ");
		builder.append("FROM DWInventoryItemBalanceDetail balance, ProductCategory cat ");
		builder.append("WHERE cat.id = balance.productCategoryId ");
		builder.append("AND balance.date >= :start ");
		builder.append("AND balance.date <= :end ");
		builder.append("AND cat.categoryType = 'SPAREPART' ");

		if (SiriusValidator.validateLongParam(criteria.getOrganization()))
			builder.append("AND balance.organizationId =:org ");

		if (SiriusValidator.validateLongParam(criteria.getContainer()))
			builder.append("AND balance.containerId =:containerId ");

		builder.append("GROUP BY balance.productId, balance.cogs ");
		builder.append("ORDER BY balance.productName ASC");

		Query query = getSession().createQuery(builder.toString());
		query.setReadOnly(true);
		query.setParameter("start", criteria.getDateFrom());
		query.setParameter("end", criteria.getDateTo());

		if (SiriusValidator.validateLongParam(criteria.getOrganization()))
			query.setParameter("org", criteria.getOrganization());

		if (SiriusValidator.validateLongParam(criteria.getContainer()))
			query.setParameter("containerId", criteria.getContainer());

		list.addAll(query.list());

		return list;
	}
}

package com.siriuserp.inventory.query;

import org.hibernate.Query;

import com.siriuserp.inventory.criteria.InventoryLedgerFilterCriteria;
import com.siriuserp.inventory.dm.ProductInOutTransaction;
import com.siriuserp.sdk.db.AbstractStandardReportQuery;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastList;

/**
 * @author ferdinand
 */

@SuppressWarnings("unchecked")
public class OnHandQuantityByDateReportQuery extends AbstractStandardReportQuery
{
	@Override
	public Object execute()
	{
		FastList<ProductInOutTransaction> list = new FastList<ProductInOutTransaction>();
		InventoryLedgerFilterCriteria criteria = (InventoryLedgerFilterCriteria) getFilterCriteria();
		StringBuilder builder = new StringBuilder();

		builder.append("SELECT NEW com.siriuserp.inventory.adapter.InventoryLedgerAdapter(SUM(inOut.quantity), SUM(inOut.receipted), ");
		builder.append("inOut.product.name, inOut.product.productCategory.categoryType, inOut.code, inOut.container.name, inOut.date) ");
		builder.append("FROM ProductInOutTransaction inOut ");
		builder.append("WHERE inOut.organization.id =:org ");
		builder.append("AND inOut.date BETWEEN :dateFrom AND :dateTo ");

		if (SiriusValidator.validateLongParam(criteria.getFacility()))
			builder.append("AND inOut.container.grid.facility.id =:facility ");

		if (SiriusValidator.validateLongParam(criteria.getProductCategory()))
			builder.append("AND inOut.product.productCategory.id =:productCategory ");

		builder.append("GROUP BY inOut.date, inOut.code, inOut.product.id, inOut.container.id ");
		builder.append("ORDER BY inOut.date, inOut.code, inOut.product.name, inOut.container.name ASC");

		Query query = getSession().createQuery(builder.toString());
		query.setReadOnly(true);
		query.setParameter("org", criteria.getOrganization());
		query.setParameter("dateFrom", criteria.getDateFrom());
		query.setParameter("dateTo", criteria.getDateTo());

		if (SiriusValidator.validateLongParam(criteria.getFacility()))
			query.setParameter("facility", criteria.getFacility());

		if (SiriusValidator.validateLongParam(criteria.getProductCategory()))
			query.setParameter("productCategory", criteria.getProductCategory());

		list.addAll(query.list());

		return list;
	}
}

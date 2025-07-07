package com.siriuserp.procurement.query;

import com.siriuserp.procurement.criteria.PurchaseReportFilterCriteria;
import com.siriuserp.sdk.db.AbstractStandardReportQuery;
import com.siriuserp.sdk.utility.SiriusValidator;
import org.hibernate.Query;

/**
 * @author Rama Almer Felix
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public class PurchaseMonthlyReportQuery extends AbstractStandardReportQuery {
	@Override
	public Object execute() {
		PurchaseReportFilterCriteria criteria = (PurchaseReportFilterCriteria) getFilterCriteria();

		StringBuilder builder = new StringBuilder();
		builder.append("SELECT NEW com.siriuserp.procurement.adapter.PurchaseMonthlyReportAdapter(")
				.append("item.product, ")
				.append("SUM(item.quantity), ")
				.append("SUM(item.quantity * item.money.amount), ")
				.append("po.purchaseDocumentType) ");
		builder.append("FROM PurchaseOrderItem item ");
		builder.append("JOIN item.purchaseOrder po ");
		builder.append("WHERE po.organization.id = :org ");
		builder.append("AND item.purchaseItemType = 'BASE' ");

		if (SiriusValidator.validateDate(criteria.getDateFrom()))
		{
			if (SiriusValidator.validateDate(criteria.getDateTo()))
				builder.append("AND po.date BETWEEN :from AND :to ");
			else
				builder.append("AND po.date >= :from ");
		} else if (SiriusValidator.validateDate(criteria.getDateTo()))
			builder.append("AND po.date <= :to ");

		builder.append("GROUP BY item.product, po.purchaseDocumentType ");
		builder.append("ORDER BY item.product.name ASC");

		Query query = getSession().createQuery(builder.toString());
		query.setReadOnly(true);
		query.setParameter("org", criteria.getOrganization());

		if (SiriusValidator.validateDate(criteria.getDateFrom()))
			query.setParameter("from", criteria.getDateFrom());

		if (SiriusValidator.validateDate(criteria.getDateTo()))
			query.setParameter("to", criteria.getDateTo());

		return query.list();
	}
}

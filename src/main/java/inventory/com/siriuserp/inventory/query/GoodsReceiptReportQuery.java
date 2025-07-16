package com.siriuserp.inventory.query;

import com.siriuserp.inventory.criteria.InventoryLedgerFilterCriteria;
import com.siriuserp.procurement.dm.PurchaseDocumentType;
import com.siriuserp.sdk.db.AbstractStandardReportQuery;
import com.siriuserp.sdk.utility.SiriusValidator;
import org.hibernate.Query;

/**
 * @author Rama Almer Felix
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@SuppressWarnings("unchecked")
public class GoodsReceiptReportQuery extends AbstractStandardReportQuery {
	@Override
	public Object execute() {
		InventoryLedgerFilterCriteria criteria = (InventoryLedgerFilterCriteria) getFilterCriteria();

		StringBuilder builder = new StringBuilder();
		builder.append("SELECT NEW com.siriuserp.inventory.adapter.GoodsReceiptReportAdapter(");
		builder.append("  poi.product, ");
		builder.append("  poi, ");
		builder.append("  wti.receipted, ");
		builder.append("  poi.money.amount ");
		builder.append(") ");
		builder.append("FROM WarehouseTransactionItem wti, PurchaseOrderItem poi ");
		builder.append("WHERE wti.type = 'IN' ");
		builder.append("AND wti.receipted > 0 ");
		builder.append("AND wti.referenceItem = poi ");
		builder.append("AND poi.purchaseOrder.organization.id = :org ");

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getSupplier()))
			builder.append("AND poi.purchaseOrder.supplier.id = :supplier ");

		if (SiriusValidator.validateDate(criteria.getDateFrom())) {
			if (SiriusValidator.validateDate(criteria.getDateTo()))
				builder.append("AND poi.purchaseOrder.date BETWEEN :from AND :to ");
			else
				builder.append("AND poi.purchaseOrder.date >= :from ");
		} else if (SiriusValidator.validateDate(criteria.getDateTo())) {
			builder.append("AND poi.purchaseOrder.date <= :to ");
		}

		if (SiriusValidator.validateParam(criteria.getReference()))
			builder.append("AND poi.purchaseOrder.purchaseDocumentType = :documentType ");

		builder.append("ORDER BY poi.purchaseOrder.date ASC, ");
		builder.append("         poi.product.name ASC");

		Query query = getSession().createQuery(builder.toString());
		query.setReadOnly(true);
		query.setParameter("org", criteria.getOrganization());

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getSupplier()))
			query.setParameter("supplier", criteria.getSupplier());

		if (SiriusValidator.validateDate(criteria.getDateFrom()))
			query.setParameter("from", criteria.getDateFrom());

		if (SiriusValidator.validateDate(criteria.getDateTo()))
			query.setParameter("to", criteria.getDateTo());

		if (SiriusValidator.validateParam(criteria.getReference()))
			query.setParameter("documentType", PurchaseDocumentType.valueOf(criteria.getReference()));

		return query.list();
	}
}

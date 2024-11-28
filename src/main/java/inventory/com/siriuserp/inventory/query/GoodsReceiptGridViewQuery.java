package com.siriuserp.inventory.query;

import com.siriuserp.inventory.criteria.GoodsReceiptFilterCriteria;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.utility.SiriusValidator;
import org.hibernate.Query;

public class GoodsReceiptGridViewQuery extends AbstractGridViewQuery {
    @Override
    public Query getQuery(ExecutorType executorType) {
        GoodsReceiptFilterCriteria criteria = (GoodsReceiptFilterCriteria) getFilterCriteria();

        StringBuilder builder = new StringBuilder();

        if (executorType.equals(ExecutorType.COUNT))
            builder.append("SELECT COUNT(DISTINCT gr) ");
        else
            builder.append("SELECT DISTINCT(gr) ");

        builder.append("FROM GoodsReceipt gr INNER JOIN gr.items item WHERE 1=1 ");

        if (SiriusValidator.validateParam(criteria.getCode()))
            builder.append("AND gr.code LIKE :code ");

        if (SiriusValidator.validateParam(criteria.getSource()))
            builder.append("AND item.warehouseTransactionItem.referenceItem.referenceFrom LIKE :source ");

        if (SiriusValidator.validateDate(criteria.getDateFrom()))
        {
            if (SiriusValidator.validateDate(criteria.getDateTo()))
                builder.append("AND gr.date BETWEEN :startDate AND :endDate ");
            else
                builder.append("AND gr.date >= :startDate ");
        } else if (SiriusValidator.validateDate(criteria.getDateTo()))
            builder.append("AND gr.date <= :endDate ");

        if (SiriusValidator.validateParam(criteria.getOrg())) {
            builder.append("AND gr.organization.fullName LIKE :org ");
        }

        if (SiriusValidator.validateParam(criteria.getCreatedBy())) {
            builder.append("AND gr.createdBy.fullName LIKE :createdby ");
        }

        if (SiriusValidator.validateParam(criteria.getReference())) {
            builder.append("AND item.warehouseTransactionItem.referenceItem.referenceCode LIKE :reference ");
        }

        if (SiriusValidator.validateParam(criteria.getCreatedBy())) {
            builder.append("AND gr.createdBy.fullName LIKE :createdby ");
        }

        builder.append(" ORDER BY gr.id DESC ");

        Query query = getSession().createQuery(builder.toString());
        query.setReadOnly(true);

        if (SiriusValidator.validateParam(criteria.getCode()))
            query.setParameter("code", "%" + criteria.getCode() + "%");

        if (SiriusValidator.validateParam(criteria.getSource()))
            query.setParameter("source", "%" + criteria.getSource() + "%");

        if (SiriusValidator.validateDate(criteria.getDateFrom()))
            query.setParameter("startDate", criteria.getDateFrom());

        if (SiriusValidator.validateDate(criteria.getDateTo()))
            query.setParameter("endDate", criteria.getDateTo());

        if (SiriusValidator.validateParam(criteria.getOrg()))
            query.setParameter("org", "%" + criteria.getOrg() + "%");

        if (SiriusValidator.validateParam(criteria.getCreatedBy()))
            query.setParameter("createdby", "%" + criteria.getCreatedBy() + "%");

        if (SiriusValidator.validateParam(criteria.getReference()))
            query.setParameter("reference", "%" + criteria.getReference() + "%");

        if (SiriusValidator.validateParam(criteria.getCreatedBy()))
            query.setParameter("createdby", "%" + criteria.getCreatedBy() + "%");

        return query;
    }
}

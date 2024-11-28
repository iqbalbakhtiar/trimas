package com.siriuserp.inventory.query;

import com.siriuserp.inventory.criteria.GoodsReceiptFilterCriteria;
import com.siriuserp.inventory.dm.WarehouseTransactionType;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.utility.SiriusValidator;
import com.siriuserp.sdk.utility.WarehouseSourceHelper;
import org.hibernate.Query;

public class ReceiptableGridViewQuery extends AbstractGridViewQuery {
    @Override
    public Query getQuery(ExecutorType type)
    {
        if(ExecutorType.HQL.equals(type)) {
            GoodsReceiptFilterCriteria criteria = (GoodsReceiptFilterCriteria) getFilterCriteria();

            StringBuilder builder = new StringBuilder();
            builder.append("SELECT new com.siriuserp.inventory.adapter.WarehouseItemAdapter(trx) ");
            builder.append("FROM WarehouseTransactionItem trx WHERE trx.unreceipted > 0");
            builder.append(" AND trx.type =:type");
            builder.append(" AND trx.locked =:locked");

            if (SiriusValidator.validateParam(criteria.getReferenceType()))
                builder.append(" AND trx.transactionSource IN(:sources)");

            if (SiriusValidator.validateParam(criteria.getOrganization()))
                builder.append(" AND trx.referenceItem.organization.id =:org");

            if (SiriusValidator.validateParam(criteria.getFacility()))
                builder.append(" AND trx.referenceItem.destination.id =:fac");

            if (SiriusValidator.validateParam(criteria.getSupplier()))
                builder.append(" AND trx.referenceItem.party.id =:supplier");

            if (SiriusValidator.validateParam(criteria.getCurrency()))
                builder.append(" AND trx.referenceItem.currency.id =:currency");

            if (SiriusValidator.validateParam(criteria.getTax()))
                builder.append(" AND trx.referenceItem.tax.id =:tax");

            builder.append(" ORDER BY trx.referenceItem.date DESC, trx.id DESC, trx.referenceItem.referenceCode DESC");

            Query query = getSession().createQuery(builder.toString());
            query.setParameter("locked", Boolean.FALSE);
            query.setParameter("type", WarehouseTransactionType.IN);

            if (SiriusValidator.validateParam(criteria.getReferenceType()))
                query.setParameterList("sources", WarehouseSourceHelper.getVarians(criteria.getReferenceType()));

            if (SiriusValidator.validateParam(criteria.getOrganization()))
                query.setParameter("org", criteria.getOrganization());

            if (SiriusValidator.validateParam(criteria.getFacility()))
                query.setParameter("fac", criteria.getFacility());

            if (SiriusValidator.validateParam(criteria.getSupplier()))
                query.setParameter("supplier", criteria.getSupplier());

            if (SiriusValidator.validateParam(criteria.getCurrency()))
                query.setParameter("currency", criteria.getCurrency());

            if (SiriusValidator.validateParam(criteria.getTax()))
                query.setParameter("tax", criteria.getTax());

            return query;
        }

        return null;
    }
}

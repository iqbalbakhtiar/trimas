package com.siriuserp.inventory.query;

import org.hibernate.Query;

import com.siriuserp.inventory.criteria.TransferOrderFilterCriteria;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author ferdinand
 */

public class TransferOrderGridViewQuery extends AbstractGridViewQuery
{
    @Override
    public Query getQuery(ExecutorType type)
    {
        TransferOrderFilterCriteria criteria = (TransferOrderFilterCriteria) getFilterCriteria();
        StringBuilder builder = new StringBuilder();

        if (type.equals(ExecutorType.COUNT))
            builder.append("SELECT COUNT(transfer) ");

        builder.append("FROM TransferOrder transfer WHERE transfer.id IS NOT NULL");

        if (SiriusValidator.validateParam(criteria.getCode()))
            builder.append(" AND transfer.code LIKE :code ");

        if (SiriusValidator.validateDate(criteria.getDateFrom()))
        {
            if (SiriusValidator.validateDate(criteria.getDateTo()))
                builder.append(" AND transfer.date BETWEEN :dateFrom AND :dateTo");
            else
                builder.append(" AND transfer.date >= :dateFrom");
        }
        else if (SiriusValidator.validateDate(criteria.getDateTo()))
            builder.append(" AND transfer.date <= :dateTo");

        if (type.equals(ExecutorType.HQL))
            builder.append(" ORDER BY transfer.date DESC, transfer.id DESC");

        Query query = getSession().createQuery(builder.toString());
        query.setCacheable(true);
        query.setReadOnly(true);

        if (SiriusValidator.validateParam(criteria.getCode()))
            query.setParameter("code", "%" + criteria.getCode() + "%");

        if (SiriusValidator.validateDate(criteria.getDateFrom()))
            query.setParameter("dateFrom", criteria.getDateFrom());

        if (SiriusValidator.validateDate(criteria.getDateTo()))
            query.setParameter("dateTo", criteria.getDateTo());

        return query;
    }
}

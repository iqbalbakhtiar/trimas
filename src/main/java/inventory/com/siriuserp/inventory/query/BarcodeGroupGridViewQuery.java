package com.siriuserp.inventory.query;

import com.siriuserp.inventory.criteria.BarcodeGroupFilterCriteria;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.dm.BarcodeGroupType;
import com.siriuserp.sdk.dm.BarcodeStatus;
import com.siriuserp.sdk.utility.SiriusValidator;
import org.hibernate.Query;

/**
 * @author Ferdinand
 * @author Rama Almer Felix
 *  Sirius Indonesia, PT
 *  www.siriuserp.com
 */

@SuppressWarnings("unchecked")
public class BarcodeGroupGridViewQuery extends AbstractGridViewQuery {

    @Override
    public Query getQuery(ExecutorType type) {
        BarcodeGroupFilterCriteria criteria = (BarcodeGroupFilterCriteria) getFilterCriteria();
        StringBuilder builder = new StringBuilder();

        if (type.compareTo(ExecutorType.COUNT) == 0)
            builder.append("SELECT COUNT (barcode.id) ");

        builder.append("FROM BarcodeGroup barcode WHERE 1=1 ");

        if (SiriusValidator.validateParam(criteria.getCode()))
            builder.append("AND barcode.code LIKE :code ");

        if (SiriusValidator.validateParam(criteria.getFacility()))
            builder.append("AND barcode.facility.name LIKE :facility ");

        if (SiriusValidator.validateParam(criteria.getBarcodeGroupType()))
            builder.append("AND barcode.barcodeGroupType = :type ");

        if (SiriusValidator.validateParam(criteria.getCreatedBy()))
            builder.append("AND barcode.createdBy.fullName LIKE :createdBy ");

        if (SiriusValidator.validateParam(criteria.getStatus()))
            builder.append("AND barcode.status = :status ");

        if (criteria.getDateFrom() != null)
        {
            if (criteria.getDateTo() != null)
                builder.append("AND barcode.date BETWEEN :start AND :end ");
            else
                builder.append("AND barcode.date >= :start ");
        }

        if (criteria.getDateTo() != null)
            builder.append("AND barcode.date <= :end ");

        if (type.compareTo(ExecutorType.HQL) == 0)
            builder.append("ORDER BY barcode.id DESC ");

        Query query = getSession().createQuery(builder.toString());
        query.setCacheable(true);
        query.setReadOnly(true);

        if (SiriusValidator.validateParam(criteria.getCode()))
            query.setParameter("code", "%"+criteria.getCode()+"%");

        if (SiriusValidator.validateParam(criteria.getCreatedBy()))
            query.setParameter("createdBy", "%"+criteria.getCreatedBy()+"%");

        if (SiriusValidator.validateParam(criteria.getFacility()))
            query.setParameter("facility", "%"+criteria.getFacility()+"%");

        if (SiriusValidator.validateParam(criteria.getBarcodeGroupType()))
            query.setParameter("type", BarcodeGroupType.valueOf(criteria.getBarcodeGroupType()));

        if (criteria.getDateFrom() != null)
            query.setParameter("start",criteria.getDateFrom());

        if (criteria.getDateTo() != null)
            query.setParameter("end",criteria.getDateTo());

        if (SiriusValidator.validateParam(criteria.getStatus()))
            query.setParameter("status", BarcodeStatus.valueOf(criteria.getStatus()));

        return query;
    }
}

package com.siriuserp.inventory.query;

import org.hibernate.Query;

import com.siriuserp.inventory.criteria.ProductFilterCriteria;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Muhammad Rizal
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public class MachineGridViewQuery extends AbstractGridViewQuery {

    @Override
    public Query getQuery(ExecutorType type) {
        ProductFilterCriteria criteria = (ProductFilterCriteria) getFilterCriteria();

        StringBuilder builder = new StringBuilder();
        
        if (ExecutorType.COUNT.equals(type)) {
            builder.append("SELECT COUNT(mac.id) ");
        } else if (ExecutorType.HQL.equals(type)) {
            builder.append("SELECT mac ");
        }

        builder.append("FROM Machine mac WHERE mac.id IS NOT NULL ");
        
        if (SiriusValidator.validateParam(criteria.getCode())) {
            builder.append("AND mac.code LIKE :code ");
        }

        if (SiriusValidator.validateParam(criteria.getName())) {
            builder.append("AND mac.name LIKE :name ");
        }
        
        if (SiriusValidator.validateParam(criteria.getStatus())) {
			builder.append("AND mac.status =:status ");
		}
        
        if (ExecutorType.HQL.equals(type)) {
            builder.append("ORDER BY mac.id DESC");
        }

        Query query = getSession().createQuery(builder.toString());
        query.setCacheable(true);
        query.setReadOnly(true);

        if (SiriusValidator.validateParam(criteria.getCode())) {
            query.setParameter("code", "%" + criteria.getCode() + "%");
        }

        if (SiriusValidator.validateParam(criteria.getName())) {
            query.setParameter("name", "%" + criteria.getName() + "%");
        }
       
        if (SiriusValidator.validateParam(criteria.getStatus())) {
			query.setParameter("status", criteria.getStatus());
		}

        return query;
    }
}

package com.siriuserp.production.query;

import org.hibernate.Query;

import com.siriuserp.inventory.criteria.MasterDataFilterCriteria;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Muhammad Rizal
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public class CostCenterGridViewQuery extends AbstractGridViewQuery {

    @Override
    public Query getQuery(ExecutorType type) {
        MasterDataFilterCriteria criteria = (MasterDataFilterCriteria) getFilterCriteria();

        StringBuilder builder = new StringBuilder();
        
        if (ExecutorType.COUNT.equals(type)) {
            builder.append("SELECT COUNT(cc.id) ");
        } else if (ExecutorType.HQL.equals(type)) {
            builder.append("SELECT cc ");
        }

        builder.append("FROM CostCenter cc WHERE cc.id IS NOT NULL ");
        
        if (SiriusValidator.validateParam(criteria.getCode())) {
            builder.append("AND cc.code LIKE :code ");
        }

        if (SiriusValidator.validateParam(criteria.getName())) {
            builder.append("AND cc.name LIKE :name ");
        }
        
        if (criteria.getType() != null) {
			builder.append("AND cc.type =:type ");
		}
        
        if (ExecutorType.HQL.equals(type)) {
            builder.append("ORDER BY cc.id DESC");
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
       
        if (criteria.getType()!=null) {
			query.setParameter("type", criteria.getType());
		}

        return query;
    }
}

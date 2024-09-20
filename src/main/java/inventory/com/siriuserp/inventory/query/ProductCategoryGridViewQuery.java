package com.siriuserp.inventory.query;

import org.hibernate.Query;

import com.siriuserp.inventory.criteria.ProductCategoryFilterCriteria;
import com.siriuserp.inventory.dm.ProductCategoryType;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.utility.SiriusValidator;

public class ProductCategoryGridViewQuery extends AbstractGridViewQuery {
    @Override
    public Query getQuery(ExecutorType type) {
        ProductCategoryFilterCriteria filter = (ProductCategoryFilterCriteria) getFilterCriteria();
        
        StringBuilder builder = new StringBuilder();
        
        if (type.compareTo(ExecutorType.COUNT) == 0)
            builder.append("SELECT COUNT(cat.id) ");
        
        builder.append("FROM ProductCategory cat WHERE 1=1 ");
        
        if (SiriusValidator.validateParam(filter.getCode())) {
            builder.append("AND cat.code like :code ");
        }

        if (SiriusValidator.validateParam(filter.getName())) {
            builder.append("AND cat.name like :name ");
        }

        if (SiriusValidator.validateParam(filter.getType())) {
            builder.append("AND cat.type = :type ");
        }

        if (!getAccessibleCategories().isEmpty()) {
            builder.append("AND cat.id IN (:cats) ");
        }
        
        if (type.compareTo(ExecutorType.HQL) == 0)
            builder.append("ORDER BY cat.id DESC");
        
        Query query = getSession().createQuery(builder.toString());
        query.setCacheable(true);
        query.setReadOnly(true);
        
        if (!getAccessibleCategories().isEmpty())
            query.setParameterList("cats", getAccessibleCategories());

        if (SiriusValidator.validateParam(filter.getCode()))
            query.setParameter("code", "%" + filter.getCode() + "%");

        if (SiriusValidator.validateParam(filter.getName()))
            query.setParameter("name", "%" + filter.getName() + "%");

        if (SiriusValidator.validateParam(filter.getType()))
            query.setParameter("type", ProductCategoryType.valueOf(filter.getType()));

        return query;
    }
}

/**
 * May 4, 2009 5:22:18 PM
 * com.siriuserp.popup.query
 * GridPopupGridViewQuery.java
 */
package com.siriuserp.administration.query;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.siriuserp.administration.criteria.GridFilterCriteria;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.Grid;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastList;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public class GridPopupGridViewQuery extends AbstractGridViewQuery
{

    @Override
    public Long count()
    {
        return Long.valueOf(0);
    }

    @SuppressWarnings("unchecked")
    public Object execute()
    {
        FastList<Container> list = new FastList<Container>();

        GridFilterCriteria filter = (GridFilterCriteria)getFilterCriteria();

        Criteria criteria = getSession().createCriteria(Grid.class);

        if(SiriusValidator.validateParamWithZeroPosibility(filter.getFacility()))
            criteria.createCriteria("facility").add(Restrictions.eq("id",filter.getFacility()));
        
        criteria.setFirstResult(filter.start());
        criteria.setMaxResults(filter.getMax());
        list.addAll(criteria.list());

        return list;
    }

}

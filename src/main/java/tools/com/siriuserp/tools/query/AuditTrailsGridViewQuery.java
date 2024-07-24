/**
 * Jun 2, 2009 1:56:10 PM
 * com.siriuserp.tools.query
 * AuditTrailsGridViewQuery.java
 */
package com.siriuserp.tools.query;

import java.sql.Timestamp;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.dm.ActivityHistory;
import com.siriuserp.sdk.utility.SiriusValidator;
import com.siriuserp.tools.criteria.AuditTrailsFilterCriteria;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public class AuditTrailsGridViewQuery extends AbstractGridViewQuery
{

    @Override
    public Long count()
    {
        AuditTrailsFilterCriteria filter = (AuditTrailsFilterCriteria)getFilterCriteria();

        Criteria criteria = getSession().createCriteria(ActivityHistory.class);
        criteria.setCacheable(true);

        if(SiriusValidator.validateParam(filter.getMenu()))
            criteria.add(Restrictions.like("accessedModule",filter.getMenu(),MatchMode.ANYWHERE));

        if(SiriusValidator.validateParam(filter.getAction()))
            criteria.add(Restrictions.eq("actionType",AuditTrailsActionType.valueOf(filter.getAction())));

        if(SiriusValidator.validateParam(filter.getId()))
            criteria.add(Restrictions.eq("accessedModuleId",filter.getId()));

        if(SiriusValidator.validateParamWithZeroPosibility(filter.getActivePerson()))
            criteria.createCriteria("activePerson").add(Restrictions.eq("id",filter.getActivePerson()));            

        if(filter.getDateFrom() != null)
        {
            if(filter.getDateTo() != null)
                criteria.add(Restrictions.between("actionDate",new Timestamp(filter.getDateFrom().getTime()),new Timestamp(filter.getDateTo().getTime())));
            else
                criteria.add(Restrictions.ge("actionDate",new Timestamp(filter.getDateFrom().getTime())));
        }

        if(filter.getDateTo() != null)
            criteria.add(Restrictions.le("actionDate",new Timestamp(filter.getDateTo().getTime())));

        return Long.valueOf(criteria.list().size());
    }

    @Override
    public Object execute()
    {
        AuditTrailsFilterCriteria filter = (AuditTrailsFilterCriteria)getFilterCriteria();
        
        Criteria criteria = getSession().createCriteria(ActivityHistory.class);
        criteria.setCacheable(true);
        criteria.addOrder(Order.desc("actionDate"));
        
        if(SiriusValidator.validateParam(filter.getMenu()))
            criteria.add(Restrictions.like("accessedModule",filter.getMenu(),MatchMode.ANYWHERE));

        if(SiriusValidator.validateParam(filter.getAction()))
            criteria.add(Restrictions.eq("actionType",AuditTrailsActionType.valueOf(filter.getAction())));

        if(SiriusValidator.validateParam(filter.getId()))
            criteria.add(Restrictions.eq("accessedModuleId",filter.getId()));

        if(SiriusValidator.validateParamWithZeroPosibility(filter.getPerson()))
            criteria.createCriteria("activePerson").add(Restrictions.eq("id",filter.getPerson()));            

        if(filter.getDateFrom() != null)
        {
            if(filter.getDateTo() != null)
                criteria.add(Restrictions.between("actionDate",new Timestamp(filter.getDateFrom().getTime()),new Timestamp(filter.getDateTo().getTime())));
            else
                criteria.add(Restrictions.ge("actionDate",new Timestamp(filter.getDateFrom().getTime())));
        }

        if(filter.getDateTo() != null)
            criteria.add(Restrictions.le("actionDate",new Timestamp(filter.getDateTo().getTime())));

        criteria.setFirstResult(filter.start());
        criteria.setMaxResults(filter.getMax());
        
        return criteria.list();
    }

}

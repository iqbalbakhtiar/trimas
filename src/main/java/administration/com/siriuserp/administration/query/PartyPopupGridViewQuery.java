/**
 * Apr 14, 2009 3:23:56 PM
 * com.siriuserp.popup.controller
 * CompanyStructureStandardGridViewQuery.java
 */
package com.siriuserp.administration.query;

import org.hibernate.Query;

import com.siriuserp.administration.criteria.PartyFilterCriteria;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public class PartyPopupGridViewQuery extends AbstractGridViewQuery
{
    public Long count()
    {
        PartyFilterCriteria filter = (PartyFilterCriteria)filterCriteria;

        StringBuilder builder = new StringBuilder();
        builder.append("SELECT COUNT(party) FROM Party party");
        
        if(SiriusValidator.validateParam(filter.getName()))
        {
            builder.append(" WHERE (party.firstName like '%"+filter.getName()+"%' OR ");
            builder.append("party.middleName like '%"+filter.getName()+"%' OR ");
            builder.append("party.lastName like '%"+filter.getName()+"%' OR ");
            builder.append("party.code like '%"+filter.getName()+"%')");
        }

        Query query = getSession().createQuery(builder.toString());
        query.setCacheable(true);
        
        Object object = query.uniqueResult();
        if(object != null)
            return (Long)object;

        return Long.valueOf(0);
    }

    @Override
    public Object execute()
    {
        PartyFilterCriteria filter = (PartyFilterCriteria)filterCriteria;

        StringBuilder builder = new StringBuilder();
        builder.append("FROM Party party");
        
        if(SiriusValidator.validateParam(filter.getName()))
        {
            builder.append(" WHERE (party.firstName like '%"+filter.getName()+"%' OR ");
            builder.append("party.middleName like '%"+filter.getName()+"%' OR ");
            builder.append("party.lastName like '%"+filter.getName()+"%' OR ");
            builder.append("party.code like '%"+filter.getName()+"%')");
        }

        builder.append(" ORDER BY party.code ASC,party.firstName ASC");

        Query query = getSession().createQuery(builder.toString());
        query.setCacheable(true);
        query.setFirstResult(filter.start());
        query.setMaxResults(filter.getMax());

        return query.list();
    }
}

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
public class PartyRelationPopupGridViewQuery extends AbstractGridViewQuery
{
    public Long count()
    {
        PartyFilterCriteria criteria = (PartyFilterCriteria)filterCriteria;

        StringBuilder builder = new StringBuilder();
        builder.append("SELECT COUNT(DISTINCT relation.fromRole.party) FROM PartyRelationship relation ");
        builder.append("WHERE relation.fromRole.partyRoleType.id =:fromRole ");
        builder.append("AND relation.toRole.party.id =:party ");
        builder.append("AND relation.toRole.active =:status ");
        
        if(SiriusValidator.validateParam(criteria.getName()))
        {
            builder.append("AND (relation.fromRole.party.firstName like '%"+criteria.getName()+"%' ");
            builder.append("OR relation.fromRole.party.middleName like '%"+criteria.getName()+"%' ");
            builder.append("OR relation.fromRole.party.lastName like '%"+criteria.getName()+"%' ");
            builder.append("OR relation.fromRole.party.code like '%"+criteria.getName()+"%')");
        }

        Query query = getSession().createQuery(builder.toString());
        query.setCacheable(true);
        query.setParameter("fromRole",criteria.getFromRoleType());
        query.setParameter("party",criteria.getOrganization());
        query.setParameter("status",Boolean.TRUE);
        
        Object object = query.uniqueResult();
        if(object != null)
            return (Long)object;

        return Long.valueOf(0);
    }

    @Override
    public Object execute()
    {
        PartyFilterCriteria criteria = (PartyFilterCriteria)filterCriteria;

        StringBuilder builder = new StringBuilder();
        builder.append("SELECT DISTINCT relation.fromRole.party FROM PartyRelationship relation ");
        builder.append("WHERE relation.fromRole.partyRoleType.id =:fromRole ");
        builder.append("AND relation.toRole.party.id =:party ");
        builder.append("AND relation.toRole.active =:status ");
        
        if(SiriusValidator.validateParam(criteria.getName()))
        {
            builder.append("AND (relation.fromRole.party.firstName like '%"+criteria.getName()+"%' ");
            builder.append("OR relation.fromRole.party.middleName like '%"+criteria.getName()+"%' ");
            builder.append("OR relation.fromRole.party.lastName like '%"+criteria.getName()+"%' ");
            builder.append("OR relation.fromRole.party.code like '%"+criteria.getName()+"%')");
        }

        builder.append("ORDER BY relation.fromRole.party.firstName ASC");

        Query query = getSession().createQuery(builder.toString());
        query.setCacheable(true);
        query.setParameter("fromRole",criteria.getFromRoleType());
        query.setParameter("party",criteria.getOrganization());
        query.setParameter("status",Boolean.TRUE);
        query.setFirstResult(criteria.start());
        query.setMaxResults(criteria.getMax());

        return query.list();
    }
}

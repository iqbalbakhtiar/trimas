/**
 * Mar 27, 2009 2:20:06 PM
 * com.siriuserp.popup.query
 * PartyRelationshipPopupGridViewQuery.java
 */
package com.siriuserp.administration.query;

import org.hibernate.Query;

import com.siriuserp.administration.criteria.PartyRelationshipFilterCriteria;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.dm.PartyRelationship;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastList;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public class PartyRelationshipPopupGridViewQuery extends AbstractGridViewQuery
{

    @Override
    public Long count()
    {
        PartyRelationshipFilterCriteria criteria = (PartyRelationshipFilterCriteria)getFilterCriteria();

        StringBuilder builder = new StringBuilder();
        builder.append("SELECT COUNT(rel) FROM PartyRelationship rel WHERE rel.fromRole.party.id =:party ");
        
        if(SiriusValidator.validateParamWithZeroPosibility(criteria.getType()))
            builder.append("AND rel.relationshipType.id =:type ");

        Query query = getSession().createQuery(builder.toString());
        query.setCacheable(true);
        query.setParameter("party",criteria.getParty());
        
        if(SiriusValidator.validateParamWithZeroPosibility(criteria.getType()))
            query.setParameter("type",criteria.getType());
        
        Object object = query.uniqueResult();
        if(object != null)
            return (Long)object;

        return Long.valueOf(0);
    }

    @SuppressWarnings("unchecked")
    public Object execute()
    {
        FastList<PartyRelationship> list = new FastList<PartyRelationship>();

        PartyRelationshipFilterCriteria criteria = (PartyRelationshipFilterCriteria)getFilterCriteria();

        StringBuilder builder = new StringBuilder();
        builder.append("FROM PartyRelationship rel WHERE rel.fromRole.party.id =:party ");
        
        if(SiriusValidator.validateParamWithZeroPosibility(criteria.getType()))
            builder.append("AND rel.relationshipType.id =:type ");

        Query query = getSession().createQuery(builder.toString());
        query.setCacheable(true);
        query.setParameter("party",criteria.getParty());
        query.setFirstResult(criteria.start());
        query.setMaxResults(criteria.getMax());
        
        if(SiriusValidator.validateParamWithZeroPosibility(criteria.getType()))
            query.setParameter("type",criteria.getType());

        list.addAll(query.list());

        return list;
    }

}

/**
 * Jun 29, 2009 2:09:07 PM
 * com.siriuserp.administration.query
 * ContactMechanismPopupGridViewQuery.java
 */
package com.siriuserp.administration.query;

import org.hibernate.Query;

import com.siriuserp.administration.criteria.ContactMechanismFilterCriteria;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.dm.ContactMechanism;
import com.siriuserp.sdk.dm.ContactMechanismType;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastList;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public class ContactMechanismPopupGridViewQuery extends AbstractGridViewQuery
{

    @Override
    public Long count()
    {
        return Long.valueOf(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object execute()
    {
        FastList<ContactMechanism> list = new FastList<ContactMechanism>();
        
        ContactMechanismFilterCriteria criteria = (ContactMechanismFilterCriteria)getFilterCriteria();
        
        if(SiriusValidator.validateParamWithZeroPosibility(criteria.getParty()))
        {
            StringBuilder builder = new StringBuilder();
            builder.append("FROM ContactMechanism contact WHERE contact.party.id =:party");
            builder.append(" AND contact.active =:active");
            
            if(SiriusValidator.validateParam(criteria.getType()))
                builder.append(" AND contact.contactMechanismType =:contactMechanismType");
            
            Query query = getSession().createQuery(builder.toString());
            query.setParameter("party",criteria.getParty());
            query.setParameter("active",Boolean.TRUE);
            
            if(SiriusValidator.validateParam(criteria.getType()))
                query.setParameter("contactMechanismType",ContactMechanismType.valueOf(criteria.getType()));
            
            list.addAll(query.list());
        }
        
        return list;
    }

}

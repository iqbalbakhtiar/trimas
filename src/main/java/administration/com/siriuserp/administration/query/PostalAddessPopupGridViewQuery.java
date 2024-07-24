/**
 * Apr 21, 2009 6:21:09 PM
 * com.siriuserp.popup.query
 * PostalAddessPopupGridViewQuery.java
 */
package com.siriuserp.administration.query;

import org.hibernate.Query;

import com.siriuserp.administration.criteria.PostalAddressFilterCriteria;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.dm.AddressType;
import com.siriuserp.sdk.dm.PostalAddress;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastList;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public class PostalAddessPopupGridViewQuery extends AbstractGridViewQuery
{

    @Override
    public Long count()
    {
        PostalAddressFilterCriteria criteria = (PostalAddressFilterCriteria)getFilterCriteria();

        StringBuilder builder = new StringBuilder();
        builder.append("SELECT COUNT(postal.id) FROM PostalAddress postal join postal.addressTypes type WHERE postal.party.id =:party AND postal.enabled =:enabled");

        if(SiriusValidator.validateParam(criteria.getType()))
            builder.append(" AND type.type =:addressType");
        
        Query query = getSession().createQuery(builder.toString());
        query.setParameter("party",criteria.getParty());
        query.setParameter("enabled",Boolean.TRUE);
        
        if(SiriusValidator.validateParam(criteria.getType()))
            query.setParameter("addressType",AddressType.valueOf(criteria.getType()));
        
        Object object = query.uniqueResult();
        if(object != null)
            return (Long)object;
        
        return Long.valueOf(0);
    }

    @SuppressWarnings("unchecked")
    public Object execute()
    {
        FastList<PostalAddress> list = new FastList<PostalAddress>();

        PostalAddressFilterCriteria criteria = (PostalAddressFilterCriteria)getFilterCriteria();

        StringBuilder builder = new StringBuilder();
        builder.append("SELECT DISTINCT(postal) FROM PostalAddress postal join postal.addressTypes type WHERE postal.party.id =:party AND postal.enabled =:enabled");
        
        if(SiriusValidator.validateParam(criteria.getType()))
            builder.append(" AND type.type =:addressType");
        
        Query query = getSession().createQuery(builder.toString());
        query.setParameter("party",criteria.getParty());
        query.setParameter("enabled",Boolean.TRUE);
        query.setFirstResult(criteria.start());
        query.setMaxResults(criteria.getMax());
        
        if(SiriusValidator.validateParam(criteria.getType()))
            query.setParameter("addressType",AddressType.valueOf(criteria.getType()));
        
        list.addAll(query.list());

        return list;
    }

}

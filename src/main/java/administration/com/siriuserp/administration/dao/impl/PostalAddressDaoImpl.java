/**
 * Oct 31, 2008 10:24:19 AM
 * com.siriuserp.sdk.dao.impl
 * PostalAddressDaoImpl.java
 */
package com.siriuserp.administration.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.siriuserp.sdk.dao.PostalAddressDao;
import com.siriuserp.sdk.db.DaoHelper;
import com.siriuserp.sdk.dm.AddressType;
import com.siriuserp.sdk.dm.PostalAddress;
import com.siriuserp.sdk.dm.PostalAddressType;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@SuppressWarnings("unchecked")
@Component
public class PostalAddressDaoImpl extends DaoHelper<PostalAddress> implements PostalAddressDao
{   
    @SuppressWarnings("rawtypes")
    @Override
    public PostalAddress loadDefault(AddressType type,Long party)
    {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT DISTINCT type.postalAddress FROM PostalAddressType type ");
        builder.append("WHERE type.postalAddress.selected =:selected ");
        builder.append("AND type.postalAddress.enabled =:enabled ");
        builder.append("AND type.type =:addressType ");
        builder.append("AND type.enabled =:enabled ");
        builder.append("AND type.postalAddress.party.id = :party ");

        Query query = getSession().createQuery(builder.toString());
        query.setParameter("selected",Boolean.TRUE);
        query.setParameter("enabled",Boolean.TRUE);
        query.setParameter("addressType",type);
        query.setParameter("party", party);
        
		List list = query.list();
        if(!list.isEmpty())
            return (PostalAddress)list.get(0);
        
        return null;
    }

	public List<PostalAddress> loadByAddressType(Long organizatiton) 
	{
		List<PostalAddress> postaladdress = new ArrayList<PostalAddress>();
		
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT DISTINCT(postal) FROM PostalAddress postal join postal.addressTypes type WHERE postal.party.id =:party AND postal.enabled =:enabled");
		builder.append(" AND type.type =:addressType");
		
		Query query = getSession().createQuery(builder.toString());
		query.setParameter("party",organizatiton);
		query.setParameter("enabled",Boolean.TRUE);
		query.setParameter("addressType",AddressType.TAX);

		List<PostalAddress> lis = query.list();
		for(PostalAddress address : lis){
			for(PostalAddressType type : address.getAddressTypes()){
				if(type.isEnabled()){
					if(type.getType()==AddressType.TAX){
						postaladdress.add(address);
					}
				}
			}
		}
		
		return postaladdress;
	}
	
	@Override
	public PostalAddress loadByDefault(String selected, Long party) {
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT postal FROM PostalAddress postal WHERE postal.party.id =:party ");
	    builder.append("AND postal.selected =:selected ");
	    
	    Query query = getSession().createQuery(builder.toString());
	    query.setParameter("party",party);
        query.setParameter("selected",Boolean.valueOf(selected));
        
        if(Boolean.valueOf(selected) == false)
        	return null;
		
		return (PostalAddress) query.uniqueResult();
	}
	
	@Override
	public List<PostalAddress> loadAll(Long party) {
		
		List<PostalAddress> postaladdress = new ArrayList<PostalAddress>();
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT DISTINCT(postal) FROM PostalAddress postal join postal.addressTypes type WHERE postal.party.id =:party ");
	    builder.append("AND type.type =:addressType ");
	    builder.append("AND postal.enabled =:enabled ");
		
	    Query query = getSession().createQuery(builder.toString());
	    query.setParameter("party",party);
		query.setParameter("addressType",AddressType.SHIPPING);
		query.setParameter("enabled",Boolean.TRUE);
		
    	List<PostalAddress> lis = query.list();
		for(PostalAddress address : lis){
			for(PostalAddressType type : address.getAddressTypes()){
				if(type.isEnabled()){
					if(type.getType()==AddressType.SHIPPING){
						postaladdress.add(address);
					}
				}
			}
		}
		
		return postaladdress;
	}
	
}

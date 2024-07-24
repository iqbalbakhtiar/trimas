/**
 * 
 */
package com.siriuserp.administration.query;

import org.hibernate.Query;

import com.siriuserp.administration.criteria.ContainerFilterCriteria;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastList;

/**
 * @author
 * Betsu Brahmana Restu
 * PT. Sirius Indonesia
 * betsu@siriuserp.com
 */
public class ContainerViewQuery extends AbstractGridViewQuery 
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
		FastList<Container> list = new FastList<Container>();

	    ContainerFilterCriteria filter = (ContainerFilterCriteria)getFilterCriteria();
	        
	    StringBuilder builder = new StringBuilder();
	    builder.append("FROM Container container WHERE container.grid.facility.owner.id =:org ");
	     
	    if(SiriusValidator.validateLongParam(filter.getFacility()))
	    	builder.append(" AND container.grid.facility.id =:facility ");
	    
	    builder.append("ORDER BY container.grid.facility.id,container.grid.id,container.id ASC");
	        
	    Query query = getSession().createQuery(builder.toString());
	    query.setCacheable(true);
	    query.setReadOnly(true);
	    query.setParameter("org",filter.getOrganization());
	    
	    if(SiriusValidator.validateLongParam(filter.getFacility()))
	    	query.setParameter("facility",filter.getFacility());

	    list.addAll(query.list());
	      
	    return list;
	}

}

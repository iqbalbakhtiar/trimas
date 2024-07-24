package com.siriuserp.tools.query;

import org.hibernate.Query;

import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.dm.ModuleGroup;
import com.siriuserp.sdk.utility.SiriusValidator;
import com.siriuserp.tools.criteria.RoleFilterCriteria;

import javolution.util.FastList;

/**
 * @author
 * Betsu Brahmana Restu
 * PT. Sirius Indonesia
 * betsu@siriuserp.com
 */
public class ModuleGroupGridViewQuery extends AbstractGridViewQuery {

	@Override
	public Long count() 
	{
		RoleFilterCriteria criteria = (RoleFilterCriteria)getFilterCriteria();
	
		Object object = createQuery(new StringBuilder("SELECT COUNT(module.id) FROM ModuleGroup module"), criteria).uniqueResult();
        if(object != null)
            return (Long)object;	
        
		return Long.valueOf(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object execute() 
	{
		 FastList<ModuleGroup> list = new FastList<ModuleGroup>();

		 RoleFilterCriteria criteria = (RoleFilterCriteria)getFilterCriteria();

	     Query query = createQuery(new StringBuilder("FROM ModuleGroup module"), criteria);
	     query.setFirstResult(criteria.start());
		 query.setMaxResults(criteria.getMax());
			
		 list.addAll(query.list());
	 
	     return list;
	}
	
	public Query createQuery(StringBuilder builder, RoleFilterCriteria criteria)
	{
		builder.append(" WHERE module.id IS NOT NULL");
	        
	    if(SiriusValidator.validateParam(criteria.getName()))
	    	builder.append(" AND module.name LIKE :name");

	    Query query = getSession().createQuery(builder.toString());

	    if(SiriusValidator.validateParam(criteria.getName()))
	    	query.setParameter("name", "%"+criteria.getName()+"%");
	        
	    return query;
	}
}

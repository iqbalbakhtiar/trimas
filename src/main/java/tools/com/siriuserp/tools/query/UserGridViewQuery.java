/**
 * Aug 13, 2009 4:41:57 PM
 * com.siriuserp.tools.query
 * UserGridViewQuery.java
 */
package com.siriuserp.tools.query;

import org.hibernate.Query;

import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.dm.FlagLevel;
import com.siriuserp.sdk.utility.SiriusValidator;
import com.siriuserp.tools.criteria.UserFilterCriteria;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public class UserGridViewQuery extends AbstractGridViewQuery
{
	@Override
    public Query getQuery(ExecutorType type)
    {
		UserFilterCriteria filter = (UserFilterCriteria)getFilterCriteria();
		
		StringBuilder sql = new StringBuilder();
		
		if(type.compareTo(ExecutorType.COUNT) == 0)
			sql.append(" SELECT COUNT(usr)");
		
		sql.append("FROM User usr WHERE usr.person.id IS NOT NULL ");
		
		if(getUser().getFlagLevel().compareTo(FlagLevel.SYSTEMLEVEL) != 0)
			sql.append(" AND usr.flagLevel !=:sys ");
		
		if(SiriusValidator.validateParam(filter.getName()))
        	sql.append(" AND usr.username LIKE '%"+ filter.getName() +"%'");
	        
        if(SiriusValidator.validateParam(filter.getCode()))
        	sql.append(" AND person.fullName LIKE '%"+ filter.getCode() +"%' ");
 
        if(SiriusValidator.validateParam(filter.getRoleName()))
        	sql.append(" AND usr.role.name LIKE '%"+ filter.getRoleName() +"%'");
		
        Query query = getSession().createQuery(sql.toString());
        query.setCacheable(true);
        query.setReadOnly(true);
        
        if(getUser().getFlagLevel().compareTo(FlagLevel.SYSTEMLEVEL) != 0)
			query.setParameter("sys", FlagLevel.SYSTEMLEVEL);
        
        return query;
    }
}

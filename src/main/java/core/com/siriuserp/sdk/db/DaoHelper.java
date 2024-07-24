/**
 * Dec 18, 2007 1:37:40 PM
 * com.siriuserp.sdk.db
 * DaoHelper.java
 */
package com.siriuserp.sdk.db;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.siriuserp.sdk.base.ReportDao;
import com.siriuserp.sdk.exceptions.DataAdditionException;
import com.siriuserp.sdk.exceptions.DataDeletionException;
import com.siriuserp.sdk.exceptions.DataEditException;
import com.siriuserp.sdk.exceptions.ServiceException;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@SuppressWarnings("rawtypes")
public class DaoHelper<T> implements ReportDao
{
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	public Session getSession() 
	{
	    return sessionFactory.getCurrentSession();
	}
	
	public void add(T bean) throws DataAdditionException
	{
		getSession().save(bean);
	}

	public void delete(T bean) throws DataDeletionException
	{
		getSession().delete(bean);
	}

	public void update(T bean) throws DataEditException
	{
		getSession().update(bean);
	}

	public void merge(T object)  throws DataEditException
	{
		getSession().merge(object);
	}
	
	public void clear() throws ServiceException
	{
		getSession().clear();;
	}
	
	@Override
	public List generateReport(ReportQuery reportQuery)
	{
		reportQuery.setSession(getSession());
		return (List) reportQuery.execute();
	}

	@Override
	public Object generate(ReportQuery reportQuery)
	{
		reportQuery.setSession(getSession());
		return reportQuery.execute();
	}
	
	public List filter(GridViewQuery query)
	{
		query.setSession(getSession());
        query.init();
        
        Query qry = query.getQuery(ExecutorType.HQL);
        if(qry != null) {
	        qry.setFirstResult(query.getFilterCriteria().start());
	        
	        if(query.getFilterCriteria().getTotalPage() > 1)
	        	qry.setMaxResults(query.getFilterCriteria().getMax());

			return qry.list();
        }
        
        return (List)query.execute();
	}

	public Long getMax(GridViewQuery query)
	{
		query.setSession(getSession());
        query.init();
        
        Query qry = query.getQuery(ExecutorType.COUNT);
		if (qry != null)
			return (Long) qry.uniqueResult();

		return query.count();
	}
}

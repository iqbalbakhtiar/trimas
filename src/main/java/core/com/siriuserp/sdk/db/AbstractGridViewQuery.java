/**
 * Dec 5, 2008 2:43:49 PM
 * com.siriuserp.sdk.db
 * AbstractStandardQuery.java
 */
package com.siriuserp.sdk.db;

import org.hibernate.Query;
import org.hibernate.Session;

import com.siriuserp.sdk.dm.User;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;

import javolution.util.FastList;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@SuppressWarnings("unchecked")
public abstract class AbstractGridViewQuery implements GridViewQuery
{
	protected Session session;
	protected GridViewFilterCriteria filterCriteria;
	protected FastList<Long> accessibleOrganizations = new FastList<Long>();
	protected FastList<Long> accessibleCategories = new FastList<Long>();
	protected FastList<Long> accessibleCreators = new FastList<Long>();
	protected FastList<Long> accessibleFacilities = new FastList<Long>();
	protected FastList<Long> accessibleGrids = new FastList<Long>();

	public FastList<Long> getAccessibleOrganizations()
	{
		return accessibleOrganizations;
	}

	public void setAccessibleOrganizations(FastList<Long> accessibleOrganizations)
	{
		this.accessibleOrganizations = accessibleOrganizations;
	}

	public <T extends GridViewFilterCriteria> T getFilterCriteria()
	{
		return (T) this.filterCriteria;
	}

	public void setFilterCriteria(GridViewFilterCriteria filterCriteria)
	{
		this.filterCriteria = filterCriteria;
	}

	public FastList<Long> getAccessibleCreators()
	{
		return accessibleCreators;
	}

	public void setAccessibleCreators(FastList<Long> accessibleCreators)
	{
		this.accessibleCreators = accessibleCreators;
	}

	public FastList<Long> getAccessibleCategories()
	{
		return accessibleCategories;
	}

	public void setAccessibleCategories(FastList<Long> accessibleCategories)
	{
		this.accessibleCategories = accessibleCategories;
	}

	public FastList<Long> getAccessibleFacilities()
	{
		return accessibleFacilities;
	}

	public void setAccessibleFacilities(FastList<Long> accessibleFacilities)
	{
		this.accessibleFacilities = accessibleFacilities;
	}

	public FastList<Long> getAccessibleGrids()
	{
		return accessibleGrids;
	}

	public void setAccessibleGrids(FastList<Long> accessibleGrids)
	{
		this.accessibleGrids = accessibleGrids;
	}

	@Override
	public Long count()
	{
		return Long.valueOf(0);
	}

	@Override
	public Object execute()
	{
		return null;
	}

	@Override
	public Query getQuery(ExecutorType type)
	{
		return null;
	}

	@Override
	public Session getSession()
	{
		return this.session;
	}

	@Override
	public void setSession(Session session)
	{
		this.session = session;
	}

	public User getUser()
	{
		return getFilterCriteria().getUser();
	}

	public void init()
	{
		/*if (getSession() != null && getFilterCriteria() != null && getUser() != null)
		{
			FastList.recycle(getAccessibleOrganizations());
			getAccessibleOrganizations().addAll(getUser().getAccess().get("orgs"));
		
			FastList.recycle(getAccessibleCategories());
			getAccessibleCategories().addAll(getUser().getAccess().get("cats"));
		
			FastList.recycle(getAccessibleFacilities());
			getAccessibleFacilities().addAll(getUser().getAccess().get("facs"));
		}*/
	}
}

/**
 * Jul 12, 2006
 * FilterParam.java
 */
package com.siriuserp.sdk.filter;

import com.siriuserp.sdk.dm.Role;
import com.siriuserp.sdk.dm.User;

import javolution.util.FastList;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public interface GridViewFilterCriteria extends FilterCriteria
{
	public void setPage(int totalPage);
	public int getPage();
	public void setNextPage(int totalPage);
	public int getNextPage();
	public void setPrevPage(int totalPage);	
	public int getPrevPage();	
	public void setTotalPage(int totalPage);
	public int getTotalPage();
	public int getMax();
	public void setMax(int max);
	
	public Long getOrganization();
    public void setOrganization(Long organization);
    
    public FastList<Long> getOrganizations();
    public void setOrganizations(FastList<Long> organizations);

    public Role getRole();
    public void setRole(Role role);
    
    public User getUser();
    public void setUser(User user);
    
    public int start();
}

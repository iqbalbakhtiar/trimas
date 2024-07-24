/**
 * Jun 18, 2009 10:50:10 AM
 * com.siriuserp.tools.adapter
 * RoleUIAdapter.java
 */
package com.siriuserp.tools.adapter;

import com.siriuserp.sdk.adapter.AbstractUIAdapter;
import com.siriuserp.sdk.dm.AccessType;
import com.siriuserp.sdk.dm.Role;

import javolution.util.FastList;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public class RoleUIAdapter extends AbstractUIAdapter
{
    private static final long serialVersionUID = 3260215935897947659L;

    private Role role;
    
    private FastList<RoleDetailUIAdapter> accessModules = new FastList<RoleDetailUIAdapter>();
    
    private FastList<RoleDetailUIAdapter> approvalRoles = new FastList<RoleDetailUIAdapter>();
    private FastList<RoleDetailUIAdapter> gridRoles = new FastList<RoleDetailUIAdapter>();
    private FastList<RoleDetailUIAdapter> facilityRoles = new FastList<RoleDetailUIAdapter>();
    private FastList<RoleDetailUIAdapter> categoryRoles = new FastList<RoleDetailUIAdapter>();
    private FastList<RoleDetailUIAdapter> organizationRoles = new FastList<RoleDetailUIAdapter>();
    
    private FastList<AccessType> types = new FastList<AccessType>();
    
    public RoleUIAdapter(){}
    
    public RoleUIAdapter(Role role)
    {
    	this.role = role;
    }

    public Role getRole()
    {
        return role;
    }

    public void setRole(Role role)
    {
        this.role = role;
    }

	public FastList<RoleDetailUIAdapter> getAccessModules() 
	{
		return accessModules;
	}

	public void setAccessModules(FastList<RoleDetailUIAdapter> accessModules) 
	{
		this.accessModules = accessModules;
	}

	public FastList<RoleDetailUIAdapter> getOrganizationRoles() 
	{
		return organizationRoles;
	}

	public void setOrganizationRoles(FastList<RoleDetailUIAdapter> organizationRoles) 
	{
		this.organizationRoles = organizationRoles;
	}

	public FastList<RoleDetailUIAdapter> getApprovalRoles() 
	{
		return approvalRoles;
	}

	public void setApprovalRoles(FastList<RoleDetailUIAdapter> approvalRoles) 
	{
		this.approvalRoles = approvalRoles;
	}

	public FastList<RoleDetailUIAdapter> getGridRoles() 
	{
		return gridRoles;
	}

	public void setGridRoles(FastList<RoleDetailUIAdapter> gridRoles) 
	{
		this.gridRoles = gridRoles;
	}

	public FastList<RoleDetailUIAdapter> getFacilityRoles() 
	{
		return facilityRoles;
	}

	public void setFacilityRoles(FastList<RoleDetailUIAdapter> facilityRoles) 
	{
		this.facilityRoles = facilityRoles;
	}

	public FastList<RoleDetailUIAdapter> getCategoryRoles() 
	{
		return categoryRoles;
	}

	public void setCategoryRoles(FastList<RoleDetailUIAdapter> categoryRoles)
	{
		this.categoryRoles = categoryRoles;
	}

	public FastList<AccessType> getTypes() 
	{
		return types;
	}

	public void setTypes(FastList<AccessType> types)
	{
		this.types = types;
	}
} 

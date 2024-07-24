/**
 * 
 */
package com.siriuserp.tools.adapter;

import com.siriuserp.sdk.adapter.AbstractUIAdapter;
import com.siriuserp.sdk.dm.AccessType;

/**
 * @author
 * Betsu Brahmana Restu
 * PT. Sirius Indonesia
 * betsu@siriuserp.com
 */
public class RoleDetailUIAdapter extends AbstractUIAdapter implements Comparable<RoleDetailUIAdapter>
{
	private static final long serialVersionUID = 2969720038302354457L;
	
	private boolean enabled = false;
	private Long module;
	private Long id;
	private AccessType type;
	private String code;
	private String name;
	
	public RoleDetailUIAdapter(){}
	
	public RoleDetailUIAdapter(boolean enabled, Long module, String code, String name, Long id, AccessType type)
	{
		this.enabled = enabled;
		this.module = module;
		this.code = code;
		this.name = name;
		this.id = id;
		this.type = type;
	}
	
	public RoleDetailUIAdapter(boolean enabled, Long module, String code, String name, Long id)
	{
		this.enabled = enabled;
		this.module = module;
		this.code = code;
		this.name = name;
		this.id = id;
	}
	
	public RoleDetailUIAdapter(Long module, String code, String name)
	{
		this.module = module;
		this.code = code;
		this.name = name;
	}

	public Long getModule() 
	{
		return module;
	}

	public void setModule(Long module) 
	{
		this.module = module;
	}
	
	public Long getId() 
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public boolean isEnabled() 
	{
		return enabled;
	}

	public void setEnabled(boolean enabled) 
	{
		this.enabled = enabled;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getName()
	{
		return name;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}

	public AccessType getType() 
	{
		return type;
	}

	public void setType(AccessType type) 
	{
		this.type = type;
	}

	@Override
	public int compareTo(RoleDetailUIAdapter adapter) 
	{
		return getName().toLowerCase().compareTo(adapter.getName().toLowerCase());
	}
}

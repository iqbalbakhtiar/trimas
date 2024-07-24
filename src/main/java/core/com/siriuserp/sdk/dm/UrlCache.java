/**
 * 
 */
package com.siriuserp.sdk.dm;

import java.io.Serializable;

/**
 * @author
 * Betsu Brahmana Restu
 * PT. Sirius Indonesia
 * betsu@siriuserp.com
 */
public class UrlCache implements Serializable
{
	private static final long serialVersionUID = -1520287644614305240L;
	
	private AccessType accessType;
	private ModuleDetailType  detailType;
	
	private String role;
	private String name;
	private String code;
	
	public UrlCache() {}

	public String getRole()
	{
		return role;
	}

	public void setRole(String role) 
	{
		this.role = role;
	}

	public AccessType getAccessType() 
	{
		return accessType;
	}

	public void setAccessType(AccessType accessType)
	{
		this.accessType = accessType;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getCode() 
	{
		return code;
	}

	public void setCode(String code) 
	{
		this.code = code;
	}

	public ModuleDetailType getDetailType() 
	{
		return detailType;
	}

	public void setDetailType(ModuleDetailType detailType)
	{
		this.detailType = detailType;
	}
}

/**
 * Aug 13, 2009 4:42:43 PM
 * com.siriuserp.tools.criteria
 * UserFilterCriteria.java
 */
package com.siriuserp.tools.criteria;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public class UserFilterCriteria extends AbstractFilterCriteria
{
    private static final long serialVersionUID = -4559557733343475475L;

    private String code;
    private String name;
    private String roleName;
    
    public UserFilterCriteria(){}

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

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
 
}

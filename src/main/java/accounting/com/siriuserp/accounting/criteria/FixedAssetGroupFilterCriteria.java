/**
 * Feb 18, 2009 2:10:25 PM
 * com.siriuserp.sdk.filter
 * FixedAssetGroupFilterCriteria.java
 */
package com.siriuserp.accounting.criteria;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public class FixedAssetGroupFilterCriteria extends AbstractFilterCriteria
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -3506607328729396074L;

	/**
	 * 
	 */

	private String code;
    
    private String name;
   
    public FixedAssetGroupFilterCriteria(){}

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

        
}

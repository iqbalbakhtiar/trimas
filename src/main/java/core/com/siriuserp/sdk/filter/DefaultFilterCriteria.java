/**
 * Nov 16, 2008 1:51:32 PM
 * com.siriuserp.sdk.filter
 * DefaultFilterCriteria.java
 */
package com.siriuserp.sdk.filter;

import javolution.util.FastMap;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public class DefaultFilterCriteria extends AbstractFilterCriteria
{
    private static final long serialVersionUID = -9030468242121934510L;

    private FastMap<String,String> parameters = new FastMap<String, String>();
    
    public DefaultFilterCriteria(){}
    
    public String get(String parameterName)
    {
        return parameters.get(parameterName);
    }
}

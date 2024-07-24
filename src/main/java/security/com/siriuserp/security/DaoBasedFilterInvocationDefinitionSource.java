package com.siriuserp.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.SecurityConfig;
import org.springframework.security.intercept.web.FilterInvocation;
import org.springframework.security.intercept.web.FilterInvocationDefinitionSource;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.sdk.dao.AccessibleModuleDao;
import com.siriuserp.sdk.dm.UrlCache;
import com.siriuserp.sdk.utility.UserHelper;

import javolution.util.FastList;
import javolution.util.FastMap;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@SuppressWarnings("rawtypes")
@Transactional(readOnly=true)
public class DaoBasedFilterInvocationDefinitionSource implements FilterInvocationDefinitionSource
{
    public static final FastMap<String,Boolean> BASIC_URI = new FastMap<String, Boolean>();

    static
    {
        BASIC_URI.put("/index.jsp",Boolean.TRUE);
        BASIC_URI.put("/page/signin.htm",Boolean.TRUE);
        BASIC_URI.put("/page/dashboard.htm",Boolean.TRUE);
        BASIC_URI.put("/page/signout.htm",Boolean.TRUE);
        BASIC_URI.put("/login.jsp",Boolean.TRUE);
    }

    @Autowired
    private AccessibleModuleDao accessibleModuleDao;

    public void setAccessibleModuleDao(AccessibleModuleDao accessibleModuleDao)
    {
        this.accessibleModuleDao = accessibleModuleDao;
    }

    public ConfigAttributeDefinition lookupAttributes(String url)
    {
        if(url == null)
            throw new NullPointerException("Parameter of url is null");

        String[] locations = url.split("\\?");
        
        FastList<SecurityConfig> config = new FastList<SecurityConfig>();
        ConfigAttributeDefinition definition = null;
        
        if(BASIC_URI.get(locations[0]) == null)
        {
            try
            {
            	UrlCache cache = UserHelper.activeUser().getUrls().get(locations[0]);
                if(cache != null)
                {
                    SecurityConfig securityConfig = new SecurityConfig(cache.getRole());
                    config.add(securityConfig);
                }
            }
            catch(Exception e) {}
        }
        else
        {
            for(String name:accessibleModuleDao.findAccessibleRoleNameByLocation(locations[0].trim()))
            {
                SecurityConfig securityConfig = new SecurityConfig(name);
                config.add(securityConfig);
            }
        }
        
        if(!config.isEmpty())
            definition = new ConfigAttributeDefinition(config);
        else
            definition = new ConfigAttributeDefinition(new FastList());

        return definition;
    }

    @Override
    public ConfigAttributeDefinition getAttributes(Object invocation) throws IllegalArgumentException
    {
        if ((invocation == null) || !this.supports(invocation.getClass()))
            throw new IllegalArgumentException("Object must be a FilterInvocation");

        String url = ((FilterInvocation) invocation).getRequestUrl();

        return lookupAttributes(url);
    }

    @Override
    public boolean supports(Class clazz)
    {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    @Override
    public Collection getConfigAttributeDefinitions()
    {
        return null;
    }
}
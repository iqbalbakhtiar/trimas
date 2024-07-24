/**
 * Apr 2, 2009 5:44:32 PM
 * com.siriuserp.ar.util
 * NormalizerFacade.java
 */
package com.siriuserp.sdk.transformer;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Component
public class BeanTransformer implements ApplicationContextAware
{
    private ApplicationContext context;
    
    public Object execute(String transformer, Object proxy)throws ServiceException
    {
        Object result = null;
        
        Transformer _transformer = (Transformer)context.getBean(toQualifyName(transformer));
        
        if(_transformer != null)
           result = _transformer.transform(proxy);
        
        return result;
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException
    {
        this.context = context;
    }
    
    private String toQualifyName(String name)
    {
        if(!SiriusValidator.validateParam(name))
            return "";
        
        StringBuilder builder = new StringBuilder();
        builder.append(name.substring(0,1).toLowerCase());
        builder.append(name.substring(1));
        
        return builder.toString();
    }
}

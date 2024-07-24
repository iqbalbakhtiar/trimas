package com.siriuserp.sdk.springmvc;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.siriuserp.sdk.annotation.ExceptionForwardParam;
import com.siriuserp.sdk.annotation.ForwardMethodType;
import com.siriuserp.sdk.annotation.ThrowableException;
import com.siriuserp.sdk.aspect.ExceptionViewerService;
import com.siriuserp.sdk.exceptions.ServiceException;

import javolution.util.FastList;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public class SiriusExceptionResolver extends SimpleMappingExceptionResolver
{    
    @Autowired
    private ExceptionViewerService service;
    
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) 
    {
        ex.printStackTrace();
        
        try
        {            
            ServiceException exception = null;
            
            if(ex instanceof ServiceException)
                exception = (ServiceException)ex;
            
            if(ex.getCause() instanceof ServiceException)
                exception = (ServiceException)ex.getCause();

            if(exception != null)
            {
                service.writeEvent(exception,handler);
                
                ModelAndView view = forward(request, handler, exception);
                if(view != null)
                    return view;
                
                /**
                 * Support for @DefaultRedirect disabled due to spring internal bug.
                 * spring cannot handle redirect action correctly and 
                 * displaying exception stacktrace directly to the user page
                 * this bug start happening from 2.5.0 - 2.5.6 release.
                 * 
                 * String uri = redirect(handler);
                 * if(SiriusValidator.validateParam(uri))
                 *      ViewHelper.redirectTo(uri);
                 */
            }
            else
                service.writeEvent(ex, handler);
        }
        catch (Exception e){}
        
        return new ModelAndView("dataAccessFailure","em",ex.getMessage());
    }

    /**
    private String redirect(Object handler)
    {
        DefaultRedirect redirect = handler.getClass().getAnnotation(DefaultRedirect.class);
        if(redirect != null)
            return redirect.url();
        
        return null;
    }
    */

	private ModelAndView forward(HttpServletRequest request, Object handler, ServiceException exception) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
    {
        Method[] methods = handler.getClass().getDeclaredMethods();
        for(Method method:methods)
        {
            ThrowableException throwableException = method.getAnnotation(ThrowableException.class);
            if(throwableException != null)
            {
                FastList<ExceptionForwardParam> forwardParams = new FastList<ExceptionForwardParam>();

                Method target = handler.getClass().getMethod(throwableException.forwardMethod(),throwableException.forwardParam());
                for(Annotation annotation:method.getDeclaredAnnotations())
                {
                    if(annotation instanceof ExceptionForwardParam)
                        forwardParams.add((ExceptionForwardParam)annotation);
                }

                Object result = null;

                if(forwardParams.size() > 0)
                {
                    Object methodParams[] = new Object[forwardParams.size()];
                    for(ExceptionForwardParam forwardParam:forwardParams)
                    {
                        Object _sessionModel = request.getSession().getAttribute(throwableException.attributeName());
                        if(_sessionModel == null)
                            break;

                        Object _propertyValue = get(_sessionModel,forwardParam.property());
                        
                        if(_propertyValue == null)
                            break;

                        if(forwardParam.forwardMethodType().equals(ForwardMethodType.STRING))
                            methodParams[forwardParam.order()] = _propertyValue.toString();
                        else
                            methodParams[forwardParam.order()] = _propertyValue;
                    }

                    result = target.invoke(handler,methodParams);
                }
                else
                    result = target.invoke(handler,new Object[]{});

                if(result == null)
                    break;

                ModelAndView mv = (ModelAndView)result;
                mv.addAllObjects(request.getParameterMap());
                mv.addObject(throwableException.attributeName(), request.getSession().getAttribute(throwableException.attributeName()));
                mv.addObject("ex",exception);
                mv.addObject("message",exception.getMessage());

                return mv;
            }
        }
        
        return null;
    }

    private String toQName(String name)
    {
        StringBuilder builder = new StringBuilder();
        builder.append("get");
        builder.append(name.substring(0,1).toUpperCase());
        builder.append(name.substring(1));
        
        return builder.toString();
    }
    
    private Object get(Object target,String name) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
    {
        String path[] = name.split("[.]",2);

        Method method = target.getClass().getDeclaredMethod(toQName(path[0]), new Class[]{});
        
        Object value = method.invoke(target,new Object[]{});
        
        if(path.length == 1)
            return value;
        else
            return get(value, path[1]);
    }    
}

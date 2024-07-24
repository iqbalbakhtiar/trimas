/**
 * Oct 26, 2007 3:39:06 PM
 * com.siriuserp.sdk.aspect
 * ErrorDetectorAspect.java
 */
package com.siriuserp.sdk.aspect;


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.context.SecurityContextHolder;

import com.siriuserp.sdk.dm.User;
import com.siriuserp.sdk.exceptions.UserSessionExpired;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Aspect
public class UserSessionAspect
{
    @Before("execution(* com.siriuserp.*.service.*.*(..))")
    public void detect() throws UserSessionExpired
    {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal == null || !(principal instanceof User))
            throw new UserSessionExpired();
    }
}

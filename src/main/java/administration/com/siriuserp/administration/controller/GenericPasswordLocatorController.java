/**
 * Dec 20, 2006 2:06:57 PM
 * net.konsep.sirius.presentation.administration
 * PasswordLocatorController.java
 */
package com.siriuserp.administration.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Controller("genericPasswordLocatorController")
public class GenericPasswordLocatorController
{
    @RequestMapping("/preparePasswordLocator.htm")
    public ModelAndView preparePasswordLocator()
    {
        return new ModelAndView("passwordLocator");
    }
    
    @RequestMapping("/locatePassword.htm")
    public ModelAndView locatePassword(@RequestParam("userID")String id)
    {
        
//        if(id != null && !id.equals(""))
//        {
//            success = userManagementManager.locatePassword(id);
//            
//            if(success)
//            {
//                message = "Your Password has been send to your email address.";
//                return new ModelAndView("login","message",message);
//            }
//            else
//            {
//                message = "Invalid User ID or You don'n provide correct email address";
//                return new ModelAndView("passwordLocator","message",message);
//            }
//        }
        
        return new ModelAndView("login");
    }
}

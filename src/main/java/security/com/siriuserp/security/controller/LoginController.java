package com.siriuserp.security.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dm.DisplayConfiguration;
import com.siriuserp.sdk.utility.PathHelper;
import com.siriuserp.sdk.utility.SessionHelper;

/**
 * @author Ronny Mailindra
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Controller
public class LoginController extends ControllerBase
{    
	@Autowired
	private GenericDao genericDao;
	
    @RequestMapping("/signin.htm")
    public ModelAndView handleRequest(HttpServletRequest request,HttpServletResponse response) throws Exception 
    {
        DisplayConfiguration displayConfiguration = genericDao.load(DisplayConfiguration.class, Long.valueOf(1));
        if(displayConfiguration != null)
            SessionHelper.refreshLocale(request.getSession(false),displayConfiguration.getLocale());
        
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 1);
        
        return new ModelAndView("login","","");
    }
}
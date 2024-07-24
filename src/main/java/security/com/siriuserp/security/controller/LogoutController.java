package com.siriuserp.security.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.ui.rememberme.TokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.sdk.springmvc.view.ViewHelper;

@Controller
public class LogoutController
{
    @RequestMapping("/signout.htm")
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception 
    {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 1);
			
		HttpSession session = request.getSession(false);
		if(session != null)
		{
	        request.getSession(false).removeAttribute("menu");
	        request.getSession(false).invalidate(); //invalidate session		    
		}		

		Cookie terminate = new Cookie(TokenBasedRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY, null);
		terminate.setMaxAge(0);
		response.addCookie(terminate);

        SecurityContextHolder.getContext().setAuthentication(null);  /*old*/

		return ViewHelper.redirectTo("signin.htm");
	}
}

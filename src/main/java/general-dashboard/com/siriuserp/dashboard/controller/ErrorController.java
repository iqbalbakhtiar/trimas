package com.siriuserp.dashboard.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.sdk.base.ControllerBase;

/**
 * @author Muhammad Khairullah
 * Sirius Indonesia,PT
 * www.siriuserp.com
 */
@Controller
public class ErrorController extends ControllerBase {
	@RequestMapping("/error.htm")
    public ModelAndView error(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView("jspFailure");
    }

    @RequestMapping("/error404.htm")
    public ModelAndView pagenotfound(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView("404");
    }
    
    @RequestMapping("/error400.htm")
    public ModelAndView badrequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView("400");
    }
}

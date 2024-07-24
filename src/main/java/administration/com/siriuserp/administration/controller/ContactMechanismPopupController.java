/**
 * Jun 29, 2009 2:02:17 PM
 * com.siriuserp.administration.controller
 * ContactMechanismPopupController.java
 */
package com.siriuserp.administration.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.administration.criteria.ContactMechanismFilterCriteria;
import com.siriuserp.administration.query.ContactMechanismPopupGridViewQuery;
import com.siriuserp.administration.service.ContactMechanismService;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.JSONResponse;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Controller
public class ContactMechanismPopupController extends ControllerBase
{
    @Autowired
    private ContactMechanismService service;
    
    @RequestMapping("/popupcontactmechanismview.htm")
    public ModelAndView view(HttpServletRequest request,@RequestParam("target")String target) throws ServiceException
    {
        return new ModelAndView("contactMechanismPopup",service.view(criteriaFactory.create(request,ContactMechanismFilterCriteria.class),ContactMechanismPopupGridViewQuery.class));
    }
    
    @RequestMapping("/popupcontactmechanismjsonview.htm")
    public ModelAndView view(HttpServletRequest request) throws ServiceException
    {
        JSONResponse response = new JSONResponse();
        response.store("contacts",service.view(criteriaFactory.create(request,ContactMechanismFilterCriteria.class),ContactMechanismPopupGridViewQuery.class).get("contacts"));
        
        return response;
    }
}

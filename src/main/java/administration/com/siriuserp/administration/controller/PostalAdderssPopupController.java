/**
 * Apr 21, 2009 5:51:28 PM
 * com.siriuserp.popup.controller
 * PostalAdderssPopupController.java
 */
package com.siriuserp.administration.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.administration.criteria.PostalAddressFilterCriteria;
import com.siriuserp.administration.query.PostalAddessPopupGridViewQuery;
import com.siriuserp.administration.service.PostalAddressService;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.JSONResponse;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Controller
public class PostalAdderssPopupController extends ControllerBase
{
    @Autowired
    private PostalAddressService service;
    
    @RequestMapping("/popuppostaladdressview.htm")
    public ModelAndView view(HttpServletRequest request) throws ServiceException
    {
        return new ModelAndView("/administration-popup/postalAddressPopup",service.view(criteriaFactory.createPopup(request,PostalAddressFilterCriteria.class), PostalAddessPopupGridViewQuery.class));
    }
    
    @RequestMapping("/popuppostaladdressview2.htm")
    public ModelAndView view2(HttpServletRequest request) throws ServiceException
    {
        return new ModelAndView("/administration-popup/postalAddressPopup2",service.view(criteriaFactory.createPopup(request,PostalAddressFilterCriteria.class), PostalAddessPopupGridViewQuery.class));
    }
    
    @RequestMapping("/popuppostaladdressjsonview.htm")
    public ModelAndView json(HttpServletRequest request) throws ServiceException
    {
        JSONResponse view = new JSONResponse();
        view.store("addresses",service.view(criteriaFactory.createPopup(request,PostalAddressFilterCriteria.class), PostalAddessPopupGridViewQuery.class).get("addresses"));
        
        return view;
    }
}

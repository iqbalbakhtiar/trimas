/**
 * Feb 17, 2009 1:37:21 PM
 * com.siriuserp.popup
 * CustomerPopupController.java
 */
package com.siriuserp.administration.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.administration.query.CustomerPopupGridViewQuery2;
import com.siriuserp.administration.service.CustomerService;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.CustomerFilterCriteria;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
public class CustomerPopupController extends ControllerBase
{
	@Autowired
	private CustomerService customerService;

	@RequestMapping("/popupcustomerview.htm")
	public ModelAndView view(HttpServletRequest request) throws ServiceException
	{
		return new ModelAndView("/party-popup/customerPopup", customerService.view(criteriaFactory.createPopup(request, CustomerFilterCriteria.class), CustomerPopupGridViewQuery2.class));
	}

}

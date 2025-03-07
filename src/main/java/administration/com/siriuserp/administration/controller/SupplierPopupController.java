/**
 * Mar 27, 2009 3:18:53 PM
 * com.siriuserp.popup.controller
 * SupplierPopupController.java
 */
package com.siriuserp.administration.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.administration.query.SupplierGridViewQuery;
import com.siriuserp.administration.service.SupplierService;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.filter.SupplierFilterCriteria;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Controller
public class SupplierPopupController extends ControllerBase
{
	@Autowired
	private SupplierService service;

	@RequestMapping("/popupsupplierview.htm")
	public ModelAndView view(HttpServletRequest request, @RequestParam("target") String target, @RequestParam(required = false, value = "active") String active, @RequestParam(required = false, value = "reference") String reference) throws Exception
	{
		ModelAndView view = new ModelAndView("/party-popup/supplierPopup");
		view.addObject("target", target);
		view.addAllObjects(service.view(criteriaFactory.createPopup(request, SupplierFilterCriteria.class), SupplierGridViewQuery.class));

		return view;
	}

}

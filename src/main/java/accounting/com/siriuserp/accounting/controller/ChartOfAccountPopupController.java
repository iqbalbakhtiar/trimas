/**
 * Sep 8, 2009 10:55:37 AM
 * com.siriuserp.accounting.controller
 * ChartOfAccountPopupController.java
 */
package com.siriuserp.accounting.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.accounting.criteria.chartOfAccountFilterCriteria;
import com.siriuserp.accounting.query.ChartOfAccountPopupGridViewQuery;
import com.siriuserp.accounting.service.ChartOfAccountService;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.exceptions.ServiceException;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
public class ChartOfAccountPopupController extends ControllerBase
{
	@Autowired
	private ChartOfAccountService service;

	@RequestMapping("/popupchartofaccountview.htm")
	public ModelAndView view(HttpServletRequest request, @RequestParam("target") String target) throws ServiceException
	{
		ModelAndView view = new ModelAndView("/accounting/chartOfAccountPopup");
		view.addObject("target", target);
		view.addAllObjects(service.view(criteriaFactory.createPopup(request, chartOfAccountFilterCriteria.class), ChartOfAccountPopupGridViewQuery.class));

		return view;
	}
}

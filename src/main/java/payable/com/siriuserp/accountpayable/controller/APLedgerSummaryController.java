/**
 * Mar 11, 2010 10:35:42 AM
 * com.siriuserp.accountpayable.controller
 * APLedgerSummaryController.java
 */
package com.siriuserp.accountpayable.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.accountpayable.criteria.APLedgerFilterCriteria;
import com.siriuserp.accountpayable.service.APLedgerService;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Party;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 * Version 1.5
 */

@Controller
@SessionAttributes(value = "criteria", types = APLedgerFilterCriteria.class)
public class APLedgerSummaryController extends ControllerBase
{
	@Autowired
	private APLedgerService service;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		initBinderFactory.initBinder(binder, Party.class);
	}

	@RequestMapping("/apledgersummarypre.htm")
	public ModelAndView pre()
	{
		return new ModelAndView("apLedgerSummaryReportAdd", service.pre());
	}

	@RequestMapping("/apledgersummaryview.htm")
	public ModelAndView view(@ModelAttribute("criteria") APLedgerFilterCriteria criteria)
	{
		return new ModelAndView("apLedgerSummaryReportList", service.view(criteria));
	}

}

/**
 * Mar 11, 2010 10:35:42 AM
 * com.siriuserp.accountpayable.controller
 * APLedgerSummaryController.java
 */
package com.siriuserp.accountpayable.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
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
public class APLedgerDetailController extends ControllerBase
{
	@Autowired
	private APLedgerService service;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
	}

	@RequestMapping("/apledgerdetailpre.htm")
	public ModelAndView pre() throws Exception
	{
		return new ModelAndView("/report/payable/apLedgerDetailReportAdd", service.pre());
	}

	@RequestMapping("/apledgerdetailview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/report/payable/apLedgerDetailReportList", service.viewdetail(criteriaFactory.createReport(request, APLedgerFilterCriteria.class)));
	}
}

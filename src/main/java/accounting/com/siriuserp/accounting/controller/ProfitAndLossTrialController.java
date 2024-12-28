/**
 * Dec 12, 2008 9:22:19 AM
 * com.siriuserp.reporting.accounting.controller
 * ProfitLossController.java
 */
package com.siriuserp.accounting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.accounting.dm.AccountingPeriod;
import com.siriuserp.accounting.service.ProfitLossTrialService;
import com.siriuserp.reporting.accounting.criteria.ProfitLossFilterCriteria;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.springmvc.XLSFile;

/**
 * @author Muhammad Khairullah
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = "filterCriteria", types = ProfitLossFilterCriteria.class)
@DefaultRedirect(url = "profitlosstrialreportpre.htm")
public class ProfitAndLossTrialController extends ControllerBase
{
	@Autowired
	private ProfitLossTrialService profitLossService;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(AccountingPeriod.class, modelEditor.forClass(AccountingPeriod.class));
		binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
	}

	@RequestMapping("/profitlosstrialreportpre.htm")
	public ModelAndView prepare()
	{
		return new ModelAndView("/report/accounting/profitLossTrialReportAdd", profitLossService.pre());
	}

	@RequestMapping("/profitlosstrialreportview.htm")
	public ModelAndView view(@ModelAttribute("filterCriteria") ProfitLossFilterCriteria criteria, BindingResult result, SessionStatus status)
	{
		return new ModelAndView("/report/accounting/profitLossTrialReportList", profitLossService.view(criteria));
	}

	@RequestMapping("/profitlosstrialreportexcelview.xls")
	public ModelAndView toexcel(@ModelAttribute("filterCriteria") ProfitLossFilterCriteria criteria, BindingResult result, SessionStatus status)
	{
		return new XLSFile("/report/accounting/profitLossTrialReportPrint", profitLossService.view(criteria));
	}
}

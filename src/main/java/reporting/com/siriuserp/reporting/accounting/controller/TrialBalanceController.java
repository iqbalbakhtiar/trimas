/**
 * Dec 2, 2008 9:49:44 AM
 * com.siriuserp.reporting.controller.accounting
 * TrialBalanceController.java
 */
package com.siriuserp.reporting.accounting.controller;

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

import com.siriuserp.accounting.criteria.DefaultAccountingReportFilterCriteria;
import com.siriuserp.accounting.dm.AccountingPeriod;
import com.siriuserp.reporting.accounting.service.TrialBalanceService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Party;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = "filterCriteria", types = DefaultAccountingReportFilterCriteria.class)
@DefaultRedirect(url = "trialbalancereportpre.htm")
public class TrialBalanceController extends ControllerBase
{
	@Autowired
	private TrialBalanceService trialBalanceService;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(AccountingPeriod.class, modelEditor.forClass(AccountingPeriod.class));
		binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
	}

	@RequestMapping("/trialbalancereportpre.htm")
	public ModelAndView pre()
	{
		return new ModelAndView("/report/accounting/trialBalanceReportAdd", trialBalanceService.pre());
	}

	@RequestMapping("/trialbalancereportview.htm")
	public ModelAndView view(@ModelAttribute("filterCriteria") DefaultAccountingReportFilterCriteria criteria, BindingResult result, SessionStatus status)
	{
		return new ModelAndView("/report/accounting/trialBalanceReportList", trialBalanceService.view(criteria));
	}
}

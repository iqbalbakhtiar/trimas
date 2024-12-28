/**
 * Dec 4, 2008 3:46:20 PM
 * com.siriuserp.reporting.accounting.controller
 * BalanceSheetController.java
 */
package com.siriuserp.reporting.accounting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.accounting.dm.AccountingPeriod;
import com.siriuserp.reporting.accounting.criteria.ProfitLossFilterCriteria;
import com.siriuserp.reporting.accounting.service.BalanceSheetService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.springmvc.XLSFile;

import javolution.util.FastList;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = "filterCriteria", types = ProfitLossFilterCriteria.class)
@DefaultRedirect(url = "balancesheetreportpre.htm")
public class BalanceSheetController extends ControllerBase
{
	@Autowired
	private BalanceSheetService service;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(AccountingPeriod.class, modelEditor.forClass(AccountingPeriod.class));
		binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
	}

	@RequestMapping("/balancesheetreportpre.htm")
	public ModelAndView pre()
	{
		return new ModelAndView("/report/accounting/balanceSheetReportAdd", service.pre());
	}

	@RequestMapping("/balancesheetreportview.htm")
	public ModelAndView view(@ModelAttribute("filterCriteria") ProfitLossFilterCriteria criteria, BindingResult result, SessionStatus status, @RequestParam(value = "organizations", required = false) Long[] orgs,
			@RequestParam(value = "periods", required = false) Long[] periods)
	{
		if (orgs != null)
		{
			FastList.recycle(criteria.getOrganizationIds());
			for (Long org : orgs)
				criteria.getOrganizationIds().add(org);
		}

		if (periods != null)
		{
			FastList.recycle(criteria.getAccountingPeriodIds());
			for (Long period : periods)
				criteria.getAccountingPeriodIds().add(period);
		}

		return new ModelAndView("/report/accounting/balanceSheetReportList", service.view(criteria));
	}

	@RequestMapping("/balancesheetreportexcelview.xls")
	public ModelAndView doexcel(@ModelAttribute("filterCriteria") ProfitLossFilterCriteria criteria, BindingResult result, SessionStatus status, @RequestParam(value = "organizations", required = false) Long[] orgs,
			@RequestParam(value = "periods", required = false) Long[] periods)
	{
		if (orgs != null)
		{
			FastList.recycle(criteria.getOrganizationIds());
			for (Long org : orgs)
				criteria.getOrganizationIds().add(org);
		}

		if (periods != null)
		{
			FastList.recycle(criteria.getAccountingPeriodIds());
			for (Long period : periods)
				criteria.getAccountingPeriodIds().add(period);
		}

		return new XLSFile("/report/accounting/balanceSheetReportPrint", service.view(criteria));
	}
}

/**
 * Nov 10, 2008 3:55:14 PM
 * com.siriuserp.accounting.controller
 * ChartOfAccountController.java
 */
package com.siriuserp.accounting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.accounting.dm.ChartOfAccount;
import com.siriuserp.accounting.service.ChartOfAccountService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.view.ViewHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = "chartOfAccount", types = ChartOfAccount.class)
@DefaultRedirect(url = "chartofaccountview.htm")
public class ChartOfAccountController
{
	@Autowired
	private ChartOfAccountService service;

	@RequestMapping("/chartofaccountview.htm")
	public ModelAndView view()
	{
		return new ModelAndView("/accounting/chartOfAccountList", service.view());
	}

	@RequestMapping("/chartofaccountpreadd.htm")
	public ModelAndView preadd()
	{
		return new ModelAndView("/accounting/chartOfAccountAdd", service.preadd());
	}

	@RequestMapping("/chartofaccountadd.htm")
	public ModelAndView add(@ModelAttribute("chartOfAccount") ChartOfAccount chartOfAccount, BindingResult result, SessionStatus status) throws ServiceException
	{
		service.add(chartOfAccount);
		return ViewHelper.redirectTo("chartofaccountview.htm");
	}

	@RequestMapping("/chartofaccountpreedit.htm")
	public ModelAndView preedit(@RequestParam("id") String id)
	{
		return new ModelAndView("/accounting/chartOfAccountUpdate", service.preedit(id));
	}

	@RequestMapping("/chartofaccountedit.htm")
	public ModelAndView edit(@ModelAttribute("chartOfAccount") ChartOfAccount chartOfAccount, BindingResult result, SessionStatus status) throws ServiceException
	{
		service.edit(chartOfAccount);
		return ViewHelper.redirectTo("chartofaccountview.htm");
	}

	@RequestMapping("/chartofaccountdelete.htm")
	public ModelAndView delete(@RequestParam("id") String id) throws ServiceException
	{
		service.delete(service.load(id));

		return ViewHelper.redirectTo("chartofaccountview.htm");
	}

	@RequestMapping("/chartofaccountprint.htm")
	public ModelAndView print(@RequestParam("id") String id) throws ServiceException
	{
		return new ModelAndView("/accounting/chartOfAccountPrint", "COA", service.load(id));
	}
}

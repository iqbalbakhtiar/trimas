package com.siriuserp.accounting.controller;

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
import com.siriuserp.accounting.dm.PeriodStatus;
import com.siriuserp.accounting.service.AccountingPeriodService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.editor.GenericEnumEditor;
import com.siriuserp.sdk.springmvc.view.ViewHelper;

@Controller
@SessionAttributes(value = "accountingPeriod", types = AccountingPeriod.class)
@DefaultRedirect(url = "accountingperiodview.htm")
public class AccountingPeriodController extends ControllerBase
{
	@Autowired
	private AccountingPeriodService accountingPeriodService;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
		binder.registerCustomEditor(PeriodStatus.class, new GenericEnumEditor(PeriodStatus.class));
		binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
	}

	@RequestMapping("/accountingperiodview.htm")
	public ModelAndView view()
	{
		return new ModelAndView("/accounting/accountingPeriodList", accountingPeriodService.view());
	}

	@RequestMapping("/accountingperiodpreadd.htm")
	public ModelAndView preadd(@RequestParam(value = "parent", required = false) String id)
	{
		return new ModelAndView("/accounting/accountingPeriodAdd", accountingPeriodService.preadd(id));
	}

	@RequestMapping("/accountingperiodadd.htm")
	public ModelAndView add(@ModelAttribute("accountingPeriod") AccountingPeriod accountingPeriod, BindingResult result, SessionStatus status) throws ServiceException
	{
		accountingPeriodService.add(accountingPeriod);
		status.setComplete();

		return ViewHelper.redirectTo("accountingperiodview.htm");
	}

	@RequestMapping("/accountingperiodpreedit.htm")
	public ModelAndView preedit(@RequestParam("id") String id)
	{
		return new ModelAndView("/accounting/accountingPeriodUpdate", accountingPeriodService.preedit(id));
	}

	@RequestMapping("/accountingperiodedit.htm")
	public ModelAndView edit(@ModelAttribute("accountingPeriod") AccountingPeriod accountingPeriod, BindingResult result, SessionStatus status) throws ServiceException
	{
		accountingPeriodService.edit(accountingPeriod);
		status.setComplete();

		return ViewHelper.redirectTo("accountingperiodview.htm");
	}

	@RequestMapping("/accountingperioddelete.htm")
	public ModelAndView delete(@RequestParam("id") String id) throws ServiceException
	{
		accountingPeriodService.delete(accountingPeriodService.load(id));
		return ViewHelper.redirectTo("accountingperiodview.htm");
	}

	@RequestMapping("/accountingperiodopen.htm")
	public ModelAndView open(@RequestParam("id") String id) throws ServiceException
	{
		accountingPeriodService.open(id);
		return ViewHelper.redirectTo("accountingperiodview.htm");
	}

	@RequestMapping("/accountingperiodpreclose.htm")
	public ModelAndView preclose(@RequestParam("id") String id) throws Exception
	{
		accountingPeriodService.preclose(id);
		return ViewHelper.redirectTo("accountingperiodview.htm");
	}

	@RequestMapping("/accountingperiodclose.htm")
	public ModelAndView close(@RequestParam("id") String id) throws ServiceException
	{
		accountingPeriodService.close(id);
		return ViewHelper.redirectTo("accountingperiodview.htm");
	}
}

/**
 * Dec 11, 2008 2:29:53 PM
 * com.siriuserp.reporting.accounting.controller
 * GLRegisterController.java
 */
package com.siriuserp.reporting.accounting.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.accounting.criteria.GLRegisterFilterCriteria;
import com.siriuserp.accounting.dm.AccountingPeriod;
import com.siriuserp.reporting.accounting.service.GLRegisterService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.ResponseStatus;
import com.siriuserp.sdk.springmvc.XLSFile;

import javolution.util.FastList;

/**
 * @author Muhammad Rizal
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
@DefaultRedirect(url = "glregisterreportpre.htm")
public class GLRegisterController extends ControllerBase
{
	@Autowired
	private GLRegisterService registerService;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		initBinderFactory.initBinder(binder, AccountingPeriod.class, Party.class, Currency.class);
	}

	@RequestMapping("/glregisterreportpre.htm")
	public ModelAndView pre()
	{
		return new ModelAndView("/report/accounting/glRegisterReportAdd", registerService.pre());
	}

	@RequestMapping("/glregisterreportview.htm")
	public ModelAndView view(HttpServletRequest request) throws ServiceException
	{
		return new ModelAndView("/report/accounting/glRegisterReportList", registerService.view(criteriaFactory.createReportAccounting(request, GLRegisterFilterCriteria.class)));
	}

	@RequestMapping("/glregisterreportbydatepre.htm")
	public ModelAndView preByDate()
	{
		return new ModelAndView("/report/accounting/glRegisterByDateReportAdd", registerService.pre());
	}

	@RequestMapping("/glregisterreportbydateview.htm")
	public ModelAndView viewByDate(HttpServletRequest request) throws ServiceException
	{
		return new ModelAndView("/report/accounting/glRegisterByDateReportList", registerService.view(criteriaFactory.createReportAccounting(request, GLRegisterFilterCriteria.class)));
	}

	@RequestMapping("/glregisterreportjson.htm")
	public ModelAndView viewJson(HttpServletRequest request) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			response.store(registerService.detail(criteriaFactory.createReportAccounting(request, GLRegisterFilterCriteria.class)));
		} catch (Exception e)
		{
			response.setStatus(ResponseStatus.ERROR);
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

	@RequestMapping("/glregisterreportpagingexcelview.xls")
	public ModelAndView toExcel2(@ModelAttribute("glRegisterFilterCriteria") GLRegisterFilterCriteria criteria, BindingResult result, SessionStatus status, @RequestParam(value = "accounts", required = false) Long accounts)
	{
		if (accounts != null)
		{
			FastList.recycle(criteria.getAccounts());
			criteria.getAccounts().add(accounts);

		} else
			return new XLSFile("report/accounting/glRegisterPagingReportPrint", registerService.beginpaging(criteria));

		return new XLSFile("report/accounting/glRegisterPagingReportPrint", registerService.filterpaging(criteria));
	}
}

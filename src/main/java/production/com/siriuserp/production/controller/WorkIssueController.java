package com.siriuserp.production.controller;

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

import com.siriuserp.production.dm.WorkIssue;
import com.siriuserp.production.service.WorkIssueService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.ResponseStatus;
import com.siriuserp.sdk.springmvc.view.ViewHelper;

/**
 * @author ferdinand
 */

@Controller
@SessionAttributes(value = {"work_add", "work_edit"}, types = WorkIssue.class)
@DefaultRedirect(url = "workissuepreadd.htm")
public class WorkIssueController extends ControllerBase 
{
	@Autowired
	private WorkIssueService service;
	
	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(WorkIssue.class, modelEditor.forClass(WorkIssue.class));
	}
	
	@RequestMapping("/workissuepreadd.htm")
	public ModelAndView preadd(@RequestParam("id") Long id) throws ServiceException
	{
		return new ModelAndView("/production/production-order/workIssueAdd", service.preadd(id));
	}
	
	@RequestMapping("/workissueadd.htm")
	public ModelAndView add(@ModelAttribute("work_add") WorkIssue workIssue, SessionStatus status) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.add(workIssue);
			status.setComplete();

			response.store("id", workIssue.getId());
		} 
		catch (Exception e)
		{
			response.setStatus(ResponseStatus.ERROR);
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}
	
	@RequestMapping("/workissuepreedit.htm")
	public ModelAndView preedit(Long id) throws ServiceException
	{
		return new ModelAndView("/production/production-order/workIssueUpdate", service.preedit(id));
	}
	
	@RequestMapping("/workissueedit.htm")
	public ModelAndView edit(@ModelAttribute("work_edit") WorkIssue workIssue, BindingResult result, SessionStatus status) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.edit(workIssue);
			status.setComplete();

			response.store("id", workIssue.getId());
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}
	
	@RequestMapping("/workissuedelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id) throws ServiceException
	{
		Long poId = service.load(id).getProductionOrderDetail().getId();
		service.delete(service.load(id));

		return ViewHelper.redirectTo("productionorderdetailpreedit.htm?id="+poId);
	}
}

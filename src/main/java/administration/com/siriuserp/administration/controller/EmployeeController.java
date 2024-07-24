/**
 * File Name  : EmployeeController.java
 * Created On : Jan 31, 2019
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.administration.controller;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.administration.criteria.PartyFilterCriteria;
import com.siriuserp.administration.query.EmployeeGridViewQuery;
import com.siriuserp.administration.query.EmployeePopupGridViewQuery;
import com.siriuserp.administration.service.EmployeeService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.ResponseStatus;
import com.siriuserp.sdk.springmvc.view.ViewHelper;

import javolution.util.FastMap;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = { "employee_add", "employee_edit" }, types = Party.class)
@DefaultRedirect(url = "employeeview.htm")
public class EmployeeController extends ControllerBase
{
	@Autowired
	private EmployeeService service;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
	}

	@RequestMapping("/employeeview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/administration/employeeList", service.view(criteriaFactory.create(request, PartyFilterCriteria.class), EmployeeGridViewQuery.class));
	}

	@RequestMapping("/employeepreadd.htm")
	public ModelAndView preadd()
	{
		return new ModelAndView("/administration/employeeAdd", service.preadd());
	}

	@RequestMapping("/employeeadd.htm")
	public ModelAndView add(@ModelAttribute("employee_add") Party person, BindingResult result, SessionStatus status, @RequestParam(value = "employeeId", required = false) Long employeeId,
			@RequestParam(value = "file", required = false) MultipartFile file) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			FastMap<String, Object> map = service.add(person, employeeId, file);
			status.setComplete();

			response.store("data", map);
		} catch (Exception e)
		{
			response.setStatus(ResponseStatus.ERROR);
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

	@RequestMapping("/employeepreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id) throws Exception
	{
		return new ModelAndView("/administration/employeeUpdate", service.preedit(id));
	}

	@RequestMapping("/employeeedit.htm")
	public ModelAndView edit(@ModelAttribute("employee_edit") Party person, BindingResult result, SessionStatus status, @RequestParam("relationshipId") Long relationshipId, @RequestParam(value = "active", required = false) Boolean active,
			@RequestParam(value = "note", required = false) String note, @RequestParam(value = "file", required = false) MultipartFile file) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.edit(person, relationshipId, active, note, file);
			status.setComplete();

			response.store("id", relationshipId);
		} catch (Exception e)
		{
			response.setStatus(ResponseStatus.ERROR);
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

	@RequestMapping("/employeedelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id) throws Exception
	{
		service.delete(id);
		return ViewHelper.redirectTo("employeeview.htm");
	}

	@RequestMapping("/employeeshowimage.htm")
	public ModelAndView showimage(@RequestParam("id") Long id) throws Exception
	{
		return new ModelAndView("image-shower", "model", service.load(id));
	}

	@RequestMapping("/employeepopupview.htm")
	public ModelAndView popup(HttpServletRequest request, @RequestParam(value = "target", required = false) String target) throws Exception
	{
		ModelAndView view = new ModelAndView("/administration-popup/employeePopup");
		view.addAllObjects(service.view(criteriaFactory.createPopup(request, PartyFilterCriteria.class), EmployeePopupGridViewQuery.class));
		view.addObject("target", target);

		return view;
	}
}

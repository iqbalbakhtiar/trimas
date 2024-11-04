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
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.administration.query.CustomerGridViewQuery;
import com.siriuserp.administration.service.CustomerService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.CustomerFilterCriteria;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.view.ViewHelper;

import javolution.util.FastMap;

@Controller
@SessionAttributes(value = { "customer_add", "customer_edit" }, types = Party.class)
@DefaultRedirect(url = "customerview.htm")
public class CustomerController extends ControllerBase {
	
	@Autowired
	private CustomerService customerService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
	}
	
	@RequestMapping("/customerview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception	
	{
		return new ModelAndView("/administration/customerList",
				customerService.view(criteriaFactory.create(request, CustomerFilterCriteria.class), CustomerGridViewQuery.class));
	}
	
	@RequestMapping("/customerpreadd.htm")
	public ModelAndView preadd()
	{
		return new ModelAndView("/administration/customerAdd", customerService.preadd());
	}
	
	@RequestMapping("/customeradd.htm")
	public ModelAndView add(@ModelAttribute("customer_add") Party customer, BindingResult result, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try {
			FastMap<String, Object> map = customerService.add(customer);
			status.setComplete();

			response.store("data", map);
		} catch (Exception e) {
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}
	
	@RequestMapping("/customerpreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id, @RequestParam(value = "lastPanel", required = false) String lastPanel) throws Exception {
		// Param id adalah Party Relationship bukan "Party"
		ModelAndView view = new ModelAndView("/administration/customerUpdate", customerService.preedit(id));
		view.addObject("lastPanel", lastPanel);
		return view;
	}
	
	@RequestMapping("/customeredit.htm")
	public ModelAndView edit(@ModelAttribute("customer_edit") Party customer, BindingResult result, @RequestParam("relationshipId") Long relationshipId, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try {
			FastMap<String, Object> map = customerService.edit(customer, relationshipId);
			status.setComplete();

			response.store("data", map);
		} catch (Exception e) {
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}
	
	@RequestMapping("/customerdelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id) throws Exception
	{
		customerService.delete(id);
		return ViewHelper.redirectTo("customerview.htm");
	}
}

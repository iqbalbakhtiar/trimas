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

import com.siriuserp.administration.query.CustomerGroupGridViewQuery;
import com.siriuserp.administration.service.CustomerGroupService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.filter.CustomerFilterCriteria;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.view.ViewHelper;

import javolution.util.FastMap;

@Controller
@SessionAttributes(value = { "customer_group_add", "customer_group_edit" }, types = Party.class)
@DefaultRedirect(url = "customerview.htm")
public class CustomerGroupController extends ControllerBase {
	
	@Autowired
	private CustomerGroupService service;
	
	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
	}
	
	@RequestMapping("/customergrouppreadd.htm")
	public ModelAndView preadd()
	{
		return new ModelAndView("/administration/customerGroupAdd", service.preadd());
	}
	
	@RequestMapping("/customergrouppreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id) throws Exception {
		// Param id adalah Party Relationship bukan "Party"
		return new ModelAndView("/administration/customerGroupUpdate", service.preedit(id));
	}

	@RequestMapping("/customergroupadd.htm")
	public ModelAndView add(@ModelAttribute("customer_group_add") Party customerGroup, BindingResult result, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try {
			FastMap<String, Object> map = service.add(customerGroup);
			status.setComplete();

			response.store("data", map);
		} catch (Exception e) {
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}
	
	@RequestMapping("/customergroupedit.htm")
	public ModelAndView edit(@ModelAttribute("customer_group_edit") Party customerGroup, BindingResult result, @RequestParam("relationshipId") Long relationshipId, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try {
			FastMap<String, Object> map = service.edit(customerGroup, relationshipId);
			status.setComplete();

			response.store("data", map);
		} catch (Exception e) {
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}
	
	@RequestMapping("/customergroupdelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id) throws Exception
	{
		service.delete(id);
		return ViewHelper.redirectTo("customerview.htm");
	}
}

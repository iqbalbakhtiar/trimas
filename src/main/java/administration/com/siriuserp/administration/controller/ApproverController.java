package com.siriuserp.administration.controller;

import javax.servlet.http.HttpServletRequest;

import com.siriuserp.sdk.dm.PartyRoleType;
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

import com.siriuserp.administration.form.PartyForm;
import com.siriuserp.administration.query.ApproverGridViewQuery;
import com.siriuserp.administration.service.ApproverService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.filter.ApproverFilterCriteria;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.view.ViewHelper;
import com.siriuserp.sdk.utility.FormHelper;

import javolution.util.FastMap;

@Controller
@SessionAttributes(value = { "approver_add", "approver_edit" }, types = PartyForm.class)
@DefaultRedirect(url = "approverview.htm")
public class ApproverController extends ControllerBase {
	@Autowired
	private ApproverService service;
	
	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
		binder.registerCustomEditor(PartyRoleType.class, modelEditor.forClass(PartyRoleType.class));
	}
	
	@RequestMapping("/approverview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception	
	{
		return new ModelAndView("/administration/approverList", service.view(criteriaFactory.create(request, ApproverFilterCriteria.class), ApproverGridViewQuery.class));
	}
	
	@RequestMapping("/approverpreadd.htm")
	public ModelAndView preadd()
	{
		return new ModelAndView("/administration/approverAdd", service.preadd());
	}
	
	@RequestMapping("/approveradd.htm")
	public ModelAndView add(@ModelAttribute("approver_add") PartyForm partyForm, BindingResult result, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try {
			FastMap<String, Object> map = service.add(partyForm);
			status.setComplete();

			response.store("data", map);
		} catch (Exception e) {
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}
	
	@RequestMapping("/approverpreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id, @RequestParam(value = "lastPanel", required = false) String lastPanel) throws Exception {
		// Param id adalah Party Relationship bukan "Party"
		ModelAndView view = new ModelAndView("/administration/approverUpdate", service.preedit(id));
		view.addObject("lastPanel", lastPanel);
		return view;
	}
	
	@RequestMapping("/approveredit.htm")
	public ModelAndView edit(@ModelAttribute("approver_edit") PartyForm partyForm, BindingResult result, @RequestParam("relationshipId") Long relationshipId, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try {
	        Party approver = partyForm.getApprover();
	        FormHelper.update(approver, partyForm);
			
			FastMap<String, Object> map = service.edit(approver, relationshipId);
			status.setComplete();

			response.store("data", map);
		} catch (Exception e) {
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}
	
	@RequestMapping("/approverdelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id) throws Exception
	{
		service.delete(id);
		return ViewHelper.redirectTo("approverview.htm");
	}
}

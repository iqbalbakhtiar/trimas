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

import com.siriuserp.administration.query.SupplierGridViewQuery;
import com.siriuserp.administration.service.SupplierService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.filter.SupplierFilterCriteria;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.view.ViewHelper;

import javolution.util.FastMap;

@Controller
@SessionAttributes(value = { "supplier_add", "supplier_edit" }, types = Party.class)
@DefaultRedirect(url = "supplierview.htm")
public class SupplierController extends ControllerBase {
	
	@Autowired
	private SupplierService service;
	
	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
	}
	
	@RequestMapping("/supplierview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception	
	{
		return new ModelAndView("/administration/supplierList", service.view(criteriaFactory.create(request, SupplierFilterCriteria.class), SupplierGridViewQuery.class));
	}
	
	@RequestMapping("/supplierpreadd.htm")
	public ModelAndView preadd()
	{
		return new ModelAndView("/administration/supplierAdd", service.preadd());
	}
	
	@RequestMapping("/supplieradd.htm")
	public ModelAndView add(@ModelAttribute("supplier_add") Party supplier, BindingResult result, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try {
			FastMap<String, Object> map = service.add(supplier);
			status.setComplete();

			response.store("data", map);
		} catch (Exception e) {
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}
	
	@RequestMapping("/supplierpreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id, @RequestParam(value = "lastPanel", required = false) String lastPanel) throws Exception {
		// Param id adalah Party Relationship bukan "Party"
		ModelAndView view = new ModelAndView("/administration/supplierUpdate", service.preedit(id));
		view.addObject("lastPanel", lastPanel);
		return view;
	}
	
	@RequestMapping("/supplieredit.htm")
	public ModelAndView edit(@ModelAttribute("supplier_edit") Party supplier, BindingResult result, @RequestParam("relationshipId") Long relationshipId, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try {
			FastMap<String, Object> map = service.edit(supplier, relationshipId);
			status.setComplete();

			response.store("data", map);
		} catch (Exception e) {
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}
	
	@RequestMapping("/supplierdelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id) throws Exception
	{
		service.delete(id);
		return ViewHelper.redirectTo("supplierview.htm");
	}
}

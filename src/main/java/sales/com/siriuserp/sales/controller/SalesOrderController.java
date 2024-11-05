package com.siriuserp.sales.controller;

import javax.servlet.http.HttpServletRequest;

import com.siriuserp.sdk.dm.*;
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

import com.siriuserp.inventory.dm.Product;
import com.siriuserp.sales.criteria.SalesOrderFilterCriteria;
import com.siriuserp.sales.dm.SOStatus;
import com.siriuserp.sales.dm.SalesOrder;
import com.siriuserp.sales.dm.SalesOrderItem;
import com.siriuserp.sales.dm.SalesType;
import com.siriuserp.sales.form.SalesForm;
import com.siriuserp.sales.query.SalesOrderGridViewQuery;
import com.siriuserp.sales.service.SalesOrderService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.utility.FormHelper;

import javolution.util.FastMap;

@Controller
@SessionAttributes(value = { "salesOrder_form" }, types = SalesForm.class)
@DefaultRedirect(url = "salesorderview.htm")
public class SalesOrderController extends ControllerBase {
	
	@Autowired
	SalesOrderService service;
	
	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
		binder.registerCustomEditor(Product.class, modelEditor.forClass(Product.class));
		binder.registerCustomEditor(Tax.class, modelEditor.forClass(Tax.class));
		binder.registerCustomEditor(Currency.class, modelEditor.forClass(Currency.class));
		binder.registerCustomEditor(PostalAddress.class, modelEditor.forClass(PostalAddress.class));
		binder.registerCustomEditor(SalesOrder.class, modelEditor.forClass(SalesOrder.class));
		binder.registerCustomEditor(SalesOrderItem.class, modelEditor.forClass(SalesOrderItem.class));
		binder.registerCustomEditor(SalesType.class, enumEditor.forClass(SalesType.class));
		binder.registerCustomEditor(SOStatus.class, enumEditor.forClass(SOStatus.class));
		binder.registerCustomEditor(ApprovalDecisionStatus.class, enumEditor.forClass(ApprovalDecisionStatus.class));
		
	}
	
	@RequestMapping("/salesorderview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception	
	{
		return new ModelAndView("/sales/salesOrderList",
				service.view(criteriaFactory.create(request, SalesOrderFilterCriteria.class), SalesOrderGridViewQuery.class));
	}
	
	@RequestMapping("/salesorderpreadd.htm")
	public ModelAndView preadd()
	{
		return new ModelAndView("/sales/salesOrderAdd", service.preadd());
	}
	
	@RequestMapping("/salesorderadd.htm")
	public ModelAndView add(@ModelAttribute("salesOrder_form") SalesForm salesForm, BindingResult result, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try {
			FastMap<String, Object> map = service.add(FormHelper.create(SalesOrder.class, salesForm));
			status.setComplete();

			response.store("data", map);
		} catch (Exception e) {
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}
	
	@RequestMapping("/salesorderpreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id) throws Exception {
		return new ModelAndView("/sales/salesOrderUpdate", service.preedit(id));
	}

	@RequestMapping("/salesorderedit.htm")
	public ModelAndView edit(@ModelAttribute("salesOrder_form") SalesForm salesForm, BindingResult result, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try {
			service.edit(FormHelper.update(salesForm.getSalesOrder(), salesForm));
			status.setComplete();

			response.store("id", salesForm.getSalesOrder().getId());
		} catch (Exception e) {
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}

	@RequestMapping("/salesorderprint.htm")
	public ModelAndView print(@RequestParam("id") Long id) throws Exception {
		return new ModelAndView("/sales/salesOrderPrint", service.preedit(id));
	}
}

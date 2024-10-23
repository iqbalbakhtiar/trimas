package com.siriuserp.sales.controller;

import com.siriuserp.sales.criteria.DeliveryOrderFilterCriteria;
import com.siriuserp.sales.dm.DeliveryOrder;
import com.siriuserp.sales.dm.SalesOrder;
import com.siriuserp.sales.dm.SalesOrderItem;
import com.siriuserp.sales.form.DeliveryOrderForm;
import com.siriuserp.sales.query.DeliveryOrderGridViewQuery;
import com.siriuserp.sales.query.DeliveryOrderPreaddViewQuery;
import com.siriuserp.sales.service.DeliveryOrderService;
import com.siriuserp.sales.service.SalesOrderService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.PostalAddress;
import com.siriuserp.sdk.dm.SalesReferenceItem;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.utility.FormHelper;
import com.siriuserp.sdk.utility.ObjectPrinter;
import javolution.util.FastMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@SessionAttributes(value = { "deliveryOrder_form" }, types = DeliveryOrderForm.class)
@DefaultRedirect(url = "deliveryorderview.htm")
public class DeliveryOrderController extends ControllerBase {

    @Autowired
    DeliveryOrderService doService;

	@Autowired
	SalesOrderService salesOrderService;

    @InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(SalesOrder.class, modelEditor.forClass(SalesOrder.class));
		binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
		binder.registerCustomEditor(Facility.class, modelEditor.forClass(Facility.class));
		binder.registerCustomEditor(PostalAddress.class, modelEditor.forClass(PostalAddress.class));
		binder.registerCustomEditor(SalesOrderItem.class, modelEditor.forClass(SalesOrderItem.class));
		binder.registerCustomEditor(SalesReferenceItem.class, modelEditor.forClass(SalesReferenceItem.class));
	}

    @RequestMapping("/deliveryorderview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/sales/deliveryOrderList",
				doService.view(criteriaFactory.create(request, DeliveryOrderFilterCriteria.class), DeliveryOrderGridViewQuery.class));
	}

	@RequestMapping("/deliveryorderpreadd1.htm")
	public ModelAndView preadd1(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/sales/deliveryOrderAdd1",
				salesOrderService.view(criteriaFactory.create(request, DeliveryOrderFilterCriteria.class), DeliveryOrderPreaddViewQuery.class));
	}

	@RequestMapping("/deliveryorderpreadd2.htm")
	public ModelAndView preadd2(@RequestParam("salesOrder") Long id) throws Exception
	{
		return new ModelAndView("/sales/deliveryOrderAdd2", doService.preadd2(id));
	}

	@RequestMapping("/deliveryorderadd.htm")
	public ModelAndView add(@ModelAttribute("deliveryOrder_form") DeliveryOrderForm deliveryOrderForm, BindingResult result, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try {
			FastMap<String, Object> map = doService.add(FormHelper.create(DeliveryOrder.class, deliveryOrderForm));
			status.setComplete();

			response.store("data", map);
		} catch (Exception e) {
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}

	@RequestMapping("/deliveryorderpreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id) throws Exception {
		return new ModelAndView("/sales/deliveryOrderUpdate", doService.preedit(id));
	}

	@RequestMapping("/deliveryorderedit.htm")
	public ModelAndView edit(@ModelAttribute("deliveryOrder_form") DeliveryOrderForm deliveryOrderForm, BindingResult result, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try {
			doService.edit(FormHelper.update(deliveryOrderForm.getDeliveryOrder(), deliveryOrderForm));
			status.setComplete();

			response.store("id", deliveryOrderForm.getDeliveryOrder().getId());
		} catch (Exception e) {
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}

	@RequestMapping("/deliveryordersent.htm")
	public ModelAndView sent(@RequestParam("id") Long id, SessionStatus status) throws Exception {
		JSONResponse response = new JSONResponse();

		try {
			DeliveryOrder deliveryOrder = doService.sent(id);
			status.setComplete();

			response.store("id", deliveryOrder.getId());
		} catch (Exception e) {
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}
}

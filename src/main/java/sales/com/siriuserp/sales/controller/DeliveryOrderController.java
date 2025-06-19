/**
 * File Name  : DeliveryOrderController.java
 * Created On : Mar 18, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.sales.controller;

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

import com.siriuserp.sales.criteria.DeliveryOrderFilterCriteria;
import com.siriuserp.sales.dm.DeliveryOrder;
import com.siriuserp.sales.dm.DeliveryOrderReferenceItem;
import com.siriuserp.sales.dm.SOStatus;
import com.siriuserp.sales.form.SalesForm;
import com.siriuserp.sales.query.DeliveryOrderAddViewQuery;
import com.siriuserp.sales.query.DeliveryOrderViewQuery;
import com.siriuserp.sales.service.DeliveryOrderService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.PostalAddress;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.view.ViewHelper;
import com.siriuserp.sdk.utility.FormHelper;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = "deliveryOrder_form", types = SalesForm.class)
@DefaultRedirect(url = "deliveryorderview.htm")
public class DeliveryOrderController extends ControllerBase
{
	@Autowired
	private DeliveryOrderService service;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
		binder.registerCustomEditor(Facility.class, modelEditor.forClass(Facility.class));
		binder.registerCustomEditor(Container.class, modelEditor.forClass(Container.class));
		binder.registerCustomEditor(PostalAddress.class, modelEditor.forClass(PostalAddress.class));
		binder.registerCustomEditor(DeliveryOrderReferenceItem.class, modelEditor.forClass(DeliveryOrderReferenceItem.class));
	}

	@RequestMapping("/deliveryorderview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/sales/deliveryOrderList", service.view(criteriaFactory.create(request, DeliveryOrderFilterCriteria.class), DeliveryOrderViewQuery.class));
	}

	@RequestMapping("/deliveryorderpreadd1.htm")
	public ModelAndView preadd1(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/sales/deliveryOrderAdd1", service.preadd1(criteriaFactory.create(request, DeliveryOrderFilterCriteria.class), DeliveryOrderAddViewQuery.class));
	}

	@RequestMapping("/deliveryorderpreadd2.htm")
	public ModelAndView preadd2(@ModelAttribute("deliveryOrder_form") SalesForm form) throws Exception
	{
		return new ModelAndView("/sales/deliveryOrderAdd2", service.preadd2(form));
	}

	@RequestMapping("/deliveryorderadd.htm")
	public ModelAndView add(@ModelAttribute("deliveryOrder_form") SalesForm form, BindingResult result, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.add(FormHelper.create(DeliveryOrder.class, form));
			status.setComplete();

			response.store("id", form.getDeliveryOrder().getId());
		} catch (Exception e)
		{
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}

	@RequestMapping("/deliveryorderpreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id) throws Exception
	{
		return new ModelAndView("/sales/deliveryOrderUpdate", service.preedit(id));
	}

	@RequestMapping("/deliveryorderedit.htm")
	public ModelAndView edit(@ModelAttribute("deliveryOrder_form") SalesForm form, BindingResult result, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.edit(FormHelper.update(form.getDeliveryOrder(), form));
			status.setComplete();

			response.store("id", form.getDeliveryOrder().getId());
		} catch (Exception e)
		{
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}

	@RequestMapping("/deliveryorderdelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id) throws Exception
	{
		service.delete(service.load(id));

		return ViewHelper.redirectTo("deliveryorderview.htm");
	}

	@RequestMapping("/deliveryordersent.htm")
	public ModelAndView sent(@RequestParam("id") Long id, @RequestParam("status") SOStatus soStatus, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.updateStatus(id, soStatus);
			status.setComplete();

			response.store("id", id);
		} catch (Exception e)
		{
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}

	@RequestMapping("/deliveryordercancel.htm")
	public ModelAndView cancle(@RequestParam("id") Long id, @RequestParam("status") SOStatus soStatus, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.updateStatus(id, soStatus);
			status.setComplete();

			response.store("id", id);
		} catch (Exception e)
		{
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}

	@RequestMapping("/deliveryorderprint.htm")
	public ModelAndView print(@RequestParam("id") Long id, @RequestParam("printType") int printType) throws Exception
	{
		if (printType == 1)
			return new ModelAndView("/sales/deliveryOrderPrintTax", service.preedit(id));

		return new ModelAndView("/sales/deliveryOrderPrint", service.preedit(id));
	}

	@RequestMapping("/deliveryorderinvoiceprint.htm")
	public ModelAndView invoicePrint(@RequestParam("id") Long id) throws Exception
	{
		return new ModelAndView("/sales/deliveryOrderInvoicePrint", service.preedit(id));
	}
}

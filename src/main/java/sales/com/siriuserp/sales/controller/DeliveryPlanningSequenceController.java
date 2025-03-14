/**
 * File Name  : DeliveryPlanningSequenceController.java
 * Created On : Mar 6, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.sales.controller;

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
import com.siriuserp.sales.dm.DeliveryPlanning;
import com.siriuserp.sales.dm.DeliveryPlanningSequence;
import com.siriuserp.sales.form.SalesForm;
import com.siriuserp.sales.service.DeliveryPlanningSequenceService;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.PostalAddress;
import com.siriuserp.sdk.dm.Tax;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.ResponseStatus;
import com.siriuserp.sdk.springmvc.view.ViewHelper;
import com.siriuserp.sdk.utility.FormHelper;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = "planning_form", types = SalesForm.class)
public class DeliveryPlanningSequenceController extends ControllerBase
{
	@Autowired
	private DeliveryPlanningSequenceService service;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(Tax.class, modelEditor.forClass(Tax.class));
		binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
		binder.registerCustomEditor(Product.class, modelEditor.forClass(Product.class));
		binder.registerCustomEditor(Facility.class, modelEditor.forClass(Facility.class));
		binder.registerCustomEditor(Container.class, modelEditor.forClass(Container.class));
		binder.registerCustomEditor(PostalAddress.class, modelEditor.forClass(PostalAddress.class));
		binder.registerCustomEditor(DeliveryPlanning.class, modelEditor.forClass(DeliveryPlanning.class));
		binder.registerCustomEditor(DeliveryPlanningSequence.class, modelEditor.forClass(DeliveryPlanningSequence.class));
	}

	@RequestMapping("/deliveryplanningsequencepreadd.htm")
	public ModelAndView preadd(@RequestParam("id") Long planningId) throws Exception
	{
		return new ModelAndView("/sales/deliveryPlanningSequenceAdd", service.preadd(planningId));
	}

	@RequestMapping("/deliveryplanningsequenceadd.htm")
	public ModelAndView add(@ModelAttribute("planning_form") SalesForm form, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			DeliveryPlanningSequence sequence = FormHelper.create(DeliveryPlanningSequence.class, form);

			service.add(sequence);
			response.store("id", sequence.getDeliveryPlanning().getId());
			status.setComplete();
		} catch (Exception e)
		{
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getMessage());
		}

		return response;
	}

	@RequestMapping("/deliveryplanningsequencepreedit.htm")
	public ModelAndView preview(@RequestParam("id") Long id) throws Exception
	{
		return new ModelAndView("/sales/deliveryPlanningSequencePreview", service.preedit(id));
	}

	@RequestMapping("/deliveryplanningsequenceedit.htm")
	public ModelAndView edit(@ModelAttribute("planning_form") SalesForm form, BindingResult result, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.edit(FormHelper.update(form.getDeliveryPlanningSequence(), form));
			status.setComplete();

			response.store("id", form.getDeliveryPlanningSequence().getDeliveryPlanning().getId());
		} catch (Exception e)
		{
			e.printStackTrace();
			response.setStatus(ResponseStatus.ERROR);
			response.setMessage(e.getMessage());
		}

		return response;
	}

	@RequestMapping("/deliveryplanningsequencedelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id, @RequestParam(value = "plan", required = false) String plan) throws Exception
	{
		service.delete(service.load(id));
		if (plan != null)
			return ViewHelper.redirectTo("deliveryplanningpreedit.htm?id=" + plan);

		return ViewHelper.redirectTo("deliveryplanningsequenceview.htm");
	}
}

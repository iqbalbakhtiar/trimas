/**
 * File Name  : DeliveryPlanningController.java
 * Created On : Mar 6, 2025
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

import com.siriuserp.sales.criteria.DeliveryPlanningFilterCriteria;
import com.siriuserp.sales.dm.DeliveryPlanning;
import com.siriuserp.sales.dm.DeliveryPlanningSequence;
import com.siriuserp.sales.form.SalesForm;
import com.siriuserp.sales.query.DeliveryPlanningViewQuery;
import com.siriuserp.sales.service.DeliveryPlanningService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.view.ViewHelper;
import com.siriuserp.sdk.utility.FormHelper;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = "planning_form", types = SalesForm.class)
@DefaultRedirect(url = "deliveryplanningview.htm")
public class DeliveryPlanningController extends ControllerBase
{
	@Autowired
	private DeliveryPlanningService service;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
		binder.registerCustomEditor(DeliveryPlanningSequence.class, modelEditor.forClass(DeliveryPlanningSequence.class));
	}

	@RequestMapping("/deliveryplanningview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/sales/deliveryPlanningList", service.view(criteriaFactory.create(request, DeliveryPlanningFilterCriteria.class), DeliveryPlanningViewQuery.class));
	}

	@RequestMapping("/deliveryplanningpreadd1.htm")
	public ModelAndView preadd1() throws Exception
	{
		return new ModelAndView("/sales/deliveryPlanningAdd1", service.preadd1());
	}

	@RequestMapping("/deliveryplanningpreadd2.htm")
	public ModelAndView preadd2(@RequestParam("id") Long id) throws Exception
	{
		return new ModelAndView("/sales/deliveryPlanningAdd2", service.preadd2(id));
	}

	@RequestMapping("/deliveryplanningadd.htm")
	public ModelAndView add(@ModelAttribute("planning_form") DeliveryPlanning planning, BindingResult result, SessionStatus status) throws Exception
	{
		service.add(planning);
		status.setComplete();

		return ViewHelper.redirectTo("deliveryplanningpreedit.htm?id=" + planning.getId());
	}

	@RequestMapping("/deliveryplanningpreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id) throws Exception
	{
		return new ModelAndView("/sales/deliveryPlanningUpdate", service.preedit(id));
	}

	@RequestMapping("/deliveryplanningedit.htm")
	public ModelAndView edit(@ModelAttribute("planning_form") SalesForm form, BindingResult result, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.edit(FormHelper.update(form.getDeliveryPlanning(), form));
			status.setComplete();

			response.store("id", form.getDeliveryPlanning().getId());
		} catch (Exception e)
		{
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getMessage());
		}

		return response;
	}

	@RequestMapping("/deliveryplanningdelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id) throws Exception
	{
		service.delete(service.load(id));

		return ViewHelper.redirectTo("deliveryplanningview.htm");
	}
}

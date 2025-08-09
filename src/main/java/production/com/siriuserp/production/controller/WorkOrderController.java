/**
 * File Name  : WorkOrderController.java
 * Created On : Jul 30, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.production.controller;

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

import com.siriuserp.inventory.dm.Product;
import com.siriuserp.production.criteria.WorkOrderFilterCriteria;
import com.siriuserp.production.dm.Machine;
import com.siriuserp.production.dm.ProductionStatus;
import com.siriuserp.production.dm.WorkOrder;
import com.siriuserp.production.form.ProductionForm;
import com.siriuserp.production.query.WorkOrderViewQuery;
import com.siriuserp.production.service.WorkOrderService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.Tax;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.view.ViewHelper;
import com.siriuserp.sdk.utility.FormHelper;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = "workOrder_form", types = ProductionForm.class)
@DefaultRedirect(url = "workorderview.htm")
public class WorkOrderController extends ControllerBase
{
	@Autowired
	private WorkOrderService service;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
		binder.registerCustomEditor(Product.class, modelEditor.forClass(Product.class));
		binder.registerCustomEditor(Tax.class, modelEditor.forClass(Tax.class));
		binder.registerCustomEditor(Currency.class, modelEditor.forClass(Currency.class));
		binder.registerCustomEditor(Facility.class, modelEditor.forClass(Facility.class));
		binder.registerCustomEditor(Container.class, modelEditor.forClass(Container.class));
		binder.registerCustomEditor(Machine.class, modelEditor.forClass(Machine.class));
		binder.registerCustomEditor(ProductionStatus.class, enumEditor.forClass(ProductionStatus.class));
	}

	@RequestMapping("/workorderview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/production/workOrderList", service.view(criteriaFactory.create(request, WorkOrderFilterCriteria.class), WorkOrderViewQuery.class));
	}

	@RequestMapping("/workorderpreadd.htm")
	public ModelAndView preadd()
	{
		return new ModelAndView("/production/workOrderAdd", service.preadd());
	}

	@RequestMapping("/workorderadd.htm")
	public ModelAndView add(@ModelAttribute("workOrder_form") ProductionForm productionForm, BindingResult result, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.add(FormHelper.create(WorkOrder.class, productionForm));
			status.setComplete();

			response.store("id", productionForm.getWorkOrder().getId());
		} catch (Exception e)
		{
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}

	@RequestMapping("/workorderpreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id) throws Exception
	{
		return new ModelAndView("/production/workOrderUpdate", service.preedit(id));
	}

	@RequestMapping("/workorderedit.htm")
	public ModelAndView edit(@ModelAttribute("workOrder_form") ProductionForm productionForm, BindingResult result, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.edit(FormHelper.update(productionForm.getWorkOrder(), productionForm));
			status.setComplete();

			response.store("id", productionForm.getWorkOrder().getId());
		} catch (Exception e)
		{
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}

	@RequestMapping("/workorderdelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id) throws Exception
	{
		service.delete(service.load(id));

		return ViewHelper.redirectTo("workorderview.htm");
	}

	@RequestMapping("/workorderfinish.htm")
	public ModelAndView finish(@RequestParam("id") Long id, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.finish(service.load(id));
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

	@RequestMapping("/workorderchangestatus.htm")
	public ModelAndView changeStatus(@RequestParam("id") Long id, @RequestParam("productionStatus") String productionStatus, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.changeStatus(id, productionStatus);
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

	@RequestMapping("/workorderprint.htm")
	public ModelAndView print(@RequestParam("id") Long id, @RequestParam("printType") int printType) throws Exception
	{
		if (printType == 1)
			return new ModelAndView("/production/workOrderPrint", service.preedit(id));

		return new ModelAndView("/production/workOrderPrint", service.preedit(id));
	}
}

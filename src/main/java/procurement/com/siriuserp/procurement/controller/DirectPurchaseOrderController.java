package com.siriuserp.procurement.controller;

import javax.servlet.http.HttpServletRequest;

import com.siriuserp.procurement.dm.POStatus;
import com.siriuserp.sdk.dm.ContactMechanism;
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
import com.siriuserp.procurement.criteria.PurchaseOrderFilterCriteria;
import com.siriuserp.procurement.dm.PurchaseOrder;
import com.siriuserp.procurement.dm.PurchaseOrderItem;
import com.siriuserp.procurement.form.PurchaseForm;
import com.siriuserp.procurement.query.DirectPurchaseOrderGridViewQuery;
import com.siriuserp.procurement.service.DirectPurchaseOrderService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.ApprovalDecisionStatus;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.PostalAddress;
import com.siriuserp.sdk.dm.Tax;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.utility.FormHelper;

@Controller
@SessionAttributes(value = "dpo_form", types = PurchaseForm.class)
@DefaultRedirect(url = "directpurchaseorderview.htm")
public class DirectPurchaseOrderController extends ControllerBase
{
	@Autowired
	private DirectPurchaseOrderService service;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
		binder.registerCustomEditor(Product.class, modelEditor.forClass(Product.class));
		binder.registerCustomEditor(Tax.class, modelEditor.forClass(Tax.class));
		binder.registerCustomEditor(PurchaseOrder.class, modelEditor.forClass(PurchaseOrder.class));
		binder.registerCustomEditor(PurchaseOrderItem.class, modelEditor.forClass(PurchaseOrderItem.class));
		binder.registerCustomEditor(Facility.class, modelEditor.forClass(Facility.class));
		binder.registerCustomEditor(PostalAddress.class, modelEditor.forClass(PostalAddress.class));
		binder.registerCustomEditor(ContactMechanism.class, modelEditor.forClass(ContactMechanism.class));
		binder.registerCustomEditor(ApprovalDecisionStatus.class, enumEditor.forClass(ApprovalDecisionStatus.class));
		binder.registerCustomEditor(POStatus.class, enumEditor.forClass(POStatus.class));
	}

	@RequestMapping("/directpurchaseorderview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/procurement/directPurchaseOrderList", service.view(criteriaFactory.create(request, PurchaseOrderFilterCriteria.class), DirectPurchaseOrderGridViewQuery.class));
	}

	@RequestMapping("/directpurchaseorderpreadd.htm")
	public ModelAndView preadd()
	{
		return new ModelAndView("/procurement/directPurchaseOrderAdd", service.preadd());
	}

	@RequestMapping("/directpurchaseorderadd.htm")
	public ModelAndView add(@ModelAttribute("dpo_form") PurchaseForm form, BindingResult result, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.add(FormHelper.create(PurchaseOrder.class, form));
			status.setComplete();

			response.store("id", form.getPurchaseOrder().getId());
		} catch (Exception e)
		{
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}

	@RequestMapping("/directpurchaseorderpreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id) throws Exception
	{
		return new ModelAndView("/procurement/directPurchaseOrderUpdate", service.preedit(id));
	}

	@RequestMapping("/directpurchaseorderedit.htm")
	public ModelAndView edit(@ModelAttribute("dpo_form") PurchaseForm form, BindingResult result, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.edit(FormHelper.update(form.getPurchaseOrder(), form));
			status.setComplete();

			response.store("id", form.getPurchaseOrder().getId());
		} catch (Exception e)
		{
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}

	@RequestMapping("/directpurchaseorderprint.htm")
	public ModelAndView print(@RequestParam("id") Long id) throws Exception
	{
		return new ModelAndView("/procurement/directPurchaseOrderPrint", service.preedit(id));
	}
}

/**
 * File Name  : StandardPurchaseOrderController.java
 * Created On : Feb 22, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.procurement.controller;

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
import com.siriuserp.procurement.criteria.PurchaseOrderFilterCriteria;
import com.siriuserp.procurement.dm.PurchaseOrder;
import com.siriuserp.procurement.dm.PurchaseOrderItem;
import com.siriuserp.procurement.form.PurchaseForm;
import com.siriuserp.procurement.query.StandardPurchaseOrderViewQuery;
import com.siriuserp.procurement.service.StandardPurchaseOrderService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.ApprovalDecisionStatus;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.PostalAddress;
import com.siriuserp.sdk.dm.Tax;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.view.ViewHelper;
import com.siriuserp.sdk.utility.FormHelper;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = "purchase_form", types = PurchaseForm.class)
@DefaultRedirect(url = "standardpurchaseorderview.htm")
public class StandardPurchaseOrderController extends ControllerBase
{
	@Autowired
	private StandardPurchaseOrderService service;

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
		binder.registerCustomEditor(ApprovalDecisionStatus.class, enumEditor.forClass(ApprovalDecisionStatus.class));
	}

	@RequestMapping("/standardpurchaseorderview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/procurement/standardPurchaseOrderList", service.view(criteriaFactory.create(request, PurchaseOrderFilterCriteria.class), StandardPurchaseOrderViewQuery.class));
	}

	@RequestMapping("/standardpurchaseorderpreadd.htm")
	public ModelAndView preadd2() throws Exception
	{
		return new ModelAndView("/procurement/standardPurchaseOrderAdd", service.preadd());
	}

	@RequestMapping("/standardpurchaseorderadd.htm")
	public ModelAndView add(@ModelAttribute("purchase_form") PurchaseForm form, BindingResult result, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.add(FormHelper.create(PurchaseOrder.class, form));
			status.setComplete();

			response.store("id", form.getPurchaseRequisition().getId());
		} catch (Exception e)
		{
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}

	@RequestMapping("/standardpurchaseorderpreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id) throws Exception
	{
		return new ModelAndView("/procurement/standardPurchaseOrderUpdate", service.preedit(id));
	}

	@RequestMapping("/standardpurchaseorderedit.htm")
	public ModelAndView edit(@ModelAttribute("purchase_form") PurchaseForm form, BindingResult result, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.edit(FormHelper.update(form.getPurchaseOrder(), form));
			status.setComplete();

			response.store("id", form.getPurchaseRequisition().getId());
		} catch (Exception e)
		{
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}

	@RequestMapping("/standardpurchaseorderdelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id) throws ServiceException
	{
		service.delete(service.load(id));

		return ViewHelper.redirectTo("standardpurchaseorderview.htm");
	}
}

/**
 * File Name  : PurchaseOrderController.java
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
import com.siriuserp.procurement.dm.PurchaseDocumentType;
import com.siriuserp.procurement.dm.PurchaseOrder;
import com.siriuserp.procurement.dm.PurchaseOrderItem;
import com.siriuserp.procurement.dm.PurchaseType;
import com.siriuserp.procurement.form.PurchaseForm;
import com.siriuserp.procurement.query.PurchaseOrderViewQuery;
import com.siriuserp.procurement.service.PurchaseOrderService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.ApprovalDecisionStatus;
import com.siriuserp.sdk.dm.ContactMechanism;
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
@DefaultRedirect(url = "purchaseorderview.htm")
public class PurchaseOrderController extends ControllerBase
{
	@Autowired
	private PurchaseOrderService service;

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
		binder.registerCustomEditor(PurchaseType.class, enumEditor.forClass(PurchaseType.class));
		binder.registerCustomEditor(PurchaseDocumentType.class, enumEditor.forClass(PurchaseDocumentType.class));
	}

	@RequestMapping("/purchaseorderview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/procurement/purchaseOrderList", service.view(criteriaFactory.create(request, PurchaseOrderFilterCriteria.class), PurchaseOrderViewQuery.class));
	}

	@RequestMapping("/purchaseorderpreadd.htm")
	public ModelAndView preadd2() throws Exception
	{
		return new ModelAndView("/procurement/purchaseOrderAdd", service.preadd());
	}

	@RequestMapping("/purchaseorderadd.htm")
	public ModelAndView add(@ModelAttribute("purchase_form") PurchaseForm form, BindingResult result, SessionStatus status) throws Exception
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

	@RequestMapping("/purchaseorderpreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id) throws Exception
	{
		return new ModelAndView("/procurement/purchaseOrderUpdate", service.preedit(id));
	}

	@RequestMapping("/purchaseorderedit.htm")
	public ModelAndView edit(@ModelAttribute("purchase_form") PurchaseForm form, BindingResult result, SessionStatus status) throws Exception
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

	@RequestMapping("/purchaseorderdelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id) throws ServiceException
	{
		service.delete(service.load(id));

		return ViewHelper.redirectTo("purchaseorderview.htm");
	}

	@RequestMapping("/purchaseorderprint.htm")
	public ModelAndView print(@RequestParam("id") Long id, @RequestParam("printType") int printType) throws Exception
	{
		if (printType == 1)
			return new ModelAndView("/procurement/purchaseOrderPrint", service.preedit(id));
		else if (printType == 2)
			return new ModelAndView("/procurement/purchaseOrderPrintTax", service.preedit(id));
		else if (printType == 3)
			return new ModelAndView("/procurement/purchaseOrderPrintSparepart", service.preedit(id));

		return new ModelAndView("/procurement/purchaseOrderPrintSparepartTax", service.preedit(id));
	}
}

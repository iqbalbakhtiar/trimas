/**
 * File Name  : PurchaseRequisitionController.java
 * Created On : Feb 20, 2025
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
import com.siriuserp.procurement.criteria.PurchaseRequisitionFilterCriteria;
import com.siriuserp.procurement.dm.PurchaseRequisition;
import com.siriuserp.procurement.dm.PurchaseRequisitionItem;
import com.siriuserp.procurement.form.PurchaseForm;
import com.siriuserp.procurement.query.PurchaseRequisitionViewQuery;
import com.siriuserp.procurement.service.PurchaseRequisitionService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Department;
import com.siriuserp.sdk.dm.Party;
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
@SessionAttributes(value = "requisition_form", types = PurchaseForm.class)
@DefaultRedirect(url = "purchaserequisitionview.htm")
public class PurchaseRequisitionController extends ControllerBase
{
	@Autowired
	private PurchaseRequisitionService service;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
		binder.registerCustomEditor(Product.class, modelEditor.forClass(Product.class));
		binder.registerCustomEditor(Department.class, modelEditor.forClass(Department.class));
	}

	@RequestMapping("/purchaserequisitionview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/procurement/purchaseRequisitionList", service.view(criteriaFactory.create(request, PurchaseRequisitionFilterCriteria.class), PurchaseRequisitionViewQuery.class));
	}

	@RequestMapping("/purchaserequisitionpreadd.htm")
	public ModelAndView preadd() throws Exception
	{
		return new ModelAndView("/procurement/purchaseRequisitionAdd", service.preadd());
	}

	@RequestMapping("/purchaserequisitionadd.htm")
	public ModelAndView add(@ModelAttribute("requisition_form") PurchaseForm form, BindingResult result, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.add(FormHelper.create(PurchaseRequisition.class, form));
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

	@RequestMapping("/purchaserequisitionpreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id) throws Exception
	{
		return new ModelAndView("/procurement/purchaseRequisitionUpdate", service.preedit(id));
	}

	@RequestMapping("/purchaserequisitionedit.htm")
	public ModelAndView edit(@ModelAttribute("requisition_form") PurchaseForm form, BindingResult result, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.edit(FormHelper.update(form.getPurchaseRequisition(), form));
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

	@RequestMapping("/purchaserequisitionlockitem.htm")
	public ModelAndView lockItem(@RequestParam("id") Long id) throws ServiceException
	{
		PurchaseRequisitionItem item = service.loadItem(id);
		service.lockItem(item);

		return ViewHelper.redirectTo("purchaserequisitionpreedit.htm?id=" + item.getPurchaseRequisition().getId());
	}

	@RequestMapping("/purchaserequisitiondelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id) throws ServiceException
	{
		service.delete(service.load(id));

		return ViewHelper.redirectTo("purchaserequisitionview.htm");
	}

	@RequestMapping("/purchaserequisitionprint.htm")
	public ModelAndView print(@RequestParam("id") Long id) throws Exception
	{
		return new ModelAndView("/procurement/purchaseRequisitionPrint", service.preedit(id));
	}
}

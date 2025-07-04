package com.siriuserp.production.controller;

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
import com.siriuserp.production.dm.Machine;
import com.siriuserp.production.dm.ProductionOrderDetail;
import com.siriuserp.production.dm.ProductionOrderDetailMaterialRequest;
import com.siriuserp.production.form.ProductionForm;
import com.siriuserp.production.service.ProductionOrderDetailMaterialRequestService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.ResponseStatus;
import com.siriuserp.sdk.utility.FormHelper;

/**
 * @author ferdinand
 */

@Controller
@SessionAttributes(value = {"request_add", "request_edit"}, types = {ProductionOrderDetailMaterialRequest.class, ProductionForm.class})
@DefaultRedirect(url = "productionorderdetailmaterialrequestpreadd.htm")
public class ProductionOrderDetailMaterialRequestController extends ControllerBase
{
	@Autowired
	private ProductionOrderDetailMaterialRequestService service;
	
	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
		binder.registerCustomEditor(Product.class, modelEditor.forClass(Product.class));
		binder.registerCustomEditor(Machine.class, modelEditor.forClass(Machine.class));
		binder.registerCustomEditor(Facility.class, modelEditor.forClass(Facility.class));
		binder.registerCustomEditor(Container.class, modelEditor.forClass(Container.class));
		binder.registerCustomEditor(ProductionOrderDetail.class, modelEditor.forClass(ProductionOrderDetail.class));
		binder.registerCustomEditor(ProductionOrderDetailMaterialRequest.class, modelEditor.forClass(ProductionOrderDetailMaterialRequest.class));
	}
	
	@RequestMapping("/productionorderdetailmaterialrequestpreadd.htm")
	public ModelAndView preadd(@RequestParam("id") Long id) throws ServiceException
	{
		return new ModelAndView("/production/production-order/productionOrderDetailMaterialRequestAdd", service.preadd(id));
	}
	
	@RequestMapping("/productionorderdetailmaterialrequestadd.htm")
	public ModelAndView add(@ModelAttribute("request_add") ProductionForm form, SessionStatus status) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.add(FormHelper.create(ProductionOrderDetailMaterialRequest.class, form));
			status.setComplete();

			response.store("id", form.getProductionOrderDetailMaterialRequest().getId());
		} 
		catch (Exception e)
		{
			response.setStatus(ResponseStatus.ERROR);
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}
	
	@RequestMapping("/productionorderdetailmaterialrequestpreedit.htm")
	public ModelAndView preedit(Long id) throws ServiceException
	{
		return new ModelAndView("/production/production-order/productionOrderDetailMaterialRequestUpdate", service.preedit(id));
	}
	
	@RequestMapping("/productionorderdetailmaterialrequestedit.htm")
	public ModelAndView edit(@ModelAttribute("request_edit") ProductionOrderDetailMaterialRequest materialRequest, BindingResult result, SessionStatus status) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.edit(materialRequest);
			status.setComplete();

			response.store("id", materialRequest.getId());
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}
}

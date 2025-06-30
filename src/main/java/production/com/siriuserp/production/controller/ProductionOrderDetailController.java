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

import com.siriuserp.production.dm.ProductionOrderDetail;
import com.siriuserp.production.form.ProductionForm;
import com.siriuserp.production.service.ProductionOrderDetailService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.ResponseStatus;
import com.siriuserp.sdk.springmvc.view.ViewHelper;

/**
 * @author ferdinand
 */

@Controller
@SessionAttributes(value = {"detail_add", "detail_edit"}, types = {ProductionOrderDetail.class, ProductionForm.class})
@DefaultRedirect(url = "productionorderdetailpreadd.htm")
public class ProductionOrderDetailController extends ControllerBase
{
	@Autowired
	private ProductionOrderDetailService service;
	
	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(ProductionOrderDetail.class, modelEditor.forClass(ProductionOrderDetail.class));
	}
	
	@RequestMapping("/productionorderdetailpreadd.htm")
	public ModelAndView preadd(@RequestParam("id") Long id) throws ServiceException
	{
		return new ModelAndView("/production/production-order/productionOrderDetailAdd", service.preadd(id));
	}
	
	@RequestMapping("/productionorderdetailadd.htm")
	public ModelAndView add(@ModelAttribute("detail_add") ProductionOrderDetail productionOrderDetail, SessionStatus status) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.add(productionOrderDetail);
			status.setComplete();

			response.store("id", productionOrderDetail.getId());
		} 
		catch (Exception e)
		{
			response.setStatus(ResponseStatus.ERROR);
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}
	
	@RequestMapping("/productionorderdetailpreedit.htm")
	public ModelAndView preedit(Long id) throws ServiceException
	{
		return new ModelAndView("/production/production-order/productionOrderDetailUpdate", service.preedit(id));
	}
	
	@RequestMapping("/productionorderdetailedit.htm")
	public ModelAndView edit(@ModelAttribute("detail_edit") ProductionOrderDetail productionOrderDetail, BindingResult result, SessionStatus status) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.edit(productionOrderDetail);
			status.setComplete();

			response.store("id", productionOrderDetail.getId());
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}
	
	@RequestMapping("/productionorderdetaildelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id) throws ServiceException
	{
		Long poId = service.load(id).getProductionOrder().getId();
		service.delete(service.load(id));

		return ViewHelper.redirectTo("productionorderpreedit.htm?id="+poId);
	}
}

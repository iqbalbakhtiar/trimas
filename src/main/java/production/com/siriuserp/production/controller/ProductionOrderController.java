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

import com.siriuserp.inventory.criteria.ProductionOrderFilterCriteria;
import com.siriuserp.inventory.dm.Product;
import com.siriuserp.inventory.query.ProductionOrderGridViewQuery;
import com.siriuserp.production.dm.CostCenterGroup;
import com.siriuserp.production.dm.CostCenterGroupProduction;
import com.siriuserp.production.dm.MaterialType;
import com.siriuserp.production.dm.ProductionOrder;
import com.siriuserp.production.form.ProductionForm;
import com.siriuserp.production.service.ProductionOrderService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.ResponseStatus;
import com.siriuserp.sdk.springmvc.view.ViewHelper;

/**
 * @author ferdinand
 */

@Controller
@SessionAttributes(value = {"order_add", "order_edit"}, types = ProductionForm.class)
@DefaultRedirect(url = "productionorderview.htm")
public class ProductionOrderController extends ControllerBase
{
	@Autowired
	private ProductionOrderService service;
	
	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
		binder.registerCustomEditor(Product.class, modelEditor.forClass(Product.class));
		binder.registerCustomEditor(CostCenterGroup.class, modelEditor.forClass(CostCenterGroup.class));
		binder.registerCustomEditor(CostCenterGroupProduction.class, modelEditor.forClass(CostCenterGroupProduction.class));
		binder.registerCustomEditor(MaterialType.class, enumEditor.forClass(MaterialType.class));
	}
	
	@RequestMapping("/productionorderview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/production/production-order/productionOrderList", service.view(criteriaFactory.create(request, ProductionOrderFilterCriteria.class), ProductionOrderGridViewQuery.class));
	}

	@RequestMapping("/productionorderpreadd.htm")
	public ModelAndView preadd() throws ServiceException
	{
		return new ModelAndView("/production/production-order/productionOrderAdd", service.preadd());
	}

	@RequestMapping("/productionorderadd.htm")
	public ModelAndView add(@ModelAttribute("order_add") ProductionForm form, SessionStatus status) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.add(form);
			status.setComplete();

			response.store("id", form.getProductionOrder().getId());
		} 
		catch (Exception e)
		{
			response.setStatus(ResponseStatus.ERROR);
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}
	
	@RequestMapping("/productionorderpreedit.htm")
	public ModelAndView preedit(Long id) throws ServiceException
	{
		return new ModelAndView("/production/production-order/productionOrderUpdate", service.preedit(id));
	}
	
	@RequestMapping("/productionorderedit.htm")
	public ModelAndView edit(@ModelAttribute("order_edit") ProductionOrder productionOrder, BindingResult result, SessionStatus status) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.edit(productionOrder);
			status.setComplete();

			response.store("id", productionOrder.getId());
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}
	
	@RequestMapping("/productionorderdelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id) throws ServiceException
	{
		service.delete(service.load(id));

		return ViewHelper.redirectTo("productionorderview.htm");
	}
}

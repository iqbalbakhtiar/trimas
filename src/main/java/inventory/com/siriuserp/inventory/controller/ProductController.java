package com.siriuserp.inventory.controller;

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

import com.siriuserp.inventory.criteria.ProductFilterCriteria;
import com.siriuserp.inventory.dm.Product;
import com.siriuserp.inventory.dm.ProductCategory;
import com.siriuserp.inventory.dm.ProductType;
import com.siriuserp.inventory.dm.UnitOfMeasure;
import com.siriuserp.inventory.form.InventoryForm;
import com.siriuserp.inventory.query.ProductGridViewQuery;
import com.siriuserp.inventory.service.ProductService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.ResponseStatus;
import com.siriuserp.sdk.springmvc.view.ViewHelper;
import com.siriuserp.sdk.utility.FormHelper;

@Controller
@SessionAttributes(value = "product_form", types = InventoryForm.class)
@DefaultRedirect(url = "productview.htm")
public class ProductController extends ControllerBase
{
	@Autowired
	private ProductService service;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(ProductType.class, enumEditor.forClass(ProductType.class));
		binder.registerCustomEditor(ProductCategory.class, modelEditor.forClass(ProductCategory.class));
		binder.registerCustomEditor(UnitOfMeasure.class, modelEditor.forClass(UnitOfMeasure.class));
	}

	@RequestMapping("/productview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/inventory/item-management/productList", service.view(criteriaFactory.create(request, ProductFilterCriteria.class), ProductGridViewQuery.class));
	}

	@RequestMapping("/productpreadd.htm")
	public ModelAndView preadd() throws Exception
	{
		return new ModelAndView("/inventory/item-management/productAdd", service.preadd());
	}

	@RequestMapping("/productadd.htm")
	public ModelAndView add(@ModelAttribute("product_form") InventoryForm form, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.add(FormHelper.create(Product.class, form));
			status.setComplete();

			response.store("id", form.getProduct().getId());
		} catch (Exception e)
		{
			response.setStatus(ResponseStatus.ERROR);
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

	@RequestMapping("/productpreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id) throws Exception
	{
		return new ModelAndView("/inventory/item-management/productUpdate", service.preedit(id));
	}

	@RequestMapping("/productedit.htm")
	public ModelAndView edit(@ModelAttribute("product_form") InventoryForm form, BindingResult result, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.edit(FormHelper.update(form.getProduct(), form));
			status.setComplete();

			response.store("id", form.getProduct().getId());
		} catch (Exception e)
		{
			response.setStatus(ResponseStatus.ERROR);
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

	@RequestMapping("/productdelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id) throws Exception
	{
		service.delete(service.load(id));
		return ViewHelper.redirectTo("productview.htm");
	}

	@RequestMapping("/productshowimage.htm")
	public ModelAndView showimage(@RequestParam("id") Long id) throws ServiceException
	{
		return new ModelAndView("image-shower", "model", service.load(id));
	}

	@RequestMapping("/popupproductview.htm")
	public ModelAndView popup(HttpServletRequest request, @RequestParam(value = "target", required = false) String target) throws Exception
	{
		ModelAndView view = new ModelAndView("/inventory-popup/productPopup");
		view.addAllObjects(service.view(criteriaFactory.createPopup(request, ProductFilterCriteria.class), ProductGridViewQuery.class));
		view.addObject("target", target);

		return view;
	}
}

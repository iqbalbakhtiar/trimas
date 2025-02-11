package com.siriuserp.inventory.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.inventory.criteria.ProductCategoryFilterCriteria;
import com.siriuserp.inventory.dm.ProductCategory;
import com.siriuserp.inventory.dm.ProductCategoryType;
import com.siriuserp.inventory.query.ProductCategoryGridViewQuery;
import com.siriuserp.inventory.service.ProductCategoryService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.ModelReferenceView;
import com.siriuserp.sdk.springmvc.view.ViewHelper;

@Controller
@SessionAttributes(value = { "category_add", "category_edit" }, types = ProductCategory.class)
@DefaultRedirect(url = "productcategoryview.htm")
public class ProductCategoryController extends ControllerBase {
	
	@Autowired
	private ProductCategoryService service;
	
	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(ProductCategoryType.class, enumEditor.forClass(ProductCategoryType.class));
		binder.registerCustomEditor(ProductCategory.class, modelEditor.forClass(ProductCategory.class));
	}
	
	@RequestMapping("/productcategoryview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception {
		return new ModelAndView("/inventory/item-management/productCategoryList", service.view(criteriaFactory.create(request, ProductCategoryFilterCriteria.class), ProductCategoryGridViewQuery.class));
	}
	
	@RequestMapping("/productcategorypreadd.htm")
	public ModelAndView preadd() {
		return new ModelAndView("/inventory/item-management/productCategoryPreAdd", "category_add", new ProductCategory());
	}
	
	@RequestMapping("/productcategoryadd.htm")
	public ModelAndView add(@ModelAttribute("category_add") ProductCategory category, SessionStatus status) throws Exception {
		JSONResponse response = new JSONResponse();

		try {
			service.add(category);
			status.setComplete();
		} catch (Exception e) {
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}
	
	@RequestMapping("/productcategorypreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id)
	{
		return new ModelAndView("/inventory/item-management/productCategoryUpdate", service.preedit(id));
	}
	
	@RequestMapping("/productcategoryedit.htm")
	public ModelAndView edit(@ModelAttribute("category_edit") ProductCategory category, SessionStatus status) throws Exception {
		JSONResponse response = new JSONResponse();

		try
		{
			service.edit(category);
			status.setComplete();
		} catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}
	
	@RequestMapping("/productcategorydelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id) throws Exception {
		service.delete(service.load(id));
		return ViewHelper.redirectTo("productcategoryview.htm");
	}
	
	@RequestMapping("/popupproductcategoryview.htm")
	public ModelAndView viewGroupN(HttpServletRequest request) throws Exception {
		return new ModelReferenceView("/inventory-popup/productCategoryPopup", request.getParameter("ref"), service.view(criteriaFactory.createPopup(request, ProductCategoryFilterCriteria.class), ProductCategoryGridViewQuery.class));
	}
}

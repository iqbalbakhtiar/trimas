/**
 * Mar 31, 2009 2:52:09 PM
 * com.siriuserp.accounting.controller
 * ProductCategoryAccountingShemaController.java
 */
package com.siriuserp.accounting.controller;

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

import com.siriuserp.accounting.dm.GLAccount;
import com.siriuserp.accounting.dm.ProductCategoryAccountingSchema;
import com.siriuserp.accounting.service.ProductCategoryAccountingSchemaService;
import com.siriuserp.inventory.dm.ProductCategory;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.view.ViewHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = "categorySchema", types = ProductCategoryAccountingSchema.class)
@DefaultRedirect(url = "accountingshemaview.htm")
public class ProductCategoryAccountingSchemaController extends ControllerBase
{
	@Autowired
	private ProductCategoryAccountingSchemaService service;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(GLAccount.class, modelEditor.forClass(GLAccount.class));
		binder.registerCustomEditor(ProductCategory.class, modelEditor.forClass(ProductCategory.class));
	}

	@RequestMapping("/productcategoryaccountingschemapreadd.htm")
	public ModelAndView preadd(@RequestParam("id") String id)
	{
		return new ModelAndView("/accounting/productCategoryAccountingSchemaAdd", service.preadd(id));
	}

	@RequestMapping("/productcategoryaccountingschemaadd.htm")
	public ModelAndView add(@ModelAttribute("categorySchema") ProductCategoryAccountingSchema schema, BindingResult result, SessionStatus status) throws ServiceException
	{
		JSONResponse response = new JSONResponse();
		try
		{
			service.add(schema);
			status.setComplete();
			response.store("id", schema.getAccountingSchema().getId());
		} catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}

	@RequestMapping("/productcategoryaccountingschemapreedit.htm")
	public ModelAndView preedit(@RequestParam("id") String id)
	{
		return new ModelAndView("/accounting/productCategoryAccountingSchemaUpdate", service.preedit(id));
	}

	@RequestMapping("/productcategoryaccountingschemaedit.htm")
	public ModelAndView edit(@ModelAttribute("categorySchema") ProductCategoryAccountingSchema schema, BindingResult result, SessionStatus status) throws ServiceException
	{
		service.edit(schema);
		status.setComplete();

		return ViewHelper.redirectTo("accountingschemapreedit.htm?id=" + schema.getAccountingSchema().getId());
	}
}

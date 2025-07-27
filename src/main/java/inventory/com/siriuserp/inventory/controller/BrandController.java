package com.siriuserp.inventory.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.inventory.criteria.ProductFilterCriteria;
import com.siriuserp.inventory.dm.Brand;
import com.siriuserp.inventory.form.InventoryForm;
import com.siriuserp.inventory.query.BrandGridViewQuery;
import com.siriuserp.inventory.service.BrandService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.ResponseStatus;
import com.siriuserp.sdk.springmvc.view.ViewHelper;
import com.siriuserp.sdk.utility.FormHelper;

/**
 * @author ferdinand
 * @author Iqbal Bakhtiar
 */

@Controller
@SessionAttributes(value = "brand_form", types = InventoryForm.class)
@DefaultRedirect(url = "brandview.htm")
public class BrandController extends ControllerBase
{
	@Autowired
	private BrandService service;

	@RequestMapping("/brandview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/inventory/item-management/brandList", service.view(criteriaFactory.create(request, ProductFilterCriteria.class), BrandGridViewQuery.class));
	}

	@RequestMapping("/brandpreadd.htm")
	public ModelAndView preadd() throws Exception
	{
		return new ModelAndView("/inventory/item-management/brandAdd", service.preadd());
	}

	@RequestMapping("/brandadd.htm")
	public ModelAndView add(@ModelAttribute("brand_form") InventoryForm form, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.add(FormHelper.create(Brand.class, form));
			status.setComplete();

			response.store("id", form.getBrand().getId());
		} catch (Exception e)
		{
			response.setStatus(ResponseStatus.ERROR);
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

	@RequestMapping("/brandpreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id) throws Exception
	{
		return new ModelAndView("/inventory/item-management/brandUpdate", service.preedit(id));
	}

	@RequestMapping("/brandedit.htm")
	public ModelAndView edit(@ModelAttribute("brand_form") InventoryForm form, BindingResult result, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.edit(FormHelper.update(form.getBrand(), form));
			status.setComplete();

			response.store("id", form.getBrand().getId());
		} catch (Exception e)
		{
			response.setStatus(ResponseStatus.ERROR);
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

	@RequestMapping("/branddelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id) throws Exception
	{
		service.delete(service.load(id));
		return ViewHelper.redirectTo("brandview.htm");
	}
}

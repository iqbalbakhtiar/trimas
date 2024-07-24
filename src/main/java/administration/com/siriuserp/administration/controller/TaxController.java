/**
 * Apr 24, 2006
 * TaxController.java
 */
package com.siriuserp.administration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.valang.ValangValidator;

import com.siriuserp.administration.service.TaxService;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Tax;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.view.ViewHelper;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = { "tax_add", "tax_edit" }, types = Tax.class)
public class TaxController extends ControllerBase
{
	@Autowired
	@Qualifier("taxValidator")
	private ValangValidator validator;

	@Autowired
	private TaxService taxService;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
	}

	@RequestMapping("taxview.htm")
	public ModelAndView view()
	{
		return new ModelAndView("/administration/taxList", "data", taxService.loadAllTax());
	}

	@RequestMapping("taxpreadd.htm")
	public ModelAndView preadd()
	{
		return new ModelAndView("/administration/taxAdd", taxService.preadd());
	}

	@RequestMapping("taxadd.htm")
	public ModelAndView add(@ModelAttribute("tax_add") Tax tax, BindingResult result) throws ServiceException
	{
		validator.validate(tax, result);
		if (result.hasErrors())
		{
			ModelAndView view = new ModelAndView("/administration/taxAdd");
			view.addAllObjects(result.getModel());

			return view;
		}

		taxService.add(tax);

		return ViewHelper.redirectTo("taxview.htm");
	}

	@RequestMapping("taxpreedit.htm")
	public ModelAndView preedit(@RequestParam("id") String id)
	{
		return new ModelAndView("/administration/taxUpdate", taxService.preedit(id));
	}

	@RequestMapping("taxedit.htm")
	public ModelAndView edit(@ModelAttribute("tax_edit") Tax tax, BindingResult result) throws ServiceException
	{
		validator.validate(tax, result);
		if (result.hasErrors())
		{
			ModelAndView view = new ModelAndView("taxAdd");
			view.addAllObjects(result.getModel());

			return view;
		}

		taxService.edit(tax);

		return ViewHelper.redirectTo("taxview.htm");
	}

	@RequestMapping("taxdelete.htm")
	public ModelAndView delete(@RequestParam("id") String id) throws ServiceException
	{
		if (SiriusValidator.validateParam(id))
			taxService.delete(taxService.load(id));

		return ViewHelper.redirectTo("taxview.htm");
	}
}

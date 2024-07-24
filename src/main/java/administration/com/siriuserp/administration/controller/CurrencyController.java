/**
 * Apr 7, 2006
 * CurrencyController.java
 */
package com.siriuserp.administration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.valang.ValangValidator;

import com.siriuserp.administration.service.CurrencyService;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.view.ViewHelper;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * 
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = { "currency_add", "currency_edit" }, types = Currency.class)
public class CurrencyController
{
	@Autowired
	private CurrencyService currencyService;

	@Autowired
	@Qualifier("currencyValidator")
	private ValangValidator validator;

	public void setValidator(ValangValidator validator)
	{
		this.validator = validator;
	}

	@RequestMapping("/currencyview.htm")
	public ModelAndView view()
	{
		return new ModelAndView("/administration/currencyList", "data", currencyService.loadAllCurrency());
	}

	@RequestMapping("/currencypreadd.htm")
	public ModelAndView preadd()
	{
		ModelAndView view = new ModelAndView("/administration/currencyAdd");
		view.addObject("currency_add", new Currency());

		return view;
	}

	@RequestMapping("/currencyadd.htm")
	public ModelAndView add(@ModelAttribute("currency_add") Currency currency, BindingResult result) throws ServiceException
	{
		validator.validate(currency, result);
		if (result.hasErrors())
			return new ModelAndView("/administration/currencyAdd", result.getModel());

		currencyService.add(currency);
		return ViewHelper.redirectTo("currencyview.htm");
	}

	@RequestMapping("/currencypreedit.htm")
	public ModelAndView preedit(@RequestParam("id") String id)
	{
		return new ModelAndView("/administration/currencyUpdate", currencyService.preedit(id));
	}

	@RequestMapping("/currencyedit.htm")
	public ModelAndView edit(@ModelAttribute("currency_edit") Currency currency, BindingResult result) throws ServiceException
	{
		currencyService.edit(currency);
		return ViewHelper.redirectTo("currencyview.htm");
	}

	@RequestMapping("/currencydelete.htm")
	public ModelAndView delete(@RequestParam("id") String id) throws ServiceException
	{
		if (SiriusValidator.validateParam(id))
			currencyService.delete(currencyService.load(id));

		return ViewHelper.redirectTo("currencyview.htm");
	}
}
